package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;

public class CourseNotesActivity extends AppCompatActivity {

    FullDatabase db;
    EditText editNotes;
    Course selectedCourse;
    int termId;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);

        db = FullDatabase.getInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        termId = extras.getInt("termId");
        courseId = extras.getInt("courseId");

        selectedCourse = db.courseDao().getCourse(termId, courseId);
        setTitle("Course Notes: " + selectedCourse.getCourse_name());

        editNotes = findViewById(R.id.editNotes);

        updateViews();
    }

    private void updateViews() {
        if(selectedCourse != null) {
            editNotes.setText(selectedCourse.getCourse_notes());
        } else {
            Log.d(TermDetailsActivity.LOG_TAG, "Selected Course is Null");
        }
    }

    public void shareNotes(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, selectedCourse.getCourse_notes());
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Course Notes: " + selectedCourse.getCourse_name());
        sendIntent.setType("text/plan");

        Intent shareItent = Intent.createChooser(sendIntent, null);
        startActivity(shareItent);
    }
}