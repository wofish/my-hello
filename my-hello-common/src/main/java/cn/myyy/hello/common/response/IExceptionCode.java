/*
 *
 *  * My-You Confidential
 *  *
 *  * Copyright (C) 2017 Shanghai Heng-You Information Technology Co., Ltd.
 *  * All rights reserved.
 *  *
 *  * No part of this file may be reproduced or transmitted in any form or by any means,
 *  * electronic, mechanical, photocopying, recording, or otherwise, without prior
 *  * written permission of Shanghai Heng-You Information Technology Co., Ltd.
 *
 */

package cn.myyy.hello.common.response;

/**
 * error.warn
 * @project: my-user
 * @date: 2018/01/11
 * @author: SUNYAFEI
 */
public interface IExceptionCode<K,L,V> {

    String getCode();
    String getType();
    String getMessage();

}
