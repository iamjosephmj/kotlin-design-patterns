package main.kotlin


interface DataSource

class DatabaseDataSource : DataSource

class NetworkDataSource : DataSource

abstract class DataSourceFactory {
    abstract fun makeDataSource(): DataSource

    companion object {
        inline fun <reified T : DataSource> createFactory(): DataSourceFactory =
            when (T::class) {
                DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}

class NetworkFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()
}

class DatabaseFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}