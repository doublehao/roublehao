package com.rouble.worker;

import com.rouble.zk.ZKApis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-10
 * Time: ÉÏÎç10:59
 * To change this template use File | Settings | File Templates.
 */
public class ZKWorker implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKWorker.class);
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    public void work(){
        LOGGER.info(">>>>>>zkWorker starting...");
        try{
            ZKApis.updateData();
        }catch (Exception e){
            LOGGER.error(">>>>>>zkWorker error:",e);
        }
    }

    @Override
    public void run() {
        work();
    }

    public static void updateDBS(){
        SCHEDULED_EXECUTOR_SERVICE.schedule(new ZKWorker(),0, TimeUnit.SECONDS);
    }

    public static void main(String[] args){
        updateDBS();
    }
}
