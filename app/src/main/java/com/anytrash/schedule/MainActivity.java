package com.anytrash.schedule;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.anytrash.schedule.ui.main.SectionsPagerAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    GetGroupList getGroupList;
    GetSchedule getSchedule;
    ArrayList<String> groupList;
    public static Schedule groupSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.group_list);

        getGroupList = new GetGroupList(this);
        getGroupList.execute("https://miet.ru/schedule/groups");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (groupList.size() > 0) {
                    getSchedule = new GetSchedule(MainActivity.this);
                    getSchedule.execute("https://miet.ru/schedule/data?group=" + groupList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                viewPager.setCurrentItem(0);
                break;
            case Calendar.TUESDAY:
                viewPager.setCurrentItem(1);
                break;
            case Calendar.WEDNESDAY:
                viewPager.setCurrentItem(2);
                break;
            case Calendar.THURSDAY:
                viewPager.setCurrentItem(3);
                break;
            case Calendar.FRIDAY:
                viewPager.setCurrentItem(4);
                break;
            case Calendar.SATURDAY:
                viewPager.setCurrentItem(5);
                break;
        }
    }

    private class GetGroupList extends GetRequest<String, ArrayList<String>> {
        private ProgressDialog progressDialog;
        private Context context;

        GetGroupList(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(getString(R.string.get_group_list_progress_title));
            progressDialog.setMessage(getString(R.string.get_group_list_progress_message));
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String responseBody;
            try {
                responseBody = getRequest(strings[0]);
            } catch (IOException e) {
                responseBody = "";
                e.printStackTrace();
            }
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            ArrayList<String> groupList = new ArrayList<>();
            Type groupListType = new TypeToken<ArrayList<String>>() {
            }.getType();
            if (responseBody != "") groupList = gson.fromJson(responseBody, groupListType);
            return groupList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinner.setVisibility(View.INVISIBLE);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<String> stringArrayList) {
            super.onPostExecute(stringArrayList);
            groupList = stringArrayList;
            arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, groupList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
    }

    private class GetSchedule extends GetRequest<String, Schedule> {
        private ProgressDialog progressDialog;

        GetSchedule(Context context) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(getString(R.string.get_schedule_progress_title));
            progressDialog.setMessage(getString(R.string.get_schedule_progress_message));
        }

        @Override
        protected Schedule doInBackground(String... strings) {
            String responseBody;
            try {
                responseBody = getRequest(strings[0]);
            } catch (IOException e) {
                responseBody = "";
                e.printStackTrace();
            }
            GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Gson gson = gsonBuilder.create();
            Schedule schedule;
            Type semesterDataType = new TypeToken<Schedule>() {
            }.getType();
            schedule = gson.fromJson(responseBody, semesterDataType);
            return schedule;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Schedule schedule) {
            super.onPostExecute(schedule);
            groupSchedule = schedule;
            progressDialog.dismiss();
        }
    }
}
