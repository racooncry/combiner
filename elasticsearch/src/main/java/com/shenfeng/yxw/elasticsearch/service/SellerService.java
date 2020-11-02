package com.shenfeng.yxw.elasticsearch.service;

import com.shenfeng.yxw.elasticsearch.common.BusinessException;
import com.shenfeng.yxw.elasticsearch.model.SellerModel;

import java.util.List;

public interface SellerService {

    SellerModel create(SellerModel sellerModel);
    SellerModel get(Integer id);
    List<SellerModel> selectAll();
    SellerModel changeStatus(Integer id, Integer disabledFlag) throws BusinessException;

    Integer countAllSeller();
}
