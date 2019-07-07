package day1.exercise3

data class EchoMachine(
    val writer: (String) -> Console<Unit>,
    val reader: () -> Console<String>
){

    fun echo(){
        val console: Console<String> = reader()
        val message = console.exec()
        writer(message).exec()
    }
}