package com.doublekit.oms.product.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.product.product.model.Product;
import com.doublekit.product.product.model.ProductQuery;
import com.doublekit.product.product.service.ProductService;
import com.doublekit.rpc.annotation.Reference;
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
    @Reference(address = "${ocs.service.address}")
    private ProductService productService;

    @RequestMapping(path="/createProduct",method = RequestMethod.POST)
    @ApiMethod(name = "createProduct",desc = "创建产品")
    @ApiParam(name = "product",desc = "product",required = true)
    public Result<String> createProduct(@RequestBody @NotNull @Valid Product product){
        String id = productService.createProduct(product);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateProduct",method = RequestMethod.POST)
    @ApiMethod(name = "updateProduct",desc = "修改")
    @ApiParam(name = "product",desc = "product",required = true)
    public Result<Void> updateProduct(@RequestBody @NotNull @Valid Product product){
        productService.updateProduct(product);

        return Result.ok();
    }

    @RequestMapping(path="/deleteProduct",method = RequestMethod.POST)
    @ApiMethod(name = "deleteProduct",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteProduct(@NotNull String id){
        productService.deleteProduct(id);

        return Result.ok();
    }

    @RequestMapping(path="/findProduct",method = RequestMethod.POST)
    @ApiMethod(name = "findProduct",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Product> findProduct(@NotNull String id){
        Product product = productService.findProduct(id);

        return Result.ok(product);
    }

    @RequestMapping(path="/findAllProduct",method = RequestMethod.POST)
    @ApiMethod(name = "findAllProduct",desc = "查询所有")
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
    @ApiMethod(name = "findProductPage",desc = "分页条件查询")
    @ApiParam(name = "productQuery",desc = "productQuery",required = true)
    public Result<Pagination<Product>> findProductPage(@RequestBody @Valid @NotNull ProductQuery productQuery){
        Pagination<Product> pagination = productService.findProductNewVersionPage(productQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findNewProductAll",method = RequestMethod.POST)
    @ApiMethod(name = "findNewProductAll",desc = "查询下载产品（ce ee版）的所有数据")
    public Result<List<Product>> findNewProductAll(){
        List<Product> downProductList= productService.findNewProductAll();

        return Result.ok(downProductList);
    }



    @RequestMapping(path = "/updateSort",method = RequestMethod.POST)
    @ApiMethod(name = "updateSort",desc = "修改排序")
    @ApiParam(name = "productQuery",desc = "productQuery",required = true)
    public Result<Void> updateSort( @RequestBody @Valid @NotNull ProductQuery productQuery){
        productService.updateSort(productQuery);

        return Result.ok();
    }

        @RequestMapping(path="/findAllProductState",method = RequestMethod.POST)
        @ApiMethod(name = "findAllProductState",desc = "查询所有产品以及订阅状态")
        @ApiParam(name = "productQuery",desc = "type:saas  ee ce; tenantId 租户id ; user ",required = true)
        public Result<List<Product>> findAllProductState(@RequestBody @Valid @NotNull ProductQuery productQuery){
            List<Product> productList = productService.findAllProductState(productQuery);

            return Result.ok(productList);
        }

    @RequestMapping(path="/findValidProduct",method = RequestMethod.POST)
    @ApiMethod(name = "findValidProduct",desc = "查询saas单个产品是否在有效内")
    @ApiParam(name = "code",desc = "产品编码  code; tenantId 租户id ",required = true)
    public Result<Product> findValidProduct( String tenantId,String code){
        Product product= productService.findValidProduct(tenantId,code);

        return Result.ok(product);
    }

    @RequestMapping(path="/findAllProductListSig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllProductListSig",desc = "查询所有产品并根据code去重")
    public Result<List<Product>> findAllProductListSig(){
        List<Product>  product= productService.findAllProductListSig();

        return Result.ok(product);
    }
}
