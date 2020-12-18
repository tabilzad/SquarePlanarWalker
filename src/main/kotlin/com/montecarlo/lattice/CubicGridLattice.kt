package com.montecarlo.lattice

/**
 * Created by FERMAT on 3/26/2018.
 */
enum class CubicGridLattice(override val centerPoint: Int,
                             override val trap: Int = 0,
                             override val trap2: Int = 0
) : Lattice {
    CubicGrid_16x2(16);

    fun from(value: String): CubicGridLattice {
        return values().find { it.name.contains(value) }
                ?.let {
                    this
                } ?: throw IllegalArgumentException("could not find enum for $value")
    }

}
