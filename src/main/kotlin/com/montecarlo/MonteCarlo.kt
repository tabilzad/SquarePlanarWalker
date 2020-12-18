package com.montecarlo

import com.montecarlo.lattice.SquarePlanarWallLattice
import com.montecarlo.lattice.WallType
import java.util.concurrent.ThreadLocalRandom


const val threads_count = 36
const val probability = 0.0
const val Iterations = 8000000 / threads_count
val size = SquarePlanarWallLattice.Square_6
fun main() {
            Simulation(
                    threads = threads_count,
                    iters = Iterations,
                    probability = probability,
                    lattice = size,
                    wallType = WallType.LINEAR_HORIZONTAL_6
            ).start()
    println("DONE~!!")
}

fun List<String>.chooseRandom() = this[ThreadLocalRandom.current().nextInt(this.size)]

fun prob(probabilityTrue: Double): Boolean = ThreadLocalRandom.current().nextDouble() >= 1.0 - probabilityTrue



