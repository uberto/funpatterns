package day2.todoapp.application

import day2.todoapp.commands.CommandHandler
import day2.todoapp.events.ToDoEventPersistenceInMem
import day2.todoapp.events.ToDoEventStore
import day2.todoapp.queries.ProjectionAllItems
import day2.todoapp.queries.QueryHandler
import day2.todoapp.queries.ToDoRowProjectorInMem
import day2.todoapp.webserver.ToDoHandler
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer

object TodoApp {

    fun createWebServer(port: Int): Http4kServer
        {
            val eventStore = ToDoEventStore(ToDoEventPersistenceInMem())

            return ToDoHandler(
                CommandHandler(eventStore),
                QueryHandler(ProjectionAllItems( ToDoRowProjectorInMem(), eventStore))
            ).asServer(Jetty(port))
        }


}
