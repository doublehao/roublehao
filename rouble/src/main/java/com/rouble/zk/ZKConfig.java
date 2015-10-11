package com.rouble.zk;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-9-29
 * Time: ÏÂÎç5:12
 * To change this template use File | Settings | File Templates.
 */
public class ZKConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKConfig.class);
    public static String ipList = "127.0.0.1:2181";
    public static Integer sessionTimeOut = 20000;
    public static String dbPath = "/dbserver";
    static {
        init();
    }
    public ZKConfig(String ipString, Integer timeOut, String db){
        ipList = ipString;
        sessionTimeOut = timeOut;
        dbPath = db;
    }
    public void setIpList(String ipString){
        ipList = ipString;
    }
    public String getIpList(){
        return ipList;
    }
    public static void init(){
        Properties properties = new Properties();
        try {
            InputStream inputStream = ZKConfig.class.getResourceAsStream("/properties/zookeeper.properties");
            properties.load(inputStream);
            ipList = properties.getProperty("ipList");
            if(StringUtils.isNumeric(properties.getProperty("session.timeout"))){
                sessionTimeOut = new Integer(properties.getProperty("session.timeout"));
            }else{
                LOGGER.info("[zookeeper.session.timeout] must be numberic");
            }
            dbPath = properties.getProperty("zk.db.path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
