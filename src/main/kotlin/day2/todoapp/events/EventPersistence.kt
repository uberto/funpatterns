package day2.todoapp.events

interface EventPersistence<EVENT, KEY> {
    fun store(event: EVENT): OrderedEvent<EVENT>
    fun filterByKey(key: KEY): List<OrderedEvent<EVENT>>
    fun getFrom(from: EventId): Sequence<OrderedEvent<EVENT>>

}
