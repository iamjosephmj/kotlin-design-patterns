package main.kotlin

const val USER_ID = "USER_ID"

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


data class User(val userId: String)

// Facade
class Repository(private val database: Database) {

    fun storeUser(user: User) {
        database.store(USER_ID, user.userId)
        database.commit()
    }

    fun fetchUser() = database.read(USER_ID)
}

