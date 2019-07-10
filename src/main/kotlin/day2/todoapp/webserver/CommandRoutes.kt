package day2.todoapp.webserver

import com.fasterxml.jackson.databind.JsonNode
import day2.todoapp.commands.*
import day2.todoapp.events.ItemId
import day2.todoapp.fp.andThen
import day2.todoapp.fp.fold
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.format.Jackson
import org.http4k.routing.bind
import org.http4k.routing.path

data class CommandRoutes(val handler: CommandHandler) {


    operator fun invoke() = listOf(

        "/todos/add" bind Method.POST to { it.toAdd() execute ::toResponse },

        "/todos/{id}/complete" bind Method.PUT to { it.toComplete() execute ::toResponse },

        "/todos/{id}/cancel" bind Method.PUT to { it.toCancel() execute ::toResponse },

        "/todos/{id}/reopen" bind Method.PUT to { it.toReopen() execute ::toResponse },

        "/todos/{id}/edit" bind Method.PUT to { it.toEdit() execute ::toResponse }

    )


    infix fun <T> ToDoCommand.execute(transf: (CommandOutcome) -> T): T = (handler::invoke andThen transf)(this)

}

val Request.id: ItemId
    get() = this.path("id").orEmpty()


private fun Request.toAdd(): AddToDoItem = toJsonObj(bodyString()).let {
    AddToDoItem(
        name = it["name"].asText(),
        desc = it["description"].asText()
    )
}

private fun toJsonObj(jsonStr: String): JsonNode = Jackson{jsonStr.asJsonObject()}

private fun Request.toComplete(): CompleteToDoItem = CompleteToDoItem(id)

private fun Request.toCancel(): CancelToDoItem = CancelToDoItem(id)

private fun Request.toReopen(): ReOpenToDoItem = ReOpenToDoItem(id)

private fun Request.toEdit(): EditToDoItem = TODO("request to edit non implemented")


fun toResponse(outcome: CommandOutcome): Response = outcome.fold(
    { Response(BAD_REQUEST).body(it.toString()) },
    { Response(CREATED).body(it.toString()) }
)
