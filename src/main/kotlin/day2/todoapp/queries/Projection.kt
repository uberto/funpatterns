package day2.todoapp.queries

import day2.todoapp.events.OrderedEvent

interface Projection<ROW, EVENT> {

    fun updateFrom(event: OrderedEvent<EVENT>)

}


