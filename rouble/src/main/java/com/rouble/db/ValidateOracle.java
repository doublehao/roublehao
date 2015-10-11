package com.rouble.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-9
 * Time: ÉÏÎç11:29
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ValidateOracle extends ValidateDB{
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateMysql.class);
    private static final String driver = "oracle.jdbc.driver.OracleDriver";

    @Override
    public void configDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.error("config driver error :"+driver,e);
        }
    }
}
