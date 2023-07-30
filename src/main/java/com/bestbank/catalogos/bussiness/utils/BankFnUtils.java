package com.bestbank.catalogos.bussiness.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import java.util.Date;
import java.util.UUID;


/**
 * Clase que proporciona funciones y utilidades relacionadas con operaciones bancarias.
 */
public class BankFnUtils {
  
  public static String uniqueProductCode() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
  
  public static java.sql.Timestamp getDateTime() {
    return java.sql.Timestamp.valueOf(LocalDateTime.now());
  }
  
  public static Date getLegacyDateTimeNow() {
    LocalDateTime currDateTime = LocalDateTime.now();
    return Date.from(currDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
  
  public static Date getLegacyFirtDateOfMonth() {
    LocalDate currentDate = LocalDate.now();
    LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
    return Date.from(firstDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
  
  public static Date getLegacyEndDateOfMonth(Integer overYears) {
    LocalDate currentDate = LocalDate.now().plusYears(overYears);
    int year = currentDate.getYear();
    int month = currentDate.getMonthValue();
    int lastDayOfMonth = currentDate.lengthOfMonth();
    LocalTime time = LocalTime.of(23, 59, 59);
    LocalDateTime lastDayOfMonthDateTime = 
        LocalDateTime.of(year, month, lastDayOfMonth, time.getHour(), 
            time.getMinute(), time.getSecond());
    return Date.from(lastDayOfMonthDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
  
  private BankFnUtils() {}
  
}
