package day2.todoapp.commands

import day2.todoapp.errors.TodoError
import day2.todoapp.events.*
import day2.todoapp.fp.Failure
import day2.todoapp.fp.Outcome
import day2.todoapp.fp.Success
import java.util.concurrent.atomic.AtomicInteger

typealias CommandOutcome = Outcome<TodoError, OrderedEvent<ToDoEvent>>


class CommandHandler(private val eventStore: ToDoEventStore) : (ToDoCommand) -> CommandOutcome {

    val nextItemId = AtomicInteger(0)

    override fun invoke(cmd: ToDoCommand): CommandOutcome =

        eventStore.run {
            when (cmd) {
                is AddToDoItem -> {
                    val id = "todo_" + nextItemId.incrementAndGet()
                    Success(ToDoItemAdded(id, cmd.name, cmd.desc).store() )
                }
                is CompleteToDoItem -> {
                    val toDoItem = cmd.id.fetch()
                    Success(ToDoItemCompleted(cmd.id).store())
                }

                else -> Failure(TodoError("Not implemented handler for $cmd"))

            }
        }

}