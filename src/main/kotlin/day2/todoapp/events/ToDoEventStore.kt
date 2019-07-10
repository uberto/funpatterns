package day2.todoapp.events

class ToDoEventStore(private val persistence: EventPersistence<ToDoEvent, ItemId>) :
    EventStore<ToDoEvent, ItemId, ToDoItem> {

    override fun ToDoEvent.store(): OrderedEvent<ToDoEvent> = persistence.store(this)

    override fun ItemId.fetch(): ToDoItem =
        persistence.filterByKey(this).map { it.event }.fold()


    override fun fetchAll(afterEvent: EventId): Sequence<OrderedEvent<ToDoEvent>> = persistence.getFrom(afterEvent)
}
