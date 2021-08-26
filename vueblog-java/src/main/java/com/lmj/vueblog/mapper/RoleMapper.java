package com.lmj.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmj.vueblog.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id查询用户的角色
     * @param userId
     * @return java.util.List<com.markerhub.entity.Role>
     */
    @Select("select r.id,r.name from m_role r left join m_user_role ur on ur.role_id = r.id where ur.user_id = #{userId}")
    List<Role> getRolesByUserId(Long userId);
}
