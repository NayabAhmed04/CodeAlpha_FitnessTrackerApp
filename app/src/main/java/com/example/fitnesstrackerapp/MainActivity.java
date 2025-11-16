package com.example.fitnesstrackerapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tvSteps, tvCalories, tvWorkout;
    ProgressBar pbSteps, pbCalories, pbWorkout;
    DBHelper db;
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSteps = findViewById(R.id.tvSteps);
        tvCalories = findViewById(R.id.tvCalories);
        tvWorkout = findViewById(R.id.tvWorkout);

        pbSteps = findViewById(R.id.pbSteps);
        pbCalories = findViewById(R.id.pbCalories);
        pbWorkout = findViewById(R.id.pbWorkout);

        chart = findViewById(R.id.barChart);

        db = new DBHelper(this);

        displayTodaySummary();
        displayWeeklyGraph();
    }

    private void displayTodaySummary() {
        Cursor cursor = db.getAllData();
        int totalSteps = 0, totalCalories = 0, totalWorkout = 0;
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if (date.equals(today)) {
                    totalSteps += cursor.getInt(cursor.getColumnIndex("steps"));
                    totalCalories += cursor.getInt(cursor.getColumnIndex("calories"));
                    totalWorkout += cursor.getInt(cursor.getColumnIndex("workoutMinutes"));
                }
            } while (cursor.moveToNext());
        }

        tvSteps.setText(totalSteps + " Steps");
        tvCalories.setText(totalCalories + " Calories");
        tvWorkout.setText(totalWorkout + " Min");

        pbSteps.setProgress(totalSteps);
        pbCalories.setProgress(totalCalories);
        pbWorkout.setProgress(totalWorkout);
    }

    private void displayWeeklyGraph() {
        Cursor cursor = db.getAllData();
        ArrayList<BarEntry> stepsEntries = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -6); // last 7 days
        String[] days = new String[7];

        for (int i = 0; i < 7; i++) {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());
            days[i] = new SimpleDateFormat("EEE", Locale.getDefault()).format(c.getTime()); // Mon, Tue...
            int daySteps = 0;

            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(cursor.getColumnIndex("date")).equals(date)) {
                        daySteps += cursor.getInt(cursor.getColumnIndex("steps"));
                    }
                } while (cursor.moveToNext());
            }
            stepsEntries.add(new BarEntry(i, daySteps));
            c.add(Calendar.DAY_OF_MONTH, 1);
        }

        BarDataSet dataSet = new BarDataSet(stepsEntries, "Steps (Weekly)");
        dataSet.setColor(getResources().getColor(R.color.purple_500));
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);

        chart.setData(data);
        chart.getXAxis().setValueFormatter((value, axis) -> days[(int) value % days.length]);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }
}
