package cn.myyy.hello.common.dao.dao.standard;

import java.io.Serializable;

/**
 * 升序
 */
public class AscOrder extends AbstractOrder implements Serializable{
    private static final long serialVersionUID = -7607614114326573461L;

    public AscOrder(String col) {
        super("asc", col);
    }
}
