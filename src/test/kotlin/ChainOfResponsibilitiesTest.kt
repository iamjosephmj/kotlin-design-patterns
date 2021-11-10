import main.kotlin.AuthenticationHeader
import main.kotlin.BodyPayloadHeader
import main.kotlin.ContentTypeHeader
import org.junit.Test

class ChainOfResponsibilitiesTest {
    @Test
    fun testChainOfResponsibility() {
        val authenticationHeader = AuthenticationHeader("token")
        val contentTypeHeader = ContentTypeHeader("application/json")
        val bodyPayloadHeader = BodyPayloadHeader("Body: {\"username\" = \"joseph\"}")

        authenticationHeader.next = contentTypeHeader
        contentTypeHeader.next = bodyPayloadHeader

        val messageWithAuthentication = authenticationHeader.addHeader("Headers with authentication")

        val messageWithoutAuthentication = contentTypeHeader.addHeader("Headers without authentication")

        assert(
            messageWithAuthentication == """
                    Headers with authentication
                    Authorization: token
                    ContentType: application/json
                    Body: {"username" = "joseph"}
                """.trimIndent()
        )

        assert(
            messageWithoutAuthentication == """
                    Headers without authentication
                    ContentType: application/json
                    Body: {"username" = "joseph"}
                """.trimIndent()
        )
    }
}