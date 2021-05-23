package com.example.springbootperformanalysis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootperformanalysis.web.controller
 * @Description: 搞一个线程死锁出来
 * @date 2021/5/23 上午11:04
 */
@RestController
public class DeadLockController {

    @Autowired
    private Executor executor;

    /**
     * @data: 2021/5/23-上午11:22
     * @User: zhaozhiwei
     * @method: deadLock

     * @return: void
     * @Description: 通过线程池模拟，设置
     *     corePoolSize: 2 #设置核心线程数
     *     maxPoolSize: 3  #设置最大线程数
     * 请求: curl http://localhost:8080/deadlock
     * 除第一次会输出线程中内容，后续只有main线程在走
     *
     * 方法1: 命令行中执行
     * 1. jps 获取 pid
     * 2. jstack -l pid # 结果中会有deadlock字眼，说明线程死锁
     *
     * 2021-05-23 11:39:40
     * Full thread dump OpenJDK 64-Bit Server VM (11.0.11+9 mixed mode):
     *
     * Threads class SMR info:
     * _java_thread_list=0x00007fd4a8000c30, length=31, elements={
     * 0x00007fd4f8318800, 0x00007fd4f831d000, 0x00007fd4f8331800, 0x00007fd4f8333800,
     * 0x00007fd4f8335800, 0x00007fd4f8338000, 0x00007fd4f833a000, 0x00007fd4f83fd000,
     * 0x00007fd4f8480000, 0x00007fd4f8484000, 0x00007fd488001800, 0x00007fd4f924d000,
     * 0x00007fd4784f6800, 0x00007fd4f924b000, 0x00007fd4f931c000, 0x00007fd4f92ef000,
     * 0x00007fd4f92fd000, 0x00007fd4f9322800, 0x00007fd4f9323800, 0x00007fd4f9325800,
     * 0x00007fd4f9328000, 0x00007fd4f932a000, 0x00007fd4f932c000, 0x00007fd4f932e000,
     * 0x00007fd4f9330000, 0x00007fd4f9334800, 0x00007fd4f933b000, 0x00007fd4f8017000,
     * 0x00007fd448120000, 0x00007fd448121800, 0x00007fd4a8001000
     * }
     *
     * JNI global refs: 59, weak refs: 5953
     *
     * Found one Java-level deadlock:
     * =============================
     * "bdg-common-1":
     *   waiting to lock monitor 0x00007fd47854b480 (object 0x000000069d463060, a java.lang.Object),
     *   which is held by "bdg-common-2"
     * "bdg-common-2":
     *   waiting to lock monitor 0x00007fd47854d580 (object 0x000000069d463050, a java.lang.Object),
     *   which is held by "bdg-common-1"
     *
     * Java stack information for the threads listed above:
     * ===================================================
     * "bdg-common-1":
     * 	at com.example.springbootperformanalysis.web.controller.DeadLockController.echo(DeadLockController.java:61)
     * 	- waiting to lock <0x000000069d463060> (a java.lang.Object)
     * 	- locked <0x000000069d463050> (a java.lang.Object)
     * 	at com.example.springbootperformanalysis.web.controller.DeadLockController.lambda$deadLock$0
     * 	(DeadLockController.java:40)
     * 	at com.example.springbootperformanalysis.web.controller.DeadLockController$$Lambda$481/0x0000000800505840.run
     * 	(Unknown Source)
     * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@11.0.11/ThreadPoolExecutor.java:1128)
     * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@11.0.11/ThreadPoolExecutor.java:628)
     * 	at java.lang.Thread.run(java.base@11.0.11/Thread.java:829)
     * "bdg-common-2":
     * 	at com.example.springbootperformanalysis.web.controller.DeadLockController.echo(DeadLockController.java:61)
     * 	- waiting to lock <0x000000069d463050> (a java.lang.Object)
     * 	- locked <0x000000069d463060> (a java.lang.Object)
     * 	at com.example.springbootperformanalysis.web.controller.DeadLockController.lambda$deadLock$1
     * 	(DeadLockController.java:44)
     * 	at com.example.springbootperformanalysis.web.controller.DeadLockController$$Lambda$482/0x0000000800505c40.run
     * 	(Unknown Source)
     * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@11.0.11/ThreadPoolExecutor.java:1128)
     * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@11.0.11/ThreadPoolExecutor.java:628)
     * 	at java.lang.Thread.run(java.base@11.0.11/Thread.java:829)
     *
     * Found 1 deadlock.
     *
     *
     * 方法2: 使用visualvm, archlinux 通过yaourt -S visualvm安装
     * 2021-05-23 12:32:11
     * Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.181-b13 mixed mode):
     *
     * "bdg-common-2" #44 prio=5 os_prio=0 tid=0x00007fcc7000f000 nid=0x88122 waiting for monitor entry
     * [0x00007fcc8a350000]
     *    java.lang.Thread.State: BLOCKED (on object monitor)
     *         at com.example.springbootperformanalysis.web.controller.DeadLockController.echo(DeadLockController
     *         .java:121)
     *         - waiting to lock <0x00000000ffe0d058> (a java.lang.Object)
     *         - locked <0x00000000ffe0d068> (a java.lang.Object)
     *         at com.example.springbootperformanalysis.web.controller.DeadLockController.lambda$deadLock$1
     *         (DeadLockController.java:104)
     *         at com.example.springbootperformanalysis.web.controller.DeadLockController$$Lambda$405/868644314.run
     *         (Unknown Source)
     *         at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
     *         at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
     *         at java.lang.Thread.run(Thread.java:748)
     *
     *    Locked ownable synchronizers:
     *         - <0x00000000ffe101b8> (a java.util.concurrent.ThreadPoolExecutor$Worker)
     *
     * "bdg-common-1" #43 prio=5 os_prio=0 tid=0x00007fcc7000d800 nid=0x88121 waiting for monitor entry
     * [0x00007fcc8a451000]
     *    java.lang.Thread.State: BLOCKED (on object monitor)
     *         at com.example.springbootperformanalysis.web.controller.DeadLockController.echo(DeadLockController
     *         .java:121)
     *         - waiting to lock <0x00000000ffe0d068> (a java.lang.Object)
     *         - locked <0x00000000ffe0d058> (a java.lang.Object)
     *         at com.example.springbootperformanalysis.web.controller.DeadLockController.lambda$deadLock$0
     *         (DeadLockController.java:100)
     *         at com.example.springbootperformanalysis.web.controller.DeadLockController$$Lambda$404/908308187.run
     *         (Unknown Source)
     *         at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
     *         at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
     *         at java.lang.Thread.run(Thread.java:748)
     *
     *    Locked ownable synchronizers:
     *         - <0x00000000ffe22ef8> (a java.util.concurrent.ThreadPoolExecutor$Worker)
     */
    @GetMapping("/deadlock")
    public void deadLock(){

        // 线程中互相依赖锁即可, 最少两对象
        final Object o1 = new Object();
        final Object o2 = new Object();

        executor.execute(() -> {
            echo(o1, o2);
        });

        executor.execute(() -> {
            echo(o2, o1);
        });

        System.out.println("执行结束: " + Thread.currentThread().getStackTrace()[1].getClassName());

    }

    private void echo(Object o1, Object o2) {
        synchronized (o1){
            System.out.printf("线程[%s] is access, 当前对象 %s\n", Thread.currentThread().getName(), o1);
            try {
                // 加入线程等待，防止一个线程跑太快，让出时间片让另一个线程拿到锁
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2){
                System.out.printf("线程[%s] is access, 当前对象 %s\n", Thread.currentThread().getName(), o2);
            }
        }
    }

}
