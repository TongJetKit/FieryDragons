package com.example.fierydragons.Cards;

/**
 * Class that represents the grouping of the 3 volcano card tiles
 */
public class VolcanoCardGroup {

    // The left volcano card tile in the volcano card group
    private VolcanoCard leftVolcano;
    // The middle volcano card tile in the volcano card group
    private VolcanoCard rightVolcano;
    // The right volcano card tile in the volcano card group
    private VolcanoCard middleVolcano;

    /**
     * Constructor to create the volcano card group tiles object
     * @param leftVolcano The left volcano card tile in the volcano card group
     * @param middleVolcano The middle volcano card tile in the volcano card group
     * @param rightVolcano The right volcano card tile in the volcano card group
     */
    public VolcanoCardGroup(VolcanoCard leftVolcano, VolcanoCard middleVolcano, VolcanoCard rightVolcano) {
        this.leftVolcano = leftVolcano;
        this.rightVolcano = rightVolcano;
        this.middleVolcano = middleVolcano;
    }

    /**
     * Getter to get the left volcano card tile in the volcano card group
     * @return The left volcano card tile in the volcano card group
     */
    public VolcanoCard getLeftVolcano() {
        return leftVolcano;
    }


    /**
     * Getter to get the right volcano card tile in the volcano card group
     * @return The right volcano card tile in the volcano card group
     */
    public VolcanoCard getRightVolcano() {
        return rightVolcano;
    }


    /**
     * Getter to get the middle volcano card tile in the volcano card group
     * @return The middle volcano card tile in the volcano card group
     */
    public VolcanoCard getMiddleVolcano() {
        return middleVolcano;
    }



}
