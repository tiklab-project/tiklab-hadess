package io.thoughtware.hadess.repository.controller;


import io.thoughtware.core.Result;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.hadess.repository.model.ResourceMan;
import io.thoughtware.hadess.repository.service.ResourceManService;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
