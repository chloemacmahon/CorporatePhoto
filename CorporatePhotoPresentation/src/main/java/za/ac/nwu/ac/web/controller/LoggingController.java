package za.ac.nwu.ac.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    public static Logger logger = LoggerFactory.getLogger(LoggingController.class);

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logTrace(String message) {
        logger.trace(message);
    }

    public static void logDebug(String message) {
        logger.debug(message);
    }

    public static void logWarn(String message) {
        logger.warn(message);
    }

    public static void logError(String message) {
        logger.error(message);
    }
}
