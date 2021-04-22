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

public class AddAssessmentActivity extends AppCompatActivity {

    FullDatabase db;
    EditText addTitle, addStartDate, addEndDate, addType;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        db = FullDatabase.getInstance(getApplicationContext());
        setTitle("Add Assessment");

        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt("courseId");

        System.out.println(courseId);
    }

    public void saveAssessment(View view) throws ParseException {

        addTitle = (EditText) findViewById(R.id.addTitle);
        String title = addTitle.getText().toString();

        addType = (EditText) findViewById(R.id.addType);
        String type = addType.getText().toString();

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

        Assessment tempAssessment1 = new Assessment();

        tempAssessment1.setAssessment_name(title);
        tempAssessment1.setCourse_id_fk(courseId);
        tempAssessment1.setAssessment_type(type);
        tempAssessment1.setAssessment_start(start.getTime());
        tempAssessment1.setAssessment_end(end.getTime());

        db.assessmentDao().insertAssessment(tempAssessment1);
        System.out.println("Added Assessment!");

        Intent intent = new Intent(AddAssessmentActivity.this, TermsActivity.class);
        startActivity(intent);

    }

}