package cn.myyy.hello.common.jedis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author SUNYAFEI
 */
public class JedisContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisContainer.class);

    private JedisPool jedisPool;

    private int dbIndex = 0;

    public JedisContainer(String host, String password, int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(80);
        config.setMinIdle(80);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        jedisPool = new JedisPool(config, host, port, Protocol.DEFAULT_TIMEOUT, password);
    }

    public JedisContainer(String host, String password, int port, int dbIndex) {
        this(host, password, port);
        this.dbIndex = dbIndex;
    }

    private Jedis getJedis() throws JedisException {
        Jedis jedis = jedisPool.getResource();
        jedis.select(dbIndex);
        return jedis;
    }

    private void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 设置REDIS数据库
     * @param dbIndex
     */
    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    /**
     * 返回REDIS数据库
     * @return
     */
    public int getDbIndex() {
        return dbIndex;
    }

    /**
     * 设置KEY的内容，并且返回原来的值，KEY没有时间限制
     * @param key
     * @param value
     * @return
     */
    public String addString(String key, String value) {
        Jedis jedis = null;
        String lastVal;
        try {
            jedis = getJedis();
            lastVal = jedis.getSet(key, value);
        } finally {
            release(jedis);
        }
        return lastVal;
    }

    /**
     * 设置KEY的内容，并且返回原来的值
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public String addString(String key, String value, int cacheSeconds) {
        Jedis jedis = null;
        String lastVal;
        try {
            jedis = getJedis();
            lastVal = jedis.getSet(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            release(jedis);
        }
        return lastVal;
    }

    /**
     * 根据key设置新的key过期时间
     * @param key
     * @param cacheSeconds
     */
    public String setNewTime(String key, int cacheSeconds) {
        Jedis jedis = null;
        String lastVal;
        try {
            jedis = getJedis();
            lastVal = jedis.get(key);
            if (lastVal != null && cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } finally {
            release(jedis);
        }
        return lastVal;
    }

    /**
     * 根据KEY得到内容
     * @param key
     * @return
     */
    public String getString(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
            LOGGER.error(key + "获取key值异常", e);
        } finally {
            release(jedis);
        }
        return value;
    }

    /**
     * 删除KEY
     * @param key
     * @return
     */
    public boolean delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(key + "删除key值异常", e);
        } finally {
            release(jedis);
        }
        return false;
    }

    /**
     * 得到KEY列表
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Set<String> list = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            list = jedis.keys(pattern);
        } catch (Exception e) {
            LOGGER.error(pattern + "获取key列表异常", e);
        } finally {
            release(jedis);
        }
        return list;
    }

    /**
     * 刷新DB
     * @return
     */
    public boolean flushDB() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (null != jedis) {
                jedis.flushDB();
                return true;
            }
        } finally {
            release(jedis);
        }
        return false;
    }

    /**
     * 将指定KEY的值自增,并释放连接资源
     * @param key 自增KEY
     */
    public synchronized Long increment(String key) throws JedisConnectionException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } catch (JedisConnectionException e) {
            if (jedis != null) {
                jedis.close();
                jedis = null;
            }
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 生成id
     * @param prefix     业务前缀
     * @param timePrefix 时间前缀
     * @param maxLen     最大长度
     * @param fixed      是否固定长度
     * @param padStr     位数不足时填充字符,仅对固定的id有效
     * @return
     */
    public String generateId(String prefix, String timePrefix, int maxLen, boolean fixed, char padStr) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(timePrefix);
        if (fixed && sb.length() > maxLen) {
            return "";
        }
        // 从redis中获取自增的id
        String key = prefix;
        Long id = increment(key);
        String idStr = id.toString();
        if (fixed) {
            if (maxLen - sb.length() < idStr.length()) {
                // 重新开始计数
                addString(key, "1");
                return sb.append(StringUtils.leftPad("1", maxLen - sb.length(), padStr)).toString();
            } else {
                return sb.append(StringUtils.leftPad(idStr, maxLen - sb.length(), padStr)).toString();
            }
        } else {
            return sb.append(idStr).toString();
        }
    }

    /**
     * 给某个key设置锁
     * @param key
     * @param waitTimeout 获取锁的等待时间
     * @param lockTimeout 设置锁的有效时间
     * @return
     */
    public String getLockWithWait(String key, long waitTimeout, long lockTimeout) {
        Jedis jedis = null;
        try {
            // 获取连接
            jedis = jedisPool.getResource();
            // 随机生成一个value
            String uuid = UUID.randomUUID().toString();
            // 获取锁的超时等待时间，超过这个时间则放弃获取锁
            long timeWait = System.currentTimeMillis() + waitTimeout;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockSeconds = (int) (lockTimeout / 1000);
            // 当前时间小于超时等待时间
            while (System.currentTimeMillis() < timeWait) {
                // 获取锁
                if (1 == jedis.setnx(key, uuid)) {
                    jedis.expire(key, lockSeconds);
                    return uuid;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    continue;
                }
            }
            return null;
        } catch (Exception e) {
            if (jedis != null) {
                jedis.close();
            }
            LOGGER.error(key + "获取锁异常", e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 判断某个key是否存在
     * @param key
     * @return
     */
    public boolean isExistKey(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            if (jedis != null) {
                jedis.close();
            }
            LOGGER.error(key + "判断key值是否存在报异常", e);
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 释放锁
     * @param key   锁的key
     * @param value 锁的value
     * @return
     */
    public boolean releaseLock(String key, String value) {
        Jedis jedis = null;
        boolean retFlag = false;
        try {
            jedis = jedisPool.getResource();
            while (true) {
                // 监视lock，准备开始事务
                jedis.watch(key);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (value.equals(jedis.get(key))) {
                    Transaction trans = jedis.multi();
                    trans.del(key);
                    List<Object> results = trans.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            if (jedis != null) {
                jedis.close();
            }
            LOGGER.error(key + "释放锁异常", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return retFlag;
    }
}
