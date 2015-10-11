package com.rouble.db;

import com.rouble.zk.ZKResMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rouble.zk.ZKResMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-8
 * Time: ÏÂÎç4:20
 * To change this template use File | Settings | File Templates.
 */
public class ValidateMysqlTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ValidateMysqlTest.class);
    static {
        configDriver("com.mysql.jdbc.Driver");
    }

    public static boolean validateDBServer(Map<String,String> map){
        boolean  falg = false;
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            LOGGER.info("key:"+key+",value:"+value);
            if(!spiltTo(value)){
                falg = true;
                LOGGER.info("sqlInfo error! key:"+key+",value:"+value);
                it.remove();
            }
        }
        return  falg;
    }

    public static boolean spiltTo(String value){
        if(StringUtils.isBlank(value)){
            return  false;
        }
        String[] sqlIfo= StringUtils.split(value,",");
        if(sqlIfo.length != 3) {
            return  false;
        }
        String url = sqlIfo[0];
        String username = sqlIfo[1];
        String password = sqlIfo[2];
        LOGGER.info("url:"+url+",username:"+username+",password:"+password);
        return  validateConnection(url, username, password);
    }

    public static boolean validateConnection(String url, String username, String password) {
        Connection conn = null;
        LOGGER.info("validate mysql: url:" + url + ",username:" + username + ",password:" + password);
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOGGER.info("connecting mysql error SQLException:", e.getMessage());
        } catch (Exception e){
            LOGGER.info("connecting mysql error Exception:", e);
        } finally {
            return null != conn;
        }
    }

    public static void configDriver(String driver){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.error("config driver error :"+driver,e);
        }
    }

    public static void updateDB(Map<String,String> temp, Map<String,String> dbServer){
        if(null == temp || temp.size() == 0)
            return;
        if(validateDBServer(temp)){
            dbServer = new ConcurrentHashMap<String, String>(temp);
        }
    }

    public static void main(String[] args){
        LOGGER.info("before size:"+ ZKResMap.dbServer.size());
        for(String key : ZKResMap.dbServer.keySet()){
            LOGGER.info("key:"+key+",value:"+ZKResMap.dbServer.get(key));
        }
        updateDB(ZKResMap.temp, ZKResMap.dbServer);
        LOGGER.error("after size:"+ZKResMap.dbServer.size());
        for(String key : ZKResMap.dbServer.keySet()){
            LOGGER.info("key:"+key+",value:"+ZKResMap.dbServer.get(key));
        }
    }
}
