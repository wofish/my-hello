package cn.wmmm.hello.util.id;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class IdUtil {

    private static final String SERVER_ID_NAME = "_serverId_";

    private static final DecimalFormat FORMAT = new DecimalFormat("#00");
    private static final long SEQUENCE_NUM = 99L;

    private static final Lock APPLY_LOCK = new ReentrantLock();
    private static long lastTimeForApply = -1L;
    private static long sequenceForApply = 0L;
    private static long maxSequenceForApply = SEQUENCE_NUM;

    private static final Lock ORDER_LOCK = new ReentrantLock();
    private static long lastTimeForOrder = -1L;
    private static long sequenceForOrder = 0L;
    private static long maxSequenceForOrder = SEQUENCE_NUM;

    /**
     * 13位时间戳+2位序列号+1位serverID
     *
     * @return
     */
    public static String generateApplyNo() {
        APPLY_LOCK.lock();
        try {
            long now = System.currentTimeMillis();
            if (now == lastTimeForApply) {
                if (++sequenceForApply == maxSequenceForApply) {
                    now = tilNextMillis(lastTimeForApply);
                    sequenceForApply = 0;
                }
            } else {
                sequenceForApply = 0;
            }
            if (now < lastTimeForApply) {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimeForApply - now));
            }
            lastTimeForApply = now;
            return now + FORMAT.format(sequenceForApply) + getServerId(1);
        } finally {
            APPLY_LOCK.unlock();
        }
    }
    public static String generate2ApplyNo(){

        return GenerateIdUtil.generateApplyNo();
    }
    public static String generateCardBuyId(){
        return GenerateIdUtil.generateId("CB");
    }


    private static String getServerId(int length) {
        String serverId = System.getProperty(SERVER_ID_NAME);
        if (serverId == null) {
            serverId = random(length) + "";
        }
        return serverId;
    }

    private static int random(int length) {
        int multiple = (int) Math.pow(10, length - 1);
        return (int) (Math.random() * 9 * multiple) + multiple;
    }

    /**
     * 13位时间戳+2位序列号+1位serverID
     *
     * @return
     */
    public static String generateOrderNo() {
        ORDER_LOCK.lock();
        try {
            long now = System.currentTimeMillis();
            if (now == lastTimeForOrder) {
                sequenceForOrder = sequenceForOrder + 1;
                if (sequenceForOrder == maxSequenceForOrder) {
                    now = tilNextMillis(lastTimeForOrder);
                    sequenceForOrder = 0;
                }
            } else {
                sequenceForOrder = 0;
            }
            if (now < lastTimeForOrder) {
                throw new RuntimeException(
                        String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimeForApply - now));
            }
            lastTimeForOrder = now;
            return now + FORMAT.format(sequenceForOrder) + getServerId(1);
        } finally {
            ORDER_LOCK.unlock();
        }
    }

    /**
     * 13位时间戳+2位序列号+1位serverID
     *
     * @return
     */
    public static String generateBaseOrderNo() {
        return "110" + generateOrderNo();
    }

    private static long tilNextMillis(final long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 生成操作流水号
     *
     * @param memberId
     * @return
     */
    public static String generateOperatorNo(long memberId) {
        Random random = new Random();
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp + memberId + random.nextInt(13);
    }

    /**
     * 生成随机0~9数字字符串
     *
     * @param length
     * @return
     */
    public static String getRandomDigitString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(number);
        }
        return sb.toString();
    }

    public static void main(String args[]) {
        System.out.println(generateOrderNo());
    }
}
