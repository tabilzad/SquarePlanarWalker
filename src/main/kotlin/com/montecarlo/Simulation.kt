package com.montecarlo

import com.montecarlo.domain.Particle2D
import com.montecarlo.domain.Result
import com.montecarlo.domain.WallGrid
import com.montecarlo.lattice.Lattice
import com.montecarlo.lattice.WallFactory
import com.montecarlo.lattice.WallType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.nield.kotlinstatistics.descriptiveStatistics
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import kotlin.system.measureTimeMillis

class Simulation(
        val threads: Int,
        val probability: Double,
        val iters: Int,
        val lattice: Lattice,
        val wallType: WallType
) {

    fun start(trap: Particle2D?=null) {
        val gList = IntArray(iters * threads)
        var idx = 0
        val offset = (lattice.centerPoint - 6) / 2
        val time = measureTimeMillis {
            runBlocking {
                (1..threads).map { i ->
                    GlobalScope.async(Dispatchers.IO) {
                        WallGrid(
                                lattice = lattice,
                                Iterations = iters,
                                wall = WallFactory.makeWall2D(wallType, 0),
                                pb = probability
                        ).run {
                            run_sim2D_2Walker()
                            list
                        }
//                        Grid(
//                                lattice = lattice,
//                                Iterations = iters,
//                                trap = Particle2D(4,4)
//                        ).run {
//                            run_sim2D_OneWalker()
//                            list
//                        }
                    }
                }.forEach {
                    val res = it.await()
                    res.copyInto(
                            destination = gList,
                            destinationOffset = idx)
                    idx += res.size
                }
            }
        }
        display(gList, time / 1000.0, trap)
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
                    " timestamp text," +
                    " type text" +
                    " );").execute()
            db.prepareStatement("INSERT INTO Data(lattice, samples, walk_length, error, conf_interval, time, s, timestamp, type)" +
                    " VALUES (\"$lattice\", \"$samples\", \"$walk_length\", \"$error\"," +
                    " \"$conf_interval\", \"$time\", \"$mortality\", \"${now().format(ISO_LOCAL_DATE)}\", \"$type\")").executeUpdate()
            println("Written to DB")
            db.close()
        }
    }


    fun display(list: IntArray, time: Double, trap: Particle2D?) {

        println("Calculating averages...")
        val mean = list.average()
        System.gc()
        println("Walk Length: $mean")
        val error = list.descriptiveStatistics.standardDeviation / Math.sqrt(list.size.toDouble())
        // val error = 0 //list.findDeviation(mean) / Math.sqrt(list.size.toDouble())
        println("Error: $error%")
        println("Percentage: ${error * 100}%")
        println("(+/-) " + error * 1.96)
        println("Samples:" + iters * threads)
        println("Probability:$probability")
        println("Lattice:$wallType")
        println("Time: " + time
                + " seconds")

        Result(
                lattice = "2D_${lattice}; trap(${trap?.x}, ${trap?.y})",
                samples = (iters * 8).toString(),
                walk_length = mean.toString(),
                error = (error * 100).toBigDecimal().toPlainString().take(5),
                conf_interval = "${error * 1.96}",
                time = time.toString(),
                mortality = probability,
                type = "N = ${lattice.centerPoint}"
        ).writeToDB()
        println("-------------------------------")
    }

    private fun IntArray.findDeviation(mean: Double): Double {
        return Math.sqrt(fold(0.0) { acc, i ->
            acc + Math.pow(i - mean, 2.0)
        } / (this.size - 1))
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

}