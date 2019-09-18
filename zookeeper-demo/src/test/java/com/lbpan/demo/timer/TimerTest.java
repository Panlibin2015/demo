package com.lbpan.demo.timer;

import org.junit.Test;

import java.nio.channels.SelectionKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimerTest {

    public static void main(String[] args) {

        int workerNum = ((int) (6d % 6) + 6) % 6;
        System.out.println(workerNum);
        System.out.println(SelectionKey.OP_READ);
        System.out.println(SelectionKey.OP_READ == 0);
    }

    @Test
    public void timerTest() throws ParseException, InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date executeDate = simpleDateFormat.parse("2019-09-07 20:37:02");


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行了");
                countDownLatch.countDown();
            }
        };
        Timer timer = new Timer("测试定时器", true);
        // 如果给定的时间是过去的，就立马执行任务
        timer.schedule(task, executeDate);

        countDownLatch.await();

    }

    @Test
    public void timerTest2() throws ParseException, InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date executeDate = simpleDateFormat.parse("2019-09-07 20:32:02");


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalTime.now().toSecondOfDay());
                System.out.println("执行了");
            }
        };
        Timer timer = new Timer("测试定时器", true);
        // 如果给定的时间是过去的，就立马执行任务
        timer.scheduleAtFixedRate(task, 0, 1000);

        countDownLatch.await();

    }

    @Test
    public void timerTest3() throws ParseException, InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date executeDate = simpleDateFormat.parse("2019-09-07 20:32:02");


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalTime.now().toSecondOfDay());
                System.out.println("执行了");
            }
        };
        Timer timer = new Timer("测试定时器", true);
        // 如果给定的时间是过去的，就立马执行任务
        timer.scheduleAtFixedRate(task, 0, TimeUnit.HOURS.toMillis(0));

        countDownLatch.await();

    }
}
