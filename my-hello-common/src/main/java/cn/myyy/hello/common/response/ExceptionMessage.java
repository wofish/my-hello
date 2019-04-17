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

import cn.myyy.hello.util.decimal.LoanAmountUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * @project: my-user
 * @date: 2018/01/11
 * @author: SUNYAFEI
 */
public class ExceptionMessage implements Message {
    private Object[] parameters;

    private IExceptionCode IExceptionCode;

    public ExceptionMessage(IExceptionCode IExceptionCode, Object... parameters) {
        this.parameters = parameters;
        this.IExceptionCode = IExceptionCode;
    }

    @Override
    public String getRespCode() {
        return IExceptionCode.getCode();
    }

    @Override
    public String getRespMsg() {
        if (ArrayUtils.isEmpty(parameters)) {
            return IExceptionCode.getMessage();
        } else {
            Object[] formatParameters = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object object = parameters[i];
                if (object instanceof BigDecimal) {
                    formatParameters[i] = LoanAmountUtil.formatWithoutGroupingUsed((BigDecimal) object);
                } else if (object instanceof Long) {
                    formatParameters[i] = String.valueOf(object);
                } else {
                    formatParameters[i] = object;
                }
            }
            return MessageFormat.format(IExceptionCode.getMessage(), formatParameters);
        }
    }
}
