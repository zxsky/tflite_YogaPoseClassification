package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CalendarDetailsActivity extends Activity {

    public final static String yearInfo = "RecordYear";
    public final static String monthInfo = "RecordMonth";
    public final static String dayInfo = "RecordDay";

    private RecyclerView mRecyclerView;
    private DetailAdapter mAdapter;
    private TextView mTextView;
    private ScrollView mScrollView;

    private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_detail);

        int year = getIntent().getIntExtra(yearInfo, 0);
        int month = getIntent().getIntExtra(monthInfo, 0);
        int day = getIntent().getIntExtra(dayInfo, 0);
        int count = 0;

        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);
        mTextView = (TextView) findViewById(R.id.calendar_detail_text);
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> content = new ArrayList<>();

        WindowManager window = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        int num = display.getRotation();
        if (num == 0 || num == 2){
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            mScrollView.setBackground(getResources().getDrawable(R.drawable.background));
        }else if (num == 1 || num == 3){
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            mScrollView.setBackground(getResources().getDrawable(R.drawable.background_land));
        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TypedArray ta = getResources().obtainTypedArray(R.array.colors);
        colors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        ta.recycle();

        if(day != 0){
            CalendarDatabaseHelper databaseHelper = new CalendarDatabaseHelper(this);
            try {
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select TIME, CONTENT from RECORD where YEAR = ? AND MONTH = ? AND DAY = ?" ,
                        new String[]{Integer.toString(year), Integer.toString(month), Integer.toString(day)});
                count = cursor.getCount();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        time.add(cursor.getString(cursor.getColumnIndex("TIME")));
                        content.add(cursor.getString(cursor.getColumnIndex("CONTENT")));
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }catch (SQLiteException e){
                Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        mAdapter = new DetailAdapter(time, content);
        mRecyclerView.setAdapter(mAdapter);
        mTextView.setText(count + " record(s) on " + year + "-" + String.valueOf(month+1) + "-" + day +":");
    }

    private class DetailHolder extends RecyclerView.ViewHolder {

        private TextView mContentTextView;
        private TextView mDateTextView;
        private CardView mCardView;

        public DetailHolder(View itemView) {
            super(itemView);
            mContentTextView = (TextView) itemView.findViewById(R.id.calendar_detail_content_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.calendar_detail_date_text_view);
            mCardView = (CardView) itemView.findViewById(R.id.calendar_detail_card_view);
        }

        public void bindDetail(String time, String content, int position) {
            String finalContent = "";
            String[] records = content.split(",");
            int len = records.length;
            for(int i =0;i<len-2;i=i+2){
                finalContent += records[i] + " for "+ records[i+1] +"0 seconds\n";
            }
            finalContent += records[len-2] + " for "+ records[len-1] +"0 seconds";
            mContentTextView.setText(finalContent);

            time = "On " + time +", you practiced:";
            mDateTextView.setText(time);

            mCardView.setCardBackgroundColor(colors[position % colors.length]);
        }


    }

    private class DetailAdapter extends RecyclerView.Adapter<DetailHolder> {

        private ArrayList<String> mTimes;
        private ArrayList<String> mContents;

        public DetailAdapter(ArrayList<String> time, ArrayList<String> content) {
            mTimes = time;
            mContents = content;
        }

        @Override
        public int getItemCount() {
            return mTimes.size();
        }

        @Override
        public DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(CalendarDetailsActivity.this);
            View view = layoutInflater.inflate(R.layout.calendar_detail, parent, false);
            return new DetailHolder(view);
        }

        @Override
        public void onBindViewHolder(DetailHolder holder, int position) {
            String detailTime = mTimes.get(position);
            String detailContent = mContents.get(position);
            holder.bindDetail(detailTime, detailContent, position);
        }
    }

}
