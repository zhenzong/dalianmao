package me.dalianmao.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.regex.Matcher;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具方法
 *
 * @author xiezhenzong
 *
 */
public abstract class Util {

    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    public static boolean nonEqual(Object a, Object b) {
        return (a != b) && (a == null || !a.equals(b));
    }

    /**
     * get local host
     *
     * @return if exception occur, then return Unknown host.
     */
    public static String getLocalHost() {
        InetAddress address = getAddress();
        return address != null ? address.getHostAddress() : "Unknown host";
    }

    /**
     * Get host IP address
     *
     * @return IP Address
     */
    public static InetAddress getAddress() {
        try {
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces
                    .hasMoreElements();) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                if (addresses.hasMoreElements()) {
                    return addresses.nextElement();
                }
            }
        } catch (SocketException e) {
            LOG.error("get address failed", e);
        }
        return null;
    }

    /**
     * 返回枚举的toString字符串
     *
     * @param enumObj
     *            枚举对象
     * @return string
     */
    public static String enumToString(Enum<?> enumObj) {
        return ReflectionToStringBuilder.toString(enumObj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * 创建随机字符串
     *
     * @return
     */
    public static String createNonceStr() {
        return createNonce(Constants.UNDERLINE);
    }

    /**
     * 创建随机字符串
     *
     * @param seperator
     *            分隔符
     * @return
     */
    public static String createNonce(char seperator) {
        StringBuilder builder = new StringBuilder(Constants.TOKEN) //
                .append(seperator).append(System.currentTimeMillis()) //
                .append(seperator).append(Math.random());
        return DigestUtils.md5Hex(builder.toString());
    }

    /**
     * 移除非utf-8的字符
     *
     * @param text
     * @return
     *
     * @see http://blog.csdn.net/jammiwang19870815/article/details/22736457
     */
    public static String filterOffUtf8Mb4(String text) {
        try {
            byte[] bytes = text.getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            int i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    buffer.put(bytes[i++]);
                    continue;
                }
                b += 256;
                if ((b ^ 0xC0) >> 4 == 0) {
                    buffer.put(bytes, i, 2);
                    i += 2;
                } else if ((b ^ 0xE0) >> 4 == 0) {
                    buffer.put(bytes, i, 3);
                    i += 3;
                } else if ((b ^ 0xF0) >> 4 == 0) {
                    i += 4;
                } else {
                    i++;
                }
            }
            buffer.flip();
            return new String(buffer.array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("un supported encoding", e);
            return "";
        }
    }

    /**
     * 是否是手机号
     *
     * @param mobiles
     *            检查目标
     * @return true如果是手机号，false如果不是
     *
     * @see Pattern#MOBILE
     */
    public static boolean isMobile(String mobile) {
        Matcher m = Pattern.MOBILE.matcher(mobile);
        return m.matches();
    }
}
