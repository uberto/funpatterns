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
        val resp = Request(Method.GET, "http://localhost:$port/ping").send()
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
        val resp = getAllItems.send()
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains("[ ]")
        }
    }

    @Test
    fun `add a few items`() {
        val cmd1Resp = createItem("finish the exercise1", "long desc 1 blablabla").send()
        assertThat(cmd1Resp).all {
            hasStatus(CREATED)
            bodyContains("exercise1")
        }

        val cmd2Resp = createItem("finish the exercise2", "long desc 2 blablabla").send()
        assertThat(cmd2Resp).all {
            hasStatus(CREATED)
            bodyContains("exercise2")
        }

        val cmd3Resp = createItem("finish the exercise3", "long desc 3 blablabla").send()
        assertThat(cmd3Resp).all {
            hasStatus(CREATED)
            bodyContains("exercise3")
        }


        val resp = getAllItems.send()
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains("todo_1")
            bodyContains("todo_2")
            bodyContains("todo_3")
        }
    }

    @Test
    fun `complete an item`() {
        createItem("finish the slides", "long desc blablabla").send().expectSuccess()

        val id = "todo_1"
        completeItem(id).send().expectSuccess()

        val resp = getById(id).send()
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains(id)
            bodyContains("COMPLETED")
        }
    }


    @Test
    fun `cancel an item`() {
        createItem("write the example", "long desc blablabla").send().expectSuccess()

        val id = "todo_1"
        cancelItem(id).send().expectSuccess()

        val resp = getById(id).send()
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains(id)
            bodyContains("CANCELLED")
        }
    }

    @Test
    fun `reopen a cancelled item`() {
        createItem("buy eggs", "long desc blablabla").send().expectSuccess()
        createItem("clean kitchen", "long desc blablabla").send().expectSuccess()
        createItem("cook food", "long desc blablabla").send().expectSuccess()

        val id = "todo_2"
        completeItem(id).send().expectSuccess()


        reopenItem(id).send().expectSuccess()

        val resp = getById(id).send()
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains(id)
            bodyContains("OPEN")
        }
    }

    @Test
    fun `get all open returns only open items`() {
        createItem("buy eggs", "long desc blablabla").send().expectSuccess()
        createItem("clean kitchen", "long desc blablabla").send().expectSuccess()
        createItem("cook food", "long desc blablabla").send().expectSuccess()
        createItem("prepare table", "long desc blablabla").send().expectSuccess()

        val beforeJson = getAllOpen.send().expectSuccess().toJson()
        assertThat(beforeJson.size()).isEqualTo(4)

        completeItem("todo_2").send().expectSuccess()
        cancelItem("todo_1").send().expectSuccess()
        completeItem("todo_4").send().expectSuccess()
        reopenItem("todo_4").send().expectSuccess()


        val afterJson = getAllOpen.send().expectSuccess().toJson()
        assertThat(afterJson.size()).isEqualTo(2)
        assertThat(afterJson[0].get("name").asText()).isEqualTo("cook food")
        assertThat(afterJson[1].get("name").asText()).isEqualTo("prepare table")

    }

    @Test
    fun `get item by name`() {
        createItem("buy eggs", "long desc blablabla").send().expectSuccess()
        createItem("clean kitchen", "long desc blablabla").send().expectSuccess()
        createItem("cook food", "long desc blablabla").send().expectSuccess()
        createItem("prepare table", "long desc blablabla").send().expectSuccess()

        val json = getByName("cook food").send().expectSuccess().toJson()
        assertThat(json.size()).isEqualTo(1)
        assertThat(json[0].get("name").asText()).isEqualTo("cook food")
    }

    @Test
    fun `edit an item`() {
        TODO()
    }

    private fun Request.send() : Response = client(this)

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


