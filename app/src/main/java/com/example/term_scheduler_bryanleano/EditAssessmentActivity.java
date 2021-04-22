package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditAssessmentActivity extends AppCompatActivity {

    EditText editTitle, editStartDate, editEndDate, editType;
    Intent intent;
    int assessmentId;
    int courseId;
    FullDatabase db;
    Assessment selectedAssessment;
    SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        setTitle("Assessment Details");
        courseId = intent.getIntExtra("courseId", -1);
        assessmentId = intent.getIntExtra("assessmentId", -1);
        Log.d(TermDetailsActivity.LOG_TAG, "assessmentId passed in: " + assessmentId);
        selectedAssessment = db.assessmentDao().getAssessment(courseId, assessmentId);
        formatter = new SimpleDateFormat("MM/dd/yyyy");

        editTitle = findViewById(R.id.editTitle);
        editType = findViewById(R.id.editType);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);

        updateViews();
    }

    private void updateViews() {
        if(selectedAssessment != null) {
            Log.d(TermDetailsActivity.LOG_TAG, "selected Assessment is not null");
            Date startDate = selectedAssessment.getAssessment_start();
            Date endDate = selectedAssessment.getAssessment_end();
            System.out.println("Milliseconds Date: " + startDate.toString());
            String temp = formatter.format(startDate);
            String tempEnd = formatter.format(endDate);
            System.out.println("Formatted date: " + temp);
            editStartDate.setText(temp);
            editEndDate.setText(tempEnd);
            editTitle.setText(selectedAssessment.getAssessment_name());
            editType.setText(selectedAssessment.getAssessment_type());

        } else {
            Log.d(TermDetailsActivity.LOG_TAG, "Selected Assessment is Null");
            selectedAssessment = new Assessment();
        }

        String newTitle = "Assessment Details: " + selectedAssessment.getAssessment_name();
        setTitle(newTitle);
    }

    public void saveAssessment(View view) throws ParseException {

        editTitle = (EditText) findViewById(R.id.editTitle);
        String title = editTitle.getText().toString();

        editType = (EditText) findViewById(R.id.editType);
        String type = editType.getText().toString();

        editStartDate = (EditText) findViewById(R.id.editStartDate);
        String startDate = editStartDate.getText().toString();

        editEndDate = (EditText) findViewById(R.id.editEndDate);
        String endDate = editEndDate.getText().toString();

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate1 = formatter.parse(startDate);
        Date endDate1 = formatter.parse(endDate);

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate1);
        end.setTime(endDate1);

        selectedAssessment.setAssessment_name(title);
        selectedAssessment.setCourse_id_fk(courseId);
        selectedAssessment.setAssessment_type(type);
        selectedAssessment.setAssessment_start(start.getTime());
        selectedAssessment.setAssessment_end(end.getTime());

        db.assessmentDao().updateAssessment(selectedAssessment);
        System.out.println("Updated Assessment!");

        Intent intent = new Intent(EditAssessmentActivity.this, TermsActivity.class);
        startActivity(intent);

    }

    public void deleteAssessment(View view) {
        db.assessmentDao().deleteAssessment(selectedAssessment);
        System.out.println("Assessment has been deleted");

        Intent intent = new Intent(EditAssessmentActivity.this, TermsActivity.class);
        startActivity(intent);
    }

}