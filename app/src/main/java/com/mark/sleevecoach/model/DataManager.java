package com.mark.sleevecoach.model;

import android.util.Log;

import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Global.HegWeightUnit;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user1 on 3/31/2017.
 */
public class DataManager {
    public static DataManager shared = new DataManager();
    InTake objToday;
    InTake objYesterday;
    Goal objGoal;

    DataManager(){
        Goal _objCurGoal = getGoal();
        if (_objCurGoal == null) {
            createDefaultGoal();
        }
    }
    public void createDefaultGoal(){
        Goal _objGoal = new Goal();
        _objGoal.nProtein       = 100;
        _objGoal.nWater         = 80;
        _objGoal.nExercise      = 30;
        _objGoal.nSpecExGoal    = 45;
        _objGoal.nSpecDays      = 0;
        _objGoal.nHegWeightUnit = HegWeightUnit.HegWeight_PoundInch.getValue();
        _objGoal.nGoalWeight    = GloblConst.PoundMin;//60kg
        _objGoal.nHighWeight    = 220;//100kg
        _objGoal.nSurgeryWeight = 399;//200kg
        _objGoal.nHeight        = 69; //175cm
        _objGoal.save();

    }

    public Goal getGoal(){
        if (objGoal == null) {
            List<Goal> _arrRes = DataSupport.findAll(Goal.class);
            if (_arrRes.size() > 0) {
                objGoal =  _arrRes.get(0);
                if (objGoal.nGoalWeight == 0) {
                    objGoal.nHegWeightUnit = HegWeightUnit.HegWeight_PoundInch.getValue();
                    objGoal.nGoalWeight    = GloblConst.PoundMin;//60kg
                    objGoal.nHighWeight    = 220;//100kg
                    objGoal.nSurgeryWeight = 399;//200kg
                    objGoal.nHeight        = 69; //175cm
                }
                objGoal.save();
            }
        }

        return objGoal;
    }

    public InTake getIntakeWithDate(Date aDate){
        Goal _objGoal = getGoal();
        //String _strCondition = String.format("date='%s'",aDate.toString());
        long _seconds = aDate.getTime()/1000;
        List<InTake> _arrRes = DataSupport.where("date = ?", String.valueOf(_seconds * 1000)).find(InTake.class);
        if (_arrRes.size() == 0) {
            InTake _objIntake = new InTake();
            _objIntake.nITExercise          = 0;
            _objIntake.nITProtein           = 0;
            _objIntake.nITWater             = 0;
            _objIntake.nGoalExercise        = _objGoal.nExercise;
            _objIntake.nGoalProtein         = _objGoal.nProtein;
            _objIntake.nGoalWater           = _objGoal.nWater;
            _objIntake.nGoalSpecExGoal      = _objGoal.nSpecExGoal;
            _objIntake.nGoalSpecDays        = _objGoal.nSpecDays;
            _objIntake.bVitamins             = false;
            _objIntake.date                 = _seconds * 1000;
            _objIntake.nHegWeightUnit       = _objGoal.nHegWeightUnit;

            InTake _objNearest = getIntakesNearest(aDate);
            if (_objNearest == null || _objNearest.nWeight  == 0) {
                _objIntake.nWeight = _objGoal.nGoalWeight;
            }
            else
                 _objIntake.nWeight = _objNearest.nWeight;
            _objIntake.save();
            return _objIntake;
        }
        else
            return _arrRes.get(0);

    }

    public InTake getTodayIntake(){
        Date _dateToday = GloblConst.getToday();
        if (objToday == null || GloblConst.isEarlier(new Date(objToday.date),_dateToday))
            objToday = getIntakeWithDate(_dateToday);
        return objToday;
    }

    public InTake getYesterdayIntake(){
        Date _dateYesterday = GloblConst.getYesterday();
        if (objYesterday == null || GloblConst.isEarlier(new Date(objYesterday.date),_dateYesterday)) {
            objYesterday = getIntakeWithDate(_dateYesterday);
        }
        return objYesterday;
    }

    public List<InTake> getIntakesFrom(Date aDate){
        long _seconds = aDate.getTime()/1000;
        List<InTake> _arrRes = DataSupport.where("date >= ?", String.valueOf(_seconds * 1000)).order("date ASC").find(InTake.class);
        return _arrRes;
    }

    public InTake getIntakesNearest(Date aDate){
        long _seconds = aDate.getTime()/1000;
        List<InTake> _arrRes = DataSupport.where("date < ?",  String.valueOf(_seconds * 1000)).order("date DESC").limit(1).find(InTake.class);
        if (_arrRes.size() > 0 ) {
            return _arrRes.get(0);
        }
        return null;
    }

    public List<EatFeel> getEatFeelFrom(Date aDate){
        long _seconds = aDate.getTime()/1000;
        List<EatFeel> _arrRes = DataSupport.where("date >= ?",  String.valueOf(_seconds * 1000)).order("date DESC").find(EatFeel.class);
        return _arrRes;
    }

    public EatFeel getEatFeel(){
        //kch - date?
        EatFeel _objEatFeel = new EatFeel();
        return _objEatFeel;
    }
}
