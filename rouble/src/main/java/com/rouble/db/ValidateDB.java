package com.rouble.db;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-9
 * Time: ÉÏÎç11:18
 * To change this template use File | Settings | File Templates.
 */
public abstract class ValidateDB implements Validate{
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateDB.class);
    @Override
    public boolean validateDBServer(Map<String, String> map) {
        boolean  flag = false;
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            LOGGER.info("key:"+key+",value:"+value);
            if(!spiltTo(value)){
                flag = true;
                LOGGER.info("sqlInfo error! key:"+key+",value:"+value);
                it.remove();
            }
        }
        return  flag;
    }

    @Override
    public boolean spiltTo(String value) {
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

    @Override
    public boolean validateConnection(String url, String username, String password) {
        Connection conn = null;
        LOGGER.info("validate db >>> url:" + url + ",username:" + username + ",password:" + password);
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOGGER.error("connecting db error SQLException:"+e.getMessage()+"!!!url:"+url+",username:"+username+",password:"+password);
        } catch (Exception e){
            LOGGER.info("connecting db error Exception:", e);
        } finally {
            return null != conn;
        }
    }

    @Override
    public void validate(Map<String, String> temp, Map<String, String> dbServer) {
        if(null == temp || temp.size() == 0)
            return;
        configDriver();
        if(validateDBServer(temp)){
            dbServer = new ConcurrentHashMap<String, String>(temp);
        }
    }
}
