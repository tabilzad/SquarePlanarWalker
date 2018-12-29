package com.montecarlo

import com.montecarlo.domain.Grid
import com.montecarlo.domain.Result
import com.montecarlo.lattice.Lattice
import kotlinx.coroutines.*
import org.magicwerk.brownies.collections.helper.BigLists
import org.nield.kotlinstatistics.standardDeviation
import java.util.*
import kotlin.system.measureTimeMillis

class Simulation(
        val threads: Int,
        val probability: Double,
        val iters: Int,
        val lattice: Lattice
) {
    //var gList = Collections.synchronizedList(ArrayList<Int>(1_000_100_000))
    fun start() {
        var gList = IntArray(1)
        val time = measureTimeMillis {
            gList = runBlocking {
                (1..threads).map { i ->
                    GlobalScope.async(Dispatchers.IO) {
                        val g: Grid? = Grid(lattice, iters, probability)
                        g!!.run_sim_3D()
                        g.list
                    }
                }.awaitAll().reduce { acc, ints ->
                    acc.plus(ints)
                }
            }
        }
        display(gList, time / 1000.0)
      //  gList = null
    }

    private fun Result.writeToDB() {
        val url = "jdbc:sqlite:C:/sqlite/data2.sql"
        java.sql.DriverManager.getConnection(url).let { db ->
            db.prepareStatement("CREATE TABLE IF NOT EXISTS Data ( " +
                    " ID integer PRIMARY KEY AUTOINCREMENT," +
                    " lattice text NOT NULL," +
                    " samples integer NOT NULL," +
                    " walk_length real NOT NULL," +
                    " error text real NULL," +
                    " conf_interval real NOT NULL," +
                    " time real NOT NULL," +
                    " s real NOT NULL," +
                    " timestamp text" +
                    " type text" +
                    " );").execute()
            db.prepareStatement("INSERT INTO Data(lattice, samples, walk_length, error, conf_interval, time, s, timestamp, type)" +
                    " VALUES (\"$lattice\", \"$samples\", \"$walk_length\", \"$error\"," +
                    " \"$conf_interval\", \"$time\", \"$mortality\", \"${java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)}\", \"$type\")").executeUpdate()
            println("Written to DB")
        }
    }


    fun display(list: IntArray, time: Double) {

        println("Calculating averages...")
        val mean = list.average()
        println("Walk Length: $mean")
        //val error = collection.standardDeviation() / Math.sqrt(list.size.toDouble())
        val error = list.findDeviation(mean) / Math.sqrt(list.size.toDouble())
        println("Error: $error%")
        println("Percentage: ${error*100}%")
        println("(+/-) " + error * 1.96)
        println("Samples:" + list.size)
        println("Probability:$probability")
        println("Lattice:$lattice")
        println("Time: " + time
                + " seconds")
        Result(
                lattice = "$lattice",
                samples = list.size.toString(),
                walk_length = mean.toString(),
                error = (error * 100).toBigDecimal().toPlainString().take(5),
                conf_interval = "${error * 1.96}",
                time = time.toString(),
                mortality = probability,
                type = "STOPPER"
        ).writeToDB()
        println("-------------------------------")
    }

    fun mean2(list: List<Int>): Double {
        var avg = 0.0
        var t = 1
        for (x in list) {
            avg += (x - avg) / t
            ++t
        }
        return avg
    }

    fun findDeviation(nums: List<Int>, mean: Double): Double {
        var squareSum = 0.0
        nums.indices.forEach { i -> squareSum += Math.pow(nums[i] - mean, 2.0) }
        return Math.sqrt(squareSum / (nums.size - 1))
    }

    fun IntArray.findDeviation(mean: Double): Double {
       // var squareSum = 0.0
        val squareSum = this.fold(0.0) { acc, i ->
            acc + Math.pow(i-mean, 2.0)
        }
        return Math.sqrt(squareSum / (this.size - 1))
    }

}