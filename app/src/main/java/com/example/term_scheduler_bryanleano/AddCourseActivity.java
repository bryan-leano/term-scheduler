package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCourseActivity extends AppCompatActivity {

    FullDatabase db;
    EditText addTitle, addStartDate, addEndDate, addCourseStatus, addInstructorName,
            addInstructorPhone, addInstructorEmail, addNotes;
    int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        db = FullDatabase.getInstance(getApplicationContext());
        setTitle("Add Course");

        Bundle extras = getIntent().getExtras();
        termId = extras.getInt("termId");

        System.out.println(termId);
    }

    public void saveCourse(View view) throws ParseException {

        addTitle = (EditText) findViewById(R.id.addTitle);
        String title = addTitle.getText().toString();

        addStartDate = (EditText) findViewById(R.id.addStartDate);
        String startDate = addStartDate.getText().toString();

        addEndDate = (EditText) findViewById(R.id.addEndDate);
        String endDate = addEndDate.getText().toString();

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate1 = formatter.parse(startDate);
        Date endDate1 = formatter.parse(endDate);

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate1);
        end.setTime(endDate1);

        addCourseStatus = (EditText) findViewById(R.id.addCourseStatus);
        String courseStatus = addCourseStatus.getText().toString();

        addInstructorName = (EditText) findViewById(R.id.addInstructorName);
        String instructorName = addInstructorName.getText().toString();

        addInstructorPhone = (EditText) findViewById(R.id.addInstructorPhone);
        String instructorPhone = addInstructorPhone.getText().toString();

        addInstructorEmail = (EditText) findViewById(R.id.addInstructorEmail);
        String instructorEmail = addInstructorEmail.getText().toString();

        addNotes = (EditText) findViewById(R.id.addNotes);
        String notes = addNotes.getText().toString();

        Course tempCourse1 = new Course();

        tempCourse1.setCourse_name(title);
        tempCourse1.setTerm_id_fk(termId);
        tempCourse1.setCourse_start(start.getTime());
        tempCourse1.setCourse_end(end.getTime());
        tempCourse1.setCourse_status(courseStatus);
        tempCourse1.setCourse_instructor_name(instructorName);
        tempCourse1.setCourse_instructor_phone(instructorPhone);
        tempCourse1.setCourse_instructor_email(instructorEmail);
        tempCourse1.setCourse_notes(notes);

        db.courseDao().insertCourse(tempCourse1);
        System.out.println("Added Course!");

        Intent intent = new Intent(AddCourseActivity.this, TermsActivity.class);
        startActivity(intent);

    }

}