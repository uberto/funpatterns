package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.events.*
import day2.todoapp.fp.Outcome
import day2.todoapp.fp.exhaustive


class ProjectionAllItems(
    projector: Projector<ToDoRow, ItemId>,
    eventStore: EventStore<ToDoEvent, ItemId, ToDoItem>
) {

        fun updateFrom(orderedEvent: OrderedEvent<ToDoEvent>, projector: Projector<ToDoRow, ItemId>) {

            with (projector) {
                when (orderedEvent.event) {
                    is ToDoItemAdded -> insert(
                        ToDoRow(
                            key = orderedEvent.event.itemId,
                            state = RowState.OPEN,
                            name = orderedEvent.event.name,
                            desc = orderedEvent.event.description,
                            lastUpdated = orderedEvent.recorded
                        )
                    )
                    is ToDoItemCompleted -> replace(orderedEvent.event.itemId) {
                        copy(
                            state = RowState.COMPLETED,
                            lastUpdated = orderedEvent.recorded
                        )
                    }
                    is ToDoItemCancelled -> replace(orderedEvent.event.itemId) {
                        copy(
                            state = RowState.CANCELLED,
                            lastUpdated = orderedEvent.recorded
                        )
                    }
                    is ToDoItemReOpened -> replace(orderedEvent.event.itemId) {
                        copy(
                            state = RowState.OPEN,
                            lastUpdated = orderedEvent.recorded
                        )
                    }
                }.exhaustive
            }
        }

    val baseProjection = BaseProjection(projector, eventStore, ::updateFrom)


    fun getAll(): Outcome<TodoError, List<ToDoRow>> = baseProjection.updatedProjector().getFiltered { true }

    fun getOpen(): Outcome<TodoError, List<ToDoRow>> = baseProjection.updatedProjector().getFiltered { it.state == RowState.OPEN }

    fun getItem(id: ItemId): Outcome<TodoError, List<ToDoRow>> = baseProjection.updatedProjector().getFiltered { it.key == id }

}

