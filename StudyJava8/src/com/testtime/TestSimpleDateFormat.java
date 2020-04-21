package com.testtime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/20 15:57
 **/
public class TestSimpleDateFormat {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

//        Callable<Date> task = new Callable<Date>() {
//            @Override
//            public Date call() throws Exception {
////                return simpleDateFormat.parse("20200418");
//                return DateFormatThreadLocal.convert("20200418");
//            }
//        };
//
//        //线程池
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//
//        List<Future<Date>> futures = new ArrayList<>();
//        for (int i =0 ; i< 10 ;i++){
//            futures.add(pool.submit(task));
//        }
//
//        for (Future<Date> future : futures){
//            System.out.println(future.get());
//        }
//        pool.shutdown();

        //----------------------------------------------------------
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        Callable<LocalDate> task = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
//                return simpleDateFormat.parse("20200418");
                return LocalDate.parse("20200418",dtf);
            }
        };

        //线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<LocalDate>> futures = new ArrayList<>();
        for (int i =0 ; i< 10 ;i++){
            futures.add(pool.submit(task));
        }

        for (Future<LocalDate> future : futures){
            System.out.println(future.get());
        }
        pool.shutdown();
    }
}
