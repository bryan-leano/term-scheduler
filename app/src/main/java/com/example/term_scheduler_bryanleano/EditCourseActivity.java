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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditCourseActivity extends AppCompatActivity {

    FullDatabase db;
    EditText editTitle, editStartDate, editEndDate, editCourseStatus, editInstructorName,
            editInstructorPhone, editInstructorEmail, editNotes;
    int termId;
    int courseId;
    Course selectedCourse;
    SimpleDateFormat formatter;
    int numAlert;
    Long notificationStartDate;
    Long notificationEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        db = FullDatabase.getInstance(getApplicationContext());
        setTitle("Edit Course Details");

        Bundle extras = getIntent().getExtras();
        termId = extras.getInt("termId");
        courseId = extras.getInt("courseId");

        System.out.println(termId);
        Log.d(TermDetailsActivity.LOG_TAG, "TermId passed in: " + termId);
        System.out.println(courseId);
        Log.d(TermDetailsActivity.LOG_TAG, "CourseId passed in: " + courseId);
        selectedCourse = db.courseDao().getCourse(termId, courseId);
        formatter = new SimpleDateFormat("MM/dd/yyyy");

        editEndDate = findViewById(R.id.editEndDate);
        editStartDate = findViewById(R.id.editStartDate);
        editTitle = findViewById(R.id.editTitle);
        editCourseStatus = findViewById(R.id.editCourseStatus);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorPhone = findViewById(R.id.editInstructorPhone);
        editInstructorEmail = findViewById(R.id.editInstructorEmail);
        editNotes = findViewById(R.id.editNotes);

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
            editStartDate.setText(temp);
            editEndDate.setText(tempEnd);
            editTitle.setText(selectedCourse.getCourse_name());
            editCourseStatus.setText(selectedCourse.getCourse_status());
            editInstructorName.setText(selectedCourse.getCourse_instructor_name());
            editInstructorPhone.setText(selectedCourse.getCourse_instructor_phone());
            editInstructorEmail.setText(selectedCourse.getCourse_instructor_email());
            editNotes.setText(selectedCourse.getCourse_notes());
        } else {
            Log.d(TermDetailsActivity.LOG_TAG, "Selected Course is Null");
        }

        String newTitle = "Edit Course: " + selectedCourse.getCourse_name();
        setTitle(newTitle);
    }

    public void saveCourse(View view) throws ParseException {

        editTitle = (EditText) findViewById(R.id.editTitle);
        String title = editTitle.getText().toString();

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

        editCourseStatus = (EditText) findViewById(R.id.editCourseStatus);
        String courseStatus = editCourseStatus.getText().toString();

        editInstructorName = (EditText) findViewById(R.id.editInstructorName);
        String instructorName = editInstructorName.getText().toString();

        editInstructorPhone = (EditText) findViewById(R.id.editInstructorPhone);
        String instructorPhone = editInstructorPhone.getText().toString();

        editInstructorEmail = (EditText) findViewById(R.id.editInstructorEmail);
        String instructorEmail = editInstructorEmail.getText().toString();

        editNotes = (EditText) findViewById(R.id.editNotes);
        String notes = editNotes.getText().toString();

        selectedCourse.setCourse_name(title);
        selectedCourse.setTerm_id_fk(termId);
        selectedCourse.setCourse_start(start.getTime());
        selectedCourse.setCourse_end(end.getTime());
        selectedCourse.setCourse_status(courseStatus);
        selectedCourse.setCourse_instructor_name(instructorName);
        selectedCourse.setCourse_instructor_phone(instructorPhone);
        selectedCourse.setCourse_instructor_email(instructorEmail);
        selectedCourse.setCourse_notes(notes);

        db.courseDao().updateCourse(selectedCourse);
        System.out.println("This works");

        Intent intent = new Intent(EditCourseActivity.this, TermsActivity.class);
        startActivity(intent);

    }

    public void deleteCourse(View view) {
        db.courseDao().deleteCourse(selectedCourse);
        System.out.println("Course has been deleted");

        Intent intent = new Intent(EditCourseActivity.this, TermsActivity.class);
        startActivity(intent);
    }

}