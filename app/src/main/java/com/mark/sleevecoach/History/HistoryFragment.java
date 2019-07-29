package com.mark.sleevecoach.History;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Intakes.XAxisValueFormatter;
import com.mark.sleevecoach.R;
import com.mark.sleevecoach.model.DataManager;
import com.mark.sleevecoach.model.InTake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1 on 4/7/2017.
 */
public class HistoryFragment extends Fragment {

    BarChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        mChart = (BarChart)rootView.findViewById(R.id.history_chart_main);
        initChart();
        refreshChart();

        return rootView;
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
        _leftAxis.setValueFormatter(new DefaultAxisValueFormatter(1));
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
        _entry1.label = "10 Stars";
        _entry1.formColor = getResources().getColor(R.color.gold);
        _arrLegends.add(_entry1);

        _entry1 = new LegendEntry();
        _entry1.label = "Other";
        _entry1.formColor = Color.BLACK;
        _arrLegends.add(_entry1);

        _legend.setCustom(_arrLegends);
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
            int _nStars = _objIntake.getAllStarCount();

            switch (_nStars) {
                case 10:
                {
                    _yVals1.add(new BarEntry(i, 10));
                    _colors.add(getResources().getColor(R.color.gold));
                }
                break;
                default:
                    _yVals1.add(new BarEntry(i,_nStars));
                    _colors.add(Color.BLACK);
                    break;
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
            set1 = new BarDataSet(_yVals1, "10 Stars");
            set1.setColors(_colors);
            BarData data = new BarData(set1);
            data.setDrawValues(false);
            data.setBarWidth(0.2f);
            mChart.setData(data);
        }

        mChart.invalidate();
    }
}
