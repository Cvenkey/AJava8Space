package com.ajava8.space.dateTime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
/**
 * EightDateTimeLogics shows new date logics 
 * @author aJava8Space
 *
 */
public class EightDateTimeLogics {

    public static void main(String[] args) {
         stringToDate("12/09/2024", "dd/MM/yyyy");
         toPattern("dd-MMM-yy");
         LocalDate now = LocalDate.now();
         LocalDate past = LocalDate.of(1869,10,02);
         dateDifference(past,now);
         timeDifference(LocalTime.now(ZoneId.of("Asia/Kolkata")),LocalTime.now(ZoneId.of("Asia/Dubai")));
         convertZoneTimeToAnother("Asia/Calcutta");
         zoneParseFormat("yyyy-MMM-dd HH:mm:ss z",ZonedDateTime.of(2023, 10, 4, 12, 0, 0, 0, ZoneId.of("Europe/London")).toString());
         timeDiffBtnZones("Asia/Calcutta","Europe/London");
         convertTimeToAnotherZone("09-10-2024 02:17:15 PM", "Asia/Dubai");
         compareTimeZones("Australia/Sydney","Asia/Shanghai");
    }

   private static void timeDifference(LocalTime time1, LocalTime time2) {
        Duration duration;
        if(time1.isBefore(time1)){
               duration =  Duration.between(time1,time2);
         }  else{
            duration = Duration.between(time2, time1);
         }    
        System.out.printf("\ntimeDifference- Hours: %d Minutes %d, Seconds %d, MilliSeconds %d",
        duration.toHours(),duration.toMinutes() % 60, duration.getSeconds() % 60,duration.toMillis() % 1000);
    }

    private static void dateDifference(LocalDate date1, LocalDate date2){
        Period period;
         if(date1.isBefore(date2)){
               period =  Period.between(date1,date2);
         }  else{
            period = Period.between(date2, date1);
         } 
         System.out.printf("\ndateDifference- Days: %d Months: %d Years: %d" ,
         period.getDays(),period.getMonths(),period.getYears());
    }

    private static void toPattern(String pattern) {
         LocalDate date = LocalDate.now();
         String currentDate = date.format(DateTimeFormatter.ofPattern(pattern));
         System.out.println("Displaying date in pattern "+pattern+" "+currentDate);
    }

    private static void stringToDate(String strDate,String format){
        LocalDate localDate = (format != null ? LocalDate.parse(strDate, DateTimeFormatter.ofPattern(format)):
                LocalDate.parse(strDate));
        System.out.println("Formatted String Date: "+localDate);
    }

    private static void zoneMiscellaneousOps(){
        ZonedDateTime byZoneId = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        ZonedDateTime byZoneOffset = ZonedDateTime.now(ZoneOffset.of("+05:30"));
        ZonedDateTime byZoneShortId = ZonedDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("IST")));
        Set<String> zonesIds = ZoneId.getAvailableZoneIds();
              
    }


    private static void convertZoneTimeToAnother(String zoneId){
       ZonedDateTime currZonedDateTime = ZonedDateTime.now( ZoneId.of("Asia/Calcutta"));
       System.out.println("\nCurrent Zone: "+currZonedDateTime);
       System.out.println("New York time:"+currZonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York")));   
       System.out.println("Duabai: "+currZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Dubai")));   
       System.out.println("Kuwait time:"+currZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Kuwait")));   
       System.out.println("Sydney:"+currZonedDateTime.withZoneSameInstant(ZoneId.of("Australia/Sydney")));   
       System.out.println("Shanghai: "+currZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai")));   
    }

    private static void zoneParseFormat(String pattern,String parseString ){
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formatted = now.format(formatter);
        System.out.println("Formatted ZonedDateTime: " + formatted);

        String dateTimeString = parseString;
        ZonedDateTime parsedDateTime = ZonedDateTime.parse(dateTimeString);
        System.out.println("Parsed ZonedDateTime: " + parsedDateTime);
    }

    private static void timeDiffBtnZones(String zoneId1, String zoneId2) {
       System.out.println("Time Difference between given zones");
       ZoneId zone1 = ZoneId.of(zoneId1);
       ZoneId zone2 = ZoneId.of(zoneId2);
       ZonedDateTime zone1DateTime = ZonedDateTime.of(LocalDateTime.now(), zone1);
       ZonedDateTime zone2DateTime = zone1DateTime.withZoneSameInstant(zone2);

       System.out.println("Time in Zone-"+zoneId1+" "+zone1DateTime);
       System.out.println("Time in Zone"+zoneId2+" "+zone2DateTime);

       int diffMinutes =(zone1DateTime.getOffset().getTotalSeconds()- zone2DateTime.getOffset().getTotalSeconds())/60;
       System.out.println("Difference between time zones in Minutes"+Math.abs(diffMinutes));
    }

    private static void convertTimeToAnotherZone(String time, String zoneId){
       System.out.println("convertTimeToAnotherZone ");
       String format = "dd-MM-yyyy hh:mm:ss a";       
       LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format));
       ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Calcutta"));
       System.out.println("Current Zone DateTime "+zonedDateTime);

       ZonedDateTime convertedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(zoneId));      
       System.out.println("DateTime @ "+zoneId+" is:" + convertedDateTime);
    }

    private static void compareTimeZones(String zoneId, String anotherZoneId) {
         System.out.println("compareTimeZones: "+zoneId+" , "+anotherZoneId);
         ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(zoneId));
         ZonedDateTime anotherZonedDateTime = ZonedDateTime.now(ZoneId.of(anotherZoneId));

         System.out.println("Time @ "+zoneId+" "+zonedDateTime);
         System.out.println("Time @ "+anotherZoneId+" "+anotherZonedDateTime);

         System.out.println("off sets:"+ zonedDateTime.getOffset().getTotalSeconds()+" "+anotherZonedDateTime.getOffset().getTotalSeconds());
        
         if(zonedDateTime.getOffset().getTotalSeconds()>anotherZonedDateTime.getOffset().getTotalSeconds())
             System.out.println(zonedDateTime + " is first");
         else if(zonedDateTime.getOffset().getTotalSeconds()<anotherZonedDateTime.getOffset().getTotalSeconds())
             System.out.println(anotherZonedDateTime+"is first");
         else
             System.out.println("Zones "+zoneId+" , "+anotherZoneId+" are equal");
   }



      




}
