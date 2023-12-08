package io.thoughtware.hadess.repository.controller;

import io.thoughtware.hadess.repository.model.Storage;
import io.thoughtware.hadess.repository.model.StorageQuery;
import io.thoughtware.hadess.repository.service.StorageService;
import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
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
    @ApiMethod(name = "createStorage",desc = "创建存储库")
    @ApiParam(name = "storage",desc = "storage",required = true)
    public Result<String> createStorage(@RequestBody @NotNull @Valid Storage storage){
        String id = storageService.createStorage(storage);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateStorage",method = RequestMethod.POST)
    @ApiMethod(name = "updateStorage",desc = "更新存储库")
    @ApiParam(name = "storage",desc = "storage",required = true)
    public Result<Void> updateStorage(@RequestBody @NotNull @Valid Storage storage){
        storageService.updateStorage(storage);

        return Result.ok();
    }

    @RequestMapping(path="/deleteStorage",method = RequestMethod.POST)
    @ApiMethod(name = "deleteStorage",desc = "删除存储库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteStorage(@NotNull String id){
        storageService.deleteStorage(id);

        return Result.ok();
    }

    @RequestMapping(path="/findStorage",method = RequestMethod.POST)
    @ApiMethod(name = "findStorage",desc = "通过id查询存储库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Storage> findStorage(@NotNull String id){
        Storage storage = storageService.findStorage(id);

        return Result.ok(storage);
    }

    @RequestMapping(path="/findAllStorage",method = RequestMethod.POST)
    @ApiMethod(name = "findAllStorage",desc = "查询所有存储库")
    public Result<List<Storage>> findAllStorage(){
        List<Storage> storageList = storageService.findAllStorage();

        return Result.ok(storageList);
    }

    @RequestMapping(path = "/findStorageList",method = RequestMethod.POST)
    @ApiMethod(name = "findStorageList",desc = "条件查询存储库")
    @ApiParam(name = "storageQuery",desc = "storageQuery",required = true)
    public Result<List<Storage>> findStorageList(@RequestBody @Valid @NotNull StorageQuery storageQuery){
        List<Storage> storageList = storageService.findStorageList(storageQuery);

        return Result.ok(storageList);
    }

    @RequestMapping(path = "/findStoragePage",method = RequestMethod.POST)
    @ApiMethod(name = "findStoragePage",desc = "条件分页查询存储库")
    @ApiParam(name = "storageQuery",desc = "storageQuery",required = true)
    public Result<Pagination<Storage>> findStoragePage(@RequestBody @Valid @NotNull StorageQuery storageQuery){
        Pagination<Storage> pagination = storageService.findStoragePage(storageQuery);

        return Result.ok(pagination);
    }

}
