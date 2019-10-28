package com.ims.common.util;

import com.baomidou.mybatisplus.plugins.Page;


import java.util.Map;

/**
 * 
 * 类名:com.ims.common.util.Query
 * 描述:
 * 编写者:陈骑元
 * 创建时间:2018年4月9日 下午10:59:00
 * 修改说明:
 */
public class Query<T> extends Page<T> {
    /**
	 * 分页查询条件
	 */
	private static final long serialVersionUID = 1L;
	private static final String PAGE = "page";
    private static final String LIMIT = "limit";
  /*  private static final String ORDER_BY_FIELD = "_order";
    private static final String IS_ASC = "isAsc";
*/
    public Query(Map<String,Object> params) {
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(LIMIT, 20).toString()));

       /* String orderByField = params.getOrDefault(ORDER_BY_FIELD, "").toString();
        if (StringUtils.isNotEmpty(orderByField)) {
            this.setOrderByField(orderByField);
        }

        Boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.TRUE).toString());
        this.setAsc(isAsc);
*/
        params.remove(PAGE);
        params.remove(LIMIT);
       // params.remove(ORDER_BY_FIELD);
       // params.remove(IS_ASC);
       // this.setCondition(params);
    }
    
   
}
