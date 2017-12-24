package cn.myyy.hello.util.http;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class HttpUserAgentUtil {

    /**
     * 用户访问的浏览器的user-Agent值
     */
    protected static final String USER_AGENT = "User-Agent";

    /**
     * 返回请求的UserAgent值
     *
     * @param request
     * @return
     */
    private static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(USER_AGENT);
        if (StringUtils.isEmpty(userAgent)) {
            userAgent = request.getHeader("user-agent");
        }
        return userAgent;
    }

    /**
     * 判断一个请求是不是微信客户端发起的
     *
     * @param request
     * @return
     */
    public static boolean isWechatClient(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        String ua = userAgent.toLowerCase();
        if (ua.indexOf("micromessenger") > -1) {
            return true;
        }
        String isWechat = request.getParameter("wechat");
        if ("1".equals(isWechat)) {
            return true;
        }
        return false;
    }

    /**
     * 解析终端的USER-AGENT
     *
     * @param httpServletRequest
     * @return
     */
    public static ClientPlatform parseUserAgent(HttpServletRequest httpServletRequest) {
        String userAgent = getUserAgent(httpServletRequest);
        if(StringUtils.isBlank(userAgent)){
            return null;
        }
        String ipNet = "";
        if (userAgent.indexOf("NetType") != -1) {
            int index = userAgent.indexOf("NetType");
            String str1 = userAgent.substring(index);
            if (str1.indexOf("/") != -1) {
                int index1 = str1.indexOf("/");
                ipNet = str1.substring(index1 + 1);
                if (ipNet.indexOf(" ") > -1) {
                    ipNet = ipNet.substring(0, ipNet.indexOf(" "));
                }
            }
        }
        boolean isWechat = isWechatClient(httpServletRequest);
        ClientPlatform clientPlatform = parseUA(userAgent);
        clientPlatform.setRemoteIp(getRemoteIp(httpServletRequest));
        clientPlatform.setxFFIp(getXFFIp(httpServletRequest));
        clientPlatform.setRealIp(getXRealIp(httpServletRequest));
        clientPlatform.setWechatClient(isWechat);
        clientPlatform.setIpNet(ipNet);
        clientPlatform.setPageUri(httpServletRequest.getRequestURI());
        return clientPlatform;
    }

    /**
     * 获取终端设备的ip地址
     *
     * @param httpServletRequest
     * @return
     */
    public static String getXFFIp(HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("x-forwarded-for");
        return ip;
    }

    public static String getXRealIp(HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Real-IP");
        return ip;
    }

    /**
     * 上边两个都不能获得ip时
     * 用下边的方法获取ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 解析客户端的User-Agent信息，输出手机的操作系统和平台及系统版本
     *
     * @param userAgent
     * @return
     */
    public static ClientPlatform parseUA(String userAgent) {
        if (userAgent.indexOf("Windows NT 5.1") != -1) {
            return new ClientPlatform("Windows XP", null, null);
        }
        if (userAgent.indexOf("Windows NT 6.0") != -1) {
            return new ClientPlatform("Windows Vista", null, null);
        }

        if (userAgent.indexOf("Windows NT 6.1") != -1) {
            return new ClientPlatform("Windows 7", null, null);
        }

        try {
            if (userAgent.indexOf("Windows Phone") != -1) {
                //Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; <manufacturer>; <model> [;<operator])
                //Mozilla/4.0 (compatible; MSIE 7.0; Windows Phone OS 7.5; Trident/3.1; IEMobile/7.0; <manufacturer>; <model> [;<operator])
                //Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Nokia 925)
                //Mozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 920) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537 MicroMessenger/6.0
                int start = userAgent.indexOf("(") + 1;
                int end = userAgent.indexOf(")");
                String temp = userAgent.substring(start, end);
                String[] strs = temp.split(";");
                String model = "";
                if (strs.length > 9) {
                    model = strs[8] + strs[9];
                }
                String version = strs[1];
                return new ClientPlatform("Windows Phone", version, model);
            }

            if (userAgent.indexOf("iPhone") != -1) {
                //Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9
                // (KHTML, like Gecko) Mobile/8C148
                int start = userAgent.indexOf("(");
                int end = userAgent.indexOf(")");
                String os = userAgent.substring(start + 1, end);
                int index = os.indexOf(";");
                String version = os.substring(index + 1);
                return new ClientPlatform("iPhone", version, "iPhone");
            }
            if (userAgent.indexOf("iPad") != -1) {
                int start = userAgent.indexOf("(");
                int end = userAgent.indexOf(")");
                String os = userAgent.substring(start + 1, end);
                int index = os.indexOf(";");
                String version = os.substring(index + 1);
                return new ClientPlatform("iPad", version, "iPad");
            }
            if (userAgent.indexOf("iPod") != -1) {
                int start = userAgent.indexOf("(");
                int end = userAgent.indexOf(")");
                String os = userAgent.substring(start + 1, end);
                int index = os.indexOf(";");
                String version = os.substring(index + 1);
                return new ClientPlatform("iPod", version, "iPod");
            }

            if (userAgent.indexOf("Linux") != -1) {
                int index = userAgent.indexOf("Android");
                if (index != -1) {
                    //Mozilla/5.0 (Linux; U; Android 2.2.2; zh-cn; ZTE-U V880 Build/FRF91) UC
                    // AppleWebKit/530+ (KHTML, like Gecko) Mobile Safari/530
                    int end = userAgent.indexOf(";", index);
                    String os = "";
                    String model = "";
                    if (end > -1) {
                        //OS以及版本
                        os = userAgent.substring(index, end);
                        //手机型号
                        int index1 = userAgent.lastIndexOf(";");
                        int index2 = userAgent.indexOf("Build");
                        if (index1 > -1 && index2 > index1) {
                            model = userAgent.substring(index1 + 1, index2);
                        } else if (index2 > -1 && index1 > index2) {
                            index1 = userAgent.indexOf(";", end);
                            if (index2 > index1) {
                                model = userAgent.substring(index1 + 1, index2);
                            }
                        }
                    } else {
                        String[] strs = userAgent.split(" ");
                        model = strs[0];
                        if (strs.length > 2) {
                            os = strs[2];
                        }
                    }
                    return new ClientPlatform("Android", os, model);
                } else {
                    return new ClientPlatform("Linux", null, null);
                }
            }
            //MQQBrowser/2.8 (E71-1;SymbianOS/9.1 Series60/3.0)
            if (userAgent.indexOf("SymbianOS") != -1) {
                int start = userAgent.indexOf("(") + 1;
                int end = userAgent.indexOf(")");
                String temp = userAgent.substring(start, end);
                start = temp.indexOf(";");
                String model = temp.substring(0, start);
                String version = temp.substring(start + 1);
                return new ClientPlatform("Symbian", version, model);
            }
        } catch (Exception e) {
        }

        return new ClientPlatform(null, null, null);
    }

    public static String getRemoteIp(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRemoteAddr();
    }

    /**
     * 校验ip地址是否匹配，支持正则表达式的匹配
     *
     * @param allowIpDuration
     * @param ip
     * @return
     */
    public static boolean matchIp(String allowIpDuration, String ip) {
        if (allowIpDuration == null) {
            return false;
        }
        allowIpDuration = allowIpDuration.replaceAll("\\s+", ""); // 去空格
        if ("".equals(allowIpDuration)) {
            return false;
        } else if ("*".equals(allowIpDuration) || "*.*.*.*".equals(allowIpDuration)) {
            return true;
        }
        String[] ipPatterns = allowIpDuration.split(",");
        for (int i = 0; i < ipPatterns.length; i++) {
            if (matchPattern(ipPatterns[i], ip)) { // 只要满足一个即为满足
                return true;
            }
        }
        return false;
    }

    /**
     * ip去匹配每个模式
     *
     * @param pattern
     * @param ip
     * @return
     */
    private static boolean matchPattern(String pattern, String ip) {
        if (pattern == null || "".equals(pattern)) {
            return false;
        }
        // 区间段
        if (pattern.contains("~")) {
            int index = pattern.indexOf("~");
            String startIp = pattern.substring(0, index);
            String endIp = pattern.substring(index + 1);
            String ipRegex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
            if (Pattern.matches(ipRegex, startIp) && Pattern.matches(ipRegex, endIp)
                    && parseIp(startIp).compareTo(parseIp(ip)) < 0 && parseIp(ip).compareTo(parseIp(endIp)) < 0) {
                return true;
            } else {
                return false;
            }
        } else {
            // 转换为正则
            String regex = pattern.replaceAll("\\.", "\\\\.").replaceAll("\\?", "\\\\d{1}").replaceAll("\\*", "\\\\d{1,3}");
            if (Pattern.matches(regex, ip)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 匹配ip地址
     *
     * @param ip
     * @return
     */
    private static String parseIp(String ip) {
        String[] ipPer = ip.split("\\.");
        BigInteger bigInteger = null;
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < ipPer.length; i++) {
            bigInteger = new BigInteger(ipPer[i]);
            String format = bigInteger.toString(2);
            sb.append(supply(8 - format.length()) + format); // 补位到8位
        }
        return sb.toString();
    }

    /**
     * 返回len个0，最多为len = 7
     *
     * @param len
     * @return
     */
    private static String supply(int len) {
        String supply = "";
        switch (len) {
            case 1:
                supply = "0";
                break;
            case 2:
                supply = "00";
                break;
            case 3:
                supply = "000";
                break;
            case 4:
                supply = "0000";
                break;
            case 5:
                supply = "00000";
                break;
            case 6:
                supply = "000000";
                break;
            case 7:
                supply = "00000000";
                break;
            default:
                break;
        }
        return supply;
    }

    public static boolean matchURI(String excludeUrl, String url) {
        if (null == excludeUrl || "".equals(excludeUrl)) {
            return false;
        }
        String[] urlPatterns = excludeUrl.split(",");
        for (int i = 0; i < urlPatterns.length; i++) {
            if (url.contains(urlPatterns[i])) {
                return true;
            }
        }
        return false;
    }
}
