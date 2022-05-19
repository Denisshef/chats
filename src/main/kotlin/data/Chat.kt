package data

data class Chat(
    val id: String = "", // Person name
    val person: Person,
    val messageInput: Int = 0,
    val messageOut: Int = 0,
    val newMessageInput: Int = 0
){
    override fun toString(): String {
        return "Чат с '${person.name}'"
    }
}