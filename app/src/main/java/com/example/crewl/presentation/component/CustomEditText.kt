package com.example.crewl.presentation.component

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.crewl.R
import com.example.crewl.databinding.CustomEditTextBinding
import com.example.crewl.helper.ResourceHelper.getColor
import com.example.crewl.utils.asString
import com.example.crewl.utils.showKeyboard

private const val INPUT_TEXT_NORMAL = 0
private const val INPUT_TEXT_EMAIL = 1
private const val INPUT_TEXT_CAP_FIRST = 2
private const val INPUT_TEXT_CAP_WORDS = 3
private const val INPUT_TEXT_PASSWORD = 4

class CustomEditText : LinearLayout {
    private lateinit var binding: CustomEditTextBinding

    private var hintText: String? = null
    private var inputText: String? = null
    private var infoText: String? = null

    private var isPasswordFieldActive: Boolean = false
    private var autoFillValue: String? = null
    private var inputEnabled = false
    private var textType = 0

    private var inputStartIcon = 0
    private var maxLength = 0
    private var inputFontSize = 0

    private var isErrorShown: Boolean = false

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomEditTextBinding.inflate(inflater, this, true)

        val styledAttributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)

        inputText = styledAttributes.getString(R.styleable.CustomEditText_inputValue)
        hintText = styledAttributes.getString(R.styleable.CustomEditText_inputHint)
        infoText = styledAttributes.getString(R.styleable.CustomEditText_infoText)

        inputEnabled = styledAttributes.getBoolean(R.styleable.CustomEditText_inputEnabled, true)
        isPasswordFieldActive = styledAttributes.getBoolean(R.styleable.CustomEditText_inputPasswordToggle, false)

        textAlignment = styledAttributes.getInteger(R.styleable.CustomEditText_inputTextAlignment, 0)
        textType = styledAttributes.getInteger(R.styleable.CustomEditText_inputTextType, INPUT_TEXT_NORMAL)

        inputStartIcon = styledAttributes.getResourceId(R.styleable.CustomEditText_inputStartIcon, 0)
        maxLength = styledAttributes.getInteger(R.styleable.CustomEditText_maxLength, 0)
        inputFontSize = styledAttributes.getInteger(R.styleable.CustomEditText_fontSize, 0)

        autoFillValue = styledAttributes.getString(R.styleable.CustomEditText_autoFill)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && autoFillValue != null) setValue(autoFillValue)

        styledAttributes.recycle()

        setStyle()
        setInputText()
        setIcon()
        setPasswordStyle()
        checkErrorText()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun setValue(value: String?) {
        binding.inputEditText.importantForAutofill = IMPORTANT_FOR_AUTOFILL_YES
        binding.inputEditText.setAutofillHints(value)
    }

    private fun setStyle() {
        binding.inputEditText.apply {
            setTextIsSelectable(false)
            isLongClickable = false
            isSelected = false

            customSelectionActionModeCallback = object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean = true

                override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                    menu.clear()
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean = false

                override fun onDestroyActionMode(mode: ActionMode) {}
            }

            if (inputFontSize != 0) setTextSize(TypedValue.COMPLEX_UNIT_DIP, inputFontSize.toFloat())

            if (maxLength != 0) filters = arrayOf<InputFilter>(LengthFilter(maxLength))

            when (textType) {
                INPUT_TEXT_EMAIL -> inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                INPUT_TEXT_PASSWORD -> inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                INPUT_TEXT_CAP_FIRST -> inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                INPUT_TEXT_CAP_WORDS -> inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            }
        }
    }

    private fun setInputText() {
        binding.apply {
            hintText?.let { hint ->
                if (hint != "") inputEditText.hint = hint
                else {
                    inputEditText.hint = ""
                    inputEditText.visibility = GONE
                }
            } ?: run {
                inputEditText.hint = ""
                inputEditText.visibility = GONE
            }

            infoText?.let { info ->
                if (info != "") infoTextView.text = info
                else {
                    infoTextView.text = ""
                    infoTextView.visibility = GONE
                }
            } ?: run {
                infoTextView.text = ""
                infoTextView.visibility = GONE
            }
        }
    }

    private fun checkErrorText() {
        binding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isErrorShown) {
                    setErrorAnimation(isVisible = false)
                    isErrorShown = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    fun setInputError(message: String?) {
        binding.infoTextView.apply {
            message?.let {
                text = it
                setTextColor(getColor(resourceId = R.color.default_error))
                visibility = VISIBLE
                isErrorShown = true
                setErrorAnimation(isVisible = true)
            } ?: run {
                isErrorShown = false
                setErrorAnimation(isVisible = false)
                text = ""
                visibility = GONE
            }
        }
    }

    private fun setErrorAnimation(isVisible: Boolean) {
        setStrokeAnimation(isChanged = isVisible)
        setErrorTextTransition(isErrorActive = isVisible)
    }

    private fun setStrokeAnimation(isChanged: Boolean) {
        val gradientDrawable = binding.inputEditText.background as GradientDrawable
        gradientDrawable.mutate()

        val grayStrokeColor = getColor(R.color.default_input_bg)
        val errorStrokeColor = getColor(R.color.default_error)

        val strokeAnimation: ValueAnimator = if (isChanged)
            ValueAnimator.ofObject(ArgbEvaluator(), grayStrokeColor, errorStrokeColor)
        else
            ValueAnimator.ofObject(ArgbEvaluator(), errorStrokeColor, grayStrokeColor)

        strokeAnimation.duration = 330
        strokeAnimation.addUpdateListener { valueAnimator: ValueAnimator ->
            gradientDrawable.setStroke(2, valueAnimator.animatedValue as Int)
        }

        strokeAnimation.start()
    }

    private fun setErrorTextTransition(isErrorActive: Boolean) {
        binding.infoTextView.measure(0, 0)

        val errorAnimation = if (isErrorActive)
            ValueAnimator.ofInt(0, binding.infoTextView.measuredHeight + 20)
        else ValueAnimator.ofInt(binding.infoTextView.measuredHeight + 20, 0)

        errorAnimation.duration = 330
        errorAnimation.addUpdateListener { valueAnimator: ValueAnimator ->
            binding.infoTextView.translationY = valueAnimator.animatedValue.toString().toFloat()
        }

        errorAnimation.start()
    }

    fun setFocus() {
        binding.inputEditText.requestFocus()
    }

    fun showKeyboardForInput() {
        binding.inputEditText.showKeyboard()
    }

    fun getText(): String {
        return if (binding.inputEditText.text != null) binding.inputEditText.text.asString else ""
    }

    fun setText(text: String?) {
        binding.inputEditText.setText(text)
    }

    private fun setIcon() {
        binding.inputEditText.setCompoundDrawablesWithIntrinsicBounds(inputStartIcon, 0, 0, 0)
    }

    private fun setPasswordStyle() {
        if (isPasswordFieldActive) {
            var isPasswordVisible = false
            binding.rightIcon.visibility = VISIBLE
            binding.inputEditText.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD
            binding.rightIcon.setImageResource(R.drawable.ic_show_password)

            binding.rightIcon.setOnClickListener {
                if (isPasswordVisible) {
                    binding.inputEditText.transformationMethod = null
                    isPasswordVisible = false
                } else {
                    binding.inputEditText.transformationMethod = PasswordTransformationMethod()
                    isPasswordVisible = true
                }
                binding.inputEditText.clearFocus()

            }
        } else binding.rightIcon.visibility = GONE
    }
}