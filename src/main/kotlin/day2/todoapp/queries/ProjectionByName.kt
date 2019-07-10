package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.events.*
import day2.todoapp.fp.Outcome
import day2.todoapp.fp.exhaustive
import java.util.concurrent.atomic.AtomicLong

class ProjectionByName(val projector: Projector<NameRow, String>, val eventStore: EventStore<ToDoEvent, ItemId, ToDoItem>) : Projection<NameRow, ToDoEvent> {

    val lastEventId = AtomicLong(NO_EVENT)


    override fun updateFrom(orderedEvent: OrderedEvent<ToDoEvent>) {

        if (lastEventId.get() < orderedEvent.id) {
            when (orderedEvent.event) {
                is ToDoItemAdded -> projector.insert(
                    NameRow(
                        key = orderedEvent.event.name,
                        itemId = orderedEvent.event.itemId
                    )
                )
              else -> Unit //nothing to do
            }.exhaustive
            lastEventId.set(orderedEvent.id)
        }

    }

    fun updatedProjector(): Projector<NameRow, String> {
        eventStore.fetchAll(lastEventId.get()).forEach { updateFrom(it) }
        return projector
    }

    fun getItem(name: String): Outcome<TodoError, List<NameRow>> = updatedProjector().getFiltered { it.key == name }


}
