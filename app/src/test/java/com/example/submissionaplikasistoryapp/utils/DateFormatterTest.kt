package com.example.submissionaplikasistoryapp.utils

import org.junit.Assert
import org.junit.Test
import java.time.DateTimeException
import java.time.zone.ZoneRulesException

class DateFormatterTest {
    @Test
    fun `given correct ISO 8610 format then should format correctly`() {
        val currentDate = "2023-09-04T10:10:10Z"
        Assert.assertEquals(
            "04 Sep 2023 | 17:10",
            DateFormatter.formatDate(currentDate, "Asia/Jakarta")
        )
        Assert.assertEquals(
            "04 Sep 2022 | 24:00",
            DateFormatter.formatDate(currentDate, "Asia/Makassar")
        )
        Assert.assertEquals(
            "05 Sep 2022 | 01:00",
            DateFormatter.formatDate(currentDate, "Asia/Jayapura")
        )
    }

    @Test
    fun `given wrong ISO 8610 format then should throw error`() {
        val wrongFormat = "2023-09-04T10:10:10"
        Assert.assertThrows(DateTimeException::class.java) {
            DateFormatter.formatDate(wrongFormat, "Asia/Jakarta")
        }
    }

    @Test
    fun `given invalid timezone then should throw error`() {
        val wrongFormat = "2023-09-04T10:10:10Z"
        Assert.assertThrows(ZoneRulesException::class.java) {
            DateFormatter.formatDate(wrongFormat, "Asia/Invalid")
        }
    }
}