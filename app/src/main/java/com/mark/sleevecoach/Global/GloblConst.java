package com.mark.sleevecoach.Global;


import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by user1 on 3/27/2017.
 */
public class GloblConst {
    public static final int PoundMin = 150;
    public static final int PoundMax =  400;

    public static final int KilogMin =  68;
    public static final int KilogMax =  180;

    public static final int HeightInchMin =  54;
    public static final int HeightInchMax =  80;

    public static final int HeightCmMin =  140;
    public static final int  HeightCmMax  = 200;

    public static final String[] ArrSpecDays = {"2,4,6","2,4,7","2,5,7","3,5,7","3,5,1","4,6,1"};

    public static final String[] ArrHourTitle = {"12am","1am","2am","3am","4am","5am","6am","7am","8am","9am","10am","11am","12pm","1pm","2pm","3pm","4pm","5pm","6pm","7pm","8pm","9pm","10pm","11pm"};
    public static final int[] ArrHourValue = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};

    public static final String[] ArrDate = {"Today","Yesterday"};

    public static final String[] ArrEating = {"Chocolate", "Ice Cream", "Candy", "Desserts", "Bread", "Chips","Other Carbs", "Alcohol"};
    public static final String[] ArrFeeling =  {"Angry", "Tired", "Lonely", "Depressed", "Stressed", "Celebrating","Bored"};


    public static int getWeekday(Date aDate){
        Calendar _cal = Calendar.getInstance();
        _cal.setTime(aDate);
        return _cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getYear(Date aDate){
        Calendar _cal = Calendar.getInstance();
        _cal.setTime(aDate);
        return _cal.get(Calendar.YEAR);
    }
    public static int getMonth(Date aDate){
        Calendar _cal = Calendar.getInstance();
        _cal.setTime(aDate);
        return _cal.get(Calendar.MONTH);
    }

    public static int getDay(Date aDate){
        Calendar _cal = Calendar.getInstance();
        _cal.setTime(aDate);
        return _cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date aDate){
        Calendar _cal = Calendar.getInstance();
        _cal.setTime(aDate);
        return _cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date aDate){
        Calendar _cal = Calendar.getInstance();
        _cal.setTime(aDate);
        return _cal.get(Calendar.MINUTE);
    }

    public static int getSecond(Date aDate){
        Calendar _cal = Calendar.getInstance();
        _cal.setTime(aDate);
        return _cal.get(Calendar.SECOND);
    }
    public static String getDateHourString(long aMilli){
        Date aDate = new Date(aMilli);
        SimpleDateFormat format = new SimpleDateFormat("M/d/yy ha");
        return format.format(aDate);
    }


    public static Boolean isEarlier(Date aFirst, Date aSecond){
        if(aFirst.getTime() - aSecond.getTime() > 0)
            return false;
        return true;
    }

    public static Date getToday(){
        Date _dateToday = new Date(System.currentTimeMillis());
        Calendar _cal = Calendar.getInstance();
        _cal.set(GloblConst.getYear(_dateToday), GloblConst.getMonth(_dateToday), GloblConst.getDay(_dateToday),0,0,0);
        return _cal.getTime();
    }

    public static Date getYesterday(){
        Date _dateToday = new Date(System.currentTimeMillis());
        Calendar _cal = Calendar.getInstance();
        _cal.set(GloblConst.getYear(_dateToday), GloblConst.getMonth(_dateToday), GloblConst.getDay(_dateToday),0,0,0);
        _cal.add(Calendar.DATE, -1);
        return _cal.getTime();
    }

    public static Date getDateWithHour(Date aDate,int aHour){
        Calendar _cal = Calendar.getInstance();
        _cal.set(GloblConst.getYear(aDate), GloblConst.getMonth(aDate), GloblConst.getDay(aDate), aHour, 0, 0);
        return _cal.getTime();
    }

    public static Date get60daysAgo(){
        Date _dateToday = new Date(System.currentTimeMillis());
        Calendar _cal = Calendar.getInstance();
        _cal.set(GloblConst.getYear(_dateToday), GloblConst.getMonth(_dateToday), GloblConst.getDay(_dateToday),0,0,0);
        _cal.add(Calendar.DATE, -60);
        return _cal.getTime();
    }

    public static Date get1MonthAgo(){
        Date _dateToday = new Date(System.currentTimeMillis());
        Calendar _cal = Calendar.getInstance();
        _cal.set(GloblConst.getYear(_dateToday), GloblConst.getMonth(_dateToday), GloblConst.getDay(_dateToday),0,0,0);
        _cal.add(Calendar.MONTH, -1);
        return _cal.getTime();
    }

    public static Date get1YearAgo(){
        Date _dateToday = new Date(System.currentTimeMillis());
        Calendar _cal = Calendar.getInstance();
        _cal.set(GloblConst.getYear(_dateToday), GloblConst.getMonth(_dateToday), GloblConst.getDay(_dateToday),0,0,0);
        _cal.add(Calendar.YEAR, -1);
        return _cal.getTime();
    }

    static public int getImageId(Context context, String aStrName){
        int drawableResourceId = context.getResources().getIdentifier(aStrName, "drawable", context.getPackageName());
        return drawableResourceId;
    }

    static public String getChartFormDate(long aDate){
        Date _date = new Date(aDate);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        return format.format(_date);
    }

    public static String getUnitString(MenuCase acase){
        switch (acase) {
            case VT_PROTEIN:
            {
                return "g";
            }
            case VT_WATER:{
                return "oz";
            }
            case VT_EXERCISE:
            {
                return "min";
            }
            default:
                break;
        }
        return "";
    }
    public static String getHeightUnitString(HegWeightUnit aType){
        if (aType == HegWeightUnit.HegWeight_PoundInch) {
            return "in";
        }
        return "cm";
    }

    public static String getWeightUnitString(HegWeightUnit aType){
        if (aType == HegWeightUnit.HegWeight_PoundInch) {
            return "lb";
        }
        return "kg";
    }

    public static double getWeightConvertValue(HegWeightUnit aOrginalType, HegWeightUnit aNewType, double aValue){
        if (aOrginalType == aNewType) {
            return aValue;
        }
        double _nRetValue;
        if (aOrginalType == HegWeightUnit.HegWeight_PoundInch) {
            _nRetValue = UnitConverter.convertPoundToKilo(aValue);
            //_nRetValue = [[DDUnitConverter massUnitConverter] convertNumber:aValue fromUnit:DDMassUnitUSPounds toUnit:DDMassUnitKilograms];
        }
        else{
            _nRetValue = UnitConverter.convertKiloToPound(aValue);
            //_nRetValue = [[DDUnitConverter massUnitConverter] convertNumber:aValue fromUnit:DDMassUnitKilograms toUnit:DDMassUnitUSPounds];
        }

        return _nRetValue;
    }


   public static double getHeightConvertValue(HegWeightUnit aOrginalType ,HegWeightUnit aNewType ,double aValue){
        if (aOrginalType == aNewType) {
            return aValue;
        }
        double _nRetValue;
        if (aOrginalType == HegWeightUnit.HegWeight_PoundInch) {
            _nRetValue = UnitConverter.convertInchToCenti(aValue);
            //[[DDUnitConverter lengthUnitConverter] convertNumber:aValue fromUnit:DDLengthUnitInches toUnit:DDLengthUnitCentimeters];
        }
        else{
            _nRetValue = UnitConverter.convertCentiToInch(aValue);
            //_nRetValue = [[DDUnitConverter lengthUnitConverter] convertNumber:aValue fromUnit:DDLengthUnitCentimeters toUnit:DDLengthUnitInches];
        }
        return _nRetValue;
    }

    public static  double getBMI(HegWeightUnit aType,double aHeight, double aWeight){

        if (aType == HegWeightUnit.HegWeight_PoundInch) {
            return 703.0 * (aWeight /Math.pow(aHeight, 2));
        }
        else{
            return  (aWeight / Math.pow(aHeight / 100.0f, 2));
        }
    }
}
