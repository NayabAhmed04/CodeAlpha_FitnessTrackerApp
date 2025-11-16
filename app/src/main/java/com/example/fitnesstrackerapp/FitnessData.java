package com.example.fitnesstrackerapp;

public class FitnessData {
    private int id;
    private String date;
    private int steps;
    private int calories;
    private int workoutMinutes;

    public FitnessData(int id, String date, int steps, int calories, int workoutMinutes) {
        this.id = id;
        this.date = date;
        this.steps = steps;
        this.calories = calories;
        this.workoutMinutes = workoutMinutes;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public int getSteps() { return steps; }
    public int getCalories() { return calories; }
    public int getWorkoutMinutes() { return workoutMinutes; }
}
