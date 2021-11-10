package main.kotlin

interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

class AuthenticationHeader(val token: String?, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nAuthorization: $token"
            .let { next?.addHeader(it) ?: it }
}

class ContentTypeHeader(val contentType: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nContentType: $contentType"
            .let { next?.addHeader(it) ?: it }
}

class BodyPayloadHeader(val body: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\n$body"
            .let { next?.addHeader(it) ?: it }
}