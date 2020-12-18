package com.montecarlo.lattice

/**
 * Created by FERMAT on 3/26/2018.
 */
enum class SquarePlanarWallLattice(override val centerPoint: Int,
                                   override val trap: Int = 0,
                                   override val trap2: Int = 0
) : Lattice {

    Square_4(4),
    Square_6(6),
    Square_8(8),
    Square_10(10),
    Square_12(12),
    Square_14(14);

    fun from(value: String): SquarePlanarWallLattice {
        return values().find { it.name.contains(value) }
                ?.let {
                    this
                } ?: throw IllegalArgumentException("could not find enum for $value")
    }

}

