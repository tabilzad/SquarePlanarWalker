package com.montecarlo.lattice

import com.montecarlo.domain.Particle2D

enum class WallType(val points: List<Particle2D>,
                    val trap: Particle2D) {

    LINEAR_HORIZONTAL_6(listOf(
            1 to 2,
            2 to 2,
            3 to 2
    ).map { (x, y) -> Particle2D(x, y) },
            trap = Particle2D(4, 2)),

    LINEAR_BENT_SYMMETRIC_6(listOf(
            1 to 2,
            2 to 2,
            3 to 2
    ).map { (x, y) -> Particle2D(x, y) },
            trap = Particle2D(2, 3)),

    SQUARE_6(listOf(
    2 to 2,
    3 to 2,
    2 to 3
    ).map { (x, y) -> Particle2D(x, y) },
    trap = Particle2D(3, 3))
}
