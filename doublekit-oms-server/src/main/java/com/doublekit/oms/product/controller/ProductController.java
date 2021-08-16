package com.doublekit.oms.product.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Pagination;
import com.doublekit.common.Result;
import com.doublekit.product.product.model.Product;
import com.doublekit.product.product.model.ProductQuery;
import com.doublekit.product.product.service.ProductService;
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
 * ProductController
 */
@RestController
@RequestMapping("/product")
@Api(name = "ProductController",desc = "产品管理")
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(path="/createProduct",method = RequestMethod.POST)
    @ApiMethod(name = "createProduct",desc = "createProduct")
    @ApiParam(name = "product",desc = "product",required = true)
    public Result<String> createProduct(@RequestBody @NotNull @Valid Product product){
        String id = productService.createProduct(product);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateProduct",method = RequestMethod.POST)
    @ApiMethod(name = "updateProduct",desc = "updateProduct")
    @ApiParam(name = "product",desc = "product",required = true)
    public Result<Void> updateProduct(@RequestBody @NotNull @Valid Product product){
        productService.updateProduct(product);

        return Result.ok();
    }

    @RequestMapping(path="/deleteProduct",method = RequestMethod.POST)
    @ApiMethod(name = "deleteProduct",desc = "deleteProduct")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteProduct(@NotNull String id){
        productService.deleteProduct(id);

        return Result.ok();
    }

    @RequestMapping(path="/findProduct",method = RequestMethod.POST)
    @ApiMethod(name = "findProduct",desc = "findProduct")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Product> findProduct(@NotNull String id){
        Product product = productService.findProduct(id);

        return Result.ok(product);
    }

    @RequestMapping(path="/findAllProduct",method = RequestMethod.POST)
    @ApiMethod(name = "findAllProduct",desc = "findAllProduct")
    public Result<List<Product>> findAllProduct(){
        List<Product> productList = productService.findAllProduct();

        return Result.ok(productList);
    }

    @RequestMapping(path = "/findProductList",method = RequestMethod.POST)
    @ApiMethod(name = "findProductList",desc = "findProductList")
    @ApiParam(name = "productQuery",desc = "productQuery",required = true)
    public Result<List<Product>> findProductList(@RequestBody @Valid @NotNull ProductQuery productQuery){
        List<Product> productList = productService.findProductList(productQuery);

        return Result.ok(productList);
    }

    @RequestMapping(path = "/findProductPage",method = RequestMethod.POST)
    @ApiMethod(name = "findProductPage",desc = "findProductPage")
    @ApiParam(name = "productQuery",desc = "productQuery",required = true)
    public Result<Pagination<Product>> findProductPage(@RequestBody @Valid @NotNull ProductQuery productQuery){
        Pagination<Product> pagination = productService.findProductPage(productQuery);

        return Result.ok(pagination);
    }

}
