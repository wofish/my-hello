/*
 *
 *  * Heng-You Confidential
 *  *
 *  * Copyright (C) 2017 Shanghai Heng-You Information Technology Co., Ltd.
 *  * All rights reserved.
 *  *
 *  * No part of this file may be reproduced or transmitted in any form or by any means,
 *  * electronic, mechanical, photocopying, recording, or otherwise, without prior
 *  * written permission of Shanghai Heng-You Information Technology Co., Ltd.
 *
 */

package cn.myyy.hello.util.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @project: hello
 * @date: 2017/10/23
 * @author: SUNYAFEI
 */
public class ThreadUtil {

    /**
     * 数据分组
     *
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<Integer, List<T>> group(List<T> list, Class<T> clazz, int threadPoolSize) {

        Map<Integer, List<T>> res = new HashMap<>(1);
        if (list == null || list.size() == 0) {
            return res;
        }
        if (list.size() < threadPoolSize) {
            res.put(0, list);
            return res;
        }
        int per = list.size() / threadPoolSize;
        int left = list.size() % threadPoolSize;
        int from = 0;
        for (int i = 0; i < threadPoolSize; i++) {
            int to = from + per;
            if (i == (threadPoolSize - 1)) {
                to += left;
            }
            List<T> list1 = list.subList(from, to);
            from = to;
            res.put(i, list1);
        }
        return res;
    }
}
