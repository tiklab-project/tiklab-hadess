package net.tiklab.oms.sns.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.rpc.annotation.Reference;
import net.tiklab.sns.blogs.model.Blogs;
import net.tiklab.sns.blogs.model.BlogsQuery;
import net.tiklab.sns.blogs.service.BlogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * BlogsController
 */
@RestController
@RequestMapping("/blogs")
@Api(name = "BlogsController",desc = "博客管理")
public class BlogsController {

    private static Logger logger = LoggerFactory.getLogger(BlogsController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private BlogsService blogsService;

    @RequestMapping(path="/createBlogs",method = RequestMethod.POST)
    @ApiMethod(name = "createBlogs",desc = "创建博客")
    @ApiParam(name = "blogs",desc = "blogs",required = true)
    public Result<String> createBlogs(@RequestBody @NotNull @Valid Blogs blogs){
        String id = blogsService.createBlogs(blogs);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateBlogs",method = RequestMethod.POST)
    @ApiMethod(name = "updateBlogs",desc = "updateBlogs")
    @ApiParam(name = "blogs",desc = "blogs",required = true)
    public Result<Void> updateBlogs(@RequestBody @NotNull @Valid Blogs blogs){
        blogsService.updateBlogs(blogs);

        return Result.ok();
    }

    @RequestMapping(path="/deleteBlogs",method = RequestMethod.POST)
    @ApiMethod(name = "deleteBlogs",desc = "deleteBlogs")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteBlogs(@NotNull String id){
        blogsService.deleteBlogs(id);

        return Result.ok();
    }

    @RequestMapping(path="/findBlogs",method = RequestMethod.POST)
    @ApiMethod(name = "findBlogs",desc = "通过id和登录用户id查询博客")
    @ApiParam(name = "id",desc = "id,userId",required = true)
    public Result<Blogs> findBlogs(@NotNull String id,String userId){
        Blogs blogs = blogsService.findBlogs(id,userId);

        return Result.ok(blogs);
    }

    @RequestMapping(path="/findAllBlogs",method = RequestMethod.POST)
    @ApiMethod(name = "findAllBlogs",desc = "findAllBlogs")
    public Result<List<Blogs>> findAllBlogs(){
        List<Blogs> blogsList = blogsService.findAllBlogs();

        return Result.ok(blogsList);
    }

    @RequestMapping(path = "/findBlogsList",method = RequestMethod.POST)
    @ApiMethod(name = "findBlogsList",desc = "findBlogsList")
    @ApiParam(name = "blogsQuery",desc = "blogsQuery",required = true)
    public Result<List<Blogs>> findBlogsList(@RequestBody @Valid @NotNull BlogsQuery blogsQuery){
        List<Blogs> blogsList = blogsService.findBlogsList(blogsQuery);

        return Result.ok(blogsList);
    }

    @RequestMapping(path = "/findBlogsPage",method = RequestMethod.POST)
    @ApiMethod(name = "findBlogsPage",desc = "条件分页查询")
    @ApiParam(name = "blogsQuery",desc = "blogsQuery",required = true)
    public Result<Pagination<Blogs>> findBlogsPage(@RequestBody @Valid @NotNull BlogsQuery blogsQuery){
        Pagination<Blogs> pagination = blogsService.findBlogsPage(blogsQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findBlogById",method = RequestMethod.POST)
    @ApiMethod(name = "findBlogById",desc = "通过id查询博客")
    @ApiParam(name = "blogsQuery",desc = "blogsQuery",required = true)
    public Result<Blogs> findBlogById(@RequestBody @Valid @NotNull BlogsQuery blogsQuery){
        Pagination<Blogs> pagination = blogsService.findBlogsPage(blogsQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findHotBlog",method = RequestMethod.POST)
    @ApiMethod(name = "findHotBlog",desc = "查询热门")
    public Result<List<Blogs>> findHotBlog(){
        List<Blogs> blogsList = blogsService.findHotBlog();

        return Result.ok(blogsList);
    }

}
