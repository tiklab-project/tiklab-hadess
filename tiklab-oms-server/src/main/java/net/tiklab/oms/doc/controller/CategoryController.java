package  net.tiklab.oms.doc.controller;

import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.doc.category.model.Category;
import  net.tiklab.doc.category.model.CategoryQuery;
import  net.tiklab.doc.category.service.CategoryService;
import  net.tiklab.rpc.annotation.Reference;
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
import java.util.Map;

/**
 * CategoryController
 */
@RestController
@RequestMapping("/category")
@Api(name = "CategoryController",desc = "目录管理")
public class CategoryController {

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private CategoryService categoryService;



    @RequestMapping(path="/createCategory",method = RequestMethod.POST)
    @ApiMethod(name = "createCategory",desc = "创建目录")
    @ApiParam(name = "category",desc = "category",required = true)
    public Result<String> createCategory(@RequestBody @NotNull @Valid Category category){
        String id = categoryService.createCategory(category);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateCategory",method = RequestMethod.POST)
    @ApiMethod(name = "updateCategory",desc = "修改目录")
    @ApiParam(name = "category",desc = "category",required = true)
    public Result<Void> updateCategory(@RequestBody @NotNull @Valid Category category){
        categoryService.updateCategory(category);

        return Result.ok();
    }

    @RequestMapping(path="/deleteCategory",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCategory",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteCategory(@NotNull String id){
        categoryService.deleteCategory(id);

        return Result.ok();
    }

    @RequestMapping(path="/findCategory",method = RequestMethod.POST)
    @ApiMethod(name = "findCategory",desc = "查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Category> findCategory(@NotNull String id){
        Category category = categoryService.findCategory(id);

        return Result.ok(category);
    }

    @RequestMapping(path="/findAllCategory",method = RequestMethod.POST)
    @ApiMethod(name = "findAllCategory",desc = "查询所有")
    public Result<List<Category>> findAllCategory(){
        List<Category> categoryList = categoryService.findAllCategory();

        return Result.ok(categoryList);
    }

    @RequestMapping(path = "/findCategoryList",method = RequestMethod.POST)
    @ApiMethod(name = "findCategoryList",desc = "条件查询")
    @ApiParam(name = "categoryQuery",desc = "categoryQuery",required = true)
    public Result<List<Category>> findCategoryList(@RequestBody @Valid @NotNull CategoryQuery categoryQuery){
        List<Category> categoryList = categoryService.findCategoryList(categoryQuery);

        return Result.ok(categoryList);
    }

    @RequestMapping(path = "/findCategoryPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCategoryPage",desc = "条件分页查询")
    @ApiParam(name = "categoryQuery",desc = "categoryQuery",required = true)
    public Result<Pagination<Category>> findCategoryPage(@RequestBody @Valid @NotNull CategoryQuery categoryQuery){
        Pagination<Category> pagination = categoryService.findCategoryPage(categoryQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findCategoryListTree",method = RequestMethod.POST)
    @ApiMethod(name = "findCategoryListTree",desc = "空间id查询文档目录树")
    @ApiParam(name = "categoryQuery",desc = "categoryQuery",required = true)
    public Result<Map> findCategoryListTree(@RequestBody @Valid @NotNull CategoryQuery categoryQuery){
        Map<String, List> categoryListTree = categoryService.findCategoryListTree(categoryQuery);
        return Result.ok(categoryListTree);
    }


    @RequestMapping(path = "/likeFindCategoryListTree",method = RequestMethod.POST)
    @ApiMethod(name = "likeFindCategoryListTree",desc = "模糊条件查询目录树")
    @ApiParam(name = "categoryQuery",desc = "categoryQuery",required = true)
    public Result<Map> likeFindCategoryListTree(@RequestBody @Valid @NotNull CategoryQuery categoryQuery){
        Map<String, List> categoryListTree = categoryService.likeFindCategoryListTree(categoryQuery);
        return Result.ok(categoryListTree);
    }

    @RequestMapping(path = "/findChildrenCategory",method = RequestMethod.POST)
    @ApiMethod(name = "findChildrenCategory",desc = "通过目录id查询目录及下面的子目录")
    @ApiParam(name = "categoryId",desc = "categoryId",required = true)
    public Result<Category> findChildrenCategory( @Valid @NotNull String categoryId){
        Category category = categoryService.findChildrenCategory(categoryId);

        return Result.ok(category);
    }

}