package net.tiklab.xpack.repository.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.xpack.repository.model.Storage;
import net.tiklab.xpack.repository.model.StorageQuery;
import net.tiklab.xpack.repository.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * StorageController
 */
@RestController
@RequestMapping("/storage")
@Api(name = "StorageController",desc = "存储库管理")
public class StorageController {

    private static Logger logger = LoggerFactory.getLogger(StorageController.class);

    @Autowired
    private StorageService storageService;

    @RequestMapping(path="/createStorage",method = RequestMethod.POST)
    @ApiMethod(name = "createStorage",desc = "createStorage")
    @ApiParam(name = "storage",desc = "storage",required = true)
    public Result<String> createStorage(@RequestBody @NotNull @Valid Storage storage){
        String id = storageService.createStorage(storage);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateStorage",method = RequestMethod.POST)
    @ApiMethod(name = "updateStorage",desc = "updateStorage")
    @ApiParam(name = "storage",desc = "storage",required = true)
    public Result<Void> updateStorage(@RequestBody @NotNull @Valid Storage storage){
        storageService.updateStorage(storage);

        return Result.ok();
    }

    @RequestMapping(path="/deleteStorage",method = RequestMethod.POST)
    @ApiMethod(name = "deleteStorage",desc = "deleteStorage")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteStorage(@NotNull String id){
        storageService.deleteStorage(id);

        return Result.ok();
    }

    @RequestMapping(path="/findStorage",method = RequestMethod.POST)
    @ApiMethod(name = "findStorage",desc = "findStorage")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Storage> findStorage(@NotNull String id){
        Storage storage = storageService.findStorage(id);

        return Result.ok(storage);
    }

    @RequestMapping(path="/findAllStorage",method = RequestMethod.POST)
    @ApiMethod(name = "findAllStorage",desc = "findAllStorage")
    public Result<List<Storage>> findAllStorage(){
        List<Storage> storageList = storageService.findAllStorage();

        return Result.ok(storageList);
    }

    @RequestMapping(path = "/findStorageList",method = RequestMethod.POST)
    @ApiMethod(name = "findStorageList",desc = "findStorageList")
    @ApiParam(name = "storageQuery",desc = "storageQuery",required = true)
    public Result<List<Storage>> findStorageList(@RequestBody @Valid @NotNull StorageQuery storageQuery){
        List<Storage> storageList = storageService.findStorageList(storageQuery);

        return Result.ok(storageList);
    }

    @RequestMapping(path = "/findStoragePage",method = RequestMethod.POST)
    @ApiMethod(name = "findStoragePage",desc = "findStoragePage")
    @ApiParam(name = "storageQuery",desc = "storageQuery",required = true)
    public Result<Pagination<Storage>> findStoragePage(@RequestBody @Valid @NotNull StorageQuery storageQuery){
        Pagination<Storage> pagination = storageService.findStoragePage(storageQuery);

        return Result.ok(pagination);
    }

}
