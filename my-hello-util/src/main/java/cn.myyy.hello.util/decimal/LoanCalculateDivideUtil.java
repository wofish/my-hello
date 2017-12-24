package cn.myyy.hello.util.decimal;

import java.math.BigDecimal;

/**
 * 除法运算工具类
 */
public class LoanCalculateDivideUtil {

    /**
     * 默认按照四舍五入算法
     */
    private final static int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    /**
     * 进行除法运算
     *
     * @param p1
     * @param p2
     * @param length 保留小数精度
     * @return
     */
    public static BigDecimal divide(BigDecimal p1, BigDecimal p2, int length) {
        return p1.divide(p2, length, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 进行除法运算
     *
     * @param p1
     * @param p2
     * @param length 保留小数精度
     * @return
     */
    public static BigDecimal divide(String p1, String p2, int length) {
        return divide(new BigDecimal(p1), new BigDecimal(p2), length, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 进行除法运算
     *
     * @param p1
     * @param p2
     * @param length 保留小数精度
     * @return
     */
    public static BigDecimal divide(BigDecimal p1, BigDecimal p2, int length, int roundingMode) {
        return p1.divide(p2, length, roundingMode);
    }
}
