package com.tiklab.oms.sns.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.rpc.annotation.Reference;
import com.tiklab.sns.comandlikes.model.Comment;
import com.tiklab.sns.comandlikes.model.CommentQuery;
import com.tiklab.sns.comandlikes.service.CommentService;
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
 * CommentController
 */
@RestController
@RequestMapping("/comment")
@Api(name = "CommentController",desc = "评论管理")
public class CommentController {

    private static Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    @Reference(address = "${ocs.service.address}")
    private CommentService commentService;

    @RequestMapping(path="/createComment",method = RequestMethod.POST)
    @ApiMethod(name = "createComment",desc = "添加评论")
    @ApiParam(name = "comment",desc = "comment",required = true)
    public Result<String> createComment(@RequestBody @NotNull @Valid Comment comment){
        String id = commentService.createComment(comment);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateComment",method = RequestMethod.POST)
    @ApiMethod(name = "updateComment",desc = "修改评论")
    @ApiParam(name = "comment",desc = "comment",required = true)
    public Result<Void> updateComment(@RequestBody @NotNull @Valid Comment comment){
        commentService.updateComment(comment);

        return Result.ok();
    }

    @RequestMapping(path="/deleteComment",method = RequestMethod.POST)
    @ApiMethod(name = "deleteComment",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteComment(@NotNull String id){
        commentService.deleteComment(id);

        return Result.ok();
    }

    @RequestMapping(path="/findComment",method = RequestMethod.POST)
    @ApiMethod(name = "findComment",desc = "通过id 查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Comment> findComment(@NotNull String id){
        Comment comment = commentService.findComment(id);

        return Result.ok(comment);
    }

    @RequestMapping(path="/findAllComment",method = RequestMethod.POST)
    @ApiMethod(name = "findAllComment",desc = "查询所有")
    public Result<List<Comment>> findAllComment(){
        List<Comment> commentList = commentService.findAllComment();

        return Result.ok(commentList);
    }

    @RequestMapping(path = "/findCommentList",method = RequestMethod.POST)
    @ApiMethod(name = "findCommentList",desc = "通过条件查询")
    @ApiParam(name = "commentQuery",desc = "commentQuery",required = true)
    public Result<List<Comment>> findCommentList(@RequestBody @Valid @NotNull CommentQuery commentQuery){
        List<Comment> commentList = commentService.findCommentList(commentQuery);

        return Result.ok(commentList);
    }

    @RequestMapping(path = "/findCommentPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCommentPage",desc = "通过条件分页查询")
    @ApiParam(name = "commentQuery",desc = "commentQuery",required = true)
    public Result<Pagination<Comment>> findCommentPage(@RequestBody @Valid @NotNull CommentQuery commentQuery){
        Pagination<Comment> pagination = commentService.findCommentPage(commentQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findCommentAndLike",method = RequestMethod.POST)
    @ApiMethod(name = "findCommentAndLike",desc = "查询该对象所有的评论和点赞")
    @ApiParam(name = "commentQuery",desc = "commentQuery （commentObjectId,commentUser）",required = true)
    public Result<Pagination<Comment>> findCommentAndLike(@RequestBody @Valid @NotNull CommentQuery commentQuery){
        Pagination<Comment> commentList = commentService.findCommenAndLike(commentQuery);

        return Result.ok(commentList);
    }

    @RequestMapping(path = "/findCommentTreePage",method = RequestMethod.POST)
    @ApiMethod(name = "findCommentTreePage",desc = "分页查询评论树 （该接口用于文档的评论）")
    @ApiParam(name = "commentQuery",desc = "commentQuery （commentObjectId,commentUser）",required = true)
    public Result<List<Comment>> findCommentTreePage(@RequestBody @Valid @NotNull CommentQuery commentQuery){
        List<Comment> commentList = commentService.findCommentTreePage(commentQuery);

        return Result.ok(commentList);
    }


}
