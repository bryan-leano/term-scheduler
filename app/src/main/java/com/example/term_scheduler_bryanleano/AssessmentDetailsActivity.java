package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AssessmentDetailsActivity extends AppCompatActivity {

    static final String LOG_TAG = "CourseDetailActivity";
    TextView assessmentTitleTextView, assessmentStartTextView, assessmentEndTextView, assessmentTypeTextView;
    Intent intent;
    int assessmentId;
    int courseId;
    FullDatabase db;
    Assessment selectedAssessment;
    SimpleDateFormat formatter;
    int numAlert;
    Long notificationStartDate;
    Long notificationEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        setTitle("Assessment Details");
        courseId = intent.getIntExtra("courseId", -1);
        assessmentId = intent.getIntExtra("assessmentId", -1);
        Log.d(TermDetailsActivity.LOG_TAG, "assessmentId passed in: " + assessmentId);
        selectedAssessment = db.assessmentDao().getAssessment(courseId, assessmentId);
        formatter = new SimpleDateFormat("MM/dd/yyyy");

        assessmentTitleTextView = findViewById(R.id.assessmentTitleTextView);
        assessmentTypeTextView = findViewById(R.id.assessmentTypeTextView);
        assessmentStartTextView = findViewById(R.id.assessmentStartTextView);
        assessmentEndTextView = findViewById(R.id.assessmentEndTextView);

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
            assessmentStartTextView.setText(temp);
            assessmentEndTextView.setText(tempEnd);
            assessmentTitleTextView.setText(selectedAssessment.getAssessment_name());
            assessmentTypeTextView.setText(selectedAssessment.getAssessment_type());

        } else {
            Log.d(TermDetailsActivity.LOG_TAG, "Selected Assessment is Null");
            selectedAssessment = new Assessment();
        }

        String newTitle = "Assessment Details: " + selectedAssessment.getAssessment_name();
        setTitle(newTitle);
    }

    public void goToEditAssessment(View view) {
        Intent intent = new Intent(AssessmentDetailsActivity.this, EditAssessmentActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("courseId", courseId);
        extras.putInt("assessmentId", assessmentId);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void createAssessmentStartAlert(View view) {
        Date startDate = selectedAssessment.getAssessment_start();
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Intent intent = new Intent(AssessmentDetailsActivity.this, ReminderBroadcast.class);
        intent.putExtra("key", "Assessment start date notification");
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailsActivity.this, ++numAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        notificationStartDate = start.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, notificationStartDate, sender);
    }

    public void createAssessmentEndAlert(View view) {
        Date endDate = selectedAssessment.getAssessment_start();
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        Intent intent = new Intent(AssessmentDetailsActivity.this, ReminderBroadcast.class);
        intent.putExtra("key", "Assessment end date notification");
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailsActivity.this, ++numAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        notificationEndDate = end.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, notificationEndDate, sender);
    }
}