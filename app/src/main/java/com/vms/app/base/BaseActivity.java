package com.vms.app.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.vms.app.R;
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements BaseView  {

    public static final String EXTRA_IS_FROM = "from";


    public static final int REQUEST_PERMISSION_LOCATION = 1;
    public static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};

    public View mProgressView;
    private int mProgressCount;
    public Dialog mSuccessDialog;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_out_right);
    }

    protected void configToolBar(Toolbar toolbar, TextView titleView, int title) {
        configToolBar(toolbar, titleView, getString(title));
    }

    protected void configToolBar(Toolbar toolbar, TextView titleView, String title) {
        configToolBar(toolbar, titleView, true, title);
    }

    protected void configToolBar(Toolbar toolbar, TextView titleView, boolean setDisplayHomeAsUpEnabled, String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
        titleView.setText(title);
    }

    public void showProgress() {
        if (null == mProgressView) return;
        mProgressCount = mProgressCount + 1;
        mProgressView.setVisibility(View.VISIBLE);
    }

    public void dismissProgress() {
        if (null == mProgressView) return;
        if (mProgressCount > 0)
            mProgressCount = mProgressCount - 1;
        if (0 == mProgressCount)
            mProgressView.setVisibility(View.GONE);
    }



    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    public boolean checkLocationPermission() {
        if (isLocationPermissionEnabled())
            return true;
        ActivityCompat.requestPermissions(this, PERMISSIONS_LOCATION, REQUEST_PERMISSION_LOCATION);
        return false;
    }

    public boolean isLocationPermissionEnabled() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public Dialog customDialog(int layout) {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setContentView(layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }

    public Dialog customDialog(int layout, int height) {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setContentView(layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = height;
        window.setAttributes(lp);
        return dialog;
    }

    protected void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_VIEW); //ACTION_VIEW --> ACTION_DIAL
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    protected void sendEmail(String email, String emailSubject, String intentTitle) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        startActivity(Intent.createChooser(emailIntent, intentTitle));
    }

    public void openWhatsAppConversation(String number) {
        if (!isAppInstalled("com.whatsapp")) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp")));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            return;
        }
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void showSuccessDialog(String message) {

    }



    public boolean isAppInstalled(String packageName) {
        try {
            getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    private String fileExt(String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.contains("%")) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.contains("/")) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

    protected void goToActivity(Class clazz) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        }
    }
}


