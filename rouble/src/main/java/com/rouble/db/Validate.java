package com.rouble.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-9
 * Time: ионГ11:00
 * To change this template use File | Settings | File Templates.
 */
public interface Validate {
    public abstract boolean validateDBServer(Map<String,String> map);
    public abstract boolean spiltTo(String value);
    public abstract boolean validateConnection(String url, String username, String password);
    public abstract void configDriver();
    public abstract void validate(Map<String,String> temp, Map<String,String> dbServer);
}
