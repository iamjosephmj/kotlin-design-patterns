<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/structures.jpg" />
</p>

# What Are Design Patterns

Design patterns are a way to solve similar problems in a similar ways. So, basically we are not trying to re-invent the
wheel when we are building an application or a piece of code. What we want is to be able to solve certain problems in a
way that we know is successful and scalable which will allow us not to run into issues down the line. When we co-relate
with the android development, there are a lot of areas where you can implement these practices so that we can make the
app more scalable and robust.

You can think of design patterns as a "Standard Terminology" that the developers uses to make everyday life simpler.
There is a method to this madness, meaning that the team in which you are working can understand the code on the go.

Design-patterns can also give you a little heads up on how to solve a problem using the best practices. This Repository
is based on a book from [Gang of Four](http://wiki.c2.com/?GangOfFour) often referred to as GoF, The Bible, The
Foundation book...etc

This repo is targeted to audiences with any level of knowledge in kotlin. If you have not yet started kotlin, here are
some super useful links to get the learning materials that I personally recommend.

- [Kotlin-Depth-Vol-I](https://www.amazon.com/Kotlin-Depth-Vol-I-Comprehensive-Multi-Paradigm/dp/9389328586)
- [Kotlin-depth-Vol-II](https://www.amazon.com/Kotlin-depth-Vol-II-comprehensive-multi-paradigm/dp/9389423228)
- [Kotlin Lang](https://kotlinlang.org/docs/getting-started.html)
- [Kotlin Bootcamp](https://developer.android.com/codelabs/kotlin-bootcamp-introduction)

# Table Of Contents

* [Introduction](#Introduction)
* [Creational-Patterns](#Creational-Patterns)
    * [Singleton](#Singleton)
    * [Factory](#Factory)
    * [Abstract Factory](#Abstract-Factory)
    * [Builder](#Builder)
    * [Lazy Initialization](#Lazy-Initialization)
    * [Prototype](#Prototype)
* [Structural-Patterns](#Structural-Patterns)
    * [Adapter](#Adapter)
    * [Bridge](#Bridge)
    * [Facade](#Facade)
    * [Decorator](#Decorator)
    * [Composite](#Composite)
    * [Proxy](#Proxy)
* [Behavioral-Patterns](#Behavioral-Patterns)
    * [Observer](#Observer)
    * [Chain-of-Responsibility](#Chain-of-Responsibility)
    * [Command](#Command)
    * [Strategy](#Strategy)
    * [State](#State)
    * [Visitor](#Visitor)

## Introduction

There are mainly three types of design patterns.

#### Creational

This pattern gives us a heads-up on how we create the objects on demand. They provide various object creation
mechanisms, which increase flexibility and reuse of existing code.

#### Structural

This pattern gives us an idea on how various objects and components in our application relates to each other. This also
explains how to assemble objects and classes into larger structures while keeping these structures flexible and
efficient. This can also help you achieve some good objectives like separation-of-concerns, scalability,
testability...etc.

#### Behavioural Pattern

This pattern gives more emphasis on how an object function inside your code. They are more concerned with algorithms and
the assignment of responsibilities between objects.

## Creational Patterns

### Singleton

This is a very simple/common design pattern. So, lets assume that you have few components in your application that are
trying to access some external environments/utility like "Network Communication Instance". Instantiating such an
instance on demand is quite an overhead. So, we basically need only "One" instance of such services. This single
instance idea here is pretty memory efficient. Because we don't need an additional space in the memory heap. In other
words this design pattern lets you ensure that a class has only one instance, while providing a global access point to
this instance.

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/singleton.png" />
</p>

Kotlin gives you out of the box support for the singleton implementation using the keyword **object**

refer to [Singleton.kt](https://github.com/iamjosephmj/kotlin-design-patterns/blob/main/src/main/kotlin/Singleton.kt)

```kotlin

object NetworkDriver {
    init {
        println("Initializing: $this")
    }

    fun log(): NetworkDriver = apply { println("Network driver: $this") }
}

```

### Factory

As the name sounds, Factory is something that produces a particular type of product. Taking this in the kotlin
perspective, You can think of factory as a class that can instantiate objects according to the needs of the subclass. In
other words Factory method provides an interface of creating objects in the super class but allows the subclass to alter
the type of objects that will be created.

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/factory.png" />
</p>

refer to [Factory.kt](https://github.com/iamjosephmj/kotlin-design-patterns/blob/main/src/main/kotlin/Factory.kt)

For the sake of explanation, let's take the case of currencies. Ideally, we need to abstract the country from currency.
"Abstract"? yep, you can you interface for this purpose. But as this repos is completely kotlin based we use a special
class named [sealed class](https://kotlinlang.org/docs/sealed-classes.html) for restricting our hierarchy.

```kotlin

sealed class Country

```

what else? we also need children of **Country**:

```kotlin

object Spain : Country()
object Greece : Country()
object USA : Country()
object Poland : Country()
object Canada : Country()

```

basically we write all the implementations inside the parent **sealed class**. This is only done to avoid confusions.

Now, let's take the nature of Factory. Do we need multiple instances of factory? nope. For this example, you only need
one. Yes, I am referring to using [Singleton Pattern](#Singleton) for Factory class.

```kotlin

object CurrencyFactory {
    fun currencyForCountry(country: Country): Currency =
        when (country) {
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Canada -> Currency("CAD")
            is Poland -> Currency("PLN")
        }
}

```

We are now pretty much done with the Factory implementation. Now, let's get to the real bread and butter of this
methodology.

```kotlin

val greekCurrency = CurrencyFactory.currencyForCountry(Greece).code
println("Greek currency: $greekCurrency")

```

### Abstract Factory

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/abstractfactory.png" />
</p>

You can think of abstract factory as kind of another level on the factory method that we had seen above. Let's take the
above-mentioned example, Let's say we have a display/presentation that will be fetching the data from a certain data
source. This Data-source is can be thought of as an interface that will have multiple implementation. There are two key
reasons for doing this:

- The presentation layer should have a clear boundary with the dataSource/Factory layers.
- The only thing that the presentation layer needs is to have a contract to the DataSource-Interface (Dependency Rule).

So, that being said, The presentation layer should get the data according to the request that they are sending. i.e. It
should not care about how the data is created/delivered. Now here comes the **Abstract Factory**. In other words, we can
think of abstract factory as a design pattern as is a creational design pattern that lets you produce families of
related objects without specifying their concrete classes.

Now, lets dive in to the example. We can have the two data sources as Database and Network.

Let's start with the structure of the data source:

```kotlin

interface DataSource

```

now, let's have the two implementations of the DataSource.

```kotlin

class DatabaseDataSource : DataSource

class NetworkDataSource : DataSource

```

Let's start of with a blueprint of the Abstract factory. Fow now I am
using [Abstract class](https://www.geeksforgeeks.org/kotlin-abstract-class/) from kotlin that gives us the
structure/body for factory.

```kotlin

abstract class DataSourceFactory {
    abstract fun makeDataSource(): DataSource
}

```

Now, let's have an implementation for the same:

```kotlin

class NetworkFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()
}

class DatabaseFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}

```

Now here is the core part of the factory method. We need to have
a [static function](https://kotlinlang.org/docs/object-declarations.html)
that can give you the factory that you needed to produce the specific datasource.

```kotlin

companion object {
    inline fun <reified T : DataSource> createFactory(): DataSourceFactory =
        when (T::class) {
            DatabaseDataSource::class -> DatabaseFactory()
            NetworkDataSource::class -> NetworkFactory()
            else -> throw IllegalArgumentException()
        }
}

```

This is the only implementation that we are going to use in the DataSourceFactory class for the abstracted-object
delivery.

please take a look at
the [AbstractFactory.kt](https://github.com/iamjosephmj/kotlin-design-patterns/blob/main/src/main/kotlin/AbstractFactory.kt)
to see the complete code example.

### Builder

Basically a builder is used when we have multiple parameters to initialize. This pattern will save you a lot of
boilerplate code that you will need to write for the constructors. In other words, this pattern lets you construct
complex objects step by step. The pattern allows you to produce different types and representations of an object using
the same construction code.

```kotlin

class Component private constructor(builder: Builder) {
    var param1: String? = null
    var param2: Int? = null

    class Builder {
        private var param1: String? = null
        private var param2: Int? = null

        fun setParam1(param1: String) = apply { this.param1 = param1 }
        fun setParam2(param2: Int) = apply { this.param2 = param2 }
        fun build() = Component(this)

        fun getParam1() = param1
        fun getParam2() = param2
        fun getParam3() = param3
    }

    init {
        param1 = builder.getParam1()
        param2 = builder.getParam2()
    }
}

```

refer to the code
example [Builder.kt](https://github.com/iamjosephmj/kotlin-design-patterns/blob/main/src/main/kotlin/Builder.kt)

### Lazy Initialization

This design patterns is a very common in software development. The whole idea behind the lazy initialization is that,
you will only create data when needed.

Kotlin gives you out of the box support for the lazy initialization.

```kotlin

class Foo {

    val item by lazy { SomeObject() }

}

```

The instantiation of the object only happens when the item is called for the first time.

There is one more way of doing this when we use `var`

```kotlin

class Foo {
    lateinit var item: SomeObject

    fun doSomething() {
        // your code
        // initialization.  
        item = Something()
        // your code
    }
}

```

### Prototype

This design pattern let's copy an existing object without depending on their classes. All we need are interfaces. The
copied object should provide the full functionality of the object that it was cloned. Yes, I am referring to the
cloneable interface. But Again, Kotlin gives you out of the box support for implementing this. You can use this class
named [data](https://kotlinlang.org/docs/data-classes.html) for the same.

```kotlin

data class SomeClass(val item1: Int, val item2: Int)

```

you can clone/copy the same by:

```kotlin

val inst = SomeClass(1, 2)
val inst2 = inst.copy()

```

Here, changing `inst` doesn't impact `inst2`

You can even copy the value even if you had tweaked the variables.

let's consider a code example with mutable variables

```kotlin

data class SomeClass(var item1: Int, val item2: Int)

```

you can clone/copy the same even after tweaking the values:

```kotlin

val inst = SomeClass(1, 2)
inst.item1 = 10

// you will get the latest value.
val inst2 = inst.copy()
```

## Structural-Patterns

### Adapter

If you have experience with Android development, Then you will be pretty familiar with the name Adapter. Basically an
Adapter converts an interface of a class into another interface that client expects. You can think of it as, You have
two different classes `A` and `B` that both have their own interfaces (Kotlin-Interface, methods .etc.) So, like we
said, The adapter pattern converts interface `A` to interface `B`. In other words Adapter pattern gives the class `B` to
adapt and accept the changes that are posted from `A`.

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/adapter.png" />
</p>

Consider that we have a client class that interacts with the Target class. This Target class has an interface ->
that has a method named call(). On the other side, We have a library named Adaptee that has an interface ->
that will have a function named specificCall().

Adapter class helps us in converting the call() to specificCall().

Interfaces required:

```kotlin

interface TargetInterface {
    fun call(limit: Int): List<Int>
}

interface AdapteeInterface {
    fun specificCall(data: List<String>): String
}

interface TargetAdapteeConverter {
    fun convertTargetToAdaptee(limit: Int): List<String>
}

```

Class Implementations

```kotlin

class Target : TargetInterface {
    override fun call(limit: Int): List<Int> = (0..limit).toList()
}

class Adaptee : AdapteeInterface {
    override fun specificCall(data: List<String>): String = data.map { it }.reduce { acc, s -> "$acc$s" }
}

``` 

Adapter Implementation

```kotlin

class Adapter : TargetAdapteeConverter {
    override fun convertTargetToAdaptee(limit: Int): List<String> {
        val target = Target()
        return target.call(limit).map { it.toString() }
    }
}

```

### Bridge

Let's now look into a hypothetical example.

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/bridge.png" />
</p>

In the above example, we have a class named ShapeColor(stores shape and colors). So, we had derived `RedCircle`,
`BlueCircle`, `RedSquare` and `BlueSquare`. We presently have only two features, but what if you wanted to add a new
feature? - You will need to think of making use of inheritance. Consider there are a lot more features to be added in
the future down the line. Then this approach is not going to scale. This is where the Bridge pattern comes into play.

A better solution for the above problem will be to separate each features and connect them in some way.

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/bridge2.png" />
</p>

This is how you structure the classes:

Interfaces

```kotlin

interface Shape {
    fun renderShape(): String
}

interface Color {
    fun renderColorShape(): String
}

```

Shape Implementation

```kotlin

class ShapeImpl(private val shapeName: String) : Shape {
    override fun renderShape(): String = shapeName
}

```

Bridge

```kotlin

class ColorShapeImpl(private val color: String,/* Bridge */ private val shape: Shape) : Color {
    override fun renderColorShape(): String = "$color ${shape.renderShape()}"
}

```

### Facade

This is the most commonly used design pattern, We can think of facade as an interface to a complex functionality. This
will also help us in eliminating the need of designing complex objects and their memory management. In a broader sense,
facade eliminates and simplifies the client side complexity and gives you a simple higher level API.

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/facade.png" />
</p>

Let's consider a simple database implementation

```kotlin


class Database(private val databaseName: String) {

    // ignore the declaration
    private val database by lazy {
        /*
        * consider that we are mocking the database as a hashmap for this example sake.
        */
        hashMapOf<String, String>()
    }

    fun store(key: String, value: String) = apply {
        database[key] = value
    }

    fun read(key: String): String? = database[key]


    fun commit() {
        // mock apply
        print("status of database $databaseName saved successfully!")
    }
}



```

let's now design a User class that can in that is able to pass in values to our repository.

```kotlin

data class User(val userId: String)

```

Now, As the final step we encapsulate all the User-DB interactions inside the repository:

```kotlin

class Repository(private val database: Database) {

    fun storeUser(user: User) {
        database.store(USER_ID, user.userId)
        database.commit()
    }

    fun fetchUser() = database.read(USER_ID)
}

```

### Decorator

This pattern is also called wrapper pattern. The basic idea about this design pattern is that you are going to attach a
new behaviour to an object. But, critically you cannot change the existing code. Hmmm sounds like a Hotfix release
right?... This can be because the existing code is in a different library or we want to preserve some functionality that
already exists but the main idea is that we want to add some more features to it (override).

For instance, let's take a coffee machine example:

Consider we have a CoffeeMachine Interface that is having the blueprint of the functionality of a coffee maker.

```kotlin

interface CoffeeMachine {

    // Functionality 1
    fun getHotWater()

    // Functionality 2
    fun makeCoffee()

}

``` 

Now, One of my friend named Brett bought a Coffee machine CF from a nearby store. Now, This CF is made based on the
blueprint CoffeeMachine.

```kotlin

class CF : CoffeeMachine {

    override fun getHotWater() {
        print("hot water")
    }

    override fun makeCoffee() {
        print("coffee")
    }

}

```

Brett hates plain coffee, He always wanted to make add a Colombian flavour to the coffee. He came across the
CoffeeMachine blueprint. He is exceptionally brilliant, so he plans to create a new machine out of the CF.

```kotlin

class BrettCoffeeMachine(private val machine: CoffeeMachine) : CoffeeMachine by machine {

    // Rest of the functionalities remains the same.

    override fun makeCoffee() {
        print("add Colombian flavour")
        machine.makeCoffee()
    }
}

```

Now, he has the flavor as well as the old coffee machine functionality.

### Composite

This design pattern is applicable to problem that includes a tree structure where each individual component provides its
own functionality. So the key idea about choosing this design pattern is that it works when the core functionality can
be represented as a tree, and we can encapsulate(consider) them as a single entity.

Let's take the example of assembling a PC. Lucia is a friend of mine, She is smart and decides to assemble a Gaming PC.
She now wants to build a system to simulate the PC such a way that each component can give its own price, and when
connecting them together they give the total sum that she has to pay.

let's say, The base component (base of all PC component) name Equipment can be considered as

```kotlin

open class Equipment(
    open val price: Float,
    val name: String
)


```

Each Equipment can be logically bounded in its Composite class - you can call them as composition.

```kotlin

open class Composite(name: String) : Equipment(0, name) {
    // To store the equipments
    private val equipments: ArrayList<Equipment> = arrayListOf()

    // maps each objects and find its sum.

    override val price: Int
        get() {

            val compositePrice = composites.sumOf { composite ->
                composite.price
            }

            return equipments.sumOf { it.price } + compositePrice
        }

    // adds the equipment to the list. 
    fun addEquipment(equipment: Equipment) = apply {
        equipments.add(equipment)
    }

    fun addComponent(composite: Composite) = apply {
        composites.add(composite)
    }
}

```

She has pretty much all the base classes ready. Now, it is time to select each component of her PC from the nearby
store.

```kotlin

// PC that she is building.


class Computer : Composite("Lucia's PC")

class Cabinet : Composite("GAMING CPU")

class Processor : Equipment(1000, "Lucia's Fav processor")

class Ram : Equipment(200, "Lucia's Fav Ram")

class GraphicsCard : Equipment(2000, "Lucia's Fav GPU")

class OtherCpuComponents : Equipment(1000, "Lucia's selected set of components for CPU")

class Others : Composite("Display and other input devices")

class Monitor : Equipment(700, "Lucia's Fav monitor with 144hz refresh rate")

class Keyboard : Equipment(200, "Lucia's Fav keyboard")

class Mouse : Equipment(200, "Lucia's Fav mouse")

class PopToy : Equipment(10, "Lucia's Fav pop toy to keep near her PC")

```

Finally, this is how she stitches them in the UI.

```kotlin
//Main composite
val computer = Computer()

// Sub-composite
val cabinet = Cabinet()
val other = Others()

// Equipments
val processor = Processor()
val ram = Ram()
val graphicsCard = GraphicsCard()
val otherComponent = OtherCpuComponents()

// Equipments
val monitor = Monitor()
val keyboard = Keyboard()
val mouse = Mouse()
val popToy = PopToy()

// attaching equipments to others
other.addEquipment(monitor)
other.addEquipment(keyboard)
other.addEquipment(mouse)
other.addEquipment(popToy)

// attaching equipments to others
cabinet.addEquipment(processor)
cabinet.addEquipment(ram)
cabinet.addEquipment(graphicsCard)
cabinet.addEquipment(otherComponent)


// attaching sub-components to main component.
computer.addComponent(cabinet)
computer.addComponent(other)

```

### Proxy

This design pattern comes into play when the program has a dependency with disk storage. Whenever you have such
dependencies, fetching the files each time from the Disk is not efficient. Instead, we will provide a middle man who can
hold the value temporarily for the course of the runtime(like a proxy). This is very similar to Facade, but the only
difference is that, there will be an additional logic added for proxy mechanism.

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/proxy.png" />
</p>

Let's say, we have an Image structure that can display an Image

```kotlin

interface Image {
    fun display()
}

```

Function to fetch the image from Disk

```kotlin

class RealImage(private val filename: String) : Image {
    override fun display() {
        println("RealImage: Displaying $filename")
    }

    private fun loadFromDisk(filename: String) {
        println("RealImage: Loading $filename")
    }

    init {
        loadFromDisk(filename)
    }
}


```

Proxy

```kotlin

class ProxyImage(private val filename: String) : Image {
    private var realImage: RealImage? = null

    override fun display() {
        println("ProxyImage: Displaying $filename")
        if (realImage == null) {
            realImage = RealImage(filename)
        }
        realImage!!.display()
    }
}

```

## Behavioral-Patterns

### Observer

The observer is quite a common patterns, it allows us to notify a set of subscribers a certain even that have occurred.
So, basically at it core this design pattern describes a subscription mechanism. So, in real life, you can think of it
when you subscribe for a magazine/youtube channel. Everytime something is published to that service, the user gets a
notification. So that is the same idea here. Observer pattern allows the object to be an observer of a service. This
allows us to notify multiple objects simultaneously, and in a broader view, this pattern showcases one-many
relationship.

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/observer.png" />
</p>

This is the general diagram of the observer pattern. Like the other patterns, Observer also work on top of interfaces.
The event-manager has two functionalities. Store the references of the subscribers, and notify the subscribers. Event
generator will make the event manager to push the events to the subscribers.

Let's consider a typical push notification service for the code example:

Basic push event

```kotlin

// Logs the name of the event
data class PushEvent(val eventName: String)

```

Event manager

```kotlin

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

```

Event generator

```kotlin

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

```

Simple notifications services

```kotlin
// sending event via email
class EmailNotificationListener(private val email: String) : EventListener {
    override fun update(eventType: String, pushEvent: PushEvent) {
        println("Email to $email: Someone has performed $eventType operation.")
    }
}

// Logging event to file 
class LogOpenListener(var filename: String) : EventListener {
    override fun update(eventType: String, pushEvent: PushEvent) {
        println("Save to log $filename: Someone has performed $eventType operation.")
    }
}

```

### Chain-of-Responsibility

Basically this pattern implies that there are a chain of so-called `Handlers` (Something that can handle requests in
some way). This is very much useful when you want to solve a problem that has to process certain requests in n-number of
steps to produce the result.

- `Handles` has the authority to pass-on a request without processing them.
- You don't need to start form the first `Handler` in the chain, instead, you can start from any of the `Handlers`
  in the chain.
- `Handlers` has the potential to break the chain and return a specific result.

Let's see an example in android where we apply this design pattern.

Request header creation is a common thing that developers does, let's take that as a typical example for projecting the
use of this behavioural pattern.

Basic structure of the handler chain

```kotlin

interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

```

Implementations of Handler chain:

```kotlin

// Auth Header
class AuthenticationHeader(val token: String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nAuthorization: $token"
            .let { next?.addHeader(it) ?: it }
}

// Content type header
class ContentTypeHeader(val contentType: String, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nContentType: $contentType"
            .let { next?.addHeader(it) ?: it }
}

// Payload header 
class BodyPayloadHeader(val body: String, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\n$body"
            .let { next?.addHeader(it) ?: it }
}

```

Creating chain-of-responsibility

```kotlin

val authenticationHeader = AuthenticationHeader("token")
val contentTypeHeader = ContentTypeHeader("application/json")
val bodyPayloadHeader = BodyPayloadHeader("Body: {\"username\" = \"joseph\"}")

authenticationHeader.next = contentTypeHeader
contentTypeHeader.next = bodyPayloadHeader

val messageWithAuthentication = authenticationHeader.addHeader("Headers with authentication")

val messageWithoutAuthentication = contentTypeHeader.addHeader("Headers without authentication")

```

### Command

Command is a behavioral design pattern that turns a request into a stand-alone object that contains all information
about the request. This transformation lets you pass requests as a method arguments, delay or queue a request’s
execution, and support undoable operations.

In Android, Event-Bus library is a typical example of command pattern.

Let's consider a simple Add-to-cart / checkout scenario.

Let's have a unified interface for command.

```kotlin

interface Command {
    fun execute()
}

```

Add to cart and checkout commands

```kotlin

class OrderAddCommand(val id: Long) : Command {
    override fun execute() {
        println("Adding order with id $id")
    }
}

class OrderCheckoutCommand(val id: Long) : Command {
    override fun execute() {
        println("Paying for order with id $id")
    }
}

```

Class that processes the command

```kotlin

class CommandProcessor {
    private val queue = arrayListOf<Command>()

    fun addToQueue(command: Command): CommandProcessor = apply { queue.add(command) }

    fun processCommands(): CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}

```

Example for firing commands

```kotlin

CommandProcessor()
    .addToQueue(OrderAddCommand(1L))
    .addToQueue(OrderAddCommand(2L))
    .addToQueue(OrderCheckoutCommand(2L))
    .addToQueue(OrderCheckoutCommand(1L))
    .processCommands()

```

### Strategy

Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate
class, and make their objects interchangeable.

Let's consider a simple scenario of printing a certain string in different format:

```kotlin

class Printer(private val stringFormatterStrategy: (String) -> String) {
    fun printString(message: String) {
        println(stringFormatterStrategy(message))
    }
}

```

Now, if you closely take a look at this printer class, you can see that there is a generic strategy (constructor) with
which you can print a given string. Now, Let's formulate our strategies

```kotlin

val lowercaseFormatter = { it: String -> it.toLowerCase() }
val uppercaseFormatter = { it: String -> it.toUpperCase() }

```

Testing our strategy

```kotlin

val inputString = "Test for strategy pattern"

val lowercasePrinter = Printer(lowercaseFormatter)
lowercasePrinter.printString(inputString)

val uppercasePrinter = Printer(uppercaseFormatter)
uppercasePrinter.printString(inputString)

```

### State

State is a behavioral design pattern that lets an object alter its behavior when its internal state changes. 
It appears as if the object changed its class.

You can think of an `ACCESS-TOKEN` store in android as a simple example. The store works in two states, It will deliver 
an access token in when the login is completed, else it will return a state Not-Authorized-Exception. 

If you take a closer look at this scenario, The Characteristics of the Store is actually controlled by state of the 
module. 

Defining States
```kotlin

sealed class AuthorizationState

object Unauthorized : AuthorizationState()

class Authorized(val username: String) : AuthorizationState()

```

Auth store implementation

```kotlin

class AuthorizationPresenter {
    private var state: AuthorizationState = Unauthorized

    val getAccessToken: String
        get() = when (state) {
            is Authorized -> "<Some token>"
            is Unauthorized -> throw IllegalArgumentException()
        }
    fun loginUser(username: String) {
        state = Authorized(username)
    }

    fun logoutUser() {
        state = Unauthorized
    }

}

```

### Visitor

Visitor is a behavioral design pattern that lets you separate algorithms from the objects on which they operate.

Let's consider a typical example of an `Ice-Cream` store.

A store delivers a certain flavour of Ice-Cream `<R>` in 3 different scoop types to a store visitor `StoreVisitor<R>`,
i.e. a visitor that likes `StoreVisitor` who likes `<R>` flavour of the ice-cream.


Scoop types

```kotlin

sealed class Scoop {
    object Small : Scoop()
    object Medium : Scoop()
    object Large : Scoop()
}

```

Store interface

```kotlin

interface Store {
  fun <R> deliver(visitor: StoreVisitor<R>): R
}

```

Different flavours of ice-cream

```kotlin

data class VanillaIceCream(val scoopType: Scoop) : Store {
  override fun <R> deliver(visitor: StoreVisitor<R>): R = visitor.visit()
}

data class BlueBerryIceCream(val scoopType: Scoop) : Store {
  override fun <R> deliver(visitor: StoreVisitor<R>): R = visitor.visit()
}

data class SpanishDelightIceCream(val scoopType: Scoop) : Store {
  override fun <R> deliver(visitor: StoreVisitor<R>): R = visitor.visit()
}

```

Store Visitor example

```kotlin

interface StoreVisitor<out R> {
  fun visit(): R
}

```

Implementation of a store visitors

```kotlin

// VanillaIceCream lover
class StoreVisitorA(private val contract: Long) : StoreVisitor<VanillaIceCream> {
  override fun visit(): VanillaIceCream {
    return when (contract) {
      in 1..5 -> {
        VanillaIceCream(Scoop.Small)
      }
      in 5..10 -> {
        VanillaIceCream(Scoop.Medium)
      }
      else -> {
        VanillaIceCream(Scoop.Large)
      }
    }
  }
}

// BlueBerryIceCream lover
class StoreVisitorB(private val contract: Long) : StoreVisitor<BlueBerryIceCream> {
  override fun visit(): BlueBerryIceCream {
    return when (contract) {
      in 1..7 -> {
        BlueBerryIceCream(Scoop.Small)
      }
      in 7..20 -> {
        BlueBerryIceCream(Scoop.Medium)
      }
      else -> {
        BlueBerryIceCream(Scoop.Large)
      }
    }
  }
}

// SpanishDelightIceCream lover
class StoreVisitorC(private val contract: Long) : StoreVisitor<SpanishDelightIceCream> {
  override fun visit(): SpanishDelightIceCream {
    return when (contract) {
      in 1..2 -> {
        SpanishDelightIceCream(Scoop.Small)
      }
      in 2..7 -> {
        SpanishDelightIceCream(Scoop.Medium)
      }
      else -> {
        SpanishDelightIceCream(Scoop.Large)
      }
    }
  }
}

```

let's see how the store works for the three customers

```kotlin

        val visitorA = StoreVisitorA(5)
        val visitorB = StoreVisitorB(8)
        val visitorC = StoreVisitorC(10)

        assert(visitorA.visit().scoopType == Scoop.Small)
        assert(visitorB.visit().scoopType == Scoop.Medium)
        assert(visitorC.visit().scoopType == Scoop.Large)

```
