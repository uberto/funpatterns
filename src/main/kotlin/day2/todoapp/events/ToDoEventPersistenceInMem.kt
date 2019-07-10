package day2.todoapp.events

import java.time.Clock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write


class ToDoEventPersistenceInMem(private val clock: Clock = Clock.systemUTC()): EventPersistence<ToDoEvent, ItemId>{

    private val lock = ReentrantReadWriteLock()
    private val events = mutableListOf<OrderedEvent<ToDoEvent>>()

    override fun store(event: ToDoEvent): OrderedEvent<ToDoEvent> =
        lock.write {
            val eventId = events.size + 1L
            val event = OrderedEvent(eventId, clock.instant(), event)
            events.add(event)
            event
        }


    override fun filterByKey(key: ItemId): List<OrderedEvent<ToDoEvent>> =
        lock.read {
            events.filter { it.event.itemId == key }
        }

    override fun getFrom(from: EventId): Sequence<OrderedEvent<ToDoEvent>> =
        lock.read {
            events.filter { it.id > from }.asSequence()
        }
}