package cn.myyy.hello.util.id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class GenerateIdUtil {
    private static Random random = new Random();

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public static String generateApplyNo() {
        return generateId("A");
    }

    protected static String generateId(String type) {
        String datePrefix = sdf.format(new Date());
        return datePrefix + type + System.currentTimeMillis() + random3Code();
    }

    private static String random3Code() {
        int rand = random.nextInt(1000);
        if (rand < 10) {
            return "00" + rand;
        } else if (rand < 100) {
            return "0" + rand;
        }
        return rand + "";
    }

}
