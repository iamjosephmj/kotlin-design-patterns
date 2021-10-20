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