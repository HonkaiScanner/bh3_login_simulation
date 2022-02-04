package com.github.haocen2004.login_simulation;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.github.haocen2004.login_simulation.BuildConfig.DEBUG;
import static com.github.haocen2004.login_simulation.BuildConfig.VERSION_CODE;
import static com.github.haocen2004.login_simulation.util.Constant.BH_VER;
import static com.github.haocen2004.login_simulation.util.Constant.CHECK_VER;
import static com.github.haocen2004.login_simulation.util.Constant.MDK_VERSION;

import android.app.Application;
import android.content.SharedPreferences;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatDelegate;

import com.github.haocen2004.login_simulation.data.LogLiveData;
import com.github.haocen2004.login_simulation.data.database.sponsor.SponsorRepo;
import com.github.haocen2004.login_simulation.util.Logger;
import com.hjq.toast.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;

public class MainApplication extends Application {
    private SharedPreferences app_pref;
    private Logger Log;
    @Override
    public void onCreate() {
        super.onCreate();
        LogLiveData.getINSTANCE(this); //init live data
        Log = Logger.getLogger(this);
        ToastUtils.init(this);
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 50);
        CrashReport.initCrashReport(getApplicationContext(), "4bfa7b722e", DEBUG);
        app_pref = getDefaultSharedPreferences(this);
        if (app_pref.getBoolean("is_first_run", true) || app_pref.getInt("version", 1) < VERSION_CODE) {
            app_pref.edit()
                    .putBoolean("is_first_run", false)
                    .putInt("version", VERSION_CODE)
                    .apply();
            try {
                new SponsorRepo(getApplicationContext()).reset();
            } catch (Exception ignore) {
            }
            if (!app_pref.contains("auto_confirm")) {
                app_pref.edit()
                        .putBoolean("auto_confirm", false)
                        .apply();
            }
//            if (!app_pref.contains("enable_ad")) {
//                app_pref.edit()
//                        .putBoolean("enable_ad", true)
//                        .apply();
//            }
            if (!app_pref.contains("server_type")) {
                app_pref.edit()
                        .putString("server_type", "Official")
                        .apply();
            }

            if (!app_pref.contains("showBetaInfo")) {
                app_pref.edit()
                        .putBoolean("showBetaInfo", DEBUG)
                        .apply();
            }
            if (!app_pref.contains("custom_username")) {
                app_pref.edit()
                        .putString("custom_username", "崩坏3扫码器用户")
                        .apply();
            }
            if (!app_pref.contains("check_update")) {
                app_pref.edit()
                        .putBoolean("check_update", !getPackageName().contains("dev"))
                        .apply();
            }
            if (!app_pref.contains("official_type")) {
                app_pref.edit()
                        .putInt("official_type", 0)
                        .apply();
            }
            if (!app_pref.contains("dark_type")) {
                app_pref.edit()
                        .putString("dark_type", "-1")
                        .apply();
            }
            if (!app_pref.contains("mdk_ver")) {
                app_pref.edit()
                        .putString("mdk_ver", MDK_VERSION)
                        .apply();
            }
            if (!app_pref.contains("bh_ver")) {
                app_pref.edit()
                        .putString("bh_ver", BH_VER)
                        .apply();
            }
            if (!app_pref.contains("use_socket")) {
                app_pref.edit()
                        .putBoolean("use_socket", false)
                        .apply();
            }

        }
        CHECK_VER = app_pref.getBoolean("check_update", true);

        switch (app_pref.getString("dark_type", "-1")) {
            case "-1":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case "1":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "2":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }


    }

}
