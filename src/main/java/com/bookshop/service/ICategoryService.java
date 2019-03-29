package com.bookshop.service;


import com.bookshop.common.ServerResponse;
import com.bookshop.pojo.Category;

import java.util.List;

public interface ICategoryService {
    ServerResponse addCategroy(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
