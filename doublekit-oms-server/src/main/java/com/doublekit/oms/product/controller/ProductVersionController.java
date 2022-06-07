package com.doublekit.oms.product.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.download.product.model.ProductVersion;
import com.doublekit.download.product.model.ProductVersionQuery;
import com.doublekit.download.product.service.ProductVersionService;
import com.doublekit.rpc.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/homes/productVersion")
@Api(name = "ProductVersionController",desc = "产品版本的管理")
public class ProductVersionController {

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private ProductVersionService productVersionService;

    @RequestMapping(path = "/findProductVersionList",method = RequestMethod.POST)
    @ApiMethod(name = "findProductVersionList",desc = "通过条件查询")
    @ApiParam(name = "productVersionQuery",desc = "productVersionQuery",required = true)
    public Result<List<ProductVersion>> findProductVersionList(@RequestBody @Valid @NotNull ProductVersionQuery productVersionQuery){
        List<ProductVersion> productVersionList = productVersionService.findProductVersionList(productVersionQuery);

        return Result.ok(productVersionList);
    }
}
