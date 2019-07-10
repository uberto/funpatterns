package day2.todoapp.events

import java.time.Instant


typealias ItemId = String
typealias EventId = Long

val NO_EVENT: EventId = -1

sealed class ToDoEvent {
   abstract val itemId: ItemId
}

data class ToDoItemAdded(override val itemId: ItemId, val name: String, val description: String): ToDoEvent()
data class ToDoItemCompleted(override val itemId: ItemId): ToDoEvent()
data class ToDoItemCancelled(override val itemId: ItemId): ToDoEvent()
data class ToDoItemReOpened(override val itemId: ItemId): ToDoEvent()



data class OrderedEvent<T>(
   val id: EventId,
   val recorded: Instant,
   val event: T
)


fun List<ToDoEvent>.fold(): ToDoItem = fold(EmptyItem, ::composeEvents)

fun composeEvents(item: ToDoItem, event: ToDoEvent): ToDoItem =
   when(item){
      EmptyItem -> when (event) {
         is ToDoItemAdded -> OpenToDoItem(event.itemId, event.name, event.description)
         else -> item
      }
      is OpenToDoItem -> when (event) {
         is ToDoItemCompleted -> CompletedToDoItem(event.itemId, item.name, item.description)
         is ToDoItemCancelled -> CancelledToDoItem(event.itemId)
         else -> item
      }
      is CompletedToDoItem -> when (event) {
         is ToDoItemReOpened -> OpenToDoItem(event.itemId, item.name, item.description)
         else -> item
      }
      is CancelledToDoItem -> item
   }
