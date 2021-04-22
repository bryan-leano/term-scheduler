package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;

public class EditTermActivity extends AppCompatActivity {

    FullDatabase db;
    EditText editTitle, editStartDate, editEndDate;
    Intent intent;
    int termId;
    Term selectedTerm;
    SimpleDateFormat formatter;
    public static int numCourses;
    List<Course> allCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        db = FullDatabase.getInstance(getApplicationContext());
        setTitle("Edit Term Details");

        Bundle extras = getIntent().getExtras();
        termId = extras.getInt("termId");

        System.out.println(termId);
        Log.d(TermDetailsActivity.LOG_TAG, "TermId passed in: " + termId);
        selectedTerm = db.termDao().getTerm(termId);
        formatter = new SimpleDateFormat("MM/dd/yyyy");

        editEndDate = findViewById(R.id.editEndDate);
        editStartDate = findViewById(R.id.editStartDate);
        editTitle = findViewById(R.id.editTitle);

        //numCourses =

        updateViews();

    }

    private void updateViews() {
        if(selectedTerm != null) {
            Log.d(TermDetailsActivity.LOG_TAG, "selected Term is not null");
            Date startDate = selectedTerm.getTerm_start();
            Date endDate = selectedTerm.getTerm_end();
            System.out.println("Milliseconds Date: " + startDate.toString());
            String temp = formatter.format(startDate);
            String tempEnd = formatter.format(endDate);
            System.out.println("Formatted date: " + temp);
            editStartDate.setText(temp);
            editEndDate.setText(tempEnd);
            editTitle.setText(selectedTerm.getTerm_name());
        } else {
            Log.d(TermDetailsActivity.LOG_TAG, "Selected Term is Null");
        }

        String newTitle = "Edit Term: " + selectedTerm.getTerm_name();
        setTitle(newTitle);
    }

    public void saveTerm(View view) throws ParseException {

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

        selectedTerm.setTerm_name(title);
        selectedTerm.setTerm_start(start.getTime());
        selectedTerm.setTerm_end(end.getTime());

        db.termDao().updateTerm(selectedTerm);
        System.out.println("This works");

        Intent intent = new Intent(EditTermActivity.this, TermsActivity.class);
        startActivity(intent);

    }

    public void deleteTerm(View view) {

        allCourses = db.courseDao().getCourseList(termId);

        if (allCourses.isEmpty()) {
            db.termDao().deleteTerm(selectedTerm);
            System.out.println("Term has been deleted");

            Intent intent = new Intent(EditTermActivity.this, TermsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Can't delete a term with courses", Toast.LENGTH_LONG).show();
        }

    }

}