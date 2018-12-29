package com.montecarlo.domain

import java.util.concurrent.ThreadLocalRandom

class Particle2D : Particle {
    var x: Int = 0

    var y: Int = 0
    var steps = 0
    var Grid_size: Int = 0
    var stopped = false

    internal constructor(x: Int, y: Int) {
        this.x = x
        this.y = y
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
    }

    fun equals(x: Int, y: Int, z: Int): Boolean = this.x == x && this.y == y

    fun equals(p: Particle2D): Boolean = this.x == p.x && this.y == p.y

    override fun step() {
        if (!stopped) {
            val r = ThreadLocalRandom.current().nextInt(100)//this is not right: 121??
            when {
                r < 25 -> x--
                r < 50 -> x++
                r < 75 -> y--
                else -> y++
            }
        }
    }

    override fun randomize(size: Int) {
        x = ThreadLocalRandom.current().nextInt(0, size)
        y = ThreadLocalRandom.current().nextInt(0, size)
    }

}
