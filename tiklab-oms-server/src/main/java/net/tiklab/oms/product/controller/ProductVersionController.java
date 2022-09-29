package  net.tiklab.oms.product.controller;

import net.tiklab.core.page.Pagination;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.rpc.annotation.Reference;
import net.tiklab.updownload.product.model.ProductVersion;
import net.tiklab.updownload.product.model.ProductVersionQuery;
import net.tiklab.updownload.product.service.ProductVersionService;
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
 * ProductVersionController
 */
@RestController
@RequestMapping("/productVersion")
@Api(name = "ProductVersionController",desc = "产品版本的管理")
public class ProductVersionController {

    private static Logger logger = LoggerFactory.getLogger(ProductVersionController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private ProductVersionService productVersionService;

    @RequestMapping(path="/createProductVersion",method = RequestMethod.POST)
    @ApiMethod(name = "createProductVersion",desc = "创建产品版本")
    @ApiParam(name = "productVersion",desc = "productVersion",required = true)
    public Result<String> createProductVersion(@RequestBody @NotNull @Valid ProductVersion productVersion){
        String id = productVersionService.createProductVersion(productVersion);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateProductVersion",method = RequestMethod.POST)
    @ApiMethod(name = "updateProductVersion",desc = "修改产品版本")
    @ApiParam(name = "productVersion",desc = "productVersion",required = true)
    public Result<Void> updateProductVersion(@RequestBody @NotNull @Valid ProductVersion productVersion){
        productVersionService.updateProductVersion(productVersion);

        return Result.ok();
    }

    @RequestMapping(path="/deleteProductVersion",method = RequestMethod.POST)
    @ApiMethod(name = "deleteProductVersion",desc = "删除产品版本")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteProductVersion(@NotNull String id){
        productVersionService.deleteProductVersion(id);

        return Result.ok();
    }

    @RequestMapping(path="/findProductVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findProductVersion",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ProductVersion> findProductVersion(@NotNull String id){
        ProductVersion productVersion = productVersionService.findProductVersion(id);

        return Result.ok(productVersion);
    }

    @RequestMapping(path="/findAllProductVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findAllProductVersion",desc = "查询所有")
    public Result<List<ProductVersion>> findAllProductVersion(){
        List<ProductVersion> productVersionList = productVersionService.findAllProductVersion();

        return Result.ok(productVersionList);
    }

    @RequestMapping(path = "/findProductVersionList",method = RequestMethod.POST)
    @ApiMethod(name = "findProductVersionList",desc = "通过条件查询")
    @ApiParam(name = "productVersionQuery",desc = "productVersionQuery",required = true)
    public Result<List<ProductVersion>> findProductVersionList(@RequestBody @Valid @NotNull ProductVersionQuery productVersionQuery){
        List<ProductVersion> productVersionList = productVersionService.findProductVersionList(productVersionQuery);

        return Result.ok(productVersionList);
    }

    @RequestMapping(path = "/findProductVersionPage",method = RequestMethod.POST)
    @ApiMethod(name = "findProductVersionPage",desc = "通过条件分页查询")
    @ApiParam(name = "productVersionQuery",desc = "productVersionQuery",required = true)
    public Result<Pagination<ProductVersion>> findProductVersionPage(@RequestBody @Valid @NotNull ProductVersionQuery productVersionQuery){
        Pagination<ProductVersion> pagination = productVersionService.findProductVersionPage(productVersionQuery);

        return Result.ok(pagination);
    }



}
