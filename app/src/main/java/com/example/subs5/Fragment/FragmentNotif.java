package com.example.subs5.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.subs5.Alarm.AlarmReceiver;
import com.example.subs5.R;

import java.util.Calendar;



public class FragmentNotif extends Fragment implements CompoundButton.OnCheckedChangeListener {
    View v;
    private AlarmReceiver alarmReceiver;
    private final int ID_Daily = 100;
    private final int ID_RELEASE = 101;
    SharedPreferences myPrefs;

    public FragmentNotif() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmReceiver = new AlarmReceiver();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_notif,container,false);
        Switch dailySwitch = (Switch) v.findViewById(R.id.daily_switch);
        dailySwitch.setChecked(getFromSP("ds"));
        Switch releaseSwitch = (Switch) v.findViewById(R.id.release_switch);
        releaseSwitch.setChecked(getFromSP("rs"));

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    scheduleDailyNotification();
                }
                else
                    alarmReceiver.cancelDailyAlarm(getActivity().getApplicationContext());
            }
        });


        releaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    alarmReceiver.cancelReleaseAlarm(getActivity());
                }
                else
                    alarmReceiver.cancelReleaseAlarm(getContext());
            }
        });

        return v;

    }


    private void scheduleDailyNotification() {
        Intent notificationIntent = new Intent(getActivity(), AlarmReceiver.class);
        notificationIntent.putExtra(String.valueOf(AlarmReceiver.class), 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);

        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }


    private boolean getFromSP(String key){
        SharedPreferences preferences = getContext().getSharedPreferences("com.example.subs5", android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    private void saveInSp(String key,boolean value) {
        SharedPreferences preferences = getContext().getSharedPreferences("com.example.subs5", android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.daily_switch:
                saveInSp("ds",isChecked);
                break;
            case R.id.release_switch:
                saveInSp("rs",isChecked);
                break;
        }
    }
}
