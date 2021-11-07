package main.kotlin

// Logs the name of the event
data class PushEvent(val eventName: String)

// general interface for communication.
interface EventListener {
    fun update(eventType: String, pushEvent: PushEvent)
}


class EventManager(vararg operations: String) {
    // Storage
    var listeners = hashMapOf<String, ArrayList<EventListener>>()

    init {
        for (operation in operations) {
            listeners[operation] = ArrayList()
        }
    }

    // Subscription.
    fun subscribe(eventType: String, listener: EventListener) {
        val users = listeners[eventType]
        users?.add(listener)
    }

    // removal of subscription.
    fun unsubscribe(eventType: String, listener: EventListener) {
        val users = listeners[eventType]
        users?.remove(listener)
    }

    // Pushing events to subscribers.
    fun notify(eventType: String, file: PushEvent) {
        val users = listeners[eventType]
        users?.let {
            for (listener in it) {
                listener.update(eventType, file)
            }
        }
    }
}

class EventGenerator {
    var events = EventManager("a", "b")
        private set

    private lateinit var pushEvent: PushEvent

    fun generateEventA(filePath: String) {
        pushEvent = PushEvent(filePath)
        events.notify("a", pushEvent)
    }

    fun generateEventB() {
        pushEvent.let {
            events.notify("b", pushEvent)
        }
    }
}

class EmailNotificationListener(private val email: String) : EventListener {
    override fun update(eventType: String, pushEvent: PushEvent) {
        println("Email to $email: Someone has performed $eventType operation.")
    }
}

class LogOpenListener(var filename: String) : EventListener {
    override fun update(eventType: String, pushEvent: PushEvent) {
        println("Save to log $filename: Someone has performed $eventType operation.")
    }
}