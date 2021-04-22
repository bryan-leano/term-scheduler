package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class TermsActivity extends AppCompatActivity {

    public static String LOG_TAG = "TermsActivityLog";
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
        setContentView(R.layout.activity_terms);
        setTitle("Terms List");
        listView = findViewById(R.id.listView);
        db = FullDatabase.getInstance(getApplicationContext());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position clicked: " + position);
                Intent intent = new Intent(getApplicationContext(), TermDetailsActivity.class);
                int term_id;
                List<Term> termsList = db.termDao().getTermList();
                term_id = termsList.get(position).getTerm_id();
                intent.putExtra("termId", term_id);
                System.out.println("termID selected = " + String.valueOf(term_id));
                startActivity(intent);
            }
        });

        updateList();

    }

    public void updateList() {
        List<Term> allTerms = db.termDao().getTermList();
        System.out.println("Number of Rows in Terms Table: " + allTerms.size());

        String[] items = new String[allTerms.size()];
        if(!allTerms.isEmpty()){
            for (int i = 0; i < allTerms.size(); i++) {
                items[i] = allTerms.get(i).getTerm_name();
                System.out.println("Term in position = " + i + " with Name = " + items[i]);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void goToAddTerm(View view) {
        Intent intent = new Intent(TermsActivity.this, AddTermActivity.class);
        startActivity(intent);
    }
}