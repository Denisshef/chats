import data.Chat
import data.Message
import data.Person
import data.TagsDisplay
import service.Service

fun main() {
    val person = Person("Vasya")
    val person2 = Person("Nastya")
    val person3 = Person("Denis")

    Service.createChats(Chat(person = person3))
    Service.createChats(Chat(person = person))
    Service.createChats(Chat(person = person2))

    Service.createMessage(Message(text = "Привет как дела?", type = 1, person = person))
    Service.createMessage(Message(text = "Я дома", type = 1, person = person2))
    Service.createMessage(Message(text = "Хочу пиццу :(", type = 1, person = person2))
    Service.createMessage(Message(text = "Ok", type = 0, person = person2))
    Service.createMessage(Message(text = "4 сыра пойдет?", type = 0, person = person2))

    Service.d(
        TagsDisplay.All_CHATS,
        //TagsDisplay.GET_CHAT_ID,
        TagsDisplay.GET_INPUT_MESSAGE,
        TagsDisplay.GET_OUT_MESSAGE,
        TagsDisplay.GET_INPUT_MESSAGE
    )
}