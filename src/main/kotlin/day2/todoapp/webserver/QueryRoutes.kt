package day2.todoapp.webserver

import day2.todoapp.fp.andThen
import day2.todoapp.fp.fold
import day2.todoapp.queries.*
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson
import org.http4k.routing.bind
import org.http4k.routing.path

data class QueryRoutes(val handler: QueryHandler) {

    operator fun invoke() = listOf(

        "/ping" bind Method.GET to
                { _: Request -> Response(Status.OK) },

        "/todos/" bind Method.GET to
                { _: Request -> allItems execute ::toResponse },

        "/todos/open" bind Method.GET to
                { _: Request -> allOpenItems execute ::toResponse },

        "/todos/closed" bind Method.GET to
                { _: Request -> Response(Status.INTERNAL_SERVER_ERROR).body("not implemented query closed") },

        "/todos/name/{name}" bind Method.GET to
                { req: Request -> req.toByName() execute ::toResponse },

        "/todos/{itemId}" bind Method.GET to
                { req -> req.toById() execute ::toResponse }
    )

    infix fun <T> ToDoQuery.execute(transf: (QueryOutcome) -> T): T = (handler::invoke andThen transf)(this)

}


fun Request.toById() = singleItemById(this.id)

fun Request.toByName() = singleItemByName(this.path("name").orEmpty())

fun toResponse(outcome: QueryOutcome): Response = outcome.fold(
    { Response(BAD_REQUEST).body(it.toString()) },
    { Response(OK).body(it.toJsonString()) }
)

val json = Jackson

private fun List<ToDoRow>.toJsonString(): String =

    json.pretty(
        json.array(
            this.map {
                json.obj(
                    "key" to json.string(it.key),
                    "state" to json.string(it.state.name),
                    "name" to json.string(it.name),
                    "desc" to json.string(it.desc),
                    "last updated" to json.string(it.lastUpdated.toString())
                )
            })
    )


