package com.tiklab.oms.product.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.product.product.model.ProductType;
import com.tiklab.product.product.model.ProductTypeQuery;
import com.tiklab.product.product.service.ProductTypeService;
import com.tiklab.rpc.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ProductTypeController
 */
@RestController
@RequestMapping("/productType")
@Api(name = "ProductTypeController",desc = "产品类型管理")
public class ProductTypeController {


    @Autowired
    @Reference(address = "${ocs.service.address}")
    private ProductTypeService productTypeService;

    @RequestMapping(path="/createProductType",method = RequestMethod.POST)
    @ApiMethod(name = "createProductType",desc = "createProductType")
    @ApiParam(name = "productType",desc = "productType",required = true)
    public Result<String> createProductType(@RequestBody @NotNull @Valid ProductType productType){
        String id = productTypeService.createProductType(productType);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateProductType",method = RequestMethod.POST)
    @ApiMethod(name = "updateProductType",desc = "updateProductType")
    @ApiParam(name = "productType",desc = "productType",required = true)
    public Result<Void> updateProductType(@RequestBody @NotNull @Valid ProductType productType){
        productTypeService.updateProductType(productType);

        return Result.ok();
    }

    @RequestMapping(path="/deleteProductType",method = RequestMethod.POST)
    @ApiMethod(name = "deleteProductType",desc = "deleteProductType")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteProductType(@NotNull String id){
        productTypeService.deleteProductType(id);

        return Result.ok();
    }

    @RequestMapping(path="/findProductType",method = RequestMethod.POST)
    @ApiMethod(name = "findProductType",desc = "findProductType")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ProductType> findProductType(@NotNull String id){
        ProductType productType = productTypeService.findProductType(id);

        return Result.ok(productType);
    }

    @RequestMapping(path="/findAllProductType",method = RequestMethod.POST)
    @ApiMethod(name = "findAllProductType",desc = "查询所有的产品类型")
    public Result<List<ProductType>> findAllProductType(){
        List<ProductType> productTypeList = productTypeService.findAllProductType();

        return Result.ok(productTypeList);
    }

    @RequestMapping(path = "/findProductTypeList",method = RequestMethod.POST)
    @ApiMethod(name = "findProductTypeList",desc = "findProductTypeList")
    @ApiParam(name = "productTypeQuery",desc = "productTypeQuery",required = true)
    public Result<List<ProductType>> findProductTypeList(@RequestBody @Valid @NotNull ProductTypeQuery productTypeQuery){
        List<ProductType> productTypeList = productTypeService.findProductTypeList(productTypeQuery);

        return Result.ok(productTypeList);
    }

    @RequestMapping(path = "/findProductTypePage",method = RequestMethod.POST)
    @ApiMethod(name = "findProductTypePage",desc = "findProductTypePage")
    @ApiParam(name = "productTypeQuery",desc = "productTypeQuery",required = true)
    public Result<Pagination<ProductType>> findProductTypePage(@RequestBody @Valid @NotNull ProductTypeQuery productTypeQuery){
        Pagination<ProductType> pagination = productTypeService.findProductTypePage(productTypeQuery);

        return Result.ok(pagination);
    }

}
