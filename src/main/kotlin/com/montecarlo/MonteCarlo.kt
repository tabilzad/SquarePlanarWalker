package com.montecarlo

import SquarePlanarLattice

const val threads_count = 8
const val probability = 0.96
const val Iterations = 1_000_000_000 / threads_count
val size = SquarePlanarLattice.Square_3

fun main() {
   /* SquarePlanarLattice.values().forEach {
        (1..4).forEach { i ->
            println(it)
            Simulation(
                    threads = threads_count,
                    iters = Iterations,
                    probability = i / 100.0,
                    lattice = it
            ).start()
            System.gc()
            System.runFinalization()
        }
    }*/
    Simulation(
           threads = threads_count,
            iters = Iterations,
            probability =probability,
            lattice = size
    ).start()
    System.gc()
    Simulation(
            threads = threads_count,
            iters = Iterations,
            probability =probability,
            lattice = size
    ).start()
    System.gc()
    Simulation(
            threads = threads_count,
            iters = Iterations,
            probability =probability,
            lattice = size
    ).start()
    System.gc()
    Simulation(
            threads = threads_count,
            iters = Iterations,
            probability =probability,
            lattice = size
    ).start()




    println("DONE~!!")
}


