package day2.todoapp

import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.support.expected
import assertk.assertions.support.show
import day2.todoapp.application.TodoApp
import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
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

    fun getAnItem(id: String) = Request(Method.GET, "http://localhost:$port/todos/$id")

    fun createItem(name: String, desc: String) = Request(Method.POST, "http://localhost:$port/todos/add").body(
        """{"name": "$name", "description": "$desc" }"""
    )

    fun completeItem(id: String) = Request(Method.PUT, "http://localhost:$port/todos/$id/complete")

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
        client(createItem("finish the slides", "long desc blablabla"))
            .apply { assertThat(this).isOk() }

        val id = "todo_1"
        client(completeItem(id))
            .apply { assertThat(this).isOk() }

        val resp = client(getAnItem(id))
        assertThat(resp).all {
            hasStatus(OK)
            bodyContains(id)
            bodyContains("COMPLETED")
        }
    }


    @Test
    fun `cancel an item`() {
        TODO()
    }

    @Test
    fun `reopen a cancelled item`() {
        TODO()
    }

    @Test
    fun `edit an item`() {
        TODO()
    }

}

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


