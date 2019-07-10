package day2.todoapp.events


interface EventStore<EVENT, KEY, ENTITY> {
    fun EVENT.store(): OrderedEvent<EVENT>
    fun KEY.fetch(): ENTITY
    fun fetchAll(afterEvent: EventId): Sequence<OrderedEvent<EVENT>>
}



