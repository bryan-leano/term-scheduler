package com.example.term_scheduler_bryanleano;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class PopulateDatabase {

    public static String LOG_TAG = "PopData";
    Term tempTerm1 = new Term();
    Term tempTerm2 = new Term();
    Term tempTerm3 = new Term();
    Course tempCourse1 = new Course();
    Course tempCourse2 = new Course();
    Course tempCourse3 = new Course();
    Assessment tempAssessment1 = new Assessment();
    Assessment tempAssessment2 = new Assessment();
    Assessment tempAssessment3 = new Assessment();

    FullDatabase db;

    public void populate(Context context) {
        db = FullDatabase.getInstance(context);
        try {
            insertTerms();
            insertCourses();
            insertAssessments();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Populate DB failed");
        }
    }

    private void insertTerms() {
        Calendar start;
        Calendar end;

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, -2);
        end.add(Calendar.MONTH, 1);
        tempTerm1.setTerm_name("Spring 2021");
        tempTerm1.setTerm_start(start.getTime());
        tempTerm1.setTerm_end(end.getTime());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 2);
        end.add(Calendar.MONTH, 5);
        tempTerm2.setTerm_name("Summer 2021");
        tempTerm2.setTerm_start(start.getTime());
        tempTerm2.setTerm_end(end.getTime());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 6);
        end.add(Calendar.MONTH, 9);
        tempTerm3.setTerm_name("Fall 2021");
        tempTerm3.setTerm_start(start.getTime());
        tempTerm3.setTerm_end(end.getTime());

        db.termDao().insertAll(tempTerm1, tempTerm2, tempTerm3);

    }

    private void insertCourses() {
        Calendar start;
        Calendar end;
        List<Term> termList = db.termDao().getTermList();
        if (termList == null) return;

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, -2);
        end.add(Calendar.MONTH, -1);
        tempCourse1.setCourse_name("C482 - Software I");
        tempCourse1.setCourse_start(start.getTime());
        tempCourse1.setCourse_end(end.getTime());
        tempCourse1.setCourse_notes("Notes notes notes notes");
        tempCourse1.setCourse_status("Completed");
        tempCourse1.setTerm_id_fk(termList.get(0).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, -1);
        //end.add(Calendar.WEEK_OF_MONTH, 1);
        tempCourse2.setCourse_name("C195 - Software II");
        tempCourse2.setCourse_start(start.getTime());
        tempCourse2.setCourse_end(end.getTime());
        tempCourse2.setCourse_notes("Notes notes notes notes");
        tempCourse2.setCourse_status("Completed");
        tempCourse2.setTerm_id_fk(termList.get(0).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //start.add(Calendar.MONTH, -1);
        end.add(Calendar.MONTH, 1);
        tempCourse3.setCourse_name("C196 - Mobile Applications");
        tempCourse3.setCourse_start(start.getTime());
        tempCourse3.setCourse_end(end.getTime());
        tempCourse3.setCourse_notes("Notes notes notes notes");
        tempCourse3.setCourse_status("Completed");
        tempCourse3.setTerm_id_fk(termList.get(0).getTerm_id());

        db.courseDao().insertCourse(tempCourse1);
        db.courseDao().insertCourse(tempCourse2);
        db.courseDao().insertCourse(tempCourse3);

    }

    private void insertAssessments() {


    }

}
