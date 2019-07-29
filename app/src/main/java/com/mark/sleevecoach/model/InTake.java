package com.mark.sleevecoach.model;

import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Global.HegWeightUnit;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by user1 on 3/27/2017.
 */
public class InTake extends DataSupport {

    public boolean bVitamins;
    public long date;

    @Column(defaultValue = "0")
    public int nGoalProtein;

    @Column(defaultValue = "0")
    public int nGoalExercise;

    @Column(defaultValue = "0")
    public int nGoalSpecDays;

    @Column(defaultValue = "0")
    public int nGoalSpecExGoal;

    @Column(defaultValue = "0")
    public int nGoalWater;

    @Column(defaultValue = "0")
    public int nHegWeightUnit;

    @Column(defaultValue = "0")
    public int nITExercise;

    @Column(defaultValue = "0")
    public int nITProtein;

    @Column(defaultValue = "0")
    public int nITWater;

    @Column(defaultValue = "0")
    public double nWeight;

    public double getProteinProgress(){
        return (double)nITProtein / (double)nGoalProtein;
    }

    public double getWaterProgress(){
        return (double)nITWater / (double)nGoalWater;
    }

    public double getExerciseProgress(){
        return (double)nITExercise / (double)getExerciseGoal();
    }

    public int getExerciseGoal(){
        int _nWeekDay = GloblConst.getWeekday(new Date(date));
        String _strSpecDays = GloblConst.ArrSpecDays[nGoalSpecDays];
        int _range = _strSpecDays.lastIndexOf(String.valueOf(_nWeekDay));
        if (_range >= 0) {
            return nGoalExercise;
        }
        return nGoalSpecExGoal;
    }

    public int getProteinEarnStarCount(){
        if (getProteinProgress() >=1) {
            return 3;
        }
        else if (getProteinProgress() >=.8) {
            return 2;
        }
        else if (getProteinProgress() >=.65) {
            return 1;
        }
        else
        {
            return 0;
        }

    }

    public int getWaterEarnStarCount(){
        if (getWaterProgress() >=1) {
            return 3;
        }
        else if (getWaterProgress() >=.8) {
            return 2;
        }
        else if (getWaterProgress() >=.65) {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public int getExerciseEarnStarCount(){
        if (getExerciseProgress() >=1) {
            return 3;
        }
        else if ( getExerciseProgress() >=.8) {
            return 2;
        }
        else if ( getExerciseProgress() >=.65) {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public int getVitaminEarnStarCount(){
        if (bVitamins) {
            return 1;
        }
        return 0;
    }

    public int getAllStarCount(){
        return getProteinEarnStarCount() +  getWaterEarnStarCount() + getExerciseEarnStarCount() + getVitaminEarnStarCount();
    }

    public double getBMIValue(){
        Goal _obGoal = DataManager.shared.getGoal();
        HegWeightUnit _unitSelf =  HegWeightUnit.getHegWeightUnit(nHegWeightUnit);
        double _nConvertedHeight = GloblConst.getHeightConvertValue(_obGoal.getHegWeightUnitValue(),_unitSelf,_obGoal.nHeight);
        //[CommData getHeightConvertValue:[_obGoal getHegWeightUnitValue] NewType:[self.nHegWeightUnit intValue] Value:_obGoal.nHeight];
        return GloblConst.getBMI(_unitSelf,_nConvertedHeight,nWeight);
        //return [CommData getBMI:[self.nHegWeightUnit intValue] Height:_nConvertedHeight Weight:self.nWeight];
    }

    public double getWeightValue(){
        Goal _obGoal = DataManager.shared.getGoal();
        if (HegWeightUnit.getHegWeightUnit(nHegWeightUnit) == _obGoal.getHegWeightUnitValue()) {
            return nWeight;
        }
        HegWeightUnit _unitSelf =  HegWeightUnit.getHegWeightUnit(nHegWeightUnit);
        double _nRetValue = GloblConst.getWeightConvertValue(_unitSelf,_obGoal.getHegWeightUnitValue(),nWeight);
        //[CommData getWeightConvertValue:[self.nHegWeightUnit intValue] NewType:[_obGoal getHegWeightUnitValue] Value:self.nWeight];
        return _nRetValue;

    }



}
