package com.resumesamples.resumesamples.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.resumesamples.resumesamples.BuildConfig;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Nullable
    protected Bundle getExtras(Bundle savedInstanceState) {
        Bundle extras;
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
        } else {
            extras = savedInstanceState;
        }
        return extras;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!(this instanceof MainActivity)) {
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null)
                supportActionBar.hide();
        }

        setContentView(getContentView());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        attachActivityViews(savedInstanceState);
        initActivityViews(savedInstanceState);
//        setupActionBar();
    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            styleActionBar(actionBar);
        }
    }

    protected abstract int getContentView();

    protected void attachActivityViews(Bundle savedInstanceState) {
    }

    protected void initActivityViews(Bundle savedInstanceState) {
    }

    protected void styleActionBar(ActionBar actionBar) {
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(isHomeAsUpEnabledShown());
    }

    protected boolean isHomeAsUpEnabledShown() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = this.getCurrentFocus();
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            v.clearFocus();
        }
    }

    public void showWaitingDialog(String waitingMessage, boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setCancelable(cancelable);
        if (waitingMessage != null) {
            progressDialog.setMessage(waitingMessage);
        }
        if (progressDialog != null && !progressDialog.isShowing() && !isFinishing()) {
            progressDialog.show();
        }
    }

    public void showWaitingDialog(String waitingMessage) {
        showWaitingDialog(waitingMessage, false);
    }

    public void dismissWaitingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showInformationDialog(int message) {
        DialogFragment dialog = InformationDialog.newInstance(0, message);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public void showInformationDialog(int title, int message) {
        DialogFragment dialog = InformationDialog.newInstance(title, message);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public void showInformationDialog(String message) {
        DialogFragment dialog = InformationDialog.newInstance(null, message);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public void showInformationDialog(String title, String message) {
        DialogFragment dialog = InformationDialog.newInstance(title, message);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public void showInformationDialog(@StringRes int titleId, @StringRes int messageId,
                                      DialogInterface.OnClickListener listener) {
        DialogFragment dialog = InformationDialog.newInstance(titleId, messageId, listener);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public void showInformationDialog(String title, String message, DialogInterface.OnClickListener listener) {
        DialogFragment dialog = InformationDialog.newInstance(title, message, listener);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public void showInformationDialog(int title, int message, DialogInterface.OnClickListener listener,
                                      DialogInterface.OnCancelListener cancelListener) {
        DialogFragment dialog = InformationDialog.newInstance(title, message, listener, cancelListener);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public void showInformationDialog(String title, String message, DialogInterface.OnClickListener listener,
                                      DialogInterface.OnCancelListener cancelListener) {
        DialogFragment dialog = InformationDialog.newInstance(title, message, listener, cancelListener);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public void showInformationDialog(int titleId, int messageId, int positiveId, int negativeId,
                                      DialogInterface.OnClickListener positiveListener,
                                      DialogInterface.OnClickListener negativeListener) {
        DialogFragment dialog = InformationDialog.newInstance(titleId, messageId, positiveId,
                negativeId, positiveListener, negativeListener);
        dialog.show(getSupportFragmentManager(), "info_dialog");
    }

    public static class InformationDialog extends DialogFragment {
        private static final String TITLE_INT_BUNDLE_KEY = "title_int";
        private static final String MESSAGE_INT_BUNDLE_KEY = "message_int";
        private static final String TITLE_STRING_BUNDLE_KEY = "title_string";
        private static final String MESSAGE_STRING_BUNDLE_KEY = "message_string";
        private static final String POSITIVE_BTN_INT_BUNDLE_KEY = "positive_btn_int";
        private static final String NEGATIVE_BTN_INT_BUNDLE_KEY = "negative_btn_int";

        private DialogInterface.OnClickListener clickListener;
        private DialogInterface.OnCancelListener cancelListener;
        private DialogInterface.OnClickListener negativeClickListener;

        public static InformationDialog newInstance(int title, int message,
                                                    DialogInterface.OnClickListener listener) {
            Bundle args = new Bundle();
            args.putInt(TITLE_INT_BUNDLE_KEY, title);
            args.putInt(MESSAGE_INT_BUNDLE_KEY, message);
            return newDialog(args, listener, null, null);
        }

        public static InformationDialog newInstance(String title, String message,
                                                    DialogInterface.OnClickListener listener) {
            Bundle args = new Bundle();
            args.putString(TITLE_STRING_BUNDLE_KEY, title);
            args.putString(MESSAGE_STRING_BUNDLE_KEY, message);
            return newDialog(args, listener, null, null);
        }

        public static InformationDialog newInstance(int title, int message,
                                                    DialogInterface.OnClickListener listener,
                                                    DialogInterface.OnCancelListener cancelListener) {
            Bundle args = new Bundle();
            args.putInt(TITLE_INT_BUNDLE_KEY, title);
            args.putInt(MESSAGE_INT_BUNDLE_KEY, message);
            return newDialog(args, listener, cancelListener, null);
        }

        public static InformationDialog newInstance(String title, String message,
                                                    DialogInterface.OnClickListener listener,
                                                    DialogInterface.OnCancelListener cancelListener) {
            Bundle args = new Bundle();
            args.putString(TITLE_STRING_BUNDLE_KEY, title);
            args.putString(MESSAGE_STRING_BUNDLE_KEY, message);
            return newDialog(args, listener, cancelListener, null);
        }

        public static InformationDialog newInstance(int title, int message) {
            return newInstance(title, message, null);
        }

        public static InformationDialog newInstance(String title, String message) {
            return newInstance(title, message, null);
        }

        public static InformationDialog newInstance(int titleId, int messageId, int positiveId,
                                                    int negativeId, DialogInterface.OnClickListener positiveListener,
                                                    DialogInterface.OnClickListener negativeListener) {
            Bundle args = new Bundle();
            args.putInt(TITLE_INT_BUNDLE_KEY, titleId);
            args.putInt(MESSAGE_INT_BUNDLE_KEY, messageId);
            args.putInt(POSITIVE_BTN_INT_BUNDLE_KEY, positiveId);
            args.putInt(NEGATIVE_BTN_INT_BUNDLE_KEY, negativeId);
            return newDialog(args, positiveListener, null, negativeListener);
        }

        private static InformationDialog newDialog(Bundle args, DialogInterface.OnClickListener okListener,
                                                   DialogInterface.OnCancelListener cancelListener,
                                                   DialogInterface.OnClickListener negativeListener) {
            InformationDialog dialog = new InformationDialog();
            dialog.setPositiveOnClickListener(okListener);
            dialog.setNegativeOnClickListener(negativeListener);
            dialog.setOnCancelListener(cancelListener);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            Bundle args = getArguments();
            if (args.containsKey(TITLE_INT_BUNDLE_KEY) && args.containsKey(MESSAGE_INT_BUNDLE_KEY)) {
                int title = args.getInt(TITLE_INT_BUNDLE_KEY);
                if (title != 0) builder.setTitle(title);
                int message = args.getInt(MESSAGE_INT_BUNDLE_KEY);
                builder.setMessage(message);
            } else if (args.containsKey(TITLE_STRING_BUNDLE_KEY) && args.containsKey(MESSAGE_STRING_BUNDLE_KEY)) {
                String title = args.getString(TITLE_STRING_BUNDLE_KEY);
                String message = args.getString(MESSAGE_STRING_BUNDLE_KEY);
                builder.setTitle(title);
                builder.setMessage(message);
            }
            int positiveBtnId;
            if (args.containsKey(POSITIVE_BTN_INT_BUNDLE_KEY)) {
                positiveBtnId = args.getInt(POSITIVE_BTN_INT_BUNDLE_KEY);
            } else {
                positiveBtnId = android.R.string.ok;
            }
            builder.setPositiveButton(positiveBtnId, clickListener);

            int negativeBtnId = 0;
            if (args.containsKey(NEGATIVE_BTN_INT_BUNDLE_KEY)) {
                negativeBtnId = args.getInt(NEGATIVE_BTN_INT_BUNDLE_KEY);
                builder.setNegativeButton(negativeBtnId, negativeClickListener);
            } else if (negativeClickListener != null) {
                builder.setNegativeButton(android.R.string.cancel, negativeClickListener);
            }

            final AlertDialog alertDialog = builder.create();

            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button btnPositive = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
                    if (btnPositive != null) {
                        btnPositive.setAllCaps(false);
                    }

                    Button btnNegative = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
                    if (btnNegative != null) {
                        btnNegative.setAllCaps(false);
                    }
                }
            });

//            Button neutralButton = dialog.getButton(negativeBtnId);
//            if (negativeBtnId != 0)
//                neutralButton.setTextColor(getResources().getColor(R.color.loyalty_button_join));
//
//            Button positiveButton = dialog.getButton(positiveBtnId);
//            positiveButton.setTextColor(getResources().getColor(R.color.loyalty_button_join));

            return alertDialog;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            if (cancelListener != null)
                cancelListener.onCancel(dialog);
        }

        public void setPositiveOnClickListener(DialogInterface.OnClickListener listener) {
            clickListener = listener;
        }

        public void setNegativeOnClickListener(DialogInterface.OnClickListener listener) {
            negativeClickListener = listener;
        }

        public void setOnCancelListener(DialogInterface.OnCancelListener listener) {
            cancelListener = listener;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}