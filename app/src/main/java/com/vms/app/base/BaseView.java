package com.vms.app.base;

import android.app.Dialog;
import android.view.View;

import java.util.List;

public interface BaseView {

    void showProgress();

    void dismissProgress();

    void toast(String message);

    void toast(int stringId);

    Dialog customDialog(int layout, int height);

    Dialog customDialog(int layout);

    void openWhatsAppConversation(String number);

    void showSuccessDialog(String message);




    void hideKeyboard();
}
