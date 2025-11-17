package com.example.fitnesstrackerapp;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class DayValueFormatter extends ValueFormatter {
    private final String[] days;

    public DayValueFormatter(String[] days) {
        this.days = days;
    }

    @Override
    public String getFormattedValue(float value) {
        return days[(int) value % days.length];
    }
}
