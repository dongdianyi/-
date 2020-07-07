package com.example.common

import android.util.Base64
import java.nio.charset.StandardCharsets

object Base64Utils {
    /**
     * 字符Base64加密
     * @param str
     * @return
     */
    fun encodeToString(str: String): String {
        return Base64.encodeToString(
            str.toByteArray(StandardCharsets.UTF_8),
            Base64.NO_WRAP
        )
    }

    /**
     * 字符Base64解密
     * @param str
     * @return
     */
    fun decodeToString(str: String): String {
        return String(
            Base64.decode(
                str.toByteArray(StandardCharsets.UTF_8),
                Base64.DEFAULT
            )
        )
    }
}