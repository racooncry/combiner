package com.shenfeng.yxw.elasticsearch.controller;

import com.shenfeng.yxw.elasticsearch.common.BusinessException;
import com.shenfeng.yxw.elasticsearch.common.CommonRes;
import com.shenfeng.yxw.elasticsearch.common.EmBusinessError;
import com.shenfeng.yxw.elasticsearch.model.CategoryModel;
import com.shenfeng.yxw.elasticsearch.model.ShopModel;
import com.shenfeng.yxw.elasticsearch.service.CategoryService;
import com.shenfeng.yxw.elasticsearch.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/shop")
@RequestMapping("/shop")
@Slf4j
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryService categoryService;

    //推荐服务V1.0
    @RequestMapping("/recommend")
    @ResponseBody
    public CommonRes recommend(@RequestParam(name = "longitude") BigDecimal longitude,
                               @RequestParam(name = "latitude") BigDecimal latitude) throws BusinessException {
        if (longitude == null || latitude == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        List<ShopModel> shopModelList = shopService.recommend(longitude, latitude);
        return CommonRes.create(shopModelList);
    }


    //搜索服务V1.0
    @RequestMapping("/search")
    @ResponseBody
    public CommonRes search(@RequestParam(name = "longitude") BigDecimal longitude,
                            @RequestParam(name = "latitude") BigDecimal latitude,
                            @RequestParam(name = "keyword") String keyword,
                            @RequestParam(name = "orderby", required = false) Integer orderby,
                            @RequestParam(name = "categoryId", required = false) Integer categoryId,
                            @RequestParam(name = "tags", required = false) String tags) throws BusinessException {
        if (StringUtils.isEmpty(keyword) || longitude == null || latitude == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        log.info("key word:{}", keyword);
        //   List<ShopModel> shopModelList = shopService.search(longitude,latitude,keyword,orderby,categoryId,tags);
        //  List<ShopModel> shopModelList = (List<ShopModel>) shopService.searchES(longitude, latitude, keyword, orderby, categoryId, tags).get("shop");

        // List<ShopModel> shopModelList = (List<ShopModel>) shopService.searchES2(longitude, latitude, keyword, orderby, categoryId, tags).get("shop");


        Map<String, Object> stringObjectMap = shopService.searchES4(longitude, latitude, keyword, orderby, categoryId, tags);

        List<ShopModel> shopModelList = (List<ShopModel>) stringObjectMap.get("shop");
        List<Map<String, Object>> tagsAggregation = (List<Map<String, Object>>) stringObjectMap.get("tags");


        List<CategoryModel> categoryModelList = categoryService.selectAll();
//        List<Map<String, Object>> tagsAggregation = shopService.searchGroupByTags(keyword, categoryId, tags);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("shop", shopModelList);
        resMap.put("category", categoryModelList);
        resMap.put("tags", tagsAggregation);
        return CommonRes.create(resMap);

    }


}
