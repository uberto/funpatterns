package day2.todoapp.queries

import day2.todoapp.events.EventStore
import day2.todoapp.events.OrderedEvent

class BaseProjection<ROW: ProjectionRow<KEY>, KEY, EVENT>(
    val projector: Projector<ROW, KEY>,
    val eventStore: EventStore<EVENT, KEY, *>,
    val updateFrom: (OrderedEvent<EVENT>, Projector<ROW, KEY>) -> Unit
) {

    fun updatedProjector(): Projector<ROW, KEY> {
        eventStore.fetchAll(projector.getLastEventId()).forEach { projector.updateTo(it.id) { updateFrom(it, this) }  }
        return projector
    }

}


