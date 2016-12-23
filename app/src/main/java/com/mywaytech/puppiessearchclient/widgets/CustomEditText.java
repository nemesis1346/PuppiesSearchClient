package com.mywaytech.puppiessearchclient.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by nemesis1346 on 7/12/2016.
 */

public class CustomEditText extends LinearLayout {
    public static final int DEFAULT_MAX_LENGTH = 0;

    private EditText mEditText;
    private TextView mErrorText;

    private int mMaxLength;
    private String mHintText;
    private int mInputType;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(attrs, defStyle);
    }

    private void initializeViews(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyleAttr, 0);
            try {
                mMaxLength = styledAttributes.getInt(R.styleable.CustomEditText_android_maxLength, DEFAULT_MAX_LENGTH);
                mHintText = styledAttributes.getString(R.styleable.CustomEditText_android_hint);
                mInputType = styledAttributes.getInt(R.styleable.CustomEditText_android_inputType, InputType.TYPE_NULL);
            } finally {
                styledAttributes.recycle();
            }
        }

        if (android.os.Build.VERSION.SDK_INT > 21) {
            if (isPasswordInputType()) {
                inflate(getContext(), R.layout.custom_password_edit_text, this);
            } else {
                inflate(getContext(), R.layout.custom_edit_text, this);
            }
        } else {
            if (isPasswordInputType()) {
                inflate(getContext(), R.layout.normal_password_edit_text, this);
            } else {
                inflate(getContext(), R.layout.normal_edit_text, this);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) return;

        if (android.os.Build.VERSION.SDK_INT > 21) {
            if (isPasswordInputType()) {
                mEditText = (EditText) findViewById(R.id.password_edit_text);
                mErrorText = (TextView) findViewById(R.id.password_text_error);
            } else {
                mEditText = (EditText) findViewById(R.id.edit_text);
                mErrorText = (TextView) findViewById(R.id.text_error);
            }
        }else{
            if (isPasswordInputType()) {
                mEditText = (EditText) findViewById(R.id.password_edit_text);
                mErrorText = (TextView) findViewById(R.id.password_text_error);
            } else {
                mEditText = (EditText) findViewById(R.id.edit_text);
                mErrorText = (TextView) findViewById(R.id.text_error);
            }
        }

//        mEditText.setTypeface(Typeface.SERIF);

        if (mMaxLength > DEFAULT_MAX_LENGTH) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        }
        if (mInputType != InputType.TYPE_NULL) {
            mEditText.setInputType(mInputType | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

        mEditText.setHint(mHintText);
    }

    public void setError(String error) {
        if (error == null) {
            mErrorText.setText("");
            mErrorText.setVisibility(GONE);
        } else {
            mErrorText.setText(error);
            mErrorText.setVisibility(VISIBLE);
        }
    }

    public void setText(CharSequence text) {
        mEditText.setText(text);
    }

    public Editable getText() {
        return mEditText.getText();
    }

    public void setFilters(InputFilter[] inputFilters) {
        mEditText.setFilters(inputFilters);
    }

    public void addTextChangedListener(TextWatcher watcher) {
        mEditText.addTextChangedListener(watcher);
    }

    @Override
    public void setOnKeyListener(View.OnKeyListener l) {
        super.setOnKeyListener(l);
        mEditText.setOnKeyListener(l);
    }

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener l) {
        super.setOnFocusChangeListener(l);
        mEditText.setOnFocusChangeListener(l);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEditText.setEnabled(false);
    }

    private boolean isPasswordInputType() {
        return mInputType == InputType.TYPE_TEXT_VARIATION_PASSWORD || mInputType == InputType.TYPE_NUMBER_VARIATION_PASSWORD || mInputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void setInputType(int inputType) {
        mEditText.setInputType(inputType);
    }

    public void setPasswordVisible(boolean passwordVisible) {
        if (mEditText instanceof PasswordEditText) {
            ((PasswordEditText) mEditText).setPasswordVisible(passwordVisible);
        }
    }

    public void disableEditorAction() {
        super.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        mEditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }
}
