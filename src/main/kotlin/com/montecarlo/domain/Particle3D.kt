package com.montecarlo.domain

import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.absoluteValue

class Particle3D : Particle {
    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var steps = 0
    var Grid_size: Int = 0

    val previous: Particle3D by lazy {
        Particle3D(x, y, z)
    }
    val positions: LinkedList<Particle3D> = LinkedList()

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

    fun goBackHistory(steps: Int) {
        // toString()
        (steps).let { offset ->
            val index = ((positions.size - offset - 1).absoluteValue)
            this.jumpTo(positions[index].also {
                positions.record(it)
            })
        }
    }

    fun LinkedList<Particle3D>.record(e: Particle3D) {
        add(e)
        if(size > maxCacheSize) remove()
    }

    override fun modulo(m: Int) {
        x = Math.floorMod(x, m)
        y = Math.floorMod(y, m)
        z = Math.floorMod(z, m)
    }

    fun jumpTo(p: Particle3D) {
        this.x = p.x
        this.y = p.y
        this.z = p.z
    }

    fun moduloX(m: Int) {
        x = Math.floorMod(x, m)
    }

    fun moduloY(m: Int) {
        y = Math.floorMod(y, m)
    }

    fun moduloZ(m: Int) {
        z = Math.floorMod(z, m)
    }

    fun equals(x: Int, y: Int, z: Int): Boolean = this.x == x && this.y == y && this.z == z
    fun equals(p: Particle3D): Boolean = this.x == p.x && this.y == p.y && this.z == p.z
    fun equals(p: Particle2D): Boolean = this.x == p.x && this.y == p.y && this.z == 0


    companion object {
        const val maxCacheSize = 3
    }


    override fun step() {
        previous.jumpTo(this)
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
            positions.record(Particle3D(this.x, this.y, this.z))
        }
    }

    override fun randomize(size: Int) {
        x = ThreadLocalRandom.current().nextInt(0, 15)
        y = ThreadLocalRandom.current().nextInt(0, 17)
        z = ThreadLocalRandom.current().nextInt(0, 2)
    }

}
