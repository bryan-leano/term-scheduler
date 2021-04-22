package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    static final String LOG_TAG = "CourseDetailActivity";
    ListView assessmentListView;
    TextView courseStartTextView, courseEndTextView, courseTitleTextView, courseInstructorNameTextView,
    courseInstructorPhoneTextView, courseInstructorEmailTextView, courseStatusTextView;
    Intent intent;
    int termId;
    int courseId;
    FullDatabase db;
    Course selectedCourse;
    SimpleDateFormat formatter;
    int numAlert;
    Long notificationStartDate;
    Long notificationEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        setTitle("Course Details");
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        Log.d(TermDetailsActivity.LOG_TAG, "TermId passed in: " + termId);
        selectedCourse = db.courseDao().getCourse(termId, courseId);
        formatter = new SimpleDateFormat("MM/dd/yyyy");

        assessmentListView = findViewById(R.id.assessmentListView);
        courseStartTextView = findViewById(R.id.courseStartTextView);
        courseEndTextView = findViewById(R.id.courseEndTextView);
        courseTitleTextView = findViewById(R.id.courseTitleTextView);
        courseInstructorNameTextView = findViewById(R.id.courseInstructorNameTextView);
        courseInstructorPhoneTextView = findViewById(R.id.courseInstructorPhoneTextView);
        courseInstructorEmailTextView = findViewById(R.id.courseInstructorEmailTextView);
        courseStatusTextView = findViewById(R.id.courseStatusTextView);

        updateViews();

        assessmentListView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("Assessment Clicked: " + position);
            Intent intent = new Intent(getApplicationContext(), AssessmentDetailsActivity.class);
            int assessmentId = db.assessmentDao().getAssessmentList(courseId).get(position).getAssessment_id();
            intent.putExtra("courseId", courseId);
            intent.putExtra("assessmentId", assessmentId);

            startActivity(intent);
        });

    }

    private void updateAssessmentList() {
        List<Assessment> allAssessments = new ArrayList<>();
        try {
            allAssessments = db.assessmentDao().getAssessmentList(courseId);
            System.out.println("Number of Rows in Assessment Query: " + allAssessments.size());
        } catch (Exception e) {System.out.println("could not pull query");}

        String[] items = new String[allAssessments.size()];
        if(!allAssessments.isEmpty()){
            for (int i = 0; i < allAssessments.size(); i++) {
                items[i] = allAssessments.get(i).getAssessment_name();
                System.out.println("Inside updateList Loop: " + i);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        assessmentListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedCourse = db.courseDao().getCourse(termId, courseId);
        updateAssessmentList();
        updateViews();
    }

    private void updateViews() {
        if(selectedCourse != null) {
            Log.d(TermDetailsActivity.LOG_TAG, "selected Course is not null");
            Date startDate = selectedCourse.getCourse_start();
            Date endDate = selectedCourse.getCourse_end();
            System.out.println("Milliseconds Date: " + startDate.toString());
            String temp = formatter.format(startDate);
            String tempEnd = formatter.format(endDate);
            System.out.println("Formatted date: " + temp);
            courseStartTextView.setText(temp);
            courseEndTextView.setText(tempEnd);
            courseTitleTextView.setText(selectedCourse.getCourse_name());
            courseInstructorNameTextView.setText(selectedCourse.getCourse_instructor_name());
            courseInstructorPhoneTextView.setText(selectedCourse.getCourse_instructor_phone());
            courseInstructorEmailTextView.setText(selectedCourse.getCourse_instructor_email());
            courseStatusTextView.setText(selectedCourse.getCourse_status());
        } else {
            Log.d(TermDetailsActivity.LOG_TAG, "Selected Term is Null");
            selectedCourse = new Course();
        }

        String newTitle = "Course Details: " + selectedCourse.getCourse_name();
        setTitle(newTitle);
    }

    public void goToEditCourse(View view) {
        Intent intent = new Intent(CourseDetailsActivity.this, EditCourseActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("courseId", courseId);
        extras.putInt("termId", termId);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void goToAddAssessment(View view) {
        Intent intent = new Intent(CourseDetailsActivity.this, AddAssessmentActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("courseId", courseId);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void goToCourseNotes(View view) {
        Intent intent = new Intent(CourseDetailsActivity.this, CourseNotesActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("courseId", courseId);
        extras.putInt("termId", termId);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void createCourseStartAlert(View view) {
        Date startDate = selectedCourse.getCourse_start();
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Intent intent = new Intent(CourseDetailsActivity.this, ReminderBroadcast.class);
        intent.putExtra("key", "Course start date notification");
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetailsActivity.this, ++numAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        notificationStartDate = start.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, notificationStartDate, sender);
    }

    public void createCourseEndAlert(View view) {
        Date endDate = selectedCourse.getCourse_end();
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        Intent intent = new Intent(CourseDetailsActivity.this, ReminderBroadcast.class);
        intent.putExtra("key", "Course end date notification");
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetailsActivity.this, ++numAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        notificationEndDate = end.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, notificationEndDate, sender);
    }

}