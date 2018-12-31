package com.montecarlo.domain

import com.montecarlo.lattice.Lattice
import java.util.concurrent.*

class Grid(var Grid_size: Lattice, var Iterations: Int, var pb: Double) {
    //var list = mutableListOf<Int>()
    var list = IntArray(Iterations)

    //3D
    fun run_sim_3D() {
        (1..Iterations).forEach {
            var st = 0
            val p = Particle3D(this.Grid_size.centerPoint)
            val p2 = Particle3D(this.Grid_size.centerPoint)
            while (p.equals(p2)) {
                p.randomize(Grid_size.centerPoint)
                p2.randomize(Grid_size.centerPoint)
            }
            while (!p.equals(p2)) {

                val OLD_p = Particle3D(p.x, p.y, p.z)
                val OLD_p2 = Particle3D(p2.x, p2.y, p2.z)

                st++
                if (!prob(pb)) {
                    p.step()
                    p.modulo(Grid_size.centerPoint)
                } else {
                    p.stop()
                }
                if (!prob(pb)) {
                    p2.step()
                    p2.modulo(Grid_size.centerPoint)
                } else {
                    p2.stop()
                }

                if (p.stopped && p2.stopped) break
                if (p2.equals(OLD_p) && p.equals(OLD_p2)) {
                    break
                }
            }
            list[it-1] = st
        }
    }


    fun run_sim_2_3D() {
        (1..Iterations).forEach {
            var st = 0
            val p = Particle3D(this.Grid_size.centerPoint)
            val p2 = Particle3D(this.Grid_size.centerPoint)
            while (p.equals(p2)) {
                p.randomize(Grid_size.centerPoint)
                p2.randomize(Grid_size.centerPoint)
            }
            while (!p.equals(p2)) {

                val old_P = Particle3D(p.x, p.y, p.z)
                val old_P2 = Particle3D(p2.x, p2.y, p2.z)

                st++
                if (!prob(pb)) {
                    p.step()
                    p.modulo(Grid_size.centerPoint)
                } else {
                    break
                }

                if (!prob(pb)) {
                    p2.step()
                    p2.modulo(Grid_size.centerPoint)
                } else {
                    break
                }

                if (p2.equals(old_P) && p.equals(old_P2)) {
                    break
                }
            }
            list[it-1] = st
        }
    }

    //2D
    fun run_sim2D() {

        (1..Iterations).forEach {
            var st = 0
            val p = Particle2D(this.Grid_size.centerPoint)
            val p2 = Particle2D(this.Grid_size.centerPoint)
            while (p.equals(p2)) {
                p.randomize(Grid_size.centerPoint)
                p2.randomize(Grid_size.centerPoint)
            }
            while (!p.equals(p2)) {

                val old_P = Particle2D(p.x, p.y)
                val old_P2 = Particle2D(p2.x, p2.y)

                st++
                if (!prob(pb)) {
                    p.step()
                    p.modulo(Grid_size.centerPoint)
                } else {
                    break
                }

                if (!prob(pb)) {
                    p2.step()
                    p2.modulo(Grid_size.centerPoint)
                } else {
                    break
                }

                if (p2.equals(old_P) && p.equals(old_P2)) {
                    break
                }
            }
            list[it-1] = st
        }

    }
    fun run_sim2_2D() {

        (1..Iterations).forEach {
            var st = 0
            val p = Particle2D(this.Grid_size.centerPoint)
            val p2 = Particle2D(this.Grid_size.centerPoint)
            while (p.equals(p2)) {
                p.randomize(Grid_size.centerPoint)
                p2.randomize(Grid_size.centerPoint)
            }
            while (!p.equals(p2)) {

                val old_P = Particle2D(p.x, p.y)
                val old_P2 = Particle2D(p2.x, p2.y)

                st++
                if (!prob(pb)) {
                    p.step()
                    p.modulo(Grid_size.centerPoint)
                } else {
                    p.stop()
                }

                if (!prob(pb)) {
                    p2.step()
                    p2.modulo(Grid_size.centerPoint)
                } else {
                    p2.stop()
                }
                if (p.stopped && p2.stopped) break
                if (p2.equals(old_P) && p.equals(old_P2)) {
                    break
                }
            }
            list[it-1] = st
        }

    }

    fun prob(probabilityTrue: Double): Boolean = ThreadLocalRandom.current().nextDouble() >= 1.0 - probabilityTrue
}
