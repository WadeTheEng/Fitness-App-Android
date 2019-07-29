package com.mark.sleevecoach.Goal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.kyleduo.switchbutton.SwitchButton;
import com.mark.sleevecoach.ActionListner;
import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Global.HegWeightUnit;
import com.mark.sleevecoach.Global.MenuCase;
import com.mark.sleevecoach.R;
import com.mark.sleevecoach.model.DataManager;
import com.mark.sleevecoach.model.Goal;
import com.mark.sleevecoach.model.InTake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1 on 4/7/2017.
 */
public class GoalFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,View.OnClickListener,ActionListner {

    InTake objITToday;
    Goal objCurrentGoal;

    int nProtRow = 0,nWaterRow = 0,nTimeRow = 0,nDaysRow = 0,
            nHeightRow = 0, nWeightKindRow = 0,nWeightRow = 0;

    double nCurHeight,nCurGoalWeight,nCurSurgeryWeight,nCurHighWeight
            ,nOrigHeight,nOrigGoalWeight,nOrigSurgeryWeight,nOrigHighWeight;
    int nCurHegWeightUnit;

    Button _btnProtGoal,_btnWaterGal,_btnExTime,_btnExDays;
    Button _btnHeight,_btnGoalWeight,_btnHighWeight,_btnSurgeryWeight;
    TextView _txtCurProt,_txtCurWater,_txtCurExTime,_txtCurExDays;
    TextView _txtCurGoalBMI,_txtCurHighBMI,_txtCurSurgeryBMI;
    SwitchButton _sbPoundsInches,_sbKiloCentimeters;

    List<String> arrDays = new ArrayList<>();
    List<String> arrWeightCategory = new ArrayList<>();
    List<Integer> arrProteinRange = new ArrayList<>();
    List<Integer> arrWaterRange = new ArrayList<>();
    List<Integer> arrTimeRange = new ArrayList<>();
    List<Double> arrHeightRange = new ArrayList<>();
    List<Double> arrWeightRange = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goalsettings, container, false);

        _btnProtGoal = (Button)rootView.findViewById(R.id.goalsetting_btn_proteingoal);
        _btnWaterGal = (Button)rootView.findViewById(R.id.goalsetting_btn_watergoal);
        _btnExTime = (Button)rootView.findViewById(R.id.goalsetting_btn_exercise_time);
        _btnExDays = (Button)rootView.findViewById(R.id.goalsetting_btn_exercise_days);
        _btnHeight = (Button)rootView.findViewById(R.id.goalsetting_btn_height);
        _btnGoalWeight = (Button)rootView.findViewById(R.id.goalsetting_btn_weight_goalweight);
        _btnHighWeight = (Button)rootView.findViewById(R.id.goalsetting_btn_weight_highweight);
        _btnSurgeryWeight = (Button)rootView.findViewById(R.id.goalsetting_btn_weight_surgeryweight);

        _txtCurProt = (TextView)rootView.findViewById(R.id.goalsetting_txt_curproteingoal);
        _txtCurWater = (TextView)rootView.findViewById(R.id.goalsetting_txt_curwatergoal);
        _txtCurExTime = (TextView)rootView.findViewById(R.id.goalsetting_txt_exercise_curtime);
        _txtCurExDays = (TextView)rootView.findViewById(R.id.goalsetting_txt_exercise_curdays);
        _txtCurGoalBMI = (TextView)rootView.findViewById(R.id.goalsetting_txt_weight_curgoalbmi);
        _txtCurHighBMI = (TextView)rootView.findViewById(R.id.goalsetting_txt_weight_curhighbmi);
        _txtCurSurgeryBMI = (TextView)rootView.findViewById(R.id.goalsetting_txt_weight_cursurgerybmi);

        _sbPoundsInches = (SwitchButton)rootView.findViewById(R.id.goalsetting_sb_poundsinches);
        _sbKiloCentimeters = (SwitchButton)rootView.findViewById(R.id.goalsetting_sb_kilocentimeter);

        _btnProtGoal.setOnClickListener(this);
        _btnWaterGal.setOnClickListener(this);
        _btnExTime.setOnClickListener(this);
        _btnExDays.setOnClickListener(this);
        _btnHeight.setOnClickListener(this);
        _btnGoalWeight.setOnClickListener(this);
        _btnHighWeight.setOnClickListener(this);
        _btnSurgeryWeight.setOnClickListener(this);
        _sbPoundsInches.setOnCheckedChangeListener(this);
        _sbKiloCentimeters.setOnCheckedChangeListener(this);

        objITToday = DataManager.shared.getTodayIntake();
        objCurrentGoal = DataManager.shared.getGoal();

        initUI();
        refreshUI();

        return rootView;
    }

    public void initUI(){

        int _nIndex = 0;

        arrDays.add("Mon-Wed-Fri");
        arrDays.add("Mon-Wed-Sat");
        arrDays.add("Mon-Thu-Sat");
        arrDays.add("Tue-Thu-Sat");
        arrDays.add("Tue-Thu-Sun");
        arrDays.add("Wed-Fri-Sun");

        arrWeightCategory.add("Goal");
        arrWeightCategory.add("High");
        arrWeightCategory.add("Surgery");

        for (int i = 60; i <= 150 ; i+= 1) {
            arrProteinRange.add(i);
            if (i == objCurrentGoal.nProtein)
             nProtRow = _nIndex;
            _nIndex++;
        }

        _nIndex = 0;
        for (int i = 64; i <= 128 ; i+= 1) {
            arrWaterRange.add(i);
            if (i == objCurrentGoal.nWater)
                nWaterRow = _nIndex;
            _nIndex++;
        }

        _nIndex = 0;
        for (int i = 45; i <= 120 ; i+= 1) {
            arrTimeRange.add(i);
            if (i == objCurrentGoal.nSpecExGoal)
                nTimeRow = _nIndex;
            _nIndex++;
        }

        nDaysRow = objCurrentGoal.nSpecDays;

        nCurHeight = objCurrentGoal.nHeight;
        nCurGoalWeight = objCurrentGoal.nGoalWeight;
        nCurSurgeryWeight = objCurrentGoal.nSurgeryWeight;
        nCurHighWeight = objCurrentGoal.nHighWeight;
        nCurHegWeightUnit = objCurrentGoal.nHegWeightUnit;

        nOrigHeight = objCurrentGoal.nHeight;
        nOrigGoalWeight = objCurrentGoal.nGoalWeight;
        nOrigSurgeryWeight = objCurrentGoal.nSurgeryWeight;
        nOrigHighWeight = objCurrentGoal.nHighWeight;

    }

    public void refreshUI(){
        refreshUnit();
        refreshProtein();
        refreshWater();
        refreshExercise();
        refreshHeight();
        refreshWeight();
    }

    public void refreshUnit(){
        if (HegWeightUnit.getHegWeightUnit(nCurHegWeightUnit) == HegWeightUnit.HegWeight_PoundInch) {
            _sbPoundsInches.setChecked(true);
            _sbKiloCentimeters.setChecked(false);
        }
        else{
            _sbPoundsInches.setChecked(false);
            _sbKiloCentimeters.setChecked(true);
        }
    }
    public void refreshProtein(){
        _btnProtGoal.setText(String.format("%d%s", arrProteinRange.get(nProtRow), GloblConst.getUnitString(MenuCase.VT_PROTEIN)));
        _txtCurProt.setText(String.format("(%d%s)", objCurrentGoal.nProtein, GloblConst.getUnitString(MenuCase.VT_PROTEIN)));
    }
    public void refreshWater(){
        _btnWaterGal.setText(String.format("%d%s", arrWaterRange.get(nWaterRow), GloblConst.getUnitString(MenuCase.VT_WATER)));
        _txtCurWater.setText(String.format("(%d%s)", objCurrentGoal.nWater, GloblConst.getUnitString(MenuCase.VT_WATER)));
    }
    public void refreshExercise(){
        _btnExDays.setText(arrDays.get(nDaysRow));
        _btnExTime.setText(String.format("%d%s", arrTimeRange.get(nTimeRow), GloblConst.getUnitString(MenuCase.VT_EXERCISE)));
        _txtCurExDays.setText(String.format("(%s)",arrDays.get(objCurrentGoal.nSpecDays)));
        _txtCurExTime.setText(String.format("(%d%s)", objCurrentGoal.nSpecExGoal, GloblConst.getUnitString(MenuCase.VT_EXERCISE)));

    }
    public void refreshHeight(){
        _btnHeight.setText(String.format("%.0f%s",nCurHeight,
                GloblConst.getHeightUnitString(HegWeightUnit.getHegWeightUnit(nCurHegWeightUnit))));

    }
    public void refreshWeight(){

        HegWeightUnit _unit = HegWeightUnit.getHegWeightUnit(nCurHegWeightUnit);
        _txtCurGoalBMI.setText(String.format("(%.2f)",GloblConst.getBMI(_unit,nCurHeight,nCurGoalWeight)));
        _txtCurHighBMI.setText(String.format("(%.2f)",GloblConst.getBMI(_unit,nCurHeight,nCurHighWeight)));
        _txtCurSurgeryBMI.setText(String.format("(%.2f)",GloblConst.getBMI(_unit,nCurHeight,nCurSurgeryWeight)));

        String _strUnit = GloblConst.getWeightUnitString(_unit);
        _btnGoalWeight.setText(String.format("%.0f%s",nCurGoalWeight,_strUnit));
        _btnHighWeight.setText(String.format("%.0f%s",nCurHighWeight,_strUnit));
        _btnSurgeryWeight.setText(String.format("%.0f%s", nCurSurgeryWeight, _strUnit));

    }


    public void resetUnitRanges(){

        arrHeightRange.removeAll(arrHeightRange);
        arrWeightRange.removeAll(arrWeightRange);

        if (HegWeightUnit.getHegWeightUnit( nCurHegWeightUnit) == HegWeightUnit.HegWeight_PoundInch) {
            for (int i = GloblConst.HeightInchMin; i <= GloblConst.HeightInchMax ; i+= 1) {
                arrHeightRange.add((double)i);
            }
            for (int i = GloblConst.PoundMin; i <= GloblConst.PoundMax ; i+= 1) {
                arrWeightRange.add((double)i);
            }
        }
        else{
            for (float i = GloblConst.HeightCmMin; i <= GloblConst.HeightCmMax ; i+= 5) {
                arrHeightRange.add((double)i);
            }
            for (float i = GloblConst.KilogMin; i <= GloblConst.KilogMax ; i+= .5) {
                arrWeightRange.add((double)i);
            }
        }

    }

    public void convertValues(HegWeightUnit aPreValue,HegWeightUnit aNewValue){

        nCurHeight = GloblConst.getHeightConvertValue(aPreValue,aNewValue,nCurHeight);
        nCurGoalWeight = GloblConst.getWeightConvertValue(aPreValue, aNewValue, nCurGoalWeight);
        nCurHighWeight = GloblConst.getHeightConvertValue(aPreValue,aNewValue,nCurHighWeight);
        nCurSurgeryWeight = GloblConst.getWeightConvertValue(aPreValue, aNewValue, nCurSurgeryWeight);

        nOrigHeight = GloblConst.getHeightConvertValue(aPreValue,aNewValue,nOrigHeight);
        nOrigGoalWeight = GloblConst.getWeightConvertValue(aPreValue, aNewValue, nOrigGoalWeight);
        nOrigHighWeight = GloblConst.getWeightConvertValue(aPreValue, aNewValue, nOrigHighWeight);
        nOrigSurgeryWeight = GloblConst.getWeightConvertValue(aPreValue, aNewValue, nOrigSurgeryWeight);
        refreshWeight();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(compoundButton.getId() == R.id.goalsetting_sb_poundsinches){
            _sbKiloCentimeters.setChecked(!b);

        }
        else{
            _sbPoundsInches.setChecked(!b);

        }
        if(_sbPoundsInches.isChecked() && nCurHegWeightUnit != 0){
            nCurHegWeightUnit = HegWeightUnit.HegWeight_PoundInch.getValue();
            convertValues(HegWeightUnit.HegWeight_KilogMeter,HegWeightUnit.HegWeight_PoundInch);
        }
        else if(_sbKiloCentimeters.isChecked() && nCurHegWeightUnit != 1){
            nCurHegWeightUnit =HegWeightUnit.HegWeight_KilogMeter.getValue();
            convertValues(HegWeightUnit.HegWeight_PoundInch, HegWeightUnit.HegWeight_KilogMeter);
        }

        resetUnitRanges();
        refreshWeight();
        refreshHeight();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.goalsetting_btn_proteingoal){
            showProteinPicker();
        }
        else if(view.getId() == R.id.goalsetting_btn_watergoal){
            showWaterPicker();
        }
        else if(view.getId() == R.id.goalsetting_btn_exercise_time){
            showTimePicker();
        }
        else if(view.getId() == R.id.goalsetting_btn_exercise_days){
            showDaysPicker();
        }
        else if(view.getId() == R.id.goalsetting_btn_height){
            showHeightPicker();
        }
        else if(view.getId() == R.id.goalsetting_btn_weight_goalweight){
            showWeightPicker(0);
        }
        else if(view.getId() == R.id.goalsetting_btn_weight_highweight){
            showWeightPicker(1);
        }
        else if(view.getId() == R.id.goalsetting_btn_weight_surgeryweight){
            showWeightPicker(2);
        }
    }

    public void showProteinPicker(){
        OptionsPickerView pvInputValue;
        pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                nProtRow = options1;
                refreshProtein();
            }

            @Override
            public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                nProtRow = options1;
            }

        }).build();

        pvInputValue.setSubmitButtonTitle("");
        pvInputValue.setSelectOptions(nProtRow);
        ArrayList<String> _arrTitles = new ArrayList<String>();
        for(int i = 0; i < arrProteinRange.size(); i++){
            _arrTitles.add(String.format("%d%s",arrProteinRange.get(i),GloblConst.getUnitString(MenuCase.VT_PROTEIN)));
        }
        pvInputValue.setNPicker(_arrTitles, null,null,null);
        pvInputValue.show();
    }

    public void showWaterPicker(){
        OptionsPickerView pvInputValue;
        pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                nWaterRow = options1;
                refreshWater();
            }

            @Override
            public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                nWaterRow = options1;
            }

        }).build();

        pvInputValue.setSubmitButtonTitle("");
        pvInputValue.setSelectOptions(nWaterRow);
        ArrayList<String> _arrTitles = new ArrayList<String>();
        for(int i = 0; i < arrWaterRange.size(); i++){
            _arrTitles.add(String.format("%d%s",arrWaterRange.get(i),GloblConst.getUnitString(MenuCase.VT_WATER)));
        }
        pvInputValue.setNPicker(_arrTitles, null,null,null);
        pvInputValue.show();
    }

    public void showTimePicker(){
        OptionsPickerView pvInputValue;
        pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                nTimeRow = options1;
                refreshExercise();
            }

            @Override
            public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                nTimeRow = options1;
            }

        }).build();

        pvInputValue.setSubmitButtonTitle("");
        pvInputValue.setSelectOptions(nTimeRow);
        ArrayList<String> _arrTitles = new ArrayList<String>();
        for(int i = 0; i < arrTimeRange.size(); i++){
            _arrTitles.add(String.format("%d%s",arrTimeRange.get(i),GloblConst.getUnitString(MenuCase.VT_EXERCISE)));
        }
        pvInputValue.setNPicker(_arrTitles, null,null,null);
        pvInputValue.show();
    }

    public void showDaysPicker(){
        OptionsPickerView pvInputValue;
        pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                nDaysRow = options1;
                refreshExercise();
            }

            @Override
            public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                nDaysRow = options1;
            }

        }).build();

        pvInputValue.setSubmitButtonTitle("");
        pvInputValue.setSelectOptions(nDaysRow);
        pvInputValue.setNPicker(arrDays, null,null,null);
        pvInputValue.show();
    }

    public void showHeightPicker(){
        OptionsPickerView pvInputValue;
        pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                nHeightRow = options1;
                nCurHeight = arrHeightRange.get(nHeightRow);
                refreshHeight();
                refreshWeight();
            }

            @Override
            public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                nHeightRow = options1;
            }

        }).build();

        pvInputValue.setSubmitButtonTitle("");
        pvInputValue.setSelectOptions(nHeightRow);
        ArrayList<String> _arrTitles = new ArrayList<String>();
        for(int i = 0; i < arrHeightRange.size(); i++){
            _arrTitles.add(String.format("%.0f%s",arrHeightRange.get(i),GloblConst.getHeightUnitString(HegWeightUnit.getHegWeightUnit(nCurHegWeightUnit))));
        }
        pvInputValue.setNPicker(_arrTitles, null,null,null);
        pvInputValue.show();
    }

    public void showWeightPicker(int aMode){
        nWeightKindRow = aMode;
        OptionsPickerView pvInputValue;
        pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                nWeightKindRow = options1;
                nWeightRow = options2;
                if(nWeightKindRow == 0)
                    nCurGoalWeight = arrWeightRange.get(nWeightRow);
                if(nWeightKindRow == 1)
                    nCurHighWeight = arrWeightRange.get(nWeightRow);
                if(nWeightKindRow == 2)
                    nCurSurgeryWeight = arrWeightRange.get(nWeightRow);
                refreshWeight();
            }

            @Override
            public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                nWeightKindRow = options1;
                nWeightRow = options2;
            }

        }).build();

        pvInputValue.setSubmitButtonTitle("");
        pvInputValue.setSelectOptions(nWeightKindRow,nWeightRow);
        ArrayList<String> _arrTitles = new ArrayList<String>();
        for(int i = 0; i < arrWeightRange.size(); i++){
            _arrTitles.add(String.format("%.0f%s", arrWeightRange.get(i), GloblConst.getWeightUnitString(HegWeightUnit.getHegWeightUnit( nCurHegWeightUnit))));
        }
        pvInputValue.setNPicker(arrWeightCategory, _arrTitles,null,null);
        pvInputValue.show();
    }

    @Override
    public void onMenuAction(){

        objCurrentGoal.nProtein         = arrProteinRange.get(nProtRow);
        objCurrentGoal.nWater           = arrWaterRange.get(nWaterRow);
        objCurrentGoal.nSpecExGoal      = arrTimeRange.get(nTimeRow);
        objCurrentGoal.nSpecDays        = nDaysRow;
        objCurrentGoal.nHeight          = nCurHeight;
        objCurrentGoal.nGoalWeight      = nCurGoalWeight;
        objCurrentGoal.nSurgeryWeight   = nCurSurgeryWeight;
        objCurrentGoal.nHighWeight      = nCurHighWeight;
        objCurrentGoal.nHegWeightUnit   = nCurHegWeightUnit;

        objITToday.nGoalExercise        = objCurrentGoal.nExercise;
        objITToday.nGoalProtein         = objCurrentGoal.nProtein;
        objITToday.nGoalWater           = objCurrentGoal.nWater;
        objITToday.nGoalSpecExGoal      = objCurrentGoal.nSpecExGoal;
        objITToday.nGoalSpecDays        = objCurrentGoal.nSpecDays;
        objITToday.nHegWeightUnit       = objCurrentGoal.nHegWeightUnit;

        objCurrentGoal.save();
        objITToday.save();

        nOrigHeight = objCurrentGoal.nHeight;
        nOrigGoalWeight = objCurrentGoal.nGoalWeight;
        nOrigSurgeryWeight = objCurrentGoal.nSurgeryWeight;
        nOrigHighWeight = objCurrentGoal.nHighWeight;

        refreshUI();
        Toast.makeText(getActivity(), "Goals saved!", Toast.LENGTH_SHORT).show();
    }
}
