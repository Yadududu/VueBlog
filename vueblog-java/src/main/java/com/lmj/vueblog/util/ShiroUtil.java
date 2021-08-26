package com.lmj.vueblog.util;

import com.lmj.vueblog.config.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
