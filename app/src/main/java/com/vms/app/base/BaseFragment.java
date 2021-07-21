package com.vms.app.base;


import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import java.util.List;

public class BaseFragment
        extends Fragment
        implements  BaseView {

    private  BaseView mBaseInterface;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mBaseInterface = ( BaseView) context;
    }

    public void toast(String message) {
        mBaseInterface.toast(message);
    }

    @Override
    public void toast(int stringId) {
        mBaseInterface.toast(stringId);
    }

    @Override
    public Dialog customDialog(int layout, int height) {
        return mBaseInterface.customDialog(layout, height);
    }

    @Override
    public Dialog customDialog(int layout) {
        return mBaseInterface.customDialog(layout);
    }

    @Override
    public void openWhatsAppConversation(String number) {
        mBaseInterface.openWhatsAppConversation(number);
    }

    @Override
    public void showSuccessDialog(String message) {
        mBaseInterface.showSuccessDialog(message);
    }


    @Override
    public void hideKeyboard() {
        mBaseInterface.hideKeyboard();
    }

    public void showProgress() {
        mBaseInterface.showProgress();
    }

    public void dismissProgress() {
        mBaseInterface.dismissProgress();
    }

}
