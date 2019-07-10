package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.fp.Outcome

interface Projector<ROW: ProjectionRow<KEY>, KEY> {
    fun insert(newRow: ROW)

    fun replace(id: KEY, f: ROW.() -> ROW)

    fun getFiltered(pred: (ROW) -> Boolean): Outcome<TodoError, List<ROW>>


}
