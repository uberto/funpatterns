package day2.todoapp.errors

data class TodoError(val msg: String, val e: Throwable? = null): Error() {

}
