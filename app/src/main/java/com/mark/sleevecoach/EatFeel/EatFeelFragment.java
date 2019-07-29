package com.mark.sleevecoach.EatFeel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mark.sleevecoach.ActionListner;
import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Helper.Gallery.GalleryNavigator;
import com.mark.sleevecoach.Helper.Gallery.ObservableHorizontalScrollView;
import com.mark.sleevecoach.Intakes.XAxisValueFormatter;
import com.mark.sleevecoach.R;
import com.mark.sleevecoach.model.DataManager;
import com.mark.sleevecoach.model.EatFeel;
import com.mark.sleevecoach.model.InTake;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user1 on 4/6/2017.
 */
public class EatFeelFragment extends Fragment implements View.OnClickListener,ObservableHorizontalScrollView.OnScrollListener,ActionListner {

    Button _btnDate,_btnEatFeel,_btnAdd;
    ObservableHorizontalScrollView _weight_horz_scroll;
    GalleryNavigator _naviChart;

    PieChart _chartCheatWith,_chartFeelingWhenCheat;
    BarChart _chartEat,_chartFeature;
    int nSelDate = 0, nSelHour = 0, nSelEat = 0, nSelFeeling = 0;
    OptionsPickerView pvInputValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eatfeel, container, false);
        _btnDate = (Button)rootView.findViewById(R.id.eatfeel_btn_seldate);
        _btnEatFeel = (Button)rootView.findViewById(R.id.eatfeel_btn_seleatfeel);
        _btnAdd =  (Button)rootView.findViewById(R.id.eatfeel_btn_add);
        _btnDate.setOnClickListener(this);
        _btnEatFeel.setOnClickListener(this);
        _btnAdd.setOnClickListener(this);

        _chartCheatWith = (PieChart)rootView.findViewById(R.id.eatfeel_chart_cheatwith);
        _chartFeelingWhenCheat = (PieChart)rootView.findViewById(R.id.eatfeel_chart_feelingwhen);
        _chartEat = (BarChart)rootView.findViewById(R.id.eatfeel_chart_eat);
        _chartFeature = (BarChart)rootView.findViewById(R.id.eatfeel_chart_feature);

        _weight_horz_scroll = (ObservableHorizontalScrollView)rootView.findViewById(R.id.eatfeel_horz_scroll);
        _weight_horz_scroll.post(new Runnable() {
            @Override
            public void run() {
                int _nWidth = _weight_horz_scroll.getMeasuredWidth();
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) _chartCheatWith.getLayoutParams();
                params.width = _nWidth;
                _chartCheatWith.setLayoutParams(params);

                params = (ViewGroup.LayoutParams) _chartFeelingWhenCheat.getLayoutParams();
                params.width = _nWidth;
                _chartFeelingWhenCheat.setLayoutParams(params);

                params = (ViewGroup.LayoutParams) _chartEat.getLayoutParams();
                params.width = _nWidth;
                _chartEat.setLayoutParams(params);

                params = (ViewGroup.LayoutParams) _chartEat.getLayoutParams();
                params.width = _nWidth;
                _chartEat.setLayoutParams(params);
            }
        });
        _weight_horz_scroll.setOnScrollListener(this);
        _naviChart = (GalleryNavigator)rootView.findViewById(R.id.eatfeel_nav_chart);
        _naviChart.setSize(4);
        _naviChart.invalidate();

        pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                //返回的分别是三个级别的选中位置
                nSelDate = options1;
                nSelHour = options2;
                nSelEat = options3;
                nSelFeeling = options4;
                refreshUI();
            }

            @Override
            public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                nSelDate = options1;
                nSelHour = options2;
                nSelEat = options3;
                nSelFeeling = options4;
                refreshUI();
                addValue();
            }

        }).build();
        ArrayList<String> _arrDate = getArraylistFromArray(GloblConst.ArrDate);
        ArrayList<String> _arrHour = getArraylistFromArray(GloblConst.ArrHourTitle);
        ArrayList<String> _arrEat = getArraylistFromArray(GloblConst.ArrEating);
        ArrayList<String> _arrFeel = getArraylistFromArray(GloblConst.ArrFeeling);
        pvInputValue.setNPicker(_arrDate,_arrHour,_arrEat,_arrFeel);
        initChart();
        refreshUI();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUI();
    }

    public ArrayList<String> getArraylistFromArray(String[] arrValues){
        ArrayList<String> _arrResult = new ArrayList<>();
        for(int i = 0; i < arrValues.length; i++){
            _arrResult.add(arrValues[i]);
        }
        return  _arrResult;
    }

    public void refreshUI(){
        _btnDate.setText(String.format("%s : %s", GloblConst.ArrDate[nSelDate], GloblConst.ArrHourTitle[nSelHour]));
        _btnEatFeel.setText(String.format("%s/%s", GloblConst.ArrEating[nSelEat], GloblConst.ArrFeeling[nSelFeeling]));
        refreshChart();
    }

    public void addValue(){
        EatFeel _objEatFeel = DataManager.shared.getEatFeel();

        Date _date = GloblConst.getToday();
        if (nSelDate == 1) {
            _date = GloblConst.getYesterday();
        }

        int _nHourValue = GloblConst.ArrHourValue[nSelHour];
        _date =GloblConst.getDateWithHour(_date,_nHourValue);
        long _seconds = _date.getTime()/1000;//remove milliseconds value
        _objEatFeel.date = _seconds * 1000;
        _objEatFeel.eating = GloblConst.ArrEating[nSelEat];
        _objEatFeel.feeling =GloblConst. ArrFeeling[nSelFeeling];
        _objEatFeel.save();
        Toast.makeText(getActivity(), "Entered!", Toast.LENGTH_SHORT).show();
        refreshChart();
    }

    public void refreshChart(){
        refreshEatChart();
        refreshCheatWithChart();
        refreshFeelingWhenCheatChart();
    }

    public void initChart(){
        initEatChart();
        initCheatWithChart();
        initFeelingWhenCheatChart();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.eatfeel_btn_seldate || view.getId() == R.id.eatfeel_btn_seleatfeel){
            pvInputValue.setSelectOptions(nSelDate, nSelHour, nSelEat, nSelFeeling);
            pvInputValue.show();
        }
        else if(view.getId() == R.id.eatfeel_btn_add){
            addValue();
        }
    }

    public void refreshEatChart(){
        ArrayList<EatFeel> _arrEatFeel =(ArrayList<EatFeel>)DataManager.shared.getEatFeelFrom(GloblConst.get60daysAgo());
        ArrayList<String> _xVals = new ArrayList<>();
        ArrayList<BarEntry> _yVals1 = new ArrayList<>();

        for (int i = 0; i < GloblConst.ArrHourValue.length; i++) {
            int _nHour = GloblConst.ArrHourValue[i];
            int _nSize = _arrEatFeel.size();
            ArrayList<EatFeel> _arrFiltered= new ArrayList<>();
            for (int j = 0; j < _nSize; j++){
                EatFeel _objEatFeel = _arrEatFeel.get(j);
                if(GloblConst.getHour(new Date(_objEatFeel.date)) == _nHour){
                    _arrFiltered.add(_objEatFeel);
                }
            }

            double _fProgress;
            if (_nSize == 0) {
                _fProgress = 0;
            }
            else
                _fProgress = (float)_arrFiltered.size() / (float)_nSize * 100;
            _xVals.add(GloblConst.ArrHourTitle[i]);
            _yVals1.add(new BarEntry(i,(float)_fProgress ));
        }

        XAxis  _xAxis = _chartEat.getXAxis();
        XAxisValueFormatter _customFormatter = new XAxisValueFormatter();
        _customFormatter.mValues = _xVals;
        _xAxis.setLabelCount(6);
        _xAxis.setValueFormatter(_customFormatter);

        BarDataSet set1;
        set1 = new BarDataSet(_yVals1, "When Do I Usually Cheat?");
        set1.setColor(Color.BLACK);
        set1.setStackLabels(new String[]{"When Do I Usually Cheat?"});
        set1.setDrawValues(false);

        BarData data = new BarData(set1);
        data.setDrawValues(false);
        data.setBarWidth(0.2f);
        data.setValueTextSize(10);
        _chartEat.setData(data);
        _chartEat.invalidate();
    }

    public void refreshCheatWithChart(){
        ArrayList<EatFeel> _arrEatFeel =(ArrayList<EatFeel>)DataManager.shared.getEatFeelFrom(GloblConst.get60daysAgo());
        ArrayList<PieEntry> _yVals1 = new ArrayList<>();

        for (int i = 0; i < GloblConst.ArrEating.length; i++) {

            int _nSize = _arrEatFeel.size();
            ArrayList<EatFeel> _arrFiltered= new ArrayList<>();
            String _strCurEating = GloblConst.ArrEating[i];
            for (int j = 0; j < _nSize; j++){
                EatFeel _objEatFeel = _arrEatFeel.get(j);
                if(_objEatFeel.eating.endsWith(_strCurEating)){
                    _arrFiltered.add(_objEatFeel);
                }
            }

            double _fProgress;
            if (_nSize == 0) {
                _fProgress = 0;
            }
            else
                _fProgress = (float)_arrFiltered.size() / (float)_nSize * 100;
            _yVals1.add(new PieEntry((float)_fProgress,GloblConst.ArrEating[i]));
        }

        PieDataSet dataSet = new PieDataSet(_yVals1,"");
        dataSet.setSliceSpace(3.f);
        dataSet.setColors(new int[]{0xffe28956, 0xff56aee2, 0xffe2cf56, 0xff5668e2, 0xffaee256,
                0xff8a56e2, 0xff68e256, 0xffcf56e2});
       //chocolate//ice cream//candy//desserts//bread//chips//other carbs//alchol


        PieData data = new PieData(dataSet);
        DecimalFormat _decFormat = new DecimalFormat();
        _decFormat.setMaximumFractionDigits(1);
        _decFormat.setMultiplier(1);
        PercentFormatter _customFormatter = new PercentFormatter(_decFormat);
        data.setValueFormatter(_customFormatter);
        data.setValueTextSize(11);
        data.setValueTextColor(Color.BLACK);

        _chartCheatWith.setData(data);
        _chartCheatWith.invalidate();
    }

    public void refreshFeelingWhenCheatChart(){
        ArrayList<EatFeel> _arrEatFeel =(ArrayList<EatFeel>)DataManager.shared.getEatFeelFrom(GloblConst.get60daysAgo());
        ArrayList<PieEntry> _yVals1 = new ArrayList<>();

        for (int i = 0; i < GloblConst.ArrFeeling.length; i++) {

            int _nSize = _arrEatFeel.size();
            ArrayList<EatFeel> _arrFiltered= new ArrayList<>();
            String _strCurFeeling = GloblConst.ArrFeeling[i];
            for (int j = 0; j < _nSize; j++){
                EatFeel _objEatFeel = _arrEatFeel.get(j);
                if(_objEatFeel.feeling.endsWith(_strCurFeeling)){
                    _arrFiltered.add(_objEatFeel);
                }
            }

            double _fProgress;
            if (_nSize == 0) {
                _fProgress = 0;
            }
            else
                _fProgress = (float)_arrFiltered.size() / (float)_nSize * 100;
            _yVals1.add(new PieEntry((float)_fProgress,GloblConst.ArrFeeling[i]));
        }

        PieDataSet dataSet = new PieDataSet(_yVals1,"");
        dataSet.setSliceSpace(3.f);
        dataSet.setColors(new int[]{0xffe28956,0xff56aee2,0xffe2cf56,0xff5668e2,0xffaee256,
                0xff8a56e2,0xff68e256});
        //Angry//Tired//Lonely//Depressed//Stressed//Celegrating//Bored

        PieData data = new PieData(dataSet);
        DecimalFormat _decFormat = new DecimalFormat();
        _decFormat.setMaximumFractionDigits(1);
        _decFormat.setMultiplier(1);
        PercentFormatter _customFormatter = new PercentFormatter(_decFormat);
        data.setValueFormatter(_customFormatter);
        data.setValueTextSize(11);
        data.setValueTextColor(Color.BLACK);

        _chartCheatWith.setData(data);
        _chartCheatWith.invalidate();
    }

    public void initEatChart(){
        _chartEat.getDescription().setEnabled(false);
        _chartEat.setNoDataText("You need to provide data for the chart.");

        _chartEat.setDrawBarShadow(false);
        _chartEat.setDrawValueAboveBar(true);
        _chartEat.setMaxVisibleValueCount(60);
        _chartEat.setPinchZoom(false);
        _chartEat.setDrawGridBackground(false);

        XAxis _xAxis = _chartEat.getXAxis();
        _xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        _xAxis.setDrawGridLines(false);
        _xAxis.setTextSize(10);

        //xAxis.spaceBetweenLabels = 2.f;

        YAxis _leftAxis =_chartEat.getAxisLeft();
        _leftAxis.setLabelCount(8);
        IAxisValueFormatter _customFormatter = new DefaultAxisValueFormatter(1);
        _leftAxis.setValueFormatter(_customFormatter);
        _leftAxis.setTextSize(10);
        _leftAxis.setDrawGridLines(false);
        _leftAxis.setSpaceTop(0.15f);
        _leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        _leftAxis.setAxisMinimum(0);


        _chartEat.getAxisRight().setEnabled(false);


        Legend _legend = _chartEat.getLegend();

        _legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        _legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        _legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        _legend.setForm(Legend.LegendForm.CIRCLE);
        _legend.setFormSize(9f);
        _legend.setTextSize(11f);
        _legend.setXEntrySpace(4f);

    }

    public void initCheatWithChart(){
        initCommonPieChart(_chartCheatWith,"What do I usually \ncheat with?");
    }

    public void initFeelingWhenCheatChart(){
        initCommonPieChart(_chartFeelingWhenCheat,"What am I feeling \nwhen I cheat?");
    }

    public void initCommonPieChart(PieChart aChart,String centerText){
        aChart.getDescription().setEnabled(false);
        aChart.setNoDataText("You need to provide data for the chart.");

        aChart.setDrawHoleEnabled(true);
        aChart.setHoleColor(Color.WHITE);

        aChart.setHoleRadius(48f);
        aChart.setCenterTextSize(12);
        //self.chartView.holeTransparent = YES;
        aChart.setTransparentCircleColor(Color.WHITE);
        aChart.setTransparentCircleAlpha(110);
        aChart.setTransparentCircleRadius(61f);
        aChart.setDrawCenterText(true);
        aChart.setDrawEntryLabels(false);

        aChart.setRotationAngle(0);
        aChart.setRotationEnabled(true);
        aChart.setUsePercentValues(false);
        aChart.setCenterText(centerText);
        //_chartView.drawSliceTextEnabled = NO;

        Legend _legend = aChart.getLegend();
        _legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        _legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        _legend.setWordWrapEnabled(true);
        _legend.setXEntrySpace(7f);
        _legend.setYEntrySpace(5f);
        aChart.animateXY(1500, 1500, Easing.EasingOption.EaseOutBack, Easing.EasingOption.EaseOutBack);
    }



    @Override
    public void onScrollChanged(ObservableHorizontalScrollView scrollView, int x, int y, int oldX, int oldY){

    }

    @Override
    public void onEndScroll(ObservableHorizontalScrollView scrollView){
        float currentPosition = _weight_horz_scroll.getScrollX();
        float pagesCount = 4;
        float pageLengthInPx = _weight_horz_scroll.getMeasuredWidth();// / pagesCount;
        float currentPage = currentPosition / pageLengthInPx;

        Boolean isBehindHalfScreen = currentPage - (int) currentPage > 0.5;

        float edgePosition = 0;
        if (isBehindHalfScreen) {
            edgePosition = (int) (currentPage + 1) * pageLengthInPx;
            currentPage = currentPage + 1;
        } else {
            edgePosition = (int) currentPage * pageLengthInPx;
        }
        _naviChart.setPosition((int)currentPage);
        _naviChart.invalidate();
        _weight_horz_scroll.scrollTo((int) edgePosition, 0);
    }

    @Override
    public void onMenuAction(){
        Intent _intent = new Intent(getActivity(), AllEntriesActivity.class);
        startActivity(_intent);
    }
}
