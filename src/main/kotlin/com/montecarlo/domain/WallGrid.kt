package com.montecarlo.domain

import com.montecarlo.lattice.SquarePlanarWallLattice
import com.montecarlo.lattice.WallType
import java.util.concurrent.ThreadLocalRandom

class WallGrid(
        var lattice: SquarePlanarWallLattice,
        var Iterations: Int,
        val wallType: WallType,
        var pb: Double
) {
    var list = IntArray(Iterations)

    fun run_sim2D() {

        (1..Iterations).forEach { iteration ->
            var st = 0

            val trap = wallType.trap
            val walls = wallType.points

            val p = Particle2D(lattice.centerPoint)

            p.randomize(lattice.centerPoint)
            while (p.equals(trap) || walls.any { it.equals(p) }) {
                p.randomize(lattice.centerPoint)
            }

            while (true) {
                //   p.show()
                if (p.equals(trap) and prob(pb)) {
                    break
                }

                p.step()
                p.modulo(lattice.centerPoint)
                if (walls.any { it.equals(p) }) {
                    p.goBack()
                }
                st++
            }
            // println("Exited")
            list[iteration - 1] = st
        }

    }

    fun prob(probabilityTrue: Double): Boolean = ThreadLocalRandom.current().nextDouble() >= 1.0 - probabilityTrue
}
