package com.montecarlo.domain

import com.montecarlo.lattice.Lattice
import com.montecarlo.lattice.Wall2D
import java.util.concurrent.ThreadLocalRandom

class WallGrid(
        var lattice: Lattice,
        var Iterations: Int,
        val wall: Wall2D,
        var pb: Double
) {
    var list = IntArray(Iterations)

    fun run_sim2D() {

        (1..Iterations).forEach { iteration ->
            var st = 0

            val trap = wall.trap
            val walls = wall.points

            val p = Particle2D(lattice.centerPoint)

            p.randomize(lattice.centerPoint)
            while (p.equals(trap) || walls.any { it.equals(p) }) {
                p.randomize(lattice.centerPoint)
            }
            p.positions.add(Particle2D(p.x, p.y))
//            while (!p.equals(trap)) {
//                   p.show()
//
//                st++
//                p.step()
//                p.modulo(lattice.centerPoint)
//                if (walls.any { it.equals(p) }) {
//                    p.goBackHistory(1)
//                }
//            }

            while (true) {
                //   p.show()
                if (p.equals(trap)) {
                    break
                }

                p.step(period = lattice.centerPoint)
                if (walls.any { it.equals(p) }) {
                    p.goBack()
                }
                st++
            }
            // println("Exited")
            list[iteration - 1] = st
        }

    }

    fun run_sim3D() {

        (1..Iterations).forEach { iteration ->
            var st = 0

            val trap = wall.trap
            val walls = wall.points

            val p = Particle3D(lattice.centerPoint)

            p.randomize(lattice.centerPoint)
            while (p.equals(trap) || walls.any { it.equals(p) }) {
                p.randomize(lattice.centerPoint)
            }
            p.positions.add(Particle3D(p.x, p.y, p.z))

            while (true) {
                //   p.show()
                if (p.equals(trap)) {
                    break
                }

                p.step()
                p.modulo(lattice.centerPoint)
                if (walls.any { it.equals(p) }) {
                    p.goBackHistory(1)
                }
                st++
            }
            list[iteration] = st
        }

    }

    fun run_sim2D_2Walker() {

        (0 until Iterations).forEach { iteration ->
            var st = 0

            val walls = wall.points
            val p = Particle2D(lattice.centerPoint)
            val p2 = Particle2D(lattice.centerPoint)
            p.randomize(lattice.centerPoint)
            p2.randomize(lattice.centerPoint)
            while (p.equals(p2) || walls.any { it.equals(p) || it.equals(p2) }) {
                when {
                    p.equals(p2) -> p.randomize(lattice.centerPoint)
                    walls.any { it.equals(p) } -> p.randomize(lattice.centerPoint)
                    walls.any { it.equals(p2) } -> p2.randomize(lattice.centerPoint)
                }
            }

            while (!p.equals(p2)) {
                //  p.show()
                val old_P = Particle2D(p.x, p.y)
                val old_P2 = Particle2D(p2.x, p2.y)

                p.step(period = lattice.centerPoint)
                p2.step(period = lattice.centerPoint)

                st++
                if (walls.any { it.equals(p) }) {
                    p.goBack()
                }

                if (walls.any { it.equals(p2) }) {
                    p2.goBack()
                }

                if (p2.equals(old_P) && p.equals(old_P2)) {
                    break
                }
            }
            // println("Exited")
            list[iteration] = st
        }

    }

    fun run_sim2D_2WalkerTrap() {

        (0 until Iterations).forEach { iteration ->
            var st = 0

            val walls = wall.points
            val trap = wall.trap
            val p = Particle2D(lattice.centerPoint)
            val p2 = Particle2D(lattice.centerPoint)

            p.randomize(lattice.centerPoint)
            p2.randomize(lattice.centerPoint)

            while (walls.any { it.equals(p) || it.equals(p2) } ||
                    p.equals(trap) ||
                    p2.equals(trap)) {
                when {
                    p.equals(trap) -> p.randomize(lattice.centerPoint)
                    p2.equals(trap) -> p2.randomize(lattice.centerPoint)
                    walls.any { it.equals(p) } -> p.randomize(lattice.centerPoint)
                    walls.any { it.equals(p2) } -> p2.randomize(lattice.centerPoint)
                }
            }

            while (true) {

                  val old_P = Particle2D(p.x, p.y)
                  val old_P2 = Particle2D(p2.x, p2.y)

                p.step(lattice.centerPoint)
                p2.step(lattice.centerPoint)

                st++
                if (walls.any { it.equals(p) }) {
                    p.goBack()
                }

                if (walls.any { it.equals(p2) }) {
                    p2.goBack()
                }

                if (p.equals(trap)) break
                if (p2.equals(trap)) break
//                if(p.equals(p2)) break
//                if (p2.equals(old_P) && p.equals(old_P2)) {
//                    break
//                }
            }


            //p.show()
            //   p2.show()
            // println(Thread.currentThread().id%8)
            // println("Exited")

            list[iteration] = st
        }

    }

    fun runFast2D() {
        (1..Iterations).forEach {
            var st = 0
            val trap = wall.trap
            val walls = wall.points

            val p = Particle2D(lattice.centerPoint)

            p.randomize(lattice.centerPoint)

            while (p.equals(trap) || walls.any { it.equals(p) }) {
                p.randomize(lattice.centerPoint)
            }


            fun attempt(): Boolean {

                (0..1).forEach {

                    p.step()
                    p.modulo(lattice.centerPoint)

                    when {
                        p.equals(trap) -> return false // terminate!
                        (walls.any { it.equals(p) }) -> {

                            if (it == 0) {                  // second step

                                return true

                            } else {                        // first step
                                // while (walls.any { it.equals(p) }) {

                                //    if (walls.any { it.equals(p) }) {
                                p.goBackHistory(2)
                                //   }
                                //if(p.equals(trap)) return false
                                // }
                            }
                        }
                    }
                }
                return true //means continue walking
            }

            while (!p.equals(trap)) {
                st++

                if (!attempt()) break
            }
            list[it - 1] = st
        }

    }

    fun runFast2D_V2() {
        (1..Iterations).forEach {
            var st = 0
            val trap = wall.trap
            val walls = wall.points

            val p = Particle2D(lattice.centerPoint)

            p.randomize(lattice.centerPoint)

            while (p.equals(trap) || walls.any { it.equals(p) }) {
                p.randomize(lattice.centerPoint)
            }
            p.positions.add(Particle2D(p.x, p.y))

            fun attempt(): Boolean {
                val original = Particle2D(p.x, p.y);
                repeat(2) {
                    p.step(period = lattice.centerPoint)
                    when {
                        p.equals(trap) -> return false// terminate!
                        (walls.any { it.equals(p) }) -> {
                            if (it == 0) {
                                return true
                            } else {
                                p.goBackHistory(3)
                            }
                        }
                    }
                }
                return true //means continue walking
            }

            while (!p.equals(trap)) {

                st++

                if (!attempt()) break
            }
            list[it - 1] = st
        }

    }

    fun prob(probabilityTrue: Double): Boolean = ThreadLocalRandom.current().nextDouble() >= 1.0 - probabilityTrue
}
