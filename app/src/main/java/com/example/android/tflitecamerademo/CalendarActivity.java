package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class CalendarActivity extends Activity{

    private MaterialCalendarView materialCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        HashSet<CalendarDay> list = new HashSet<>();

        CalendarDatabaseHelper databaseHelper = new CalendarDatabaseHelper(this);
        try {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from RECORD",null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int year = cursor.getInt(cursor.getColumnIndex("YEAR"));
                    int month = cursor.getInt(cursor.getColumnIndex("MONTH"));
                    int day = cursor.getInt(cursor.getColumnIndex("DAY"));
                    list.add(CalendarDay.from(year, month, day));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        materialCalendarView.addDecorator(new EventDecorator(Color.RED, list));
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (list.contains(date)) {
                    Intent intent = new Intent(CalendarActivity.this, CalendarDetailsActivity.class);
                    intent.putExtra(CalendarDetailsActivity.yearInfo, date.getYear());
                    intent.putExtra(CalendarDetailsActivity.monthInfo, date.getMonth());
                    intent.putExtra(CalendarDetailsActivity.dayInfo, date.getDay());
                    startActivity(intent);
                }else{
                    Toast.makeText(CalendarActivity.this, "No record on the day you choose", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private class EventDecorator implements DayViewDecorator {

        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, HashSet<CalendarDay> dates){
            this.color = color;
            this.dates = dates;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }

}
