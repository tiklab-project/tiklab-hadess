package io.tiklab.hadess.repository.controller;


import io.tiklab.core.Result;
import io.tiklab.hadess.repository.model.ResourceMan;
import io.tiklab.hadess.repository.service.ResourceManService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resourceMan")
@Api(name = "ResourceManController",desc = "资源管理")
public class ResourceManController {

    @Autowired
    ResourceManService resourceManService;


    @RequestMapping(path="/findResource",method = RequestMethod.POST)
    @ApiMethod(name = "findResource",desc = "查询资源")
    public Result<ResourceMan> findResource(){
        ResourceMan resource = resourceManService.findResource();

        return Result.ok(resource);
    }
}
