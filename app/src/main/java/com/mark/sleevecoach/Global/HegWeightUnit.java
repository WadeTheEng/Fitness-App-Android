package com.mark.sleevecoach.Global;

/**
 * Created by user1 on 3/31/2017.
 */
public enum HegWeightUnit {
    HegWeight_PoundInch(0), HegWeight_KilogMeter(1);
    private final int value;
    private HegWeightUnit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    static public HegWeightUnit getHegWeightUnit(int aValue) {
        switch (aValue) {
            case 0:
                return HegWeightUnit.HegWeight_PoundInch;
            case 1:
                return HegWeightUnit.HegWeight_KilogMeter;
        }
        return  HegWeightUnit.HegWeight_KilogMeter;
    }

}
