package  net.tiklab.oms.subscribe.controller;

import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import net.tiklab.product.model.Product;
import net.tiklab.product.model.ProductQuery;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.subscribe.subscribe.model.Subscribe;
import  net.tiklab.subscribe.subscribe.model.SubscribeQuery;
import  net.tiklab.subscribe.subscribe.service.SubscribeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * SubscribeController
 */
@RestController
@RequestMapping("/subscribe")
@Api(name = "SubscribeController",desc = "订阅管理")
public class SubscribeController {

    private static Logger logger = LoggerFactory.getLogger(SubscribeController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private SubscribeService subscribeService;

    @Value("${app.path}")
    String  appPath;
    @RequestMapping(path="/createSubscribe",method = RequestMethod.POST)
    @ApiMethod(name = "createSubscribe",desc = "添加订阅 并开通")
    @ApiParam(name = "subscribe",desc = "subscribe",required = true)
    public Result<String> createSubscribe(@RequestBody @NotNull @Valid Subscribe subscribe){
        String id = subscribeService.createSubscribe(subscribe);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateSubscribe",method = RequestMethod.POST)
    @ApiMethod(name = "updateSubscribe",desc = "updateSubscribe")
    @ApiParam(name = "subscribe",desc = "subscribe",required = true)
    public Result<Void> updateSubscribe(@RequestBody @NotNull @Valid Subscribe subscribe){
        subscribeService.updateSubscribe(subscribe);

        return Result.ok();
    }

    @RequestMapping(path="/deleteSubscribe",method = RequestMethod.POST)
    @ApiMethod(name = "deleteSubscribe",desc = "deleteSubscribe")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteSubscribe(@NotNull String id){
        subscribeService.deleteSubscribe(id);

        return Result.ok();
    }

    @RequestMapping(path="/findSubscribe",method = RequestMethod.POST)
    @ApiMethod(name = "findSubscribe",desc = "findSubscribe")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Subscribe> findSubscribe(@NotNull String id){
        Subscribe subscribe = subscribeService.findSubscribe(id);

        return Result.ok(subscribe);
    }

    @RequestMapping(path="/findAllSubscribe",method = RequestMethod.POST)
    @ApiMethod(name = "findAllSubscribe",desc = "findAllSubscribe")
    public Result<List<Subscribe>> findAllSubscribe(){
        List<Subscribe> subscribeList = subscribeService.findAllSubscribe();

        return Result.ok(subscribeList);
    }

    @RequestMapping(path = "/findSubscribeList",method = RequestMethod.POST)
    @ApiMethod(name = "findSubscribeList",desc = "通过条件查询订阅")
    @ApiParam(name = "subscribeQuery",desc = "subscribeQuery",required = true)
    public Result<List<Subscribe>> findSubscribeList(@RequestBody @Valid @NotNull SubscribeQuery subscribeQuery){
        List<Subscribe> subscribeList = subscribeService.findSubscribeList(subscribeQuery);
        return Result.ok(subscribeList);
    }

    @RequestMapping(path = "/findSubscribePage",method = RequestMethod.POST)
    @ApiMethod(name = "findSubscribePage",desc = "findSubscribePage")
    @ApiParam(name = "subscribeQuery",desc = "subscribeQuery",required = true)
    public Result<Pagination<Subscribe>> findSubscribePage(@RequestBody @Valid @NotNull SubscribeQuery subscribeQuery){
        Pagination<Subscribe> pagination = subscribeService.findSubscribePage(subscribeQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findSubscribePrice",method = RequestMethod.POST)
    @ApiMethod(name = "findSubscribePrice",desc = "查询订阅价格")
    @ApiParam(name = "subscribeQuery",desc = "传参 productId  math",required = true)
    public Result<Map> findSubscribePrice(@RequestBody @Valid @NotNull SubscribeQuery subscribeQuery){
        Map resultMap = subscribeService.findSubscribePrice(subscribeQuery);

        return Result.ok(resultMap);
    }

    @RequestMapping(path = "/findSubByPrNamePage",method = RequestMethod.POST)
    @ApiMethod(name = "findSubByPrNamePage",desc = "通过产品名称查询订阅")
    @ApiParam(name = "productQuery",desc = "productQuery",required = true)
    public Result<Pagination<Subscribe>> findSubByPrNamePage(@RequestBody @Valid @NotNull ProductQuery productQuery){
        Pagination<Subscribe> pagination = subscribeService.findSubByPrNamePage(productQuery);

        return Result.ok(pagination);
    }
    

    @RequestMapping(path = "/openServe",method = RequestMethod.POST)
    @ApiMethod(name = "openServe",desc = "企业微信 创建企业 默认开通eas并开通对应的产品")
    @ApiParam(name = "subscribe",desc = "subscribe",required = true)
    public Result<Void> openServe(@RequestBody @Valid @NotNull Subscribe subscribe){
        String id  = subscribeService.openServe(subscribe);
        return Result.ok(id);
    }

    @RequestMapping(path = "/findSubscribeProduct",method = RequestMethod.POST)
    @ApiMethod(name = "findSubscribePrice",desc = "查询订阅的相关产品")
    @ApiParam(name = "subscribeQuery",desc = "subscribeQuery",required = true)
    public Result<Product> findSubscribeProduct(@RequestBody @Valid @NotNull SubscribeQuery subscribeQuery){
        List resultMap = subscribeService.findSubscribeProduct(subscribeQuery);

        return Result.ok(resultMap);
    }

}