package com.mark.sleevecoach.Global;

/**
 * Created by user1 on 3/27/2017.
*/
public enum MenuCase {
    VT_HOME(0),
    VT_GOAL(1),
    VT_PROTEIN(2),
    VT_WATER(3),
    VT_EXERCISE(4),
    VT_VITAMINS(5),
    VT_WEIGHT(6),
    VT_TUTOR(7),
    VT_5DAYRST(8),
    VT_EATFELL(9),
    VT_HISTORY(10);

    private final int value;
    private MenuCase(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    static public MenuCase getMenuCase(int aValue){
        switch (aValue){
            case 0:
                return MenuCase.VT_HOME;
            case 1:
                return MenuCase.VT_GOAL;
            case 2:
                return MenuCase.VT_PROTEIN;
            case 3:
                return MenuCase.VT_WATER;
            case 4:
                return MenuCase.VT_EXERCISE;
            case 5:
                return MenuCase.VT_VITAMINS;
            case 6:
                return MenuCase.VT_WEIGHT;
            case 7:
                return MenuCase.VT_TUTOR;
            case 8:
                return MenuCase.VT_5DAYRST;
            case 9:
                return MenuCase.VT_EATFELL;
            case 10:
                return MenuCase.VT_HISTORY;
            default:
                return MenuCase.VT_HOME;
        }
    }
}