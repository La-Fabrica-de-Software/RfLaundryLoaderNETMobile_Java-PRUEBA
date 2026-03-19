package com.lafabricadesoftware.rfidlaundry.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

const val ONLY_DATE = "yyyy-MM-dd"
const val FULL_DATE = "yyyy-MM-dd HH:mm:ss"

class DateTimeUtils {

    fun getActualFormattedDate(): String {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(ONLY_DATE))
    }

    fun getStringToLocalDate(dateAsString: String): LocalDate {
        return try {
//            val notSpacesDateTime = dateAsString.replace ( " " , "T" )
            LocalDate.parse(dateAsString.take(10), DateTimeFormatter.ofPattern(ONLY_DATE))
        } catch (e: Exception) {
            LocalDate.now().atStartOfDay().toLocalDate()
        }
    }
//    fun getStringToDate(dateAsString: String): Date {
//        return try {
////            Date.parse(dateAsString.take(10), DateTimeFormatter.ofPattern(ONLY_DATE))
//        } catch (e: Exception) {
////            Date.now().atStartOfDay().toLocalDate()
//        }
//    }

    fun asDate(localDate: LocalDate): Date? {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
    }
    fun asDate(localDateTime: LocalDateTime): Date? {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
    fun asLocalDate(date: Date): LocalDate? {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
    }
    fun asLocalDateTime(date: Date): LocalDateTime? {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}