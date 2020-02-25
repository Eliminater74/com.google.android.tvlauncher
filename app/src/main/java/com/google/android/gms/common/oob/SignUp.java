package com.google.android.gms.common.oob;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;

import java.io.IOException;

@Hide
public class SignUp {
    public static final String ACTION_OOB_SIGN_UP = "com.google.android.gms.common.oob.OOB_SIGN_UP";
    public static final String EXTRAS_CALLING_APP_DESCRIPTION = "com.google.android.gms.common.oob.EXTRAS_APP_DESCRIPTION";
    public static final String EXTRAS_CLIENT_CALLING_APP_PACKAGE = "com.google.android.gms.common.oob.EXTRAS_CLIENT_CALLING_APP_PACKAGE";
    public static final String EXTRAS_PROMO_APP_PACKAGE = "com.google.android.gms.common.oob.EXTRAS_PROMO_APP_PACKAGE";
    public static final String EXTRAS_PROMO_APP_TEXT = "com.google.android.gms.common.oob.EXTRAS_PROMO_APP_TEXT";
    public static final String EXTRA_ACCOUNT_NAME = "com.google.android.gms.common.oob.EXTRA_ACCOUNT_NAME";
    public static final String EXTRA_BACK_BUTTON_NAME = "com.google.android.gms.common.oob.EXTRA_BACK_BUTTON_NAME";
    public static final String EXTRA_GPSRC = "com.google.android.gms.plus.GPSRC";
    public static final String EXTRA_OVERRIDE_THEME = "com.google.android.gms.plus.OVERRIDE_THEME";
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = BaseGmsClient.zzb;
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_FULL = 1;
    public static final int THEME_SETUP_WIZARD = 2;

    private SignUp() {
    }

    public static Intent newSignUpIntent(String str) {
        return newSignUpIntent(str, null);
    }

    public static Intent newSignUpIntent(String str, String str2) {
        Intent intent = new Intent();
        intent.setPackage("com.google.android.gms");
        intent.setAction(ACTION_OOB_SIGN_UP);
        intent.putExtra(EXTRA_ACCOUNT_NAME, str);
        intent.putExtra(EXTRA_BACK_BUTTON_NAME, str2);
        return intent;
    }

    public static Intent newSignUpIntent(String str, String str2, String str3, String str4) {
        Intent intent = new Intent();
        intent.setPackage("com.google.android.gms");
        intent.setAction(ACTION_OOB_SIGN_UP);
        intent.putExtra(EXTRA_ACCOUNT_NAME, str);
        intent.putExtra(EXTRA_BACK_BUTTON_NAME, str2);
        intent.putExtra(EXTRAS_PROMO_APP_PACKAGE, str3);
        intent.putExtra(EXTRAS_PROMO_APP_TEXT, str4);
        return intent;
    }

    public static AccountManagerFuture<Boolean> checkSignUpState(Context context, String str, String[] strArr, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        zzau.zzb(!TextUtils.isEmpty(str), "The accountName is required");
        zzau.zzb(strArr != null, "The requiredFeatures parameter is required");
        AccountManager accountManager = AccountManager.get(context);
        boolean z = false;
        for (Account account : accountManager.getAccountsByType("com.google")) {
            if (str.equals(account.name)) {
                z = true;
            }
        }
        if (z) {
            return accountManager.hasFeatures(new Account(str, "com.google"), strArr, accountManagerCallback, handler);
        }
        throw new IllegalStateException("Given account does not exist on the device");
    }

    public static boolean isSignedUpBlocking(Context context, String str, String[] strArr) throws AuthenticatorException, OperationCanceledException, IOException {
        return checkSignUpState(context, str, strArr, null, null).getResult().booleanValue();
    }
}
