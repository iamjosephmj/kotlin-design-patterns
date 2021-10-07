import main.kotlin.DataSourceFactory
import main.kotlin.DatabaseDataSource
import org.junit.Assert
import org.junit.Test

class AbstractFactoryTest {
    @Test
    fun test() {
        val datasourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource = datasourceFactory.makeDataSource()
        println("Created datasource $dataSource")

        Assert.assertTrue(dataSource is DatabaseDataSource)
    }
}