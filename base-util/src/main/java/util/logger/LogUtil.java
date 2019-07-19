package util.logger;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;

public final class LogUtil {
    public static final Logger log = LoggerFactory.getLogger(LogUtil.class);

    public LogUtil() {
    }

    public static void debug(String userId, String tenantCode, String moduleName, String logMsg) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        debug(stacks, userId, tenantCode, moduleName, "", logMsg, (Object)null, false);
    }

    public static void debug(String userId, String tenantCode, String moduleName, String logMsg, Object obj) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        debug(stacks, userId, tenantCode, moduleName, "", logMsg, obj, true);
    }

    private static void debug(StackTraceElement[] stacks, String userId, String tenantCode, String moduleName, String api, String logMsg, Object obj, boolean isWithArgs) {
        String logReturnMsg = prepareLog4Json(stacks, userId, tenantCode, moduleName, api, logMsg, isWithArgs);
        if (obj == null) {
            log.debug(logReturnMsg);
        } else if (obj instanceof Exception) {
            Exception e = (Exception)obj;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            log.debug(logReturnMsg, str);
        } else {
            log.debug(logReturnMsg, obj);
        }

    }

    public static void info(String userId, String tenantCode, String moduleName, String api, String logMsg) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        info(stacks, userId, tenantCode, moduleName, api, logMsg, (Object)null, false);
    }

    public static void info(String userId, String tenantCode, String moduleName, String api, String logMsg, Object obj) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        info(stacks, userId, tenantCode, moduleName, api, logMsg, obj, true);
    }

    private static void info(StackTraceElement[] stacks, String userId, String tenantCode, String moduleName, String api, String logMsg, Object obj, boolean isWithArgs) {
        String logReturnMsg = prepareLog4Json(stacks, userId, tenantCode, moduleName, api, logMsg, isWithArgs);
        if (obj == null) {
            log.info(logReturnMsg);
        } else if (obj instanceof Exception) {
            Exception e = (Exception)obj;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            log.info(logReturnMsg, str);
        } else {
            log.info(logReturnMsg, obj);
        }

    }

    public static void warn(String userId, String tenantCode, String moduleName, String logMsg) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        warn(stacks, userId, tenantCode, moduleName, "", logMsg, (Object)null, false);
    }

    public static void warn(String userId, String tenantCode, String moduleName, String logMsg, Object obj) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        warn(stacks, userId, tenantCode, moduleName, "", logMsg, obj, true);
    }

    private static void warn(StackTraceElement[] stacks, String userId, String tenantCode, String moduleName, String api, String logMsg, Object obj, boolean isWithArgs) {
        String logReturnMsg = prepareLog4Json(stacks, userId, tenantCode, moduleName, api, logMsg, isWithArgs);
        if (obj == null) {
            log.warn(logReturnMsg);
        } else if (obj instanceof Exception) {
            Exception e = (Exception)obj;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            log.warn(logReturnMsg, str);
        } else {
            log.warn(logReturnMsg, obj);
        }

    }

    public static void error(String userId, String tenantCode, String moduleName, String api, String logMsg) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        error(stacks, userId, tenantCode, moduleName, api, logMsg, (Object)null, false);
    }

    public static void error(String userId, String tenantCode, String moduleName, String api, String logMsg, Object obj) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        error(stacks, userId, tenantCode, moduleName, api, logMsg, obj, true);
    }

    private static void error(StackTraceElement[] stacks, String userId, String tenantCode, String moduleName, String api, String logMsg, Object obj, boolean isWithArgs) {
        String logReturnMsg = prepareLog4Json(stacks, userId, tenantCode, moduleName, api, logMsg, isWithArgs);
        if (obj == null) {
            log.error(logReturnMsg);
        } else if (obj instanceof Exception) {
            Exception e = (Exception)obj;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            log.error(logReturnMsg, str);
        } else {
            log.error(logReturnMsg, obj);
        }

    }

    public static void trace(String userId, String tenantCode, String moduleName, String logMsg) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        trace(stacks, userId, tenantCode, moduleName, "", logMsg, (Object)null, false);
    }

    public static void trace(String userId, String tenantCode, String moduleName, String logMsg, Object obj) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        trace(stacks, userId, tenantCode, moduleName, "", logMsg, obj, true);
    }

    private static void trace(StackTraceElement[] stacks, String userId, String tenantCode, String moduleName, String api, String logMsg, Object obj, boolean isWithArgs) {
        String logReturnMsg = prepareLog4Json(stacks, userId, tenantCode, moduleName, api, logMsg, isWithArgs);
        if (obj == null) {
            log.trace(logReturnMsg);
        } else if (obj instanceof Exception) {
            Exception e = (Exception)obj;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            log.trace(logReturnMsg, str);
        } else {
            log.trace(logReturnMsg, obj);
        }

    }

    public static String object2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    private static String prepareLog4Json(StackTraceElement[] stacks, String userId, String tenantCode, String moduleName, String api, String logMsg, boolean isWithArgs) {
        String log_prefix = "{\"startTime\":\"" + ZonedDateTime.now() + "\",\"userId\":\"" + userId + "\",\"tenantCode\":\"" + tenantCode + "\",\"moduleName\":\"" + moduleName + "\",\"package\":\"" + stacks[2].getClassName() + "\",\"method\":\"" + stacks[2].getMethodName() + "\",\"line\":\"" + stacks[2].getLineNumber() + "\",\"api\":\"" + api + "\",\"";
        return isWithArgs ? log_prefix + logMsg + "\":\"{}\"}" : log_prefix + logMsg + "\":\"\"}";
    }

    public static String getCodeLocation(String moduleName) {
        try {
            String location = "";
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            location = moduleName + ", " + stacks[2].getClassName() + "." + stacks[2].getMethodName() + "(" + stacks[2].getLineNumber() + ")";
            return location;
        } catch (Exception var3) {
            return "";
        }
    }

    /** @deprecated */
    @Deprecated
    public static void info_aop(String userId, String tenantCode, String moduleName, String className, String method, String api, String localIp, String localName, String remoteIp, String startTime, String endTime, double duration, boolean result, String params) {
        String logReturnMsg = "{\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\",\"userId\":\"" + userId + "\",\"tenantCode\":\"" + tenantCode + "\",\"moduleName\":\"" + moduleName + "\",\"package\":\"" + className + "\",\"method\":\"" + method + "\",\"api\":\"" + api + "\",\"localIp\":\"" + localIp + "\",\"localName\":\"" + localName + "\",\"remoteIp\":\"" + remoteIp + "\",\"duration\":" + duration + ",\"result\":" + result + ",\"params\":" + params + "}";
        log.info(logReturnMsg);
    }

    /** @deprecated */
    @Deprecated
    public static void error_aop(String userId, String tenantCode, String moduleName, String className, String method, String api, String logMsg, Object obj) {
        String logReturnMsg = "{\"userId\":\"" + userId + "\",\"tenantCode\":\"" + tenantCode + "\",\"moduleName\":\"" + moduleName + "\",\"package\":\"" + className + "\",\"method\":\"" + method + "\",\"api\":\"" + api + ",\"" + logMsg + "\":\"{}\"}";
        if (obj instanceof Exception) {
            Exception e = (Exception)obj;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            log.error(logReturnMsg, str);
        } else {
            log.error(logReturnMsg, obj);
        }

    }
}

