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

class CustomEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var personIcon: Drawable
    private lateinit var emailIcon: Drawable
    private lateinit var passwordIcon: Drawable


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
        personIcon = ContextCompat.getDrawable(context, R.drawable.ic_person_24) as Drawable
        emailIcon = ContextCompat.getDrawable(context, R.drawable.ic_email_24) as Drawable
        passwordIcon = ContextCompat.getDrawable(context, R.drawable.ic_lock_24) as Drawable

        setOnTouchListener(this)

        when (id) {
            R.id.ed_register_name -> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().isEmpty()) {
                            this@CustomEditText.error = resources.getString(R.string.error_name)
                        }
                    }
                })
            }

            R.id.ed_register_email -> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val email = s.toString().trim()
                        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        if (!isEmailValid) {
                            this@CustomEditText.error =
                                resources.getString(R.string.error_email_format)
                        }
                    }
                })
            }

            R.id.ed_register_password -> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (this@CustomEditText.text?.trim().toString().length < 8) {
                            this@CustomEditText.error =
                                resources.getString(R.string.error_password_length)
                        }
                    }
                })
            }

            R.id.ed_login_email -> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val email = s.toString().trim()
                        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        if (!isEmailValid) {
                            this@CustomEditText.error =
                                resources.getString(R.string.error_email_format)
                        }
                    }

                })
            }

            R.id.ed_login_password -> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (this@CustomEditText.text?.trim().toString().length < 8) {
                            this@CustomEditText.error =
                                resources.getString(R.string.error_password_length)
                        }
                    }
                })
            }
        }
    }

    private fun hideClearButton() {
        when (id) {
            R.id.ed_register_name -> {
                setButtonDrawable(startOfTheText = personIcon)
            }

            R.id.ed_register_email -> {
                setButtonDrawable(startOfTheText = emailIcon)
            }

            R.id.ed_register_password -> {
                setButtonDrawable(startOfTheText = passwordIcon)
            }

            R.id.ed_login_email -> {
                setButtonDrawable(startOfTheText = emailIcon)
            }

            R.id.ed_login_password -> {
                setButtonDrawable(startOfTheText = passwordIcon)
            }
        }
    }

    private fun showClearButton() {
        when (id) {
            R.id.ed_register_name -> {
                setButtonDrawable(startOfTheText = personIcon)
            }

            R.id.ed_register_email -> {
                setButtonDrawable(startOfTheText = emailIcon)
            }

            R.id.ed_register_password -> {
                setButtonDrawable(startOfTheText = passwordIcon)
            }

            R.id.ed_login_email -> {
                setButtonDrawable(startOfTheText = emailIcon)
            }

            R.id.ed_login_password -> {
                setButtonDrawable(startOfTheText = passwordIcon)
            }
        }
    }

    private fun setButtonDrawable(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
//        when (view.id) {
//            R.id.ed_register_name -> {
//                if (compoundDrawables[2] != null) {
//                    val clearButtonStart: Float
//                    val clearButtonEnd: Float
//                    var isClearButtonClicked = false
//                    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
//                        clearButtonEnd = (paddingStart).toFloat()
//                        when {
//                            event.x < clearButtonEnd -> isClearButtonClicked = true
//                        }
//                    } else {
//                        clearButtonStart = (width - paddingEnd).toFloat()
//                        when {
//                            event.x > clearButtonStart -> isClearButtonClicked = true
//                        }
//                    }
//                    if (isClearButtonClicked) {
//                        when (event.action) {
//                            MotionEvent.ACTION_DOWN -> {
//                                personIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_person_24
//                                ) as Drawable
//
//                                showClearButton()
//                                return true
//                            }
//
//                            MotionEvent.ACTION_UP -> {
//                                personIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_person_24
//                                ) as Drawable
//
//                                when {
//                                    text != null -> text?.clear()
//                                }
//                                hideClearButton()
//                                return true
//                            }
//
//                            else -> return false
//                        }
//                    } else return false
//                }
//            }
//
//            R.id.ed_register_email -> {
//                if (compoundDrawables[2] != null) {
//                    val clearButtonStart: Float
//                    val clearButtonEnd: Float
//                    var isClearButtonClicked = false
//                    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
//                        clearButtonEnd = (paddingStart).toFloat()
//                        when {
//                            event.x < clearButtonEnd -> isClearButtonClicked = true
//                        }
//                    } else {
//                        clearButtonStart = (width - paddingEnd).toFloat()
//                        when {
//                            event.x > clearButtonStart -> isClearButtonClicked = true
//                        }
//                    }
//                    if (isClearButtonClicked) {
//                        when (event.action) {
//                            MotionEvent.ACTION_DOWN -> {
//                                emailIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_email_24
//                                ) as Drawable
//
//                                showClearButton()
//                                return true
//                            }
//
//                            MotionEvent.ACTION_UP -> {
//                                emailIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_email_24
//                                ) as Drawable
//
//                                when {
//                                    text != null -> text?.clear()
//                                }
//                                hideClearButton()
//                                return true
//                            }
//
//                            else -> return false
//                        }
//                    } else return false
//                }
//            }
//
//            R.id.ed_register_password -> {
//                if (compoundDrawables[2] != null) {
//                    val clearButtonStart: Float
//                    val clearButtonEnd: Float
//                    var isClearButtonClicked = false
//                    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
//                        clearButtonEnd = (paddingStart).toFloat()
//                        when {
//                            event.x < clearButtonEnd -> isClearButtonClicked = true
//                        }
//                    } else {
//                        clearButtonStart = (width - paddingEnd).toFloat()
//                        when {
//                            event.x > clearButtonStart -> isClearButtonClicked = true
//                        }
//                    }
//                    if (isClearButtonClicked) {
//                        when (event.action) {
//                            MotionEvent.ACTION_DOWN -> {
//                                passwordIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_lock_24
//                                ) as Drawable
//
//                                showClearButton()
//                                return true
//                            }
//
//                            MotionEvent.ACTION_UP -> {
//                                passwordIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_lock_24
//                                ) as Drawable
//
//                                when {
//                                    text != null -> text?.clear()
//                                }
//                                hideClearButton()
//                                return true
//                            }
//
//                            else -> return false
//                        }
//                    } else return false
//                }
//            }
//
//            R.id.ed_login_email -> {
//                if (compoundDrawables[2] != null) {
//                    val clearButtonStart: Float
//                    val clearButtonEnd: Float
//                    var isClearButtonClicked = false
//                    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
//                        clearButtonEnd = (paddingStart).toFloat()
//                        when {
//                            event.x < clearButtonEnd -> isClearButtonClicked = true
//                        }
//                    } else {
//                        clearButtonStart = (width - paddingEnd).toFloat()
//                        when {
//                            event.x > clearButtonStart -> isClearButtonClicked = true
//                        }
//                    }
//                    if (isClearButtonClicked) {
//                        when (event.action) {
//                            MotionEvent.ACTION_DOWN -> {
//                                emailIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_email_24
//                                ) as Drawable
//
//                                showClearButton()
//                                return true
//                            }
//
//                            MotionEvent.ACTION_UP -> {
//                                emailIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_email_24
//                                ) as Drawable
//
//                                when {
//                                    text != null -> text?.clear()
//                                }
//                                hideClearButton()
//                                return true
//                            }
//
//                            else -> return false
//                        }
//                    } else return false
//                }
//            }
//
//            R.id.ed_login_password -> {
//                if (compoundDrawables[2] != null) {
//                    val clearButtonStart: Float
//                    val clearButtonEnd: Float
//                    var isClearButtonClicked = false
//                    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
//                        clearButtonEnd = (paddingStart).toFloat()
//                        when {
//                            event.x < clearButtonEnd -> isClearButtonClicked = true
//                        }
//                    } else {
//                        clearButtonStart = (width - paddingEnd).toFloat()
//                        when {
//                            event.x > clearButtonStart -> isClearButtonClicked = true
//                        }
//                    }
//                    if (isClearButtonClicked) {
//                        when (event.action) {
//                            MotionEvent.ACTION_DOWN -> {
//                                passwordIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_lock_24
//                                ) as Drawable
//
//                                showClearButton()
//                                return true
//                            }
//
//                            MotionEvent.ACTION_UP -> {
//                                passwordIcon = ContextCompat.getDrawable(
//                                    context,
//                                    R.drawable.ic_lock_24
//                                ) as Drawable
//
//                                when {
//                                    text != null -> text?.clear()
//                                }
//                                hideClearButton()
//                                return true
//                            }
//
//                            else -> return false
//                        }
//                    } else return false
//                }
//            }
//        }
        return false
    }
}