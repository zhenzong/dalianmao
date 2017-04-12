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
public class InvokeLogger {

    private static final Logger LOG = LoggerFactory.getLogger(InvokeLogger.class);

    public static void info(String service, String tag, Object request, Object response, long start, long timecost,
            int status) {
        StringBuilder builder = new StringBuilder("[service=").append(service)
                .append("][tag=").append(tag)
                .append("][request=").append(request)
                .append("][response=").append(response)
                .append("][start=").append(start)
                .append("][timecost=").append(timecost)
                .append("][status=").append(status).append("]");
        info(builder.toString());
    }

    public static void info(String log) {
        LOG.info(log);
    }

}
