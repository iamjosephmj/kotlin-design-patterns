<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/structures.jpg" />
</p>


# What Are Design Patterns

Design patterns are a way to solve similar problems in a similar ways. So, basically we are not trying to re-invent the 
wheel when we are building an application or a piece of code. What we want is to be able to solve certain problems in a 
way that we know is successful and scalable which will allow us not to run into issues down the line. When we co-relate 
with the android development, there are a lot of areas where you can implement these practices so that we can make the app 
more scalable and robust.

You can think of design patterns as a "Standard Terminology" that the developers uses to make everyday life simpler. There 
is a method to this madness, meaning that the team in which you are working can understand the code on the go.

Design-patterns can also give you a little heads up on how to solve a problem using the best practices. This Repository 
is based on a book from [Gang of Four](http://wiki.c2.com/?GangOfFour) often referred to as GoF, The Bible, The Foundation book...etc 

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


## Introduction

There are mainly three types of design patterns.
#### Creational
This pattern gives us a heads-up on how we create the objects on demand. They provide various object creation mechanisms, which 
increase flexibility and reuse of existing code.

#### Structural 
This pattern gives us an idea on how various objects and components in our application relates to each other. This also 
explains how to assemble objects and classes into larger structures while keeping these structures flexible and efficient.
This can also help you achieve some good objectives like separation-of-concerns, scalability, testability...etc.

#### Behavioural Pattern
This pattern gives more emphasis on how an object function inside your code. They are more concerned  with algorithms 
and the assignment of responsibilities between objects. 

## Creational Patterns

### Singleton

This is a very simple/common design pattern. So, lets assume that you have few components in your application that are 
trying to access some external environments/utility like "Network Communication Instance". Instantiating the such an 
instance on demand is quite an overhead. So, we basically need only "One" instance of such services. This single instance 
idea here is pretty memory efficient. Because we don't need an additional space in the memory heap. In other words this 
design pattern lets you ensure that a class has only one instance, while providing a global access point to this instance. 

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

As the name sounds, Factory is something that a particular type of product. Taking this in the kotlin perspective, 
You can think of factory as a class that can instantiate objects according to the needs of the subclass. In other words 
Factory method provides an interface of creating objects in the super class but allows the subclass to alter the type 
of objects that will be created. 

<p align="center">
  <img src="https://github.com/iamjosephmj/kotlin-design-patters/blob/main/media/factory.png" />
</p>

refer to [Factory.kt](https://github.com/iamjosephmj/kotlin-design-patterns/blob/main/src/main/kotlin/Factory.kt)

For the sake of explanation, let's take the case of currencies. Ideally, we need to abstract the country from currency. 
"Abstract"? yep, we can you interface for this purpose. But as this repos is completely kotlin based we use a special 
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

You can think of abstract factory as kind of another level on the factory method that we had seen above.
Let's take the above-mentioned example, Let's say we have a display/presentation that will be fetching the data from 
a certain data source. This Data-source is can be thought of as an interface that will have multiple implementation. 
There are two key reasons for doing this:

- The presentation layer should have a clear boundary with the dataSource/Factory layers.
- The only guy that presentation need to have a contract is the DataSource-Interface (Dependency rule).

So, Being that said, The presentation layer should get the data according to the request that they are sending. i.e. It 
should not care about how the data is created/delivered. Now here comes the **Abstract Factory**. In other words, we can
think of abstract factory as a design pattern as  is a creational design pattern that lets you produce families of 
related objects without specifying their concrete classes.

Now, lets dive in to the example. We can have the two data sources as Database and Network.

Let's start with the structure of the data source:

```kotlin

interface DataSource

```

now, let's have the two implementations of the DataSource.

```kotlin

class DatabaseDataSource: DataSource

class NetworkDataSource: DataSource

```

Let's start of with a blueprint of the Abstract factory. Fow now I am using [Abstract class](https://www.geeksforgeeks.org/kotlin-abstract-class/) from kotlin that gives us 
the structure/body for factory.

```kotlin

abstract class DataSourceFactory {
    abstract fun makeDataSource(): DataSource
}

```

Now, let's have an implementation for the same:

```kotlin

class NetworkFactory: DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()
}

class DatabaseFactory: DataSourceFactory() {
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}

```

Now here is the core part of the factory method. We need to have a [static function](https://kotlinlang.org/docs/object-declarations.html) 
that can give you the factory that you needed to produce the specific datasource.

```kotlin

 companion object {
        inline fun <reified T: DataSource> createFactory(): DataSourceFactory =
                when(T::class) {
                    DatabaseDataSource::class -> DatabaseFactory()
                    NetworkDataSource::class -> NetworkFactory()
                    else -> throw IllegalArgumentException()
                }
    }

```

This is the only implementation that we are going to use in the DataSourceFactory class for the abstracted-object 
delivery.

please take a look at the [AbstractFactory](https://github.com/iamjosephmj/kotlin-design-patterns/blob/main/src/main/kotlin/AbstractFactory.kt) 
to see the complete code example.