package com.scout.myplayer.utils

import android.annotation.SuppressLint
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

object StringUtils {
    @SuppressLint("DefaultLocale")
    fun formatMSToTime(ms : String): String{
        try {
            val millis = ms.toLong()
            //ms to hh:mm:ss
            val min = millis/1000/60
            val sec = millis/1000%60
            val duration = java.lang.String.format(
                "%02d:%02d", min, sec
            )
            return duration
        } catch (e : Exception) {
            return ""
        }

    }
}