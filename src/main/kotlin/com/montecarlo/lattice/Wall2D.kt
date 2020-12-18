package com.montecarlo.lattice

import com.montecarlo.domain.Particle2D

interface Wall<T> {
    val points: List<T>
    val trap: T
}

data class Wall2D(
        override val points: List<Particle2D>,
        override val trap: Particle2D
) : Wall<Particle2D>

data class GraphWall(
        override val points: List<Int>,
        override val trap: Int
) : Wall<Int>


object WallFactory {

    fun makeWall2D(type: WallType, offset: Int): Wall2D {
        return Wall2D(
                points = type.points.shiftBy(offset),
                trap = Particle2D(type.trap.x + offset,
                        type.trap.y + offset
                )
        )
    }

    fun makeGraphWall(type: GraphWallType): GraphWall {
        return GraphWall(
                points = type.points.map { it-1 },
                trap = type.trap - 1
        )
    }

    fun List<Particle2D>.shiftBy(offset: Int): List<Particle2D> {
        return map { p ->
            Particle2D(
                    p.x + offset,
                    p.y + offset
            )
        }
    }

}
