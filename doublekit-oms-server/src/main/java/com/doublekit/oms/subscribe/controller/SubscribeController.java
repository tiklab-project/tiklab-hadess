package com.doublekit.oms.subscribe.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Pagination;
import com.doublekit.common.Result;
import com.doublekit.trade.subscribe.model.Subscribe;
import com.doublekit.trade.subscribe.model.SubscribeQuery;
import com.doublekit.trade.subscribe.service.SubscribeService;
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
 * SubscribeController
 */
@RestController
@RequestMapping("/subscribe")
@Api(name = "SubscribeController",desc = "订阅管理")
public class SubscribeController {

    private static Logger logger = LoggerFactory.getLogger(SubscribeController.class);

    @Autowired
    private SubscribeService subscribeService;

    @RequestMapping(path="/createSubscribe",method = RequestMethod.POST)
    @ApiMethod(name = "createSubscribe",desc = "createSubscribe")
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
    @ApiMethod(name = "findSubscribeList",desc = "findSubscribeList")
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

}
