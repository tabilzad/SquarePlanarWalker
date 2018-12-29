package com.montecarlo.domain

data class Result(
        val lattice: String,
        val samples: String,
        val walk_length: String,
        val error: String,
        val conf_interval: String,
        val time: String,
        val mortality: Double,
        val type: String
)