@file:JvmName("Main")

package sparkKotlinTest

import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.*
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions.sum
import org.apache.spark.sql.functions.callUDF
import sparkKotlinTest.model.DummyDataClass
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.api.java.UDF1


fun main(args: Array<String>){
    val spark = SparkSession
            .builder()
            .appName("Build a DataFrame from Scratch")
            .master("local[*]")
            .orCreate

    val sparkContext = JavaSparkContext(spark.sparkContext())


    val testList = listOf(
            DummyDataClass(1, 2, "first"),
            DummyDataClass(2, 3, "second"),
            DummyDataClass(3, null, "third")
    )

    val df3 = spark.createDataFrame(
            testList,
            DummyDataClass::class.java
    )

    df3.show()

    val listOfIntegers = IntRange(1, 100).toList()

    val rowRDDOfIntegers = sparkContext.parallelize(listOfIntegers).map { row: Int -> RowFactory.create(row) }


    val df4 = spark.createDataFrame(
            rowRDDOfIntegers,
            DataTypes.createStructType(
                    arrayOf(
                            DataTypes.createStructField("number", DataTypes.IntegerType, false)
                    )
            )
    )

    df4.show(100)

    df4.select(sum("number")).show()

    val higherThan50 = UDF1<Int, Boolean>{ i: Int -> i > 50 }

    spark.udf().register("higherThan50", higherThan50,  DataTypes.BooleanType)

    df4.select(callUDF("higherThan50", df4.col("number"))).show(100)
}