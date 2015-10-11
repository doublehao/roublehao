package com.rouble.job;

import com.rouble.db.ValidateMysql;
import com.rouble.zk.ZKResMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-10
 * Time: ÉÏÎç10:11
 * To change this template use File | Settings | File Templates.
 */
public class ValidateMysqlJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateMysqlJob.class);
   @Resource
   private ValidateMysql validateMysql;
    public void work(){
        LOGGER.info(">>>>>>>>>>>ValidateMysqlJob start!");
        try{
            validateMysql.validate(ZKResMap.temp, ZKResMap.dbServer);
        }catch (Exception e){
            LOGGER.error(">>>>>>>>>>>ValidateMysqlJob error!",e);
        }
    }
}
