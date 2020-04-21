package com.testtime;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/20 16:36
 **/
public class TestLocalDateTime {

    //1. LocalDate  LocalTime LocalDateTime
    @Test
    public void test1(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);


        LocalDateTime ldt2= LocalDateTime.of(2020,04,18,11,11,11);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.plusYears(2);
        System.out.println(ldt3);

        LocalDateTime ld4 = ldt.minusMonths(2);
        System.out.println(ld4);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonth());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
    }

    //2. Instant : 时间戳（以Unix 元年：1970年1月1日 00:00:00 到此时之间的毫秒值）
    @Test
    public void test2(){
        Instant ins1 = Instant.now(); //默认获取 UTC 时区
        System.out.println(ins1);

        OffsetDateTime odt= ins1.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        System.out.println(ins1.toEpochMilli());

        Instant is2 = Instant.ofEpochSecond(1000);
        System.out.println(is2);
    }

    //3.Duration : 计算两个“时间”之间的间隔
    //Period :计算两个“日期”之间的间隔
    @Test
    public void test3() throws InterruptedException {
        Instant ins1 = Instant.now();

        Thread.sleep(100);

        Instant ins2 = Instant.now();

        Duration duration= Duration.between(ins1,ins2);
        //获取毫秒用to
        System.out.println(duration.toMillis());

        System.out.println("--------------------------");
        LocalTime lt1 = LocalTime.now();
        Thread.sleep(1000);

        LocalTime lt2 = LocalTime.now();

        System.out.println(Duration.between(lt1,lt2).toMillis());
    }

    @Test
    public void test4(){
        LocalDate ld1 = LocalDate.of(2018,1,1);
        LocalDate ld2 = LocalDate.now();

        Period period = Period.between(ld1,ld2);
        System.out.println(period);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }


    //TemporalAdjuster: 时间校正器
    @Test
    public  void test5(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);

        //下一个周日
        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义，下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
           LocalDateTime ldt4 = (LocalDateTime) l;
           DayOfWeek dow=ldt4.getDayOfWeek();
           if (dow.equals(DayOfWeek.FRIDAY)){
             return   ldt4.plusDays(3);
           }else if (dow.equals(DayOfWeek.SUNDAY)){
               return ldt4.plusDays(2);
           }else {
               return ldt4.plusDays(1);
           }
        });
        System.out.println(ldt5);
    }

    //DateTimeFormatter : 格式化时间、日期
    @Test
    public void test6(){
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);
        LocalDateTime ldt2 = LocalDateTime.parse("2020-04-21T14:56:35.682");
        System.out.println(ldt2.format(DateTimeFormatter.ISO_DATE));
        System.out.println(strDate);

        System.out.println("--------------------------------------------------");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String strdate2= dtf2.format(ldt);
        System.out.println(strdate2);
        LocalDateTime newDate = ldt.parse(strdate2,dtf2);
        System.out.println(newDate);

    }

    //ZoneDate、ZoneTime、ZoneDateTime: 对时区的操作
    @Test
    public void test7(){
        Set<String> zoneIds= ZoneId.getAvailableZoneIds();
//        zoneIds.forEach(i -> System.out.println(i));
        zoneIds.forEach(System.out::println);
    }

    @Test
    public void test8(){
       LocalDateTime ldt=  LocalDateTime.now(ZoneId.of("Asia/Hong_Kong"));
        System.out.println(ldt);

        LocalDateTime ldt2 = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        ZonedDateTime zdt =  ldt2.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(zdt);
    }
}
