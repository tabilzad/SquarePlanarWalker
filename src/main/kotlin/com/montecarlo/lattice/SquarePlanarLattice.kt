import com.montecarlo.lattice.Lattice

/**
 * Created by FERMAT on 3/26/2018.
 */
enum class SquarePlanarLattice(override val centerPoint: Int,
                               override val trap: Int = 0,
                               override val trap2: Int = 0) : Lattice {
    Square_3(3),
    Square_5(5),
    Square_7(7);
   // Square_9(9),
   // Square_11(11);
   // Square_13(13),
  //  Square_15(15),
   // Square_17(17);

    fun from(value: String): SquarePlanarLattice {
        return values().find { it.name.contains(value) }
                ?.let {
                    this
                } ?: throw IllegalArgumentException("could not find enum for $value")
    }

}
