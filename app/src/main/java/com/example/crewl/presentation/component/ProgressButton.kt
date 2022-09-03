package com.example.crewl.presentation.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.example.crewl.R
import com.example.crewl.databinding.ComponentButtonBinding
import com.example.crewl.helper.ResourceHelper.getColor

private const val BUTTON_TEXT_STYLE_NORMAL = 0
private const val BUTTON_TEXT_STYLE_MEDIUM = 1
private const val BUTTON_TEXT_STYLE_BOLD = 2

class ProgressButton : LinearLayout {
    private lateinit var binding: ComponentButtonBinding

    private var density = 0f

    private var header: String? = null
    private var buttonBackground = 0
    private var textStyle = 0

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ComponentButtonBinding.inflate(inflater, this, true)

        density = resources.displayMetrics.density

        val styledAttribute: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton)

        header = styledAttribute.getString(R.styleable.ProgressButton_header)

        buttonBackground = styledAttribute.getResourceId(R.styleable.ProgressButton_buttonBackground, R.color.default_button_bg)
        textStyle = styledAttribute.getInteger(R.styleable.ProgressButton_textStyle, 1)

        styledAttribute.recycle()

        setHeader()
        setTextStyle()
        setTheme()
    }

    private fun setHeader() {
        binding.apply {
            header?.let { header ->
                if (header != "") {
                    headerText.text = header
                    headerText.letterSpacing = -0.04f
                    headerText.textAlignment = TEXT_ALIGNMENT_CENTER
                    headerText.isAllCaps = false
                }
                else {
                    headerText.text = ""
                    headerText.visibility = GONE
                }
            } ?: run {
                headerText.text = ""
                headerText.visibility = GONE
            }
        }
    }

    private fun setTextStyle() {
        binding.apply {
            when (textStyle) {
                BUTTON_TEXT_STYLE_NORMAL -> {
                    val typeFace = ResourcesCompat.getFont(context, R.font.inter_regular)
                    this.headerText.typeface = typeFace
                }
                BUTTON_TEXT_STYLE_MEDIUM -> {
                    val typeFace = ResourcesCompat.getFont(context, R.font.inter_medium)
                    this.headerText.typeface = typeFace
                }
                BUTTON_TEXT_STYLE_BOLD -> {
                    val typeFace = ResourcesCompat.getFont(context, R.font.inter_semibold)
                    this.headerText.typeface = typeFace
                }
            }
        }
    }

    private fun setTheme() {
        binding.headerText.setTextColor(Color.WHITE)
        binding.buttonCardView.setCardBackgroundColor(getColor(buttonBackground))
        binding.progressBar.indeterminateDrawable.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.WHITE, BlendModeCompat.SRC_ATOP)
    }

    fun setLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.progressBar.visibility = VISIBLE
                binding.headerText.visibility = GONE
            }
            false -> {
                binding.progressBar.visibility = GONE
                binding.headerText.visibility = VISIBLE
            }
        }
    }
}