package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class AssessmentsActivity extends AppCompatActivity {

    public static String LOG_TAG = "AssessmentsActivityLog";
    FullDatabase db;
    ListView listView;

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        setTitle("Assessments List");
        listView = findViewById(R.id.listView);
        db = FullDatabase.getInstance(getApplicationContext());

        updateList();

    }

    public void updateList() {
        List<Assessment> allAssessments = db.assessmentDao().getAllAssessments();
        System.out.println("Number of Rows in Assessments Table: " + allAssessments.size());

        String[] items = new String[allAssessments.size()];
        if(!allAssessments.isEmpty()){
            for (int i = 0; i < allAssessments.size(); i++) {
                items[i] = allAssessments.get(i).getAssessment_name();
                System.out.println("Assessment in position = " + i + " with Name = " + items[i]);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}