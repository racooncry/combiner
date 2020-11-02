package com.shenfeng.yxw.elasticsearch.service;

import com.shenfeng.yxw.elasticsearch.common.BusinessException;
import com.shenfeng.yxw.elasticsearch.model.CategoryModel;

import java.util.List;

public interface CategoryService {

    CategoryModel create(CategoryModel categoryModel) throws BusinessException;
    CategoryModel get(Integer id);
    List<CategoryModel> selectAll();

    Integer countAllCategory();
}
