package cn.myyy.hello.common.standard;

import java.io.Serializable;

/**
 * 降序
 */
public class DescOrder extends AbstractOrder implements Serializable {
    private static final long serialVersionUID = -1564892867584078308L;

    public DescOrder(String col) {
        super("desc", col);
    }
}
