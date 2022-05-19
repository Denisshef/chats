package data

data class Message(
    val id: Int = 0,
    val idChats: String = "",
    val type: Int, // 0-out, 1-input
    val person: Person,
    val text: String = "Text message",
    val read: Boolean = false
)
