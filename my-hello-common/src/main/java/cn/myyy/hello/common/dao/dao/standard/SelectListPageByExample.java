package cn.myyy.hello.common.dao.dao.standard;

import java.util.List;

public interface SelectListPageByExample<T, S> extends SelectCountByExample<S> {

    List<T> selectListPageByExample(PageModel<S> model);

}
