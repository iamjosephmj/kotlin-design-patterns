import main.kotlin.ChatUser
import main.kotlin.Mediator
import org.junit.Test

class MediatorTest {

    @Test
    fun mediatorTest(){
        val mediator = Mediator()
        val shinaz = ChatUser(mediator, "shinaz")
        val sethu = ChatUser(mediator, "sethu")
        val nikhil = ChatUser(mediator, "nikhil")

        mediator.addUser(shinaz)
            .addUser(sethu)
            .addUser(nikhil)

        nikhil.send("Hi everyone!")
    }
}