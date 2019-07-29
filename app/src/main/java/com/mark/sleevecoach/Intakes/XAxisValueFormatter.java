package com.mark.sleevecoach.Intakes;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by user1 on 4/5/2017.
 */
public class XAxisValueFormatter implements IAxisValueFormatter
{
    public List<String> mValues;
    public XAxisValueFormatter() {

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if(value >= mValues.size())
            return "";

        return mValues.get((int)value);
    }
}
