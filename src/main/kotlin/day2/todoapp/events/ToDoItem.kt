package day2.todoapp.events

sealed class ToDoItem(){
    abstract val id: ItemId
}

object EmptyItem: ToDoItem() {
    override val id: ItemId= "not implemented"
}

data class OpenToDoItem(override val id: ItemId, val name: String, val description: String): ToDoItem()
data class CompletedToDoItem(override val id: ItemId, val name: String, val description: String): ToDoItem()
data class CancelledToDoItem(override val id: ItemId): ToDoItem()