package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.events.ItemId
import day2.todoapp.fp.Outcome

class ToDoRowProjectorInMem : Projector<ToDoRow, ItemId> {

    val list = arrayListOf<ToDoRow>()

    override fun insert(newRow: ToDoRow) {
        list.add(newRow)
    }

    override fun replace(id: ItemId, f: ToDoRow.() -> ToDoRow) {
        list.indexOfFirst { it.key == id }.takeIf { it >= 0 }?.let {
            list[it] = f(list[it])
        }
    }

    override fun getFiltered(pred: (ToDoRow) -> Boolean): Outcome<TodoError, List<ToDoRow>> =
        Outcome.tryThis { list.filter(pred) }.mapFailure { TodoError(it.msg, it.t) }

}