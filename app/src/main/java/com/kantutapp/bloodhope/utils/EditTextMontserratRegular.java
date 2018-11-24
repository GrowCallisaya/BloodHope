package com.kantutapp.bloodhope.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * TextView with Font Montserrat Regular
 */

public class EditTextMontserratRegular extends AppCompatEditText {

    public EditTextMontserratRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextMontserratRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextMontserratRegular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/MontserratRegular.otf"));
        }
    }
}
