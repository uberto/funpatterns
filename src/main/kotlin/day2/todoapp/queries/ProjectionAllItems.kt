package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.events.*
import day2.todoapp.fp.Outcome
import day2.todoapp.fp.exhaustive
import java.util.concurrent.atomic.AtomicLong

val NO_EVENT: Long = -1

class ProjectionAllItems(val projector: Projector<ToDoRow, ItemId>, val eventStore: EventStore<ToDoEvent, ItemId, ToDoItem>) : Projection<ToDoRow, ToDoEvent>{

    val lastEventId = AtomicLong(NO_EVENT)

    override fun updateFrom(orderedEvent: OrderedEvent<ToDoEvent>) {

        if (lastEventId.get() < orderedEvent.id) {
            when (orderedEvent.event) {
                is ToDoItemAdded -> projector.insert(
                    ToDoRow(
                        key = orderedEvent.event.itemId,
                        state = RowState.OPEN,
                        name = orderedEvent.event.name,
                        desc = orderedEvent.event.description,
                        lastUpdated = orderedEvent.recorded
                    )
                )
                is ToDoItemCompleted -> projector.replace(orderedEvent.event.itemId) {
                    copy(
                        state = RowState.COMPLETED,
                        lastUpdated = orderedEvent.recorded
                    )
                }
                is ToDoItemCancelled -> projector.replace(orderedEvent.event.itemId) {
                    copy(
                        state = RowState.CANCELLED,
                        lastUpdated = orderedEvent.recorded
                    )
                }
                is ToDoItemReOpened -> projector.replace(orderedEvent.event.itemId) {
                    copy(
                        state = RowState.OPEN,
                        lastUpdated = orderedEvent.recorded
                    )
                }
            }.exhaustive
            lastEventId.set(orderedEvent.id)
        }

    }

    fun updatedProjector():Projector<ToDoRow, ItemId> {
        eventStore.fetchAll(lastEventId.get()).forEach { updateFrom(it) }
        return projector
    }




fun getAll(): Outcome<TodoError, List<ToDoRow>> = updatedProjector().getFiltered { true }

fun getOpen(): Outcome<TodoError, List<ToDoRow>> = updatedProjector().getFiltered { it.state == RowState.OPEN }

fun getItem(id: ItemId): Outcome<TodoError, List<ToDoRow>> = updatedProjector().getFiltered { it.key == id }

}

