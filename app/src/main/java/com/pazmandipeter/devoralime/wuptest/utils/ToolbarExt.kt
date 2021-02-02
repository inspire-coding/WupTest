package com.pazmandipeter.devoralime.wuptest.utils

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import java.util.regex.Pattern


fun Toolbar.setTitle(label: CharSequence?, textView: TextView, arguments: Bundle?) {
    if (label != null) {

        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments.get(argName).toString())
            } else {
                return
            }
        }
        matcher.appendTail(title)
        setTitle("")
        textView.text = title
    }
}