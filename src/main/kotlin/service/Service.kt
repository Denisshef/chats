package service

import data.Chat
import data.Message
import data.TagsDisplay
import exception.ErrorChat
import exception.ErrorMessage

object Service {
    private var idMessage: Int = 0
    private val chats = mutableListOf<Chat>()
    private val messages = mutableListOf<Message>()

    // Chats
    private fun getAllChats() = chats

    fun createChats(chat: Chat): Chat {
        for (value in chats) {
            if (chat.person.name == value.id) throw ErrorChat("Чат с '${chat.person.name}' уже создан")
        }
        val newChat = chat.copy(id = chat.person.name)
        chats.add(newChat)
        return newChat
    }

    fun deleteChat(chat: Chat): Boolean {
        val iteratorChat = chats.iterator()
        while (iteratorChat.hasNext()) {
            val value = iteratorChat.next()
            if (chat.person.name == value.id) iteratorChat.remove()
        }

        val iteratorMessage = messages.listIterator()
        while (iteratorMessage.hasNext()) {
            val item = iteratorMessage.next()
            if (item.idChats == chat.id) iteratorMessage.remove()
        }
        return true
    }

    private fun getChatId(id: String): Chat {
        for (value in chats) {
            if (id == value.id) return value
        }
        throw ErrorChat("Чата с '${id}' нет")
    }

    // Message
    private fun <E> List<E>.filter(predicate: (E) -> Boolean): List<E> {
        val result = mutableListOf<E>()
        for (i in this) {
            if (predicate(i)) result.add(i)
        }
        return result
    }

    fun createMessage(message: Message): Message {
        var chat: Chat? = null
        for (value in chats) {
            if (value.id == message.person.name) chat = value
        }
        if (chat == null) chat = createChats(Chat(person = message.person))

        chats.remove(chat)
        chats.add(
            chat.copy(
                messageInput = if (message.type == 1) chat.messageInput + 1 else chat.messageInput,
                messageOut = if (message.type == 0) chat.messageOut + 1 else chat.messageOut,
                newMessageInput = if (message.type == 1) chat.newMessageInput + 1 else chat.newMessageInput
            )
        )

        val newMessage = message.copy(
            id = ++idMessage,
            idChats = message.person.name
        )
        messages.add(newMessage)
        return newMessage
    }

    private fun readInputMessage(idChat: String): Boolean {
        val iteratorMessage = messages.listIterator()
        while (iteratorMessage.hasNext()) {
            val item = iteratorMessage.next()
            if (item.idChats == idChat && item.type == 1)
                iteratorMessage.set(item.copy(read = true))
        }

        val iteratorChat = chats.listIterator()
        while (iteratorChat.hasNext()) {
            val chat = iteratorChat.next()
            if (chat.id == idChat)
                iteratorChat.set(chat.copy(newMessageInput = 0))
        }
        return true
    }

    fun deleteOneMessage(message: Message): Boolean {
        if (!messages.contains(message)) throw ErrorMessage("Такого сообщения нет")
        messages.remove(message)
        return true
    }

    // Display information
    fun d(vararg value: TagsDisplay) {
        for (tag in value) {
            when (tag) {
                TagsDisplay.All_CHATS -> {
                    val list = getAllChats()
                    if (list.isEmpty()) {
                        println()
                        println("*** У вас нет активных чатов.")
                        break
                    }
                    println()
                    println("*** Все чаты: ${list.size}")
                    println("-----------------")
                    for (i in list) {
                        println("-> '${i.id}'")
                        println("-----------------")
                    }
                }
                TagsDisplay.GET_CHAT_ID -> {
                    getChatId("chat")
                }
                TagsDisplay.GET_INPUT_MESSAGE -> {
                    val list = chats.filter { it.newMessageInput > 0 }
                    if (list.isEmpty()) {
                        println()
                        println("*** Входящих сообщений нет ))")
                    } else {
                        println()
                        println("*** Не прочитанные чаты: ${list.size}")
                        println("-----------------")
                        for (i in list) {
                            println("-> от '${i.person.name}': ${i.newMessageInput}")
                            for (item in messages) {
                                if (i.id == item.idChats && !item.read && item.type == 1) {
                                    println("---| ${item.text}")
                                }
                            }
                            readInputMessage(i.id)
                            println("-----------------")
                        }
                    }
                }
                TagsDisplay.GET_OUT_MESSAGE -> {
                    val list = chats.filter { it.messageOut > 0 }
                    if (list.isEmpty()) {
                        println()
                        println("*** Отправленных сообщений нет ))")
                    } else {
                        println()
                        println("***  Отправлено сообщений в чаты: ${list.size}")
                        println("-----------------")
                        for (i in list) {
                            println("-> кому '${i.person.name}': ${i.messageOut}")
                            for (item in messages) {
                                if (i.id == item.idChats && !item.read && item.type == 0) {
                                    println("---| ${item.text} |--- не прочитано")
                                }
                            }
                            println("-----------------")
                        }
                    }
                }
            }
        }

    }
}