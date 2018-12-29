package com.montecarlo.domain

interface Particle {

    fun modulo(m: Int)

    fun step()

    fun randomize(size: Int)

    fun show()
}