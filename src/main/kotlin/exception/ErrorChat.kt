package exception

class ErrorChat(
    override val message: String
) : Exception(message)