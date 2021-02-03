package com.pazmandipeter.devoralime.wuptest.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.pazmandipeter.devoralime.wuptest.R
import kotlinx.android.synthetic.main.activity_main.view.*
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

    fun setBalanceTextColor(@ColorRes color: Int) {
        tv_balance?.setTextColor(ContextCompat.getColor(context, color))
    }

    fun enableDividerLine(enable: Boolean) {
        if(enable) {
            separator?.visibility = View.VISIBLE
        } else {
            separator?.visibility = View.GONE
        }
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

                    val balanceTextColor =
                        getInt(
                            R.styleable.AccountDetailLine_balanceTextColor, 0
                        )
                    if(balanceTextColor != 0) {
                        tv_balance?.setTextColor(ContextCompat.getColor(context, balanceTextColor))
                    }

                    val enableDividerLine =
                        getBoolean(
                            R.styleable.AccountDetailLine_enableDividerLine, true
                        )
                    if(enableDividerLine) {
                        separator?.visibility = View.VISIBLE
                    } else {
                        separator?.visibility = View.GONE
                    }
                } finally {
                    recycle()
                }
            }
        }
    }

}