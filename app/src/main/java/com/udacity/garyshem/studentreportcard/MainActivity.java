package com.udacity.garyshem.studentreportcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReportCard reportCard = new ReportCard(1);
        reportCard.setGrade("History", 'A');
        reportCard.setGrade("Math", 62);
        TextView textView = (TextView)findViewById(R.id.test_text_view);
        textView.setText(reportCard.getCharacterGradeString());
    }
}
