/**
 *
 */
package me.dalianmao.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiezhenzong
 *
 */
public class AccessLogger {

    private static final Logger LOG = LoggerFactory.getLogger(AccessLogger.class);

    public static void info(String log) {
        LOG.info(log);
    }

}
