package com.montecarlo

import com.montecarlo.lattice.SquarePlanarWallLattice


const val threads_count = 8
const val probability = 0.5
const val Iterations = 4_000_000 / threads_count
val size = SquarePlanarWallLattice.Square_6
fun main() {
    Simulation(
            threads = threads_count,
            iters = Iterations,
            probability = probability,
            lattice = size
    ).start()
    println("DONE~!!")
}


