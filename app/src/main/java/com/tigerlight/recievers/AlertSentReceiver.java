package com.tigerlight.recievers;

import com.tigerlight.registration.util.Constant;
import com.tigerlight.util.Preference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlertSentReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Preference.getInstance().mSharedPreferences.edit().putBoolean(Constant.IS_TEST_MODE, false).commit();
    }
}
