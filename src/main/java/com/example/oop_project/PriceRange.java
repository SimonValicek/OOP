package com.example.oop_project;

/**
 * Enumeracia PriceRange, reprezentujuca tri typy cenoveho rozsahu.
 */
public enum PriceRange {
    /**
     * Hodnota predstavujuca najlacnejsie cenove rozpatie.
     */
    BUDGET("1"),


    /**
     * Hodnota predstavujuca stredne cenove rozpatie.
     */
    MIDRANGE("2"),

    /**
     * Hodnota predstavujuca najdrahsie cenove rozpatie.
     */
    LUXURY("3");

    /**
     * Hodnota typu String.
     */
    private String value;

    /**
     * Metoda na ulozenie novej hodnoty typu String do premennej value.
     *
     * @param value Nova hodnota typu String.
     */
    private PriceRange(String value) {
        this.value = value;
    }

    /**
     * Metoda na rozpoznanie retazca znakov typu String a jeho nasledne priradenie k prislusnej enumeracnej hodnote typu PriceRange.
     *
     * @param value Retazec znakov typu String.
     * @return Enumeracna hodnota typu PriceRange.
     */
    public static PriceRange fromValue(String value) {
        for (PriceRange priceRange : values()) {
            if (priceRange.value.equals(value)) {
                return priceRange;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}