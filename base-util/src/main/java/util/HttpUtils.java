package util;


import com.fasterxml.jackson.databind.ObjectMapper;
import core.core.ReturnResultDTO;
import util.logger.LogUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by liuchenhong on 2017/8/27.
 */
public class HttpUtils {

    /**
     * 获取本机的IP
     *
     * @return Ip地址
     */
    public static String getLocalHostIP() {
        String result = null;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**返回 IP 地址字符串（以文本表现形式）*/
            result = addr.getHostAddress();
        } catch (UnknownHostException e) {
            LogUtil.warn("","", "CORE","getLocalHostIP EXCEPTION:UnknownHostException");
        }
        return result;
    }

    /**
     * 或者主机名：
     *
     * @return
     */
    public static String getLocalHostName() {
        String hostName = null;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**获取此 IP 地址的主机名。*/
            hostName = addr.getHostName();
        } catch (UnknownHostException e) {
            LogUtil.warn("","", "CORE","getLocalHostName EXCEPTION:UnknownHostException");
        }
        return hostName;
    }

    public static void prepareReturnResponse(final HttpServletResponse response, final int httpStatus, final String returnCode, final String data) {

        final ReturnResultDTO returnResultDTO = new ReturnResultDTO(returnCode, data);
        response.setStatus(httpStatus);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            out.append(mapper.writeValueAsString(returnResultDTO));
            response.flushBuffer();
        } catch (IOException e) {
            LogUtil.error("", "", "", "", "prepareReturnResponse Exception", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
}
