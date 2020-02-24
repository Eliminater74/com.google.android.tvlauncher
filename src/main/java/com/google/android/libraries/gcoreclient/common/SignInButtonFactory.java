package com.google.android.libraries.gcoreclient.common;

import android.content.Context;
import android.util.AttributeSet;

public interface SignInButtonFactory {
    SignInButton newSignInButton(Context context);

    SignInButton newSignInButton(Context context, AttributeSet attributeSet);

    SignInButton newSignInButton(Context context, AttributeSet attributeSet, int i);
}
