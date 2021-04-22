package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    static final String LOG_TAG = "TermDetailActivity";
    ListView courseListView;
    TextView termStartTextView;
    TextView termEndTextView;
    TextView termTitleTextView;
    Intent intent;
    int termId;
    FullDatabase db;
    Term selectedTerm;
    SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        setTitle("Term Details");
        termId = intent.getIntExtra("termId", -1);
        Log.d(TermDetailsActivity.LOG_TAG, "TermId passed in: " + termId);
        selectedTerm = db.termDao().getTerm(termId);
        formatter = new SimpleDateFormat("MM/dd/yyyy");

        courseListView = findViewById(R.id.courseListView);
        termStartTextView = findViewById(R.id.termStartTextView);
        termEndTextView = findViewById(R.id.termEndTextView);
        termTitleTextView = findViewById(R.id.termTitleTextView);

        updateViews();

        courseListView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("Course Clicked: " + position);
            Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
            int courseId = db.courseDao().getCourseList(termId).get(position).getCourse_id();
            intent.putExtra("termId", termId);
            intent.putExtra("courseId", courseId);

            startActivity(intent);
        });

        updateCourseList();

    }

    private void updateCourseList() {
        List<Course> allCourses = new ArrayList<>();
        try {
            allCourses = db.courseDao().getCourseList(termId);
            System.out.println("Number of Rows in Course Query: " + allCourses.size());
        } catch (Exception e) {System.out.println("could not pull query");}

        String[] items = new String[allCourses.size()];
        if(!allCourses.isEmpty()){
            for (int i = 0; i < allCourses.size(); i++) {
                items[i] = allCourses.get(i).getCourse_name();
                System.out.println("Inside updateList Loop: " + i);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        courseListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedTerm = db.termDao().getTerm(termId);
        updateCourseList();
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
            termStartTextView.setText(temp);
            termEndTextView.setText(tempEnd);
            termTitleTextView.setText(selectedTerm.getTerm_name());
        } else {
            Log.d(TermDetailsActivity.LOG_TAG, "Selected Term is Null");
            selectedTerm = new Term();
        }

        String newTitle = "Term Details: " + selectedTerm.getTerm_name();
        setTitle(newTitle);
    }

    public void goToAddCourse(View view) {
        Intent intent = new Intent(TermDetailsActivity.this, AddCourseActivity.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    public void goToEditTerm(View view) {
        Intent intent = new Intent(TermDetailsActivity.this, EditTermActivity.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }
}

