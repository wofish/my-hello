package cn.myyy.hello.util.decimal;

import java.math.BigDecimal;

/**
 * Created by jun.li on 2016/9/26.
 */
public class LoanCalculateSubtractUtil {
    /**
     * 进行减法运算
     *
     * @param p1
     * @param p2
     * @return
     */
    public static BigDecimal subtract(BigDecimal p1, BigDecimal p2) {
        return p1.subtract(p2);
    }

    /**
     * 进行减法运算
     *
     * @param p1
     * @param p2
     * @return
     */
    public static BigDecimal subtract(String p1, String p2) {
        return subtract(new BigDecimal(p1), new BigDecimal(p2));
    }

    /**
     * 减法重载方法
     *
     * @param args
     * @return
     */
    public static BigDecimal subtract(BigDecimal... args) {
        BigDecimal result = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i] == null) {
                args[i] = BigDecimal.ZERO;
            }
            result = subtract(result, args[i]);
        }
        return result;
    }
}
