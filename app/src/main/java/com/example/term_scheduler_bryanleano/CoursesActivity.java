package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    public static String LOG_TAG = "CoursesActivityLog";
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
        setContentView(R.layout.activity_courses);
        setTitle("Courses List");
        listView = findViewById(R.id.listView);
        db = FullDatabase.getInstance(getApplicationContext());

        updateList();

    }

    public void updateList() {
        List<Course> allCourses = db.courseDao().getAllCourses();
        System.out.println("Number of Rows in Courses Table: " + allCourses.size());

        String[] items = new String[allCourses.size()];
        if(!allCourses.isEmpty()){
            for (int i = 0; i < allCourses.size(); i++) {
                items[i] = allCourses.get(i).getCourse_name();
                System.out.println("Course in position = " + i + " with Name = " + items[i]);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}