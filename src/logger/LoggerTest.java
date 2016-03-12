package logger;




import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Valk on 08.01.16.
 */
public class LoggerTest {
    static int x, y;
    public static void main(String[] args) throws IOException {
        //настройки тут
        //C:\Users\Valk\IdeaProjects\train\src\log4j.properties
        //
        Logger logger = Logger.getLogger(LoggerTest.class);
        //что-то можно настроить и тут, но очень мало.
        //Поэтому - см. в C:\Users\Valk\IdeaProjects\train\src\log4j.properties
        //указав и тут и в пропертях - будут работать и те и те
        /*Appender htmlApp = new FileAppender(new HTMLLayout(), "log.html");
        Appender textApp = new FileAppender(new SimpleLayout(), "log.txt");
        logger.addAppender(htmlApp);
        logger.addAppender(textApp);*/
        //
        if (logger.isDebugEnabled()){
            System.out.println("+++");
        }
        //
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        //htmlApp.close();
    }
}
