package com.example.crewl.core

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.crewl.manager.WindowControl

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity(), WindowControl {

    protected abstract fun getViewModel(): Class<VM>

    protected abstract fun getViewBinding(): VB

    protected abstract val themeId: Int

    protected abstract fun onCreate(savedInstanceState: Bundle?, viewModel: VM, binding: VB)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themeId)
        super.onCreate(savedInstanceState)

        val binding: VB = getViewBinding()
        val viewModel: VM = ViewModelProvider(owner = this)[getViewModel()]

        setContentView(binding.root)

        hideBars()
        setTransparent()

        onCreate(savedInstanceState, viewModel, binding)
    }

    override fun onStart() {
        super.onStart()

    }

    override fun hideBars() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.captionBar())
            /*
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.hide(WindowInsetsCompat.Type.statusBars())
             */
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun setTransparent() {
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun setStatusBarLight() {
        window.statusBarColor = Color.WHITE
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (it.action == MotionEvent.ACTION_DOWN) {
                val view = currentFocus
                if (view is EditText) {
                    val outRect = Rect()
                    view.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        view.clearFocus()
                        val imm: InputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                    }
                }
            }
        }

        return super.dispatchTouchEvent(event)
    }
}

