package cn.myyy.hello.util.decimal;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 浮点计算工具类
 */
public class LoanAmountUtil {

    /**
     * 默认小数精度
     */
    private final static int DEFAULT_AMOUNT_SCALE = 2;

    /**
     * 数字格式化对象
     */
    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    /**
     * 转换成四舍五入之后之后的金额
     * @param value
     * @return
     */
    public static BigDecimal toAmount(BigDecimal value) {
        return value.setScale(DEFAULT_AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 进行四舍五入操作
     * @param value
     * @param length
     * @return
     */
    public static double round(double value, int length) {
        return new BigDecimal(Double.toString(value)).divide(BigDecimal.ONE, length, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 进行向上取整操作
     * @param value
     * @param length
     * @return
     */
    public static double celling(double value, int length) {
        return new BigDecimal(Double.toString(value)).divide(BigDecimal.ONE, length, BigDecimal.ROUND_CEILING).doubleValue();
    }

    /**
     * 进行向下取整操作
     * @param value
     * @param length
     * @return
     */
    public static double floor(double value, int length) {
        return new BigDecimal(Double.toString(value)).divide(BigDecimal.ONE, length, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    /**
     * 格式化数字，不使用千分位分隔数字
     * @param value
     * @return
     */
    public static String formatWithoutGroupingUsed(BigDecimal value) {
        NUMBER_FORMAT.setGroupingUsed(false);
        return NUMBER_FORMAT.format(value);
    }

    /**
     * ROUND_HALF_UP AND Compare
     * @param a
     * @param b
     * @return
     */
    public static int roundUpAndCompare(BigDecimal a,BigDecimal b){
        return toAmount(a).compareTo(toAmount(b));
    }
}
