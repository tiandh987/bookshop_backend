package com.bookshop.mapper;

import com.bookshop.pojo.Appraise;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppraiseMapper {

    List<Appraise> selectByQueryModel(@Param(value = "productId")Integer productId,
                                        @Param(value = "orderId")Integer orderId,
                                        @Param(value = "grade")Integer grade );

    int insert(Appraise appraise);

    int selectCountByOrderId(Integer orderId);
}
