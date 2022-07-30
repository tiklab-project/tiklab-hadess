package com.tiklab.oms.product.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.download.product.model.ProductUrl;
import com.tiklab.download.product.model.ProductUrlQuery;
import com.tiklab.download.product.service.ProductUrlService;
import com.tiklab.rpc.annotation.Reference;
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
 * ProductUrlController
 */
@RestController
@RequestMapping("/productUrl")
@Api(name = "ProductUrlController",desc = "ProductUrlController")
public class ProductUrlController {

    private static Logger logger = LoggerFactory.getLogger(ProductUrlController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private ProductUrlService productUrlService;

    @RequestMapping(path="/createProductUrl",method = RequestMethod.POST)
    @ApiMethod(name = "createProductUrl",desc = "createProductUrl")
    @ApiParam(name = "productUrl",desc = "productUrl",required = true)
    public Result<String> createProductUrl(@RequestBody @NotNull @Valid ProductUrl productUrl){
        String id = productUrlService.createProductUrl(productUrl);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateProductUrl",method = RequestMethod.POST)
    @ApiMethod(name = "updateProductUrl",desc = "updateProductUrl")
    @ApiParam(name = "productUrl",desc = "productUrl",required = true)
    public Result<Void> updateProductUrl(@RequestBody @NotNull @Valid ProductUrl productUrl){
        productUrlService.updateProductUrl(productUrl);

        return Result.ok();
    }

    @RequestMapping(path="/deleteProductUrl",method = RequestMethod.POST)
    @ApiMethod(name = "deleteProductUrl",desc = "deleteProductUrl")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteProductUrl(@NotNull String id){
        productUrlService.deleteProductUrl(id);

        return Result.ok();
    }

    @RequestMapping(path="/findProductUrl",method = RequestMethod.POST)
    @ApiMethod(name = "findProductUrl",desc = "findProductUrl")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ProductUrl> findProductUrl(@NotNull String id){
        ProductUrl productUrl = productUrlService.findProductUrl(id);

        return Result.ok(productUrl);
    }

    @RequestMapping(path="/findAllProductUrl",method = RequestMethod.POST)
    @ApiMethod(name = "findAllProductUrl",desc = "findAllProductUrl")
    public Result<List<ProductUrl>> findAllProductUrl(){
        List<ProductUrl> productUrlList = productUrlService.findAllProductUrl();

        return Result.ok(productUrlList);
    }

    @RequestMapping(path = "/findProductUrlList",method = RequestMethod.POST)
    @ApiMethod(name = "findProductUrlList",desc = "findProductUrlList")
    @ApiParam(name = "productUrlQuery",desc = "productUrlQuery",required = true)
    public Result<List<ProductUrl>> findProductUrlList(@RequestBody @Valid @NotNull ProductUrlQuery productUrlQuery){
        List<ProductUrl> productUrlList = productUrlService.findProductUrlList(productUrlQuery);

        return Result.ok(productUrlList);
    }

    @RequestMapping(path = "/findProductUrlPage",method = RequestMethod.POST)
    @ApiMethod(name = "findProductUrlPage",desc = "findProductUrlPage")
    @ApiParam(name = "productUrlQuery",desc = "productUrlQuery",required = true)
    public Result<Pagination<ProductUrl>> findProductUrlPage(@RequestBody @Valid @NotNull ProductUrlQuery productUrlQuery){
        Pagination<ProductUrl> pagination = productUrlService.findProductUrlPage(productUrlQuery);

        return Result.ok(pagination);
    }

}
