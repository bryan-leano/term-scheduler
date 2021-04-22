package com.example.term_scheduler_bryanleano;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id_fk"
        ))

public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessment_id;
    @ColumnInfo(name = "course_id_fk")
    private int course_id_fk;
    @ColumnInfo(name = "assessment_name")
    private String assessment_name;
    @ColumnInfo(name = "assessment_start")
    private Date assessment_start;
    @ColumnInfo(name = "assessment_end")
    private Date assessment_end;
    @ColumnInfo(name = "assessment_type")
    private String assessment_type;


    public int getAssessment_id() { return assessment_id; }

    public void setAssessment_id(int assessment_id) { this.assessment_id = assessment_id; }

    public int getCourse_id_fk() { return course_id_fk; }

    public void setCourse_id_fk(int course_id_fk) { this.course_id_fk = course_id_fk; }

    public String getAssessment_name() { return assessment_name; }

    public void setAssessment_name(String assessment_name) { this.assessment_name = assessment_name; }

    public Date getAssessment_start() { return assessment_start; }

    public void setAssessment_start(Date assessment_start) { this.assessment_start = assessment_start; }

    public Date getAssessment_end() { return assessment_end; }

    public void setAssessment_end(Date assessment_end) { this.assessment_end = assessment_end; }

    public String getAssessment_type() { return assessment_type; }

    public void setAssessment_type(String assessment_type) { this.assessment_type = assessment_type; }

}
