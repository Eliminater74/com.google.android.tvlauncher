package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;

import java.util.ArrayList;

public final class AccountPicker {
    @Hide
    public static final int CUSTOM_THEME_GAMES = 1;
    @Hide
    public static final int CUSTOM_THEME_NONE = 0;
    @Hide
    public static final int THEME_DEFAULT = 0;
    @Hide
    public static final int THEME_LIGHT = 1;

    private AccountPicker() {
    }

    public static Intent newChooseAccountIntent(Account account, ArrayList<Account> arrayList, String[] strArr, boolean z, String str, String str2, String[] strArr2, Bundle bundle) {
        return newChooseAccountIntent(account, arrayList, strArr, z, str, str2, strArr2, bundle, false, 0, 0);
    }

    @Hide
    public static Intent newChooseAccountIntent(Account account, ArrayList<Account> arrayList, String[] strArr, boolean z, String str, String str2, String[] strArr2, Bundle bundle, boolean z2, int i, int i2) {
        Intent intent = new Intent();
        zzau.zzb(true, "We only support hostedDomain filter for account chip styled account picker");
        intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
        intent.setPackage("com.google.android.gms");
        intent.putExtra("allowableAccounts", arrayList);
        intent.putExtra("allowableAccountTypes", strArr);
        intent.putExtra("addAccountOptions", bundle);
        intent.putExtra("selectedAccount", account);
        intent.putExtra("alwaysPromptForAccount", z);
        intent.putExtra("descriptionTextOverride", str);
        intent.putExtra("authTokenType", str2);
        intent.putExtra("addAccountRequiredFeatures", strArr2);
        intent.putExtra("setGmsCoreAccount", z2);
        intent.putExtra("overrideTheme", i);
        intent.putExtra("overrideCustomTheme", i2);
        intent.putExtra("hostedDomainFilter", (String) null);
        return intent;
    }
}
