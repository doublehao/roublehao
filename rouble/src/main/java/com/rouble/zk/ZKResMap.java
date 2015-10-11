package com.rouble.zk;

import com.rouble.zk.ZKConfig;
import com.rouble.zk.ZKApis;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-9-30
 * Time: обнГ5:46
 * To change this template use File | Settings | File Templates.
 */
public class ZKResMap implements Cloneable{
    public static volatile Map<String, String> dbServer;
    public static Map<String, String> temp;
    static{
        init();
    }
    public static void init(){
        dbServer = ZKApis.getChildrenNodeData(ZKConfig.dbPath, false);
        temp = ZKApis.getChildrenNodeData(ZKConfig.dbPath, false);
    }
}
