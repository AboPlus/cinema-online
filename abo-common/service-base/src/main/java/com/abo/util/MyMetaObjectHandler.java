package com.abo.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充模块：自动完成数据添加的时间和修改的时间及逻辑删除的默认值填写
 * @author : Abo
 * @date : 2022/1/21 19:26
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /** 添加数据时采取的行为 */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 添加日期到指定的字段中
        setFieldValByName("gmtCreate", new Date(), metaObject);
        setFieldValByName("gmtModified", new Date(), metaObject);
        // 逻辑删除的默认值
        setFieldValByName("isDeleted", 0, metaObject);
    }

    /** 修改数据时采取的行为 */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
