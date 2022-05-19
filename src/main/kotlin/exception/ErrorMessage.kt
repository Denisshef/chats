package exception

class ErrorMessage(
    override val message: String
) : Exception(message)
