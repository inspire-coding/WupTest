package com.pazmandipeter.devoralime.wuptest.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.pazmandipeter.devoralime.wuptest.R
import kotlinx.android.synthetic.main.view_account_detail_line.view.*

class AccountDetailLine : FrameLayout {


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
        View.inflate(context, R.layout.view_account_detail_line, this)
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

    fun setCurrency(currency: String) {
        tv_currency?.text = currency
    }

    fun setBalance(balance: String) {
        tv_balance?.text = balance
    }




    private fun processAttributes(attributeSet: AttributeSet) {
        attributeSet?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.AccountDetailLine)
            with(typedArray) {
                try {
                    val descText =
                        getString(
                            R.styleable.AccountDetailLine_descText
                        )
                    tv_descText?.text = descText

                    val currency =
                        getString(
                            R.styleable.AccountDetailLine_currency
                        )
                    tv_currency?.text = currency

                    val balance =
                        getString(
                            R.styleable.AccountDetailLine_balance
                        )
                    tv_balance?.text = balance
                } finally {
                    recycle()
                }
            }
        }
    }

}