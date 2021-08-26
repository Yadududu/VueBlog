package com.lmj.vueblog.config.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if(metaObject.hasSetter("created")){
            setInsertFieldValByName("created", LocalDateTime.now(),metaObject);
        }
        if(metaObject.hasSetter("status")){
            setInsertFieldValByName("status", 0,metaObject);
        }
        if(metaObject.hasSetter("avatar")){
            setInsertFieldValByName("avatar", "https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg",metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
