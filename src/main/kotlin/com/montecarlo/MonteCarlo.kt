package com.montecarlo

import SquarePlanarLattice

const val threads_count = 8
const val probability = 0.02
const val Iterations = 1_600_000 / threads_count
val size = SquarePlanarLattice.Square_5

fun main() {
   /* SquarePlanarLattice.values().forEach {
        (0..4).forEach { i ->
            Simulation(
                    threads = threads_count,
                    iters = Iterations,
                    probability = i / 100.0,
                    lattice = it
            ).start()
            println(i)
        }
    }*/

    Simulation(
           threads = threads_count,
            iters = Iterations,
            probability =probability,
            lattice = size
    ).start()

    println("DONE~!!")
}


