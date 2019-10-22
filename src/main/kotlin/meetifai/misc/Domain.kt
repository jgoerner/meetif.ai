package meetifai.misc

data class Person(
        val id: Number,
        val name: String,
        val location: String,
        val imgURL: String
)

data class Event(
        val name: String
)

data class EventRequestBody(val p1: String, val p2: String)