package day2.todoapp


import day2.todoapp.application.TodoApp

fun main() {

    TodoApp.createWebServer (9000).start()

}