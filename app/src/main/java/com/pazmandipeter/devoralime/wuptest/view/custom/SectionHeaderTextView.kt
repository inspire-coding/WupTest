package com.pazmandipeter.devoralime.wuptest.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.pazmandipeter.devoralime.wuptest.R
import kotlinx.android.synthetic.main.view_sectionheader_textview.view.*

class SectionHeaderTextView : FrameLayout {


    constructor(context: Context) : super(context) {
        setup(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup(attrs)
    }


    init {
        View.inflate(context, R.layout.view_sectionheader_textview, this)
    }

    /**
     *
     *
     *  Private
     *
     *
     */
    private fun setup(attrs: AttributeSet?) {
        attrs?.let {
            processAttributes(it)
        }

    }

    private fun processAttributes(attributeSet: AttributeSet) {
        attributeSet?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.SectionHeaderTextView)
            with(typedArray) {
                try {
                    val descText =
                        getString(
                            R.styleable.SectionHeaderTextView_text
                        )
                    tv_text?.text = descText
                } finally {
                    recycle()
                }
            }
        }
    }

}