package com.example.submissionaplikasistoryapp.view.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.submissionaplikasistoryapp.R

class CustomEmailValidationEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var errorIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_error_24) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    setError("Email tidak boleh kosong", errorIcon)
                    setCompoundDrawablesWithIntrinsicBounds(null, null, errorIcon, null)
                } else if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    setError("Email tidak valid", errorIcon)
                    setCompoundDrawablesWithIntrinsicBounds(null, null, errorIcon, null)
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }
}