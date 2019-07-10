package day2.todoapp.queries

import day2.todoapp.events.ItemId
import java.time.Instant

data class ToDoRow (
    override val key: ItemId,
    val state: RowState,
    val name: String,
    val desc: String,
    val lastUpdated: Instant
): ProjectionRow<ItemId>

enum class RowState { OPEN, COMPLETED, CANCELLED }
