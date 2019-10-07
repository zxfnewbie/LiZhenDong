package com.test1.calculator.utils;

import android.widget.TextView;


/**
 * 用于格式化显示中的文本
 */
public class TextUtil {
    public static String getCleanText(TextView textView) {
        return textView.getText().toString();
    }

    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }
}
