package day2.todoapp.commands

import day2.todoapp.events.ItemId


sealed class ToDoCommand

data class AddToDoItem(val name: String, val desc: String): ToDoCommand()

data class EditToDoItem(val id: ItemId, val name: String, val desc: String): ToDoCommand()

data class CompleteToDoItem(val id: ItemId): ToDoCommand()

data class CancelToDoItem(val id: ItemId): ToDoCommand()

data class ReOpenToDoItem(val id: ItemId): ToDoCommand()


