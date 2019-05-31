package in.themoneytree.utils.chart;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HorizontalBarChartActivity {

    private static final String TAG = "H BAR CHART ACTIVITY";

    public static BarChart barChartDesigner(BarChart initializedBarChart, ArrayList<String> labels, ArrayList<Float> values) {
        BarChart barChart = initializedBarChart;
        if (labels.size() != values.size()) {
            Log.d(TAG, "Labels and Values Size not same");
            return null;
        }
        // Create bar
        float start = 0f;
        int valueListSize = 0;
        ArrayList<BarEntry> yvalues = new ArrayList<>();
        while (valueListSize < values.size()) {
            yvalues.add(new BarEntry(start, values.get(valueListSize)));
            start = start + 1f;
            valueListSize++;
        }
        BarDataSet dataSet = new BarDataSet(yvalues, "Tenses");
        dataSet.setDrawValues(true);

        // Create a data object from the dataSet
        BarData data = new BarData(dataSet);
        // Format data as percentage
        data.setValueFormatter(new PercentFormatter());

        // Make the chart use the acquired data
        barChart.setData(data);

        // Create the labels for the bars
        int labelListSize = 0;
        final ArrayList<String> xVals = new ArrayList<>();
        while (labelListSize < labels.size()) {
            xVals.add(labels.get(labelListSize));
            labelListSize++;
        }

        // Display labels for bars
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));

        // Set the maximum value that can be taken by the bars
        barChart.getAxisLeft().setAxisMaximum(100);

        // Bars are sliding in from left to right
        barChart.animateXY(1000, 1000);
        // Display scores inside the bars
        barChart.setDrawValueAboveBar(true);

        // Hide grid lines
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        // Hide graph description
        barChart.getDescription().setEnabled(true);
        Description description = new Description();
        description.setText("Portfolio Allocation");
        barChart.setDescription(description);
        // Hide graph legend
        barChart.getLegend().setEnabled(false);

        // Design
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        //barChart.invalidate();
        return barChart;
    }

    public static HorizontalBarChart horizontalBarChartDesigner(HorizontalBarChart initializedBarChart, ArrayList<String> labels, ArrayList<Float> values) {
        // Initialize bar chart
        HorizontalBarChart barChart = initializedBarChart;
        if (labels.size() != values.size()) {
            Log.d(TAG, "Labels and ValuesSize not same");
            return null;
        }
        // Create bar
        float start = 0f;
        int valueListSize = 0;
        ArrayList<BarEntry> yvalues = new ArrayList<>();
        while (valueListSize < values.size()) {
            yvalues.add(new BarEntry(start, values.get(valueListSize)));
            start = start + 1f;
        }
        BarDataSet dataSet = new BarDataSet(yvalues, "Tenses");
        dataSet.setDrawValues(true);

        // Create a data object from the dataSet
        BarData data = new BarData(dataSet);
        // Format data as percentage
        data.setValueFormatter(new PercentFormatter());

        // Make the chart use the acquired data
        barChart.setData(data);

        // Create the labels for the bars
        int labelListSize = 0;
        final ArrayList<String> xVals = new ArrayList<>();
        while (labelListSize < labels.size()) {
            xVals.add(labels.get(labelListSize));
        }

        // Display labels for bars
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));

        // Set the maximum value that can be taken by the bars
        barChart.getAxisLeft().setAxisMaximum(100);

        // Bars are sliding in from left to right
        barChart.animateXY(1000, 1000);
        // Display scores inside the bars
        barChart.setDrawValueAboveBar(false);

        // Hide grid lines
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        // Hide graph description
        barChart.getDescription().setEnabled(false);
        // Hide graph legend
        barChart.getLegend().setEnabled(false);

        // Design
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        barChart.invalidate();
        return barChart;
    }
}
