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
                    when (toDoItem){
                        is OpenToDoItem ->  Success(ToDoItemCompleted(cmd.id).store())
                        else -> Failure(TodoError("Item cannot be completed $toDoItem"))
                    }
                }
                is CancelToDoItem -> {
                    val toDoItem = cmd.id.fetch()
                    when (toDoItem){
                        is OpenToDoItem ->  Success(ToDoItemCancelled(cmd.id).store())
                        else -> Failure(TodoError("Item cannot be cancelled $toDoItem"))
                    }
                }
                is ReOpenToDoItem -> {
                    val item = cmd.id.fetch()
                    when (item){
                        is CompletedToDoItem -> Success(ToDoItemReOpened(cmd.id).store())
                        else -> Failure(TodoError("Item cannot be reopened $item"))
                    }
                }

                else -> Failure(TodoError("Not implemented handler for $cmd"))

            }
        }

}