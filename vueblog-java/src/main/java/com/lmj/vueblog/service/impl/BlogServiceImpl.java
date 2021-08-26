package com.lmj.vueblog.service.impl;

import com.lmj.vueblog.entity.Blog;
import com.lmj.vueblog.mapper.BlogMapper;
import com.lmj.vueblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
