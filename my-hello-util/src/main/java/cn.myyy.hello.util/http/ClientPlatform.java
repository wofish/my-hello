package cn.myyy.hello.util.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 客户端信息
 */
public class ClientPlatform implements Serializable {

    /**
     * 手机的平台
     */
    private String platform;

    /**
     * 手机的系统版本
     */
    private String sysVersion;

    /**
     * 手机的型号
     */
    private String model;

    /**
     * 远程访问ip地址
     */
    private String remoteIp;

    /**
     * X-Forwarded-For 的ip地址
     */
    private String xFFIp;

    /**
     * 终端设备的X-Real-IP ip
     */
    private String realIp;

    /**
     * 终端的纬度
     */
    private double latitude;

    /**
     * 终端的经度
     */
    private double longitude;

    /**
     * 终端设备的屏幕宽度
     */
    private float screenWidth;

    /**
     * 终端设备的屏幕高度
     */
    private float screenHeight;

    /**
     * 标记终端浏览器是否为微信内置浏览器
     */
    private boolean isWechatClient;

    /**
     * 微信的版本号
     */
    private String wechatVersion;

    /**
     * 手机网络类型
     */
    private String ipNet;

    /**
     * 客户端的版本号
     */
    private String clientVer;

    /**
     * 客户端的类型
     */
    private String clientChannel;

    /**
     * 页面的url地址
     */
    private String pageUri;

    /**
     * 浏览器西悉尼
     */
    private String userAgent;

    public ClientPlatform() {
    }

    public ClientPlatform(String platform, String version, String model){
        this();
        this.platform = platform;
        this.sysVersion = version;
        this.model = model;
    }

    /**
     * 通过正则表达式匹配有多ip的情形
     * @return
     */
    public String[] getIps() {
        if(null == xFFIp) {
            return new String[0];
        }
        String patternString="\\d+(\\.)\\d+(\\.)\\d+(\\.)\\d+";
        Pattern pattern=Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(xFFIp);
        ArrayList<String> list = new ArrayList<String>();
        while (matcher.find()) {
            int start=matcher.start();
            int end =matcher.end();
            String match =xFFIp.substring(start,end);
            list.add(match);
        }
        String[] ips = new String[list.size()];
        for (int i = 0; i < list.size();i++) {
            ips[i] = list.get(i);
        }
        return ips;
    }

    public String getUA(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (null != platform) {
            sb.append("platform=").append(platform);
        } else {
            sb.append("platform=NULL");
        }
        if (null != sysVersion) {
            sb.append(",osVersion=").append(sysVersion);
        } else {
            sb.append(",osVersion=NULL");
        }
        if (null != model) {
            sb.append(",model=").append(model);
        } else {
            sb.append(",model=NULL");
        }
        sb.append("}");
        return sb.toString();
    }

    public String getClientIp() {
        if (null != xFFIp) {
            String[] ips = getIps();
            return ips[0];
        }
        if (null != realIp) {
            return realIp;
        }
        return remoteIp;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getxFFIp() {
        return xFFIp;
    }

    public void setxFFIp(String xFFIp) {
        this.xFFIp = xFFIp;
    }

    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    public boolean isWechatClient() {
        return isWechatClient;
    }

    public void setWechatClient(boolean wechatClient) {
        isWechatClient = wechatClient;
    }

    public String getWechatVersion() {
        return wechatVersion;
    }

    public void setWechatVersion(String wechatVersion) {
        this.wechatVersion = wechatVersion;
    }

    public String getIpNet() {
        return ipNet;
    }

    public void setIpNet(String ipNet) {
        this.ipNet = ipNet;
    }

    public String getClientVer() {
        return clientVer;
    }

    public void setClientVer(String clientVer) {
        this.clientVer = clientVer;
    }

    public String getClientChannel() {
        return clientChannel;
    }

    public void setClientChannel(String clientChannel) {
        this.clientChannel = clientChannel;
    }

    public String getPageUri() {
        return pageUri;
    }

    public void setPageUri(String pageUri) {
        this.pageUri = pageUri;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
