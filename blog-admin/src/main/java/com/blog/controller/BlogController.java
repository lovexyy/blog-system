package com.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.lang.Result;
import com.blog.common.lang.ResultCode;
import com.blog.pojo.Blog;
import com.blog.service.BlogService;
import com.blog.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  博客内容，增删改查 前端控制器
 * </p>
 *
 * @author Zx
 * @since 2022-08-03
 */
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    /**
     * 获取博客列表
     * @param currentPage
     * @return
     */
    @GetMapping("/list")
    public Result blogList(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return new Result(ResultCode.SUCCESS, pageData);
    }

    /**
     * 根据id，查看详情
     * @param id
     * @return
     */
    @GetMapping("/blog/{id}")
    public Result blogDet(@PathVariable(name = "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");
        return new Result(ResultCode.SUCCESS, blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result blogEdit(@Validated @RequestBody Blog blog){
        Blog temp = null;
        // 判断是否新增
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProFile().getId()), "没有权限编辑");
        }else {
            temp = new Blog();
            temp.setUserId(ShiroUtils.getProFile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        // 拷贝对象，过滤 "id","userId","created","status"字段
        BeanUtils.copyProperties(blog, temp, "id","userId","created","status");
        // 保存
        blogService.saveOrUpdate(temp);

        return new Result(ResultCode.SUCCESS, "更新成功");
    }

    @GetMapping("/blog/del/{id}")
    public Result blogDel(@PathVariable(name = "id") Long id){
        // 直接物理删除
        blogService.removeById(id);

        return new Result(ResultCode.SUCCESS, "删除成功");
    }

}

