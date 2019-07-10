package day2.todoapp.queries

import day2.todoapp.errors.TodoError
import day2.todoapp.fp.Outcome
import day2.todoapp.fp.flatMap


typealias QueryOutcome = Outcome<TodoError, List<ToDoRow>>


class QueryHandler(val projectionToDo: ProjectionAllItems, val projectionByName: ProjectionByName) :
        (ToDoQuery) -> QueryOutcome {

    override fun invoke(query: ToDoQuery): QueryOutcome =
        when (query) {
            allItems -> projectionToDo.getAll()
            allOpenItems -> projectionToDo.getOpen()
            is singleItemById -> projectionToDo.getItem(query.id)
            is singleItemByName -> projectionByName.getItem(query.name).flatMap {
                projectionToDo.getItem(it.firstOrNull()?.itemId ?: "")
            }
        }
}
