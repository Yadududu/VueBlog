package com.lmj.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmj.vueblog.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据角色id查询权限
     * @param roleId
     * @return java.util.List<com.markerhub.entity.Permission>
     */
    @Select("select p.id,p.name,p.url from m_permission p left join m_role_permission rp on rp.permission_id = p.id where rp.role_id = #{roleId}")
    List<Permission> getPermissionsByRoleId(Long roleId);
}
