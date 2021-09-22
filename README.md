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
- [Refactoring Guru](https://refactoring.guru/)

# Table Of Contents

* [Introduction](#Introduction)
* [Creational Patterns](#Creational Patterns)
  * [Singleton](#Singleton)


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

 
