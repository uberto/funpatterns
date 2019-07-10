package day2.todoapp.queries

import day2.todoapp.events.ItemId

data class NameRow(override val key: String, val itemId: ItemId): ProjectionRow<String>