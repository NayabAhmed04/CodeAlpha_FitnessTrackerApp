package com.example.fitnesstrackerapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etDate, etSteps, etCalories, etWorkout;
    Button btnAdd;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etDate = findViewById(R.id.etDate);
        etSteps = findViewById(R.id.etSteps);
        etCalories = findViewById(R.id.etCalories);
        etWorkout = findViewById(R.id.etWorkout);
        btnAdd = findViewById(R.id.btnAdd);

        db = new DBHelper(this);

        etDate.setOnClickListener(v -> showDatePicker());

        btnAdd.setOnClickListener(v -> addData());
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> etDate.setText(year1 + "-" + (month1 + 1) + "-" + dayOfMonth),
                year, month, day);
        dpd.show();
    }

    private void addData() {
        String date = etDate.getText().toString().trim();
        String stepsStr = etSteps.getText().toString().trim();
        String caloriesStr = etCalories.getText().toString().trim();
        String workoutStr = etWorkout.getText().toString().trim();

        if (date.isEmpty() || stepsStr.isEmpty() || caloriesStr.isEmpty() || workoutStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        int steps = Integer.parseInt(stepsStr);
        int calories = Integer.parseInt(caloriesStr);
        int workout = Integer.parseInt(workoutStr);

        boolean inserted = db.insertData(date, steps, calories, workout);
        if (inserted) {
            Toast.makeText(this, "Data Added Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Insert Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
