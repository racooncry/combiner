package com.shengfeng.yxw.baomidou.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shengfeng.yxw.baomidou.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yang xw
 * @since 2020-11-27
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    IPage<User> selectPageVo(Page<?> page, Integer state);
}
