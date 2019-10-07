package com.test1.calculator.utils;

import android.content.Context;
import android.widget.Toast;

public class ClipboardManager {
    //将文本复制到剪贴板
    public static void setClipboard(Context context, String text) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }

}
