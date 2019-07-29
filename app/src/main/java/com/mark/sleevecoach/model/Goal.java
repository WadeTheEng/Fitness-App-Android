package com.mark.sleevecoach.model;

import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Global.HegWeightUnit;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by user1 on 3/27/2017.
 */
public class Goal extends DataSupport {

    @Column(defaultValue = "0")
    public int nExercise;

    @Column(defaultValue = "0")
    public double nGoalWeight;

    @Column(defaultValue = "0")
    public int nHegWeightUnit;

    @Column(defaultValue = "0")
    public double nHeight;

    @Column(defaultValue = "0")
    public double nHighWeight;

    @Column(defaultValue = "0")
    public int nProtein;

    @Column(defaultValue = "0")
    public int nSpecDays;

    @Column(defaultValue = "0")
    public int nSpecExGoal;

    @Column(defaultValue = "0")
    public double nSurgeryWeight;

    @Column(defaultValue = "0")
    public int nWater;

    public HegWeightUnit getHegWeightUnitValue(){
        return HegWeightUnit.getHegWeightUnit(nHegWeightUnit);

    }


    public double getGoalBMIValue(){
        return GloblConst.getBMI(getHegWeightUnitValue(),nHeight ,nGoalWeight);
    }

    public double getSurgeryBMIValue(){
        return GloblConst.getBMI(getHegWeightUnitValue(),nHeight ,nSurgeryWeight);
    }

    public double getHighBMIValue(){
        return GloblConst.getBMI(getHegWeightUnitValue(),nHeight ,nHighWeight);
    }

    public double getHighestWeight(){
        double _nHighest;
        if (nGoalWeight > nHighWeight) {
            _nHighest = nGoalWeight;
        }
        else
            _nHighest = nHighWeight;

        if (_nHighest < nSurgeryWeight) {
            _nHighest = nSurgeryWeight;
        }
        return _nHighest;
    }

    public double getHighestBMI(){
        return GloblConst.getBMI(getHegWeightUnitValue(),nHeight ,getHighestWeight());
    }
}
