package com.rouble.zk;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-9-29
 * Time: ÏÂÎç4:55
 * To change this template use File | Settings | File Templates.
 */
public class ZKApis {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKApis.class);
    private static final ZKWatcher zkWatcher = new ZKWatcher();
    private static final ReentrantLock lock = new ReentrantLock();
    private static ZooKeeper zooKeeper;
    static {
        zkConnect(ZKConfig.ipList,zkWatcher);
    }
    public static void zkConnect(String ipList, Watcher watcher){
        if(zooKeeper != null){
            close();
        }
        LOGGER.info("[zookeeper is connecting to :]"+ipList);
        try {
            zooKeeper = new ZooKeeper(ZKConfig.ipList, ZKConfig.sessionTimeOut, watcher);
        } catch (IOException e) {
            LOGGER.error("[zookeeper connected exception :]", e);
        } finally {
            LOGGER.info("[zookeeper connected.]");
        }
    }
    public static ZooKeeper getZK(String ipList, Watcher watcher){
        lock.lock();
        try{
            zkConnect(ipList,watcher);
        }finally {
            lock.unlock();
        }
        return zooKeeper;
    };
    public static void close(){
        LOGGER.info("[zookeeper is closing...]");
        try {
            if(zooKeeper != null){
                zooKeeper.close();
            }
        } catch (InterruptedException e) {
            LOGGER.error("[zookeeper closing Exception:]", e);
        } finally {
            zooKeeper = null;
            LOGGER.info("[zookeeper closed.]");
        }
    }
    public static boolean createPath(String path, String data, List<ACL> acls, CreateMode createMode){
        boolean  flag = false;
        if(StringUtils.isNotBlank(path) && StringUtils.isNotBlank(data)){
            flag = true;
            if(acls == null){
                acls = ZooDefs.Ids.OPEN_ACL_UNSAFE;
            }
            if(createMode == null){
                createMode = CreateMode.PERSISTENT;
            }
            try {
                zooKeeper.create(path, data.getBytes(),acls,createMode);
            } catch (KeeperException e) {
                flag = false;
                LOGGER.error("[zookeeper create node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                flag = false;
                LOGGER.error("[zookeeper create node error! InteperrupedException:]", e);
            } catch (Exception e){
                flag = false;
                LOGGER.error("[zookeeper create node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper create node: path or data is can not null]"+"path:"+path+",data:"+data);
        }
        return flag;
    }
    public static boolean createPath(String path, String data){
        boolean  flag = false;
        if(StringUtils.isNotBlank(path) && StringUtils.isNotBlank(data)){
            flag = true;
            try {
                zooKeeper.create(path, data.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            } catch (KeeperException e) {
                flag = false;
                LOGGER.error("[zookeeper create node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                flag = false;
                LOGGER.error("[zookeeper create node error! InteperrupedException:]", e);
            } catch (Exception e){
                flag = false;
                LOGGER.error("[zookeeper create node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper create node: path or data is can not null]"+"path:"+path+",data:"+data);
        }
        return flag;
    }
    public static String readData(String path, Boolean watcher, Stat stat){
        String data = null;
        if(StringUtils.isNotBlank(path)){
            if(null == watcher){
                watcher = false;
            }
            if(null == stat){
                stat = null;
            }
            try {
                data = zooKeeper.getData(path, watcher, stat).toString();
            } catch (KeeperException e) {
                LOGGER.error("[zookeeper get node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                LOGGER.error("[zookeeper get node error! InteperrupedException:]", e);
            } catch (Exception e){
                LOGGER.error("[zookeeper get node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper get node: path is can not null]"+"path:"+path);
        }
        return data;
    }
    public static String readData(String path){
        String data = null;
        if(StringUtils.isNotBlank(path)){
            try {
                data = new String(zooKeeper.getData(path, false, null));
            } catch (KeeperException e) {
                LOGGER.error("[zookeeper get node error! KeeperException:]"+path, e);
            } catch (InterruptedException e) {
                LOGGER.error("[zookeeper get node error! InteperrupedException:]"+path, e);
            } catch (Exception e){
                LOGGER.error("[zookeeper get node error! Exception:]"+path,e);
            }
        }else{
            LOGGER.error("[zookeeper get node: path is can not null]"+"path:"+path);
        }
        return data;
    }
    public static boolean writeData(String path, String data, Integer version){
        boolean  flag = false;
        if(StringUtils.isNotBlank(path) || StringUtils.isNotBlank(data)){
            flag = true;
            if(null == version)
                version = -1;
            try {
                zooKeeper.setData(path, data.getBytes(), version);
            } catch (KeeperException e) {
                flag = false;
                LOGGER.error("[zookeeper set node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                flag = false;
                LOGGER.error("[zookeeper set node error! InteperrupedException:]", e);
            } catch (Exception e){
                flag = false;
                LOGGER.error("[zookeeper set node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper set node: path or data is can not null]"+"path:"+path+",data:"+data);
        }
        return flag;
    }
    public static boolean writeData(String path, String data){
        boolean  flag = false;
        if(StringUtils.isNotBlank(path) || StringUtils.isNotBlank(data)){
            flag = true;
            try {
                zooKeeper.setData(path, data.getBytes(), -1);
            } catch (KeeperException e) {
                flag = false;
                LOGGER.error("[zookeeper set node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                flag = false;
                LOGGER.error("[zookeeper set node error! InteperrupedException:]", e);
            } catch (Exception e){
                flag = false;
                LOGGER.error("[zookeeper set node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper set node: path or data is can not null]"+"path:"+path+",data:"+data);
        }
        return flag;
    }

    public static List<String> getChildrenNode(String path, boolean watcher){
        List<String> childRenNode = null;
        if(StringUtils.isNotBlank(path)){
            try {
                childRenNode = zooKeeper.getChildren(path, watcher);
            } catch (KeeperException e) {
                LOGGER.error("[zookeeper get children node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                LOGGER.error("[zookeeper get children node error! InteperrupedException:]", e);
            } catch (Exception e){
                LOGGER.error("[zookeeper get children node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper get children node: path is can not null]"+"path:"+path);
        }
        return  childRenNode;
    }
    public static Map<String, String> getChildrenNodeData(String path, boolean  watcher){
        List<String> childrenNode = getChildrenNode(path,watcher);
        Map<String, String> childrenNodeData = new ConcurrentHashMap<String, String>();
        if(null == childrenNode && childrenNode.size() == 0){
            return childrenNodeData;
        }
        for(String node: childrenNode){
            childrenNodeData.put(node, readData(ZKConfig.dbPath+"/"+node));
        }
        return childrenNodeData;
    }
    public static boolean exists(String path, boolean watcher){
        Stat stat = null;
        if(StringUtils.isNotBlank(path)){
            try {
                stat = zooKeeper.exists(path, watcher);
            } catch (KeeperException e) {
                LOGGER.error("[zookeeper exists  node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                LOGGER.error("[zookeeper exists  node error! InteperrupedException:]", e);
            } catch (Exception e){
                LOGGER.error("[zookeeper exists  node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper exists  node: path is can not null]"+"path:"+path);
        }
        return stat != null;
    }
    public static boolean exists(String path, Watcher watcher){
        Stat stat = null;
        if(StringUtils.isNotBlank(path)){
            try {
                stat = zooKeeper.exists(path, watcher);
            } catch (KeeperException e) {
                LOGGER.error("[zookeeper exists  node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                LOGGER.error("[zookeeper exists  node error! InteperrupedException:]", e);
            } catch (Exception e){
                LOGGER.error("[zookeeper exists  node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper exists  node: path is can not null]"+"path:"+path);
        }
        return stat != null;
    }
    public static boolean delete(String path, Integer version){
        boolean flag = false;
        if(StringUtils.isNotBlank(path)){
            flag = true;
            if(null == version)
                version = -1;
            try {
                zooKeeper.delete(path, version);
            } catch (KeeperException e) {
                flag = false;
                LOGGER.error("[zookeeper delete  node error! KeeperException:]", e);
            } catch (InterruptedException e) {
                flag = false;
                LOGGER.error("[zookeeper delete  node error! InteperrupedException:]", e);
            } catch (Exception e){
                flag = false;
                LOGGER.error("[zookeeper delete  node error! Exception:]",e);
            }
        }else{
            LOGGER.error("[zookeeper delete  node: path is can not null]"+"path:"+path);
        }
        return flag;
    }
    public static void updateData(){
        while (true){
            exists(ZKConfig.dbPath, zkWatcher);
        }
    }
    public static void main(String[] args){
        /*ApplicationContext ac = new ClassPathXmlApplicationContext("zk-config.xml");
        ZKConfig zkConfig = ac.getBean("zkConfig", ZKConfig.class);
        System.out.println("ipList:"+ZKConfig.ipList);
        LOGGER.info("ipList:" + ZKConfig.ipList);
        LOGGER.info("ipList:" + ZKConfig.ipList);
        LOGGER.info("timeOut:" + ZKConfig.sessionTimeOut);
        LOGGER.info("dbPath:" + ZKConfig.dbPath);

        Stat stat = exists(ZKConfig.dbPath, false);
        if(null == stat){
            LOGGER.info("node:" + ZKConfig.dbPath + " do not exists!");
            createPath(ZKConfig.dbPath, ZKConfig.dbPath);
        } else {
            LOGGER.info("node:" + ZKConfig.dbPath + "is exists!");
            delete(ZKConfig.dbPath, -1);
        }
        stat = exists(ZKConfig.dbPath, false);
        if(null == stat)
            LOGGER.info("node:" + ZKConfig.dbPath + " do not exists!");
        else
            LOGGER.info("node:" + ZKConfig.dbPath + "is exists!");*/
        /*LOGGER.info("size:"+ZKResMap.dbServer.size());
        for(String string : ZKResMap.dbServer.keySet()){
            LOGGER.info("Key:"+string+",value:"+ZKResMap.dbServer.get(string));
        }*/
        updateData();
    }
}
