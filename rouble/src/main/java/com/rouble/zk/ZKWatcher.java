package com.rouble.zk;

import com.rouble.db.ValidateMysql;
import com.rouble.db.ValidateMysql;
import com.rouble.db.ValidateMysqlTest;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-9-30
 * Time: ÏÂÎç5:32
 * To change this template use File | Settings | File Templates.
 */
public class ZKWatcher implements Watcher{
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKWatcher.class);
    private static final ValidateMysql VALIDATEMYSQL = new ValidateMysql();
    @Override
    public void process(WatchedEvent event) {
        LOGGER.info("[zookeeper touch event>>> :]"+event);
        if(event.getType() == Event.EventType.NodeDataChanged){
            LOGGER.info("[zookeeper node data changed:]"+event.getPath());
        }
        if(event.getType() == Event.EventType.NodeCreated){
            LOGGER.info("[zookeeper node data created:]"+event.getPath());
        }
        if(event.getType() == Event.EventType.NodeDeleted){
            LOGGER.info("[zookeeper node data deleted:]"+event.getPath());
        }
        if(event.getType() == Event.EventType.NodeChildrenChanged){
            LOGGER.info("[zookeeper node children data changed:]"+event.getPath()+",WatchedEvent:"+event);
        }
        ZKResMap.temp = ZKApis.getChildrenNodeData(ZKConfig.dbPath, true);
        VALIDATEMYSQL.validate(ZKResMap.temp, ZKResMap.dbServer);
        ZKResMap.dbServer = new ConcurrentHashMap<String, String>(ZKResMap.temp);
        LOGGER.info("dbServer changed:"+ZKResMap.dbServer);
        for(String key : ZKResMap.dbServer.keySet()){
            LOGGER.info("Key:"+key+",value:"+ZKResMap.dbServer.get(key));
        }

        /*Iterator<Map.Entry<String, String>> it = ZKResMap.dbServer.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            LOGGER.info("key:"+key+",value:"+value);
        }*/
    }
}
