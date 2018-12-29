package com.montecarlo.domain

import java.util.concurrent.ThreadLocalRandom

class Particle3D : Particle {
    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var steps = 0
    var Grid_size: Int = 0

    var stopped = false

    internal constructor(x: Int, y: Int, z: Int) {
        this.x = x
        this.y = y
        this.z = z
    }

    internal constructor(GZ: Int) {
        this.Grid_size = GZ
    }

    override fun show() {
        println("{$x,$y}: STEP:$steps")
    }

    fun stop() {
        stopped = true
    }

    override fun modulo(m: Int) {
        x = Math.floorMod(x, m)
        y = Math.floorMod(y, m)
        z = Math.floorMod(z, m)
    }

    fun equals(x: Int, y: Int, z: Int): Boolean = this.x == x && this.y == y && this.z == z
    fun equals(p: Particle3D): Boolean = this.x == p.x && this.y == p.y && this.z == p.z

    override fun step() {
        if (!stopped) {
            val r = ThreadLocalRandom.current().nextInt(120)//this is not right: 121??
            when {
                r < 20 -> x--
                r < 40 -> x++
                r < 60 -> y--
                r < 80 -> y++
                r < 100 -> z--
                else -> z++
            }
        }
    }

    override fun randomize(size: Int) {
        x = ThreadLocalRandom.current().nextInt(0, size)
        y = ThreadLocalRandom.current().nextInt(0, size)
        z = ThreadLocalRandom.current().nextInt(0, size)
    }

}
