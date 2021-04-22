package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Term Scheduler");

        /*
        PopulateDatabase p = new PopulateDatabase();
        p.populate(this);
        */

    }

    public void termsList(View view) {
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);
        startActivity(intent);
    }

    public void coursesList(View view) {
        Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
        startActivity(intent);
    }

    public void assessmentsList(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentsActivity.class);
        startActivity(intent);
    }
}
