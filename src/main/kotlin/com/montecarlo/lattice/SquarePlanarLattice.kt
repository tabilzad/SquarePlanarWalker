package com.montecarlo.lattice

/**
 * Created by FERMAT on 3/26/2018.
 */
enum class SquarePlanarLattice(override val centerPoint: Int,
                               override val trap: Int = 0,
                               override val trap2: Int = 0
) : Lattice {
    Square_3(3),
    Square_4(4),
    Square_5(5),
    Square_6(6),
    Square_7(7),
    Square_9(9),
    Square_10(10),
    Square_11(11);


    fun from(value: String): SquarePlanarLattice {
        return values().find { it.name.contains(value) }
                ?.let {
                    this
                } ?: throw IllegalArgumentException("could not find enum for $value")
    }

}
