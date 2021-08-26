package com.lmj.vueblog.mapper;

import com.lmj.vueblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 给用户配置角色
     * @param userId
     * @return java.util.List<com.markerhub.entity.Role>
     */
    @Insert("insert into m_user_role(id,user_id,role_id) value(#{id},#{userId},3)")
    void setUserRole(Long userId);
}
