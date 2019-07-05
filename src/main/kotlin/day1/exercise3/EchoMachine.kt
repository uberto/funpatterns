package day1.exercise3

data class EchoMachine(
    val writer: (String) -> Console<Unit>,
    val reader: () -> Console<String>
) {

    operator fun invoke() {
        val msg: String = reader().exec()

        writer(msg).exec()
    }
}
