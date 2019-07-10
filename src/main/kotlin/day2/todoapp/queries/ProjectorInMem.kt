package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.events.EventId
import day2.todoapp.events.NO_EVENT
import day2.todoapp.fp.Outcome
import java.util.concurrent.atomic.AtomicLong

class ProjectorInMem<ROW : ProjectionRow<KEY>, KEY> : Projector<ROW, KEY> {
    val lastEventId = AtomicLong(NO_EVENT)

    val list = arrayListOf<ROW>()

    override fun getLastEventId(): EventId = lastEventId.get()

    override fun insert(newRow: ROW) {
        list.add(newRow)
    }

    override fun replace(id: KEY, f: ROW.() -> ROW) {
        list.indexOfFirst { it.key == id }.takeIf { it >= 0 }?.let {
            list[it] = f(list[it])
        }
    }

    override fun getFiltered(pred: (ROW) -> Boolean): Outcome<TodoError, List<ROW>> =
        Outcome.tryThis { list.filter(pred) }.mapFailure { TodoError(it.msg, it.t) }

    override fun updateTo(eventId: EventId, block: Projector<ROW, KEY>.() -> Unit) {

        lastEventId.getAndUpdate {
            if (it < eventId) {
                block()
                eventId
            } else {
                it
            }

        }
    }
}