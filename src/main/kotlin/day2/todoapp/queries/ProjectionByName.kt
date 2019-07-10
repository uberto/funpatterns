package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.events.*
import day2.todoapp.fp.Outcome
import day2.todoapp.fp.exhaustive

class ProjectionByName(
    projector: Projector<NameRow, String>,
    eventStore: EventStore<ToDoEvent, ItemId, ToDoItem>
) {

    fun updateFrom(orderedEvent: OrderedEvent<ToDoEvent>, projector: Projector<NameRow, String>) {
        with(projector) {
            when (orderedEvent.event) {
                is ToDoItemAdded -> insert(
                    NameRow(
                        key = orderedEvent.event.name,
                        itemId = orderedEvent.event.itemId
                    )
                )
                else -> Unit //nothing to do
            }.exhaustive
        }
    }

    val baseProjection = BaseProjection(projector, eventStore, ::updateFrom)

    fun getItem(name: String): Outcome<TodoError, List<NameRow>> = baseProjection.updatedProjector().getFiltered { it.key == name }


}
