import com.rouble.db.ValidateMysql;
import com.rouble.zk.ZKResMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-9
 * Time: ÏÂÎç5:28
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationContextTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextTest.class);
    public static void main(String[] args){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ValidateMysql mysql =  (ValidateMysql)ac.getBean("validateMysql");
        LOGGER.info(":"+(null  == mysql));
    }
}
