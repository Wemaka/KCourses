package com.wemaka.kcourses.common.extentions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun String.formatLocalizedDate(locale: Locale): String {
    return try {
        val date = LocalDate.parse(this)

        val formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.LONG)
            .withLocale(locale)

        date.format(formatter)
            .replace(" г.", "")
            .trim()
    } catch (e: Exception) {
        this
    }
}