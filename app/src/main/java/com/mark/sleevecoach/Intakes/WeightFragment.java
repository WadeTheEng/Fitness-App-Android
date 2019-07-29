package com.mark.sleevecoach.Intakes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Global.HegWeightUnit;
import com.mark.sleevecoach.Helper.Gallery.GalleryNavigator;
import com.mark.sleevecoach.Helper.Gallery.ObservableHorizontalScrollView;
import com.mark.sleevecoach.R;
import com.mark.sleevecoach.model.DataManager;
import com.mark.sleevecoach.model.Goal;
import com.mark.sleevecoach.model.InTake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1 on 4/5/2017.
 */
public class WeightFragment extends Fragment  implements ObservableHorizontalScrollView.OnScrollListener, View.OnClickListener {

    OptionsPickerView pvInputValue;
    LineChart _chartBMI, _chartWeightTime, _chartFeature;
    ObservableHorizontalScrollView _weight_horz_scroll;
    GalleryNavigator _naviChart;

    TextView _txtBMI;
    Button _btnSave,_btnSelValue;

    Goal objCurrentGoal;
    InTake objITToday;
    double nCurWeight;

    int nWeightInd;

    ArrayList<String> arrWeightRange = new ArrayList<>();
    ArrayList<Double> arrWeightRangeValue = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weight, container, false);

        _weight_horz_scroll =(ObservableHorizontalScrollView)rootView.findViewById(R.id.weight_horz_scroll);
        _chartBMI = (LineChart)rootView.findViewById(R.id.weight_chart_bmi);
        _chartWeightTime = (LineChart)rootView.findViewById(R.id.weight_chart_weighttime);
        _chartFeature = (LineChart)rootView.findViewById(R.id.weight_chart_feature);
        _weight_horz_scroll.post(new Runnable() {
            @Override
            public void run() {
                int _nWidth = _weight_horz_scroll.getMeasuredWidth();
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) _chartBMI.getLayoutParams();
                params.width = _nWidth;
                _chartBMI.setLayoutParams(params);

                params = (ViewGroup.LayoutParams) _chartWeightTime.getLayoutParams();
                params.width = _nWidth;
                _chartWeightTime.setLayoutParams(params);

                params = (ViewGroup.LayoutParams) _chartFeature.getLayoutParams();
                params.width = _nWidth;
                _chartFeature.setLayoutParams(params);
            }
        });
        _weight_horz_scroll.setOnScrollListener(this);
        _naviChart = (GalleryNavigator)rootView.findViewById(R.id.weight_nav_chart);
        _txtBMI = (TextView)rootView.findViewById(R.id.weight_txt_bmi);
        _btnSave = (Button)rootView.findViewById(R.id.weight_btn_save);
        _btnSelValue = (Button)rootView.findViewById(R.id.weight_btn_selamount);
        _btnSave.setOnClickListener(this);
        _btnSelValue.setOnClickListener(this);

        objCurrentGoal = DataManager.shared.getGoal();
        objITToday = DataManager.shared.getTodayIntake();
        nCurWeight = objITToday.nWeight;

        _naviChart.setSize(3);
        _naviChart.invalidate();
        resetUnitRanges();
        initChart();
        refreshUI();
        refreshChart();
        return rootView;
    }

    public void resetUnitRanges(){
        nWeightInd = 0;
        arrWeightRange.removeAll(arrWeightRange);
        arrWeightRangeValue.removeAll(arrWeightRangeValue);

        if (objCurrentGoal.getHegWeightUnitValue() == HegWeightUnit.HegWeight_PoundInch) {
            for (int i = GloblConst.PoundMin; i <= GloblConst.PoundMax ; i+= 1) {
                arrWeightRange.add(String.valueOf(i) + " " +GloblConst.getWeightUnitString(objCurrentGoal.getHegWeightUnitValue())) ;
                arrWeightRangeValue.add(Double.valueOf((double) i));
            }
        }
        else{
            for (float i = GloblConst.KilogMin; i <= GloblConst.KilogMax ; i+= .5) {
                arrWeightRange.add(String.valueOf(i) + " " + GloblConst.getWeightUnitString(objCurrentGoal.getHegWeightUnitValue())) ;
                arrWeightRangeValue.add(Double.valueOf((double)i));
            }
        }

    }
    public  void refreshUI(){
        _btnSelValue.setText(String.format("%.0f %s", nCurWeight, GloblConst.getWeightUnitString(objCurrentGoal.getHegWeightUnitValue())));
        _txtBMI.setText((String.format("(BMI: %.2f)", GloblConst.getBMI(objCurrentGoal.getHegWeightUnitValue(), objCurrentGoal.nHeight, nCurWeight))));

    }

    public void saveValue(){
        nCurWeight = arrWeightRangeValue.get(nWeightInd);
        objITToday.nWeight = nCurWeight;
        objITToday.save();
        Toast.makeText(getActivity(),"Saved!",Toast.LENGTH_SHORT).show();
        refreshChart();
        refreshUI();
    }

    public void initChart(){
        initChartBMI();
        initChartWeightVSTime();
    }

    public void initChartBMI(){
        _chartBMI.setNoDataText("You need to provide data for the chart.");
        _chartBMI.getDescription().setText("BMI vs Time");
        _chartBMI.setDragEnabled(true);
        _chartBMI.setScaleEnabled(true);
        _chartBMI.setPinchZoom(true);
        _chartBMI.setDrawGridBackground(false);

        LimitLine ll1 = new LimitLine((float)objCurrentGoal.getGoalBMIValue(),"Goal BMI");
        ll1.setLineColor(Color.GREEN);
        ll1.setTextColor(Color.GREEN);
        ll1.setLineWidth(2.0f);
        ll1.enableDashedLine(2.0f, 2.0f, 0.0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10);

        LimitLine ll2 = new LimitLine((float)objCurrentGoal.getSurgeryBMIValue(),"Surgery BMI");
        ll2.setLineColor(Color.BLUE);
        ll2.setTextColor(Color.BLUE);
        ll2.setLineWidth(2.0f);
        ll2.enableDashedLine(2.0f, 2.0f, 0.0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll2.setTextSize(10);

        LimitLine ll3 = new LimitLine((float)objCurrentGoal.getHighBMIValue(),"High BMI");
        ll3.setLineColor(Color.RED);
        ll3.setTextColor(Color.RED);
        ll3.setLineWidth(2.0f);
        ll3.enableDashedLine(2.0f, 2.0f, 0.0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll3.setTextSize(10);

        _chartBMI.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis _leftAxis = _chartBMI.getAxisLeft();
        _leftAxis.addLimitLine(ll1);
        _leftAxis.addLimitLine(ll2);
        _leftAxis.addLimitLine(ll3);

        if (objCurrentGoal.getHegWeightUnitValue() == HegWeightUnit.HegWeight_PoundInch) {
            _leftAxis.setAxisMaximum((float)objCurrentGoal.getHighestBMI() + 20.0f) ;
        }
        else{
            _leftAxis.setAxisMaximum((float)objCurrentGoal.getHighestBMI() + 20.0f) ;
        }
        _leftAxis.setAxisMinimum(0f);
        _leftAxis.setGridLineWidth(2.0f);
        _leftAxis.setDrawLimitLinesBehindData(true);
        _leftAxis.setDrawZeroLine(false);

        _chartBMI.getAxisRight().setEnabled(false);
        _chartBMI.getLegend().setForm(Legend.LegendForm.LINE);
        _chartBMI.animateX(1500, Easing.EasingOption.EaseInOutQuart);
    }

    public void initChartWeightVSTime(){
        _chartWeightTime.setNoDataText("You need to provide data for the chart.");
        _chartWeightTime.setContentDescription("Weight vs Time");
        _chartWeightTime.setDragEnabled(true);
        _chartWeightTime.setScaleEnabled(true);
        _chartWeightTime.setPinchZoom(true);
        _chartWeightTime.setDrawGridBackground(false);
        LimitLine ll1 = new LimitLine((float)objCurrentGoal.nGoalWeight,"Goal Weight");
        ll1.setLineColor(Color.GREEN);
        ll1.setTextColor(Color.GREEN);
        ll1.setLineWidth(2.0f);
        ll1.enableDashedLine(2.0f, 2.0f, 0.0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10);

        LimitLine ll2 = new LimitLine((float)objCurrentGoal.nSurgeryWeight,"Surgery Weight");
        ll2.setLineColor(Color.BLUE);
        ll2.setTextColor(Color.BLUE);
        ll2.setLineWidth(2.0f);
        ll2.enableDashedLine(2.0f, 2.0f, 0.0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll2.setTextSize(10);

        LimitLine ll3 = new LimitLine((float)objCurrentGoal.nHighWeight,"High Weight");
        ll3.setLineColor(Color.RED);
        ll3.setTextColor(Color.RED);
        ll3.setLineWidth(2.0f);
        ll3.enableDashedLine(2.0f, 2.0f, 0.0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll3.setTextSize(10);

        _chartWeightTime.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis _leftAxis = _chartWeightTime.getAxisLeft();
        _leftAxis.addLimitLine(ll1);
        _leftAxis.addLimitLine(ll2);
        _leftAxis.addLimitLine(ll3);

        if (objCurrentGoal.getHegWeightUnitValue() == HegWeightUnit.HegWeight_PoundInch) {
            _leftAxis.setAxisMaximum((float)objCurrentGoal.getHighestWeight() + 20.0f) ;
            _leftAxis.setAxisMinimum(GloblConst.PoundMin - 20f);
        }
        else{
            _leftAxis.setAxisMaximum((float)objCurrentGoal.getHighestWeight() + 20.0f) ;
            _leftAxis.setAxisMinimum(GloblConst.KilogMin - 20f);
        }

        //leftAxis.startAtZeroEnabled = YES;
        _leftAxis.setGridLineWidth(2.0f);
        _leftAxis.setDrawLimitLinesBehindData(true);
        _leftAxis.setDrawZeroLine(false);

        _chartWeightTime.getAxisRight().setEnabled(false);
        _chartWeightTime.getLegend().setForm(Legend.LegendForm.LINE);
        _chartWeightTime.animateX(1500, Easing.EasingOption.EaseInOutQuart);
    }

    public void refreshChart(){
        refreshChartBMI();
        refreshChartWeightVSTime();
    }

    public void refreshChartBMI(){
        List<InTake> _arrIntakes = DataManager.shared.getIntakesFrom(GloblConst.get1YearAgo());

        ArrayList<String> _xVals = new ArrayList<>();
        ArrayList<Entry> _yVals1 = new ArrayList<>();

        for (int i = 0; i < _arrIntakes.size(); i++)
        {
            InTake _objIntake = _arrIntakes.get(i);
            _xVals.add(GloblConst.getChartFormDate(_objIntake.date));
            _yVals1.add(new Entry(i,(float)_objIntake.getBMIValue()));
        }
        LineDataSet set1 = new LineDataSet(_yVals1,"BMI");
        set1.enableDashedLine(5.f, 2.5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1.0f);
        set1.setCircleRadius(3.0f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9.0f);
        set1.setFillAlpha(65 / 255);
        set1.setFillColor(Color.BLACK);
        set1.setDrawValues(false);

        XAxis  _xAxis = _chartBMI.getXAxis();
        XAxisValueFormatter _customFormatter = new XAxisValueFormatter();
        _customFormatter.mValues = _xVals;
        _xAxis.setLabelCount(_xVals.size());
        _xAxis.setValueFormatter(_customFormatter);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets
        LineData data = new LineData(dataSets);
        _chartBMI.setData(data);
        _chartBMI.invalidate();

    }

    public void refreshChartWeightVSTime(){
        List<InTake> _arrIntakes = DataManager.shared.getIntakesFrom(GloblConst.get1YearAgo());

        ArrayList<String> _xVals = new ArrayList<>();
        ArrayList<Entry> _yVals1 = new ArrayList<>();

        for (int i = 0; i < _arrIntakes.size(); i++)
        {
            InTake _objIntake = _arrIntakes.get(i);
            _xVals.add(GloblConst.getChartFormDate(_objIntake.date));
            _yVals1.add(new Entry(i,(float)_objIntake.getWeightValue()));
        }
        LineDataSet set1 = new LineDataSet(_yVals1,"Weight");
        set1.enableDashedLine(5.f, 2.5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1.0f);
        set1.setCircleRadius(3.0f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9.0f);
        set1.setFillAlpha(65 / 255);
        set1.setFillColor(Color.BLACK);
        set1.setDrawValues(false);

        XAxis  _xAxis = _chartWeightTime.getXAxis();
        XAxisValueFormatter _customFormatter = new XAxisValueFormatter();
        _customFormatter.mValues = _xVals;
        _xAxis.setLabelCount(_xVals.size());
        _xAxis.setValueFormatter(_customFormatter);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets
        LineData data = new LineData(dataSets);
        _chartWeightTime.setData(data);
        _chartWeightTime.invalidate();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.weight_btn_save){
            saveValue();
            //refresh chart
        }
        else{//select amount
            pvInputValue = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3,int options4, View v) {
                    //返回的分别是三个级别的选中位置
                    nWeightInd = options1;
                    nCurWeight = arrWeightRangeValue.get(nWeightInd);
                    refreshUI();
                }

                @Override
                public void onOptionsAdd(int options1, int options2, int options3, int options4, View v) {
                    nWeightInd = options1;
                    saveValue();
                }

            }).build();
            pvInputValue.setSubmitButtonTitle("Save");
            pvInputValue.setSelectOptions(nWeightInd);
            pvInputValue.setNPicker(arrWeightRange, null,null,null);
            pvInputValue.show();
        }
    }

    @Override
    public void onScrollChanged(ObservableHorizontalScrollView scrollView, int x, int y, int oldX, int oldY){

    }

    @Override
    public void onEndScroll(ObservableHorizontalScrollView scrollView){
        float currentPosition = _weight_horz_scroll.getScrollX();
        float pagesCount = 3;
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


}
