package com.example.term_scheduler_bryanleano;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTermActivity extends AppCompatActivity {

    FullDatabase db;
    EditText addTitle, addStartDate, addEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        db = FullDatabase.getInstance(getApplicationContext());

    }

    public void saveTerm(View view) throws ParseException {

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

        Term tempTerm1 = new Term();
        tempTerm1.setTerm_name(title);
        tempTerm1.setTerm_start(start.getTime());
        tempTerm1.setTerm_end(end.getTime());

        db.termDao().insertTerm(tempTerm1);
        System.out.println("Added Term!");

        Intent intent = new Intent(AddTermActivity.this, TermsActivity.class);
        startActivity(intent);

    }


}