package service

import data.Chat
import data.Message
import data.Person
import data.TagsDisplay
import exception.ErrorChat
import exception.ErrorMessage
import org.junit.Test

import org.junit.Assert.*

class ServiceTest {

    @Test
    fun createChatsTrue() {
        // arrange
        val person = Person("User_2022")
        val chat = Chat(person = person)
        // act
        val result = Service.createChats(chat)
        // assert
        assertTrue(result.id == person.name)
    }

    @Test(expected = ErrorChat::class)
    fun createChatsThrow() {
        // arrange
        val person = Person("User_2022")
        val chat = Chat(person = person)
        // act
        Service.createChats(chat)
        Service.createChats(chat)
        // assert
    }

    @Test
    fun deleteChatsTrue() {
        // arrange
        val person = Person("User_2022")
        val chat = Service.createChats(Chat(person = person))
        val message = Service.createMessage(
            Message(
                type = 1,
                person = person
            )
        )

        // act
        val result = Service.deleteChat(chat)
        // assert
        assertTrue(result)
    }

    @Test
    fun createMessage() {
        // arrange
        val person = Person("user_user76")
        val message = Message(
            type = 1,
            person = person
        )
        // act
        val result = Service.createMessage(message)
        // assert
        assertTrue(result.idChats == person.name)
    }

    @Test
    fun deleteMessageTrue() {
        // arrange
        val person = Person("user_user76")
        val message = Service.createMessage(
            Message(
                type = 1,
                person = person
            )
        )
        // act
        val result = Service.deleteOneMessage(message)
        // assert
        assertTrue(result)
    }

    @Test(expected = ErrorMessage::class)
    fun deleteMessageError() {
        // arrange
        val person = Person("user_user76")
        val message = Message(
            type = 1,
            person = person
        )
        // act
        val result = Service.deleteOneMessage(message)
    }

    @Test
    fun display() {
        // arrange
        val person = Person("user_user76")
        val message = Service.createMessage(
            Message(
                type = 0,
                person = person
            )
        )
        // act
        Service.d(
            TagsDisplay.All_CHATS,
            //TagsDisplay.GET_CHAT_ID,
            TagsDisplay.GET_INPUT_MESSAGE,
            TagsDisplay.GET_OUT_MESSAGE,
            TagsDisplay.GET_INPUT_MESSAGE
        )
        // assert
        assertSame(Unit, Unit)
    }

    @Test(expected = ErrorChat::class)
    fun displayGetChatId() {
        // act
        Service.d(
            TagsDisplay.GET_CHAT_ID,
        )
    }
}