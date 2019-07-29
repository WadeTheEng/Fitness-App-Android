package com.mark.sleevecoach.Intakes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Global.MenuCase;
import com.mark.sleevecoach.R;
import com.mark.sleevecoach.model.DataManager;
import com.mark.sleevecoach.model.InTake;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1 on 3/31/2017.
 */
public class CommonIntakeFragment  extends Fragment implements View.OnClickListener {

    OptionsPickerView pvInputValue;
    InTake objITToday,objITYesterday;
    ArrayList<Integer> arrValues = new ArrayList<>();
    ArrayList<String> arrValueTitles = new ArrayList<>();
    ArrayList<String> arrDates;

    int nDateSel = 0,nValueSel = 0;
    TextView _txtSubTitle,_txtSubDesc,_txtToday,_txtYesterday,_txtInputDesc;
    TextView _txtTodayProgress,_txtYestProgress;
    ImageView _ivTod60,_ivTod85,_ivTod100,_ivYest60,_ivYest85,_ivyest100;
    Button _btnAdd,_btnSelValue;
    BarChart mChart;

    MenuCase mcCurItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common_intake, container, false);
        //init views
        int _nPos = getArguments().getInt("SubMenu");
        mcCurItem = MenuCase.getMenuCase(_nPos);

        objITToday = DataManager.shared.getTodayIntake();
        objITYesterday = DataManager.shared.getYesterdayIntake();

        _btnAdd = (Button)rootView.findViewById(R.id.commonintake_btn_add);
        _btnSelValue = (Button)rootView.findViewById(R.id.commonintake_btn_selamount);
        _btnAdd.setOnClickListener(this);
        _btnSelValue.setOnClickListener(this);

        _txtSubTitle = (TextView)rootView.findViewById(R.id.commonintake_txt_subtitle);
        _txtSubDesc = (TextView)rootView.findViewById(R.id.commonintake_txt_desc);
        _txtToday = (TextView)rootView.findViewById(R.id.commonintake_txt_today);
        _txtYesterday = (TextView)rootView.findViewById(R.id.commonintake_txt_yest);
        _txtInputDesc = (TextView)rootView.findViewById(R.id.commonintake_txt_inputdesc);
        _txtTodayProgress = (TextView)rootView.findViewById(R.id.commonintake_today_progress);
        _txtYestProgress = (TextView)rootView.findViewById(R.id.commonintake_yest_progress);
        _ivTod60 = (ImageView)rootView.findViewById(R.id.commonintake_today_iv_onestar);
        _ivTod85 = (ImageView)rootView.findViewById(R.id.commonintake_today_iv_twostar);
        _ivTod100  = (ImageView)rootView.findViewById(R.id.commonintake_today_iv_tripstar);
        _ivYest60 = (ImageView)rootView.findViewById(R.id.commonintake_yest_iv_onestar);
        _ivYest85 = (ImageView)rootView.findViewById(R.id.commonintake_yest_iv_twostar);
        _ivyest100 = (ImageView)rootView.findViewById(R.id.commonintake_yest_iv_tripstar);

        mChart = (BarChart)rootView.findViewById(R.id.commonintake_chart);
        arrDates = new ArrayList<>();
        arrDates.add(getString(R.string.str_today));
        arrDates.add(getString(R.string.str_yesterday));

        switch(mcCurItem){
            case VT_PROTEIN:
                for (int i = 30; i > 0; i--) {
                    arrValueTitles.add(String.format("+%dg",i));
                    arrValues.add(i);
                }
                arrValueTitles.add("-2g");
                arrValues.add(-2);
                nValueSel = 10;
                _txtSubTitle.setText("Track your protein intake below.");
                _txtInputDesc.setText("Tap below to enter protein:");
                break;
            case VT_WATER:
                for (int i = 30; i > 0; i--) {
                    arrValueTitles.add(String.format("+%doz",i));
                    arrValues.add(i);
                }
                nValueSel = 22;
                arrValueTitles.add("-2oz");
                arrValues.add(-2);
                _txtSubTitle.setText("Track your water/hydration intake below.");
                _txtInputDesc.setText("Tap below to enter water/hydration:");
                break;
            case VT_EXERCISE:
                for (int i = 90; i > 0; i-=5) {
                    arrValueTitles.add(String.format("+%dmin",i));
                    arrValues.add(i);
                }
                nValueSel = 12;
                arrValueTitles.add("-5min");
                arrValues.add(-5);
                _txtSubTitle.setText("Track your exercise below.");
                _txtInputDesc.setText("Tap below to enter your exercise:");
                break;
        }
        updateEnterValue();
        updateUI();
        initChart();
        refreshChart();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.commonintake_btn_add){
            addValue();
        }
        else{//select amount
            pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                    //返回的分别是三个级别的选中位置
                    nDateSel = options1;
                    nValueSel = options2;
                    updateEnterValue();
                }

                @Override
                public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                    nDateSel = options1;
                    nValueSel = options2;
                    updateEnterValue();
                    addValue();
                }

            }).build();
            pvInputValue.setSelectOptions(nDateSel,nValueSel);
            pvInputValue.setNPicker(arrDates, arrValueTitles,null,null);
            pvInputValue.show();
        }
    }

    public void updateEnterValue(){

        _btnSelValue.setText(String.format("%s : %s",arrDates.get(nDateSel),arrValueTitles.get(nValueSel)));

    }

    public void updateUI(){
        String _strUnit = GloblConst.getUnitString(mcCurItem);

        int _nutITTodayValue = 0, _nutITYestValue = 0, _nutGoalValue = 0;
        double _dTodayProgress = 0,_dYestProgress = 0;

        switch (mcCurItem) {
            case VT_PROTEIN:
            {
                _nutITTodayValue    = objITToday.nITProtein;
                _nutITYestValue    = objITYesterday.nITProtein;
                _nutGoalValue     = objITToday.nGoalProtein;
                _dTodayProgress     = objITToday.getProteinProgress();
                _dYestProgress      = objITYesterday.getProteinProgress();
            }
            break;
            case VT_WATER:{
                _nutITTodayValue    = objITToday.nITWater;
                _nutITYestValue    = objITYesterday.nITWater;
                _nutGoalValue     = objITToday.nGoalWater;
                _dTodayProgress     = objITToday.getWaterProgress();
                _dYestProgress      = objITYesterday.getWaterProgress();

            }
            break;
            case VT_EXERCISE:
            {
                _nutITTodayValue    = objITToday.nITExercise;
                _nutITYestValue    = objITYesterday.nITExercise;
                _nutGoalValue     =  objITToday.getExerciseGoal();
                _dTodayProgress     = objITToday.getExerciseProgress();
                _dYestProgress      = objITYesterday.getExerciseProgress();
            }
            break;
            default:
                break;
        }
        _txtSubDesc.setText(String.format("Goal: %d%s per day", _nutGoalValue, _strUnit));
        _txtToday.setText(String.format("Today : %d%s", _nutITTodayValue, _strUnit));

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                _txtTodayProgress.getLayoutParams();
        params.weight = (float)_dTodayProgress;
        _txtTodayProgress.setLayoutParams(params);


        String _strSuf1 = "h",_strSuf2 = "h",_strSuf3 = "h";
        if (_dTodayProgress >=1.0) {
        }
        else if (_dTodayProgress >=.8) {
            _strSuf3 = "d";
        }
        else if (_dTodayProgress >=.65) {
            _strSuf2 = "d";
            _strSuf3 = "d";
        }
        else
        {
            _strSuf1 = "d";
            _strSuf2 = "d";
            _strSuf3 = "d";
        }

        _ivTod60.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_" + _strSuf1));
        _ivTod85.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_twostar_" + _strSuf2));
        _ivTod100.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_tripstar_" + _strSuf3));

        _txtYesterday.setText(String.format("Yesterday : %d%s", _nutITYestValue, _strUnit));
        params = (LinearLayout.LayoutParams)
                _txtYestProgress.getLayoutParams();
        params.weight = (float)_dYestProgress;
        _txtYestProgress.setLayoutParams(params);

        _strSuf1 = "h";
        _strSuf2 = "h";
        _strSuf3 = "h";

        if (_dYestProgress >=1) {

        }
        else if (_dYestProgress >=.8) {
            _strSuf3 = "d";
        }
        else if (_dYestProgress >=.65) {
            _strSuf2 = "d";
            _strSuf3 = "d";
        }
        else
        {
            _strSuf1 = "d";
            _strSuf2 = "d";
            _strSuf3 = "d";
        }

        _ivYest60.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_" + _strSuf1));
        _ivYest85.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_twostar_" + _strSuf2));
        _ivyest100.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_tripstar_" + _strSuf3));
        refreshChart();

    }

    public void initChart(){
        mChart.setNoDataText("You need to provide data for the chart.");
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);

        XAxis  _xAxis = mChart.getXAxis();
        _xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        _xAxis.setDrawGridLines(false);

        YAxis _leftAxis = mChart.getAxisLeft();
        _leftAxis.setLabelCount(5);
        IAxisValueFormatter _customFormatter = new PercentFormatter();
        _leftAxis.setValueFormatter(_customFormatter);
        _leftAxis.setDrawGridLines(false);
        _leftAxis.setSpaceTop(0.15f);
        _leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        _leftAxis.setAxisMinimum(0);

        Legend _legend = mChart.getLegend();
        _legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        _legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        _legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        _legend.setForm(Legend.LegendForm.CIRCLE);
        _legend.setFormSize(9f);
        _legend.setTextSize(11f);
        _legend.setXEntrySpace(4f);

        List<LegendEntry> _arrLegends = new ArrayList<>();

        LegendEntry _entry1 = new LegendEntry();
        _entry1.label = "3 Stars";
        _entry1.formColor = getResources().getColor(R.color.gold);
        _arrLegends.add(_entry1);

        _entry1 = new LegendEntry();
        _entry1.label = "2 Stars";
        _entry1.formColor = getResources().getColor(R.color.silver);
        _arrLegends.add(_entry1);

        _entry1 = new LegendEntry();
        _entry1.label = "1 Star";
        _entry1.formColor = getResources().getColor(R.color.bronze);
        _arrLegends.add(_entry1);

        _entry1 = new LegendEntry();
        _entry1.label = "No Stars";
        _entry1.formColor = Color.BLACK;
        _arrLegends.add(_entry1);

        _legend.setCustom(_arrLegends);
    }

    public void refreshChart(){

        List<InTake> _arrInTakes = DataManager.shared.getIntakesFrom(GloblConst.get1MonthAgo());

        ArrayList<String> _xVals = new ArrayList<>();
        ArrayList<BarEntry> _yVals1 = new ArrayList<>();
        ArrayList<BarEntry> _yVals2 = new ArrayList<>();
        ArrayList<BarEntry> _yVals3 = new ArrayList<>();
        ArrayList<BarEntry> _yVals4 = new ArrayList<>();
        List<Integer> _colors = new ArrayList<Integer>();

        int _nSize = _arrInTakes.size();
        for(int i = 0; i < _nSize; i++){
            InTake _objIntake = _arrInTakes.get(i);
            _xVals.add(GloblConst.getChartFormDate(_objIntake.date));
            int _nStars = 0;
            double _dProgress = 0;
            switch (mcCurItem) {
                case VT_PROTEIN:
                {
                    _nStars = _objIntake.getProteinEarnStarCount();
                    _dProgress = _objIntake.getProteinProgress();
                }
                break;
                case VT_WATER:{
                    _nStars =_objIntake.getWaterEarnStarCount();
                    _dProgress = _objIntake.getWaterProgress();
                }
                break;
                case VT_EXERCISE:
                {
                    _nStars = _objIntake.getExerciseEarnStarCount();
                    _dProgress = _objIntake.getExerciseProgress();
                }
                break;
                default:
                    break;
            }

            if (_dProgress > 1)
                _dProgress = 1;

            switch (_nStars) {
                case 3:
                {
                    _yVals1.add(new BarEntry(i, (float) _dProgress * 100));
                    _colors.add(getResources().getColor(R.color.gold));
                   // _yVals2.add(new BarEntry(i,0));
                   // _yVals3.add(new BarEntry(i,0));
                   // _yVals4.add(new BarEntry(i,0));
                    //[arrYVals addObject:[[BarChartDataEntry alloc] initWithValues:@[@(_dProgress * 100), @(0), @(0),@(0)] xIndex:i]];
                }
                break;
                case 2:
                {
                    _yVals1.add(new BarEntry(i,(float)_dProgress * 100));
                    _colors.add(getResources().getColor(R.color.silver));
                   // _yVals1.add(new BarEntry(i,0));
                   // _yVals3.add(new BarEntry(i,0));
                   // _yVals4.add(new BarEntry(i,0));
                    //[arrYVals addObject:[[BarChartDataEntry alloc] initWithValues:@[@(0),@(_dProgress * 100), @(0),@(0)] xIndex:i]];
                }
                break;
                case 1:
                {
                    _yVals1.add(new BarEntry(i,(float)_dProgress * 100));
                    _colors.add(getResources().getColor(R.color.bronze));
                    //_yVals1.add(new BarEntry(i,0));
                    //_yVals2.add(new BarEntry(i,0));
                   // _yVals4.add(new BarEntry(i,0));
                    //[arrYVals addObject:[[BarChartDataEntry alloc] initWithValues:@[@(0),@(0),@(_dProgress * 100), @(0)] xIndex:i]];
                }
                break;
                default:
                {
                    _yVals1.add(new BarEntry(i,(float)_dProgress * 100));
                    _colors.add(Color.BLACK);
                    //_yVals1.add(new BarEntry(i,0));
                    //_yVals2.add(new BarEntry(i,0));
                    //_yVals3.add(new BarEntry(i,0));
                    //[arrYVals addObject:[[BarChartDataEntry alloc] initWithValues:@[@(0),@(0), @(0),@(_dProgress * 100)] xIndex:i]];
                }
                break;
            }
        }

        XAxis  _xAxis = mChart.getXAxis();
        XAxisValueFormatter _customFormatter = new XAxisValueFormatter();
        _customFormatter.mValues = _xVals;
        _xAxis.setLabelCount(_xVals.size());
        _xAxis.setValueFormatter(_customFormatter);

        BarDataSet set1, set2, set3, set4;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            //set2 = (BarDataSet) mChart.getData().getDataSetByIndex(1);
            //set3 = (BarDataSet) mChart.getData().getDataSetByIndex(2);
            //set4 = (BarDataSet) mChart.getData().getDataSetByIndex(3);
            set1.setValues(_yVals1);
            set1.setColors(_colors);
            //set2.setValues(_yVals2);
            //set3.setValues(_yVals3);
            //set4.setValues(_yVals4);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        }
        else{

            //"3 Stars", @"2 Stars", @"1 Star",@"No Stars"
            set1 = new BarDataSet(_yVals1, "3 Stars");
            //set1.setColor(getResources().getColor(R.color.gold));
            set1.setColors(_colors);
            /*
            set2 = new BarDataSet(_yVals2, "2 Stars");
            set2.setColor(getResources().getColor(R.color.silver));
            set3 = new BarDataSet(_yVals3, "1 Star");
            set3.setColor(getResources().getColor(R.color.bronze));
            set4 = new BarDataSet(_yVals4, "No Stars");
            set4.setColor(Color.BLACK);
            */

            BarData data = new BarData(set1);
            data.setDrawValues(false);
            data.setBarWidth(0.2f);
            mChart.setData(data);
        }
        //mChart.groupBars(0,.08f,0.03f);
        mChart.invalidate();
    }

    public void addValue(){
        InTake _objCurSel;
        if (nDateSel == 0)
            _objCurSel = objITToday;
        else
            _objCurSel = objITYesterday;
        boolean _bRefresh = false;

        switch(mcCurItem){
            case VT_PROTEIN:
                if (_objCurSel.nITProtein + arrValues.get(nValueSel) >= 0) {
                _objCurSel.nITProtein = _objCurSel.nITProtein + arrValues.get(nValueSel);
                _bRefresh = true;
                //kch - [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFY_CHANGE_PROTEIN object:nil];
                }
                break;
            case VT_WATER:
                if (_objCurSel.nITWater + arrValues.get(nValueSel) >= 0) {
                _objCurSel.nITWater = _objCurSel.nITWater + arrValues.get(nValueSel);
                _bRefresh = true;
                //kch - [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFY_CHANGE_WATER object:nil];
                }
                break;
            case VT_EXERCISE:
                if (_objCurSel.nITExercise + arrValues.get(nValueSel) >= 0) {
                _objCurSel.nITExercise = _objCurSel.nITExercise + arrValues.get(nValueSel);
                _bRefresh = true;
                //kch -  [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFY_CHANGE_EXERCISE object:nil];
                }
                break;
        }
        if(_bRefresh) {
            _objCurSel.save();
            updateUI();
        }
    }


}
