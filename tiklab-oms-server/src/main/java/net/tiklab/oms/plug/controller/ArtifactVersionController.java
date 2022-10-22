package net.tiklab.oms.plug.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.rpc.annotation.Reference;
import net.tiklab.updownload.artifact.model.ArtifactVersion;
import net.tiklab.updownload.artifact.model.ArtifactVersionQuery;
import net.tiklab.updownload.artifact.service.ArtifactVersionService;
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
 * ArtifactVersionController
 */
@RestController
@RequestMapping("/artifactVersion")
@Api(name = "ArtifactVersionController",desc = "插件版本管理")
public class ArtifactVersionController {

    private static Logger logger = LoggerFactory.getLogger(ArtifactVersionController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private ArtifactVersionService artifactVersionService;

    @RequestMapping(path="/createArtifactVersion",method = RequestMethod.POST)
    @ApiMethod(name = "createArtifactVersion",desc = "创建插件版本")
    @ApiParam(name = "artifactVersion",desc = "artifactVersion",required = true)
    public Result<String> createArtifactVersion(@RequestBody @NotNull @Valid ArtifactVersion artifactVersion){
        String id = artifactVersionService.createArtifactVersion(artifactVersion);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateArtifactVersion",method = RequestMethod.POST)
    @ApiMethod(name = "updateArtifactVersion",desc = "修改插件版本")
    @ApiParam(name = "artifactVersion",desc = "artifactVersion",required = true)
    public Result<Void> updateArtifactVersion(@RequestBody @NotNull @Valid ArtifactVersion artifactVersion){
        artifactVersionService.updateArtifactVersion(artifactVersion);

        return Result.ok();
    }

    @RequestMapping(path="/deleteArtifactVersion",method = RequestMethod.POST)
    @ApiMethod(name = "deleteArtifactVersion",desc = "删除插件版本")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteArtifactVersion(@NotNull String id){
        artifactVersionService.deleteArtifactVersion(id);

        return Result.ok();
    }

    @RequestMapping(path="/findArtifactVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findArtifactVersion",desc = "通过id 查询插件版本")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ArtifactVersion> findArtifactVersion(@NotNull String id){
        ArtifactVersion artifactVersion = artifactVersionService.findArtifactVersion(id);

        return Result.ok(artifactVersion);
    }

    @RequestMapping(path="/findAllArtifactVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findAllArtifactVersion",desc = "查询所有插件版本")
    public Result<List<ArtifactVersion>> findAllArtifactVersion(){
        List<ArtifactVersion> artifactVersionList = artifactVersionService.findAllArtifactVersion();

        return Result.ok(artifactVersionList);
    }

    @RequestMapping(path = "/findArtifactVersionList",method = RequestMethod.POST)
    @ApiMethod(name = "findArtifactVersionList",desc = "通过条件查询插件版本")
    @ApiParam(name = "artifactVersionQuery",desc = "artifactVersionQuery",required = true)
    public Result<List<ArtifactVersion>> findArtifactVersionList(@RequestBody @Valid @NotNull ArtifactVersionQuery artifactVersionQuery){
        List<ArtifactVersion> artifactVersionList = artifactVersionService.findArtifactVersionList(artifactVersionQuery);

        return Result.ok(artifactVersionList);
    }

    @RequestMapping(path = "/findArtifactVersionPage",method = RequestMethod.POST)
    @ApiMethod(name = "findArtifactVersionPage",desc = "通过条件分页查询插件版本")
    @ApiParam(name = "artifactVersionQuery",desc = "artifactVersionQuery",required = true)
    public Result<Pagination<ArtifactVersion>> findArtifactVersionPage(@RequestBody @Valid @NotNull ArtifactVersionQuery artifactVersionQuery){
        Pagination<ArtifactVersion> pagination = artifactVersionService.findArtifactVersionPage(artifactVersionQuery);

        return Result.ok(pagination);
    }

}
