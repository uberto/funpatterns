package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.fp.Outcome


typealias QueryOutcome = Outcome<TodoError, List<ToDoRow>>


class QueryHandler(val projectionToDo: ProjectionAllItems): (ToDoQuery) -> QueryOutcome{

    override fun invoke(query: ToDoQuery): QueryOutcome =
        when(query) {
            allItems -> projectionToDo.getAll()
            allOpenItems -> projectionToDo.getOpen()
            is AnItem -> projectionToDo.getItem(query.id)
        }

}