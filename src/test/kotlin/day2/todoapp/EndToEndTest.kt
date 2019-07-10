package day2.todoapp

import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.support.expected
import assertk.assertions.support.show
import com.fasterxml.jackson.databind.JsonNode
import day2.todoapp.application.TodoApp
import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EndToEndTest {

    private val port = 9001
    private val client = OkHttp()
    private val server = TodoApp.createWebServer(port)

    @BeforeEach
    fun setup() {
        server.start()
    }

    @AfterEach
    fun teardown() {
        server.stop()
    }

    @Test
    fun `responds to ping`() {
        val resp = client(Request(Method.GET, "http://localhost:$port/ping"))
        assertThat(resp).hasStatus(OK)
    }

    val getAllItems = Request(Method.GET, "http://localhost:$port/todos/")
    val getAllOpen = Request(Method.GET, "http://localhost:$port/todos/open")

    fun getById(id: String) = Request(Method.GET, "http://localhost:$port/todos/$id")
    fun getByName(name: String) = Request(Method.GET, "http://localhost:$port/todos/name/$name")

    fun createItem(name: String, desc: String) = Request(Method.POST, "http://localhost:$port/todos/add").body(
        """{"name": "$name", "description": "$desc" }"""
    )

    fun completeItem(id: String) = Request(Method.PUT, "http://localhost:$port/todos/$id/complete")
    fun cancelItem(id: String) = Request(Method.PUT, "http://localhost:$port/todos/$id/cancel")
    fun reopenItem(id: String) = Request(Method.PUT, "http://localhost:$port/todos/$id/reopen")

    @Test
    fun `at start the todo list is empty`() {
        val resp = client(getAllItems)
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains("[ ]")
        }
    }

    @Test
    fun `add a few items`() {
        val cmd1Resp = client(createItem("finish the exercise1", "long desc 1 blablabla"))
        assertThat(cmd1Resp).all {
            hasStatus(CREATED)
            bodyContains("exercise1")
        }

        val cmd2Resp = client(createItem("finish the exercise2", "long desc 2 blablabla"))
        assertThat(cmd2Resp).all {
            hasStatus(CREATED)
            bodyContains("exercise2")
        }

        val cmd3Resp = client(createItem("finish the exercise3", "long desc 3 blablabla"))
        assertThat(cmd3Resp).all {
            hasStatus(CREATED)
            bodyContains("exercise3")
        }


        val resp = client(getAllItems)
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains("todo_1")
            bodyContains("todo_2")
            bodyContains("todo_3")
        }
    }

    @Test
    fun `complete an item`() {
        client(createItem("finish the slides", "long desc blablabla")).expectSuccess()

        val id = "todo_1"
        client(completeItem(id)).expectSuccess()

        val resp = client(getById(id))
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains(id)
            bodyContains("COMPLETED")
        }
    }


    @Test
    fun `cancel an item`() {
        client(createItem("write the example", "long desc blablabla")).expectSuccess()

        val id = "todo_1"
        client(cancelItem(id)).expectSuccess()

        val resp = client(getById(id))
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains(id)
            bodyContains("CANCELLED")
        }
    }

    @Test
    fun `reopen a cancelled item`() {
        client(createItem("buy eggs", "long desc blablabla")).expectSuccess()
        client(createItem("clean kitchen", "long desc blablabla")).expectSuccess()
        client(createItem("cook food", "long desc blablabla")).expectSuccess()

        val id = "todo_2"
        client(completeItem(id)).expectSuccess()


        client(reopenItem(id)).expectSuccess()

        val resp = client(getById(id))
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains(id)
            bodyContains("OPEN")
        }
    }

    @Test
    fun `get all open returns only open items`() {
        client(createItem("buy eggs", "long desc blablabla")).expectSuccess()
        client(createItem("clean kitchen", "long desc blablabla")).expectSuccess()
        client(createItem("cook food", "long desc blablabla")).expectSuccess()
        client(createItem("prepare table", "long desc blablabla")).expectSuccess()

        val beforeJson = client(getAllOpen).expectSuccess().toJson()
        assertThat(beforeJson.size()).isEqualTo(4)

        client(completeItem("todo_2")).expectSuccess()
        client(cancelItem("todo_1")).expectSuccess()
        client(completeItem("todo_4")).expectSuccess()
        client(reopenItem("todo_4")).expectSuccess()


        val afterJson = client(getAllOpen).expectSuccess().toJson()
        assertThat(afterJson.size()).isEqualTo(2)
        assertThat(afterJson[0].get("name").asText()).isEqualTo("cook food")
        assertThat(afterJson[1].get("name").asText()).isEqualTo("prepare table")

    }

    @Test
    fun `get item by name`() {
        client(createItem("buy eggs", "long desc blablabla")).expectSuccess()
        client(createItem("clean kitchen", "long desc blablabla")).expectSuccess()
        client(createItem("cook food", "long desc blablabla")).expectSuccess()
        client(createItem("prepare table", "long desc blablabla")).expectSuccess()

        val json = client(getByName("cook food")).expectSuccess().toJson()
        assertThat(json.size()).isEqualTo(1)
        assertThat(json[0].get("name").asText()).isEqualTo("cook food")
    }

    @Test
    fun `edit an item`() {
        TODO()
    }

    private fun Response.expectSuccess() : Response =
       apply{ assertThat(this).isOk()}


}

private fun Response.toJson(): JsonNode =
    Jackson{ this@toJson.bodyString().asJsonObject() }


fun Assert<Response>.isOk() =
    given {
        if (it.status.successful) return
        expected("to be successful but was:${show(it)}")
    }


fun Assert<Response>.hasStatus(expected: Status)  =
    given {
        if (it.status == expected) return
        expected("to have status equal to $expected but was:${show(it.status)}")
    }

fun Assert<Response>.bodyContains(expected: String)  =
    given {
        if ( it.bodyString().contains(expected)) return
        expected("to have body string containing $expected but was:${show(it.bodyString())}")
    }


