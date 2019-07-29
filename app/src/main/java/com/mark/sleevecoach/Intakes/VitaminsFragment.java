package com.mark.sleevecoach.Intakes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.kyleduo.switchbutton.SwitchButton;
import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.R;
import com.mark.sleevecoach.model.DataManager;
import com.mark.sleevecoach.model.InTake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1 on 4/5/2017.
 */
public class VitaminsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    InTake objITToday,objITYesterday;
    SwitchButton _btnToday,_btnYesterday;
    ImageView _ivToday,_ivYest;
    BarChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vitamins, container, false);
        objITToday = DataManager.shared.getTodayIntake();
        objITYesterday = DataManager.shared.getYesterdayIntake();
        _btnToday = (SwitchButton)rootView.findViewById(R.id.vitamins_sb_today);
        _btnYesterday = (SwitchButton)rootView.findViewById(R.id.vitamins_sb_yesterday);
        _ivToday  = (ImageView)rootView.findViewById(R.id.vitamins_vitamins_today_iv_star);
        _ivYest  = (ImageView)rootView.findViewById(R.id.vitamins_vitamins_yest_iv_star);

        mChart = (BarChart)rootView.findViewById(R.id.vitamins_chart);

        _btnToday.setChecked(objITToday.bVitamins);
        _btnYesterday.setChecked(objITYesterday.bVitamins);
        _btnToday.setOnCheckedChangeListener(this);
        _btnYesterday.setOnCheckedChangeListener(this);

        initChart();
        updateUI();

        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        InTake _objCurSel;
        if(compoundButton.getId() == R.id.vitamins_sb_today){
            _objCurSel = objITToday;
        }
        else{
            _objCurSel = objITYesterday;
        }
        _objCurSel.bVitamins = b;
        _objCurSel.save();
        //kch- notificaton [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFY_CHANGE_EXERCISE object:nil];
        updateUI();
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

        XAxis _xAxis = mChart.getXAxis();
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
        _entry1.label = "1 Star";
        _entry1.formColor = getResources().getColor(R.color.gold);
        _arrLegends.add(_entry1);

        _entry1 = new LegendEntry();
        _entry1.label = "No Stars";
        _entry1.formColor = Color.BLACK;
        _arrLegends.add(_entry1);

        _legend.setCustom(_arrLegends);
    }

    public void updateUI(){
        int _nTodayStars = objITToday.getVitaminEarnStarCount();
        if (_nTodayStars == 1)
            _ivToday.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_h"));
        else
            _ivToday.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_d"));


        int _nYestStars = objITYesterday.getVitaminEarnStarCount();
        if (_nYestStars == 1)
            _ivYest.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_h"));
        else
            _ivYest.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_h"));
        refreshChart();
    }

    public void refreshChart(){

        List<InTake> _arrInTakes = DataManager.shared.getIntakesFrom(GloblConst.get1MonthAgo());

        ArrayList<String> _xVals = new ArrayList<>();
        ArrayList<BarEntry> _yVals1 = new ArrayList<>();
        List<Integer> _colors = new ArrayList<Integer>();

        int _nSize = _arrInTakes.size();
        for(int i = 0; i < _nSize; i++){
            InTake _objIntake = _arrInTakes.get(i);
            _xVals.add(GloblConst.getChartFormDate(_objIntake.date));
            if(_objIntake.getVitaminEarnStarCount() == 1)
            {
                _yVals1.add(new BarEntry(i, (float) 100));
                _colors.add(getResources().getColor(R.color.gold));
            }
            else{
                _yVals1.add(new BarEntry(i, (float) 50));
                _colors.add(Color.BLACK);
            }
        }

        XAxis  _xAxis = mChart.getXAxis();
        XAxisValueFormatter _customFormatter = new XAxisValueFormatter();
        _customFormatter.mValues = _xVals;
        _xAxis.setLabelCount(_xVals.size());
        _xAxis.setValueFormatter(_customFormatter);

        BarDataSet set1;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(_yVals1);
            set1.setColors(_colors);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        }
        else{
            set1 = new BarDataSet(_yVals1, "1 Star");
            set1.setColors(_colors);

            BarData data = new BarData(set1);
            data.setDrawValues(false);
            data.setBarWidth(0.2f);
            mChart.setData(data);
        }
        mChart.invalidate();
    }

}
