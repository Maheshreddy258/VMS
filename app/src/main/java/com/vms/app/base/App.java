package com.vms.app.base;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.io.File;


public class App extends Application {
    private static  App mApp;
    private FirebaseCrashlytics mCrashlytics;
    private static Context context;

    public static  App app() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        App.context = getApplicationContext();



    }



    public String deviseId() {
        return Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public FirebaseCrashlytics getCrashlytics() {
        return mCrashlytics;
    }

    public static boolean deleteAllFiles(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (String aChildren : children) {
                    deletedAll = deleteFile(new File(file, aChildren)) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }
        return deletedAll;
    }

    public static boolean deleteFile(File file) {
        if (file.exists()) return file.delete();
        return false;
    }

    private static void clearApplicationData(Context mContext) {
        File cacheDirectory = mContext.getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    //deleteFile(new File(applicationDirectory, fileName));
                    deleteAllFiles(new File(applicationDirectory, fileName));
                }
            }
        }
    }


    public static Context getAppContext() {
        return  App.context;
    }
}
