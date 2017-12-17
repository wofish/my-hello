package cn.myyy.hello.common.jedis;


import cn.myyy.hello.util.json.JsonUtil;

/**
 * Redis组件
 *
 * @author SUNYAFEI
 */
public class JedisComponent {

    private JedisContainer jedisContainer;

    public static final int HELLO_DB_INDEX = 1;
    public static final int WALLET_DB_INDEX = 2;
    public static final int USER_DB_INDEX = 3;
    public static final int CREDIT_DB_INDEX = 4;
    public static final int CHANNEL_DB_INDEX = 5;
    public static final int COLLECTION_DB_INDEX = 6;
    public static final int LOAN_DB_INDEX = 7;
    public static final int ACTIVITY_DB_INDEX = 8;
    public static final int EXTERNAL_DB_INDEX = 9;
    public static final int CARRIER_DB_INDEX = 10;

    public JedisContainer getJedisContainer() {
        return jedisContainer;
    }

    public void setJedisContainer(JedisContainer jedisContainer) {
        this.jedisContainer = jedisContainer;
    }

    /**
     * 保存参数信息
     *
     * @param key
     * @param value
     */
    public void save(String key, Object value, Integer time) {
        String valStr;
        if (value instanceof String) {
            valStr = (String) value;
        } else {
            valStr = JsonUtil.toJsonString(value);
        }
        jedisContainer.addString(key, valStr, time);
    }
    /**
     * 保存参数信息
     *
     * @param key
     * @param value
     */
    public void save(String key, Object value) {
        String valStr;
        if (value instanceof String) {
            valStr = (String) value;
        } else {
            valStr = JsonUtil.toJsonString(value);
        }
        jedisContainer.addString(key, valStr);
    }

    /**
     * 获取参数信息
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return jedisContainer.getString(key);
    }

    /**
     * 获取参数信息重载方法
     *
     * @param key
     * @return
     */
    public synchronized String get(String key, Integer dbIndex) {
        int originalIndex = jedisContainer.getDbIndex();
        if (dbIndex != null) {
            jedisContainer.setDbIndex(dbIndex);
        }
        String result = jedisContainer.getString(key);
        jedisContainer.setDbIndex(originalIndex);
        return result;
    }

    /**
     * 给key设置新的过期时间
     *
     * @param key
     * @param time
     */
    public void setNewTime(String key, Integer time) {
        jedisContainer.setNewTime(key, time);
    }

    /**
     * 删除KEY对应的内容重载方法
     *
     * @param key
     */
    public void del(String key) {
        jedisContainer.delKey(key);
    }
}
