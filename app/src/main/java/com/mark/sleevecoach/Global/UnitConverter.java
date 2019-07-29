package com.mark.sleevecoach.Global;

/**
 * Created by user1 on 4/3/2017.
 */
public class UnitConverter {

    public static double convertInchToCenti(double aValue){
         return aValue * 2.5400013716;
    }

    public static double convertCentiToInch(double aValue){
        return 0.393701 * aValue;
    }

    public static double convertPoundToKilo(double aValue){
        return 0.453592 * aValue;
    }

    public static double convertKiloToPound(double aValue){
        return 2.20462 * aValue;
    }
}
