package net.tiklab.oms.plug.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.rpc.annotation.Reference;
import net.tiklab.updownload.artifact.model.Artifact;
import net.tiklab.updownload.artifact.model.ArtifactQuery;
import net.tiklab.updownload.artifact.service.ArtifactService;
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
 * ArtifactController
 */
@RestController
@RequestMapping("/artifact")
@Api(name = "ArtifactController",desc = "插件管理")
public class ArtifactController {

    private static Logger logger = LoggerFactory.getLogger(ArtifactController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private ArtifactService artifactService;

    @RequestMapping(path="/createArtifact",method = RequestMethod.POST)
    @ApiMethod(name = "createArtifact",desc = "创建插件")
    @ApiParam(name = "artifact",desc = "artifact",required = true)
    public Result<String> createArtifact(@RequestBody @NotNull @Valid Artifact artifact){
        String id = artifactService.createArtifact(artifact);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateArtifact",method = RequestMethod.POST)
    @ApiMethod(name = "updateArtifact",desc = "修改插件")
    @ApiParam(name = "artifact",desc = "artifact",required = true)
    public Result<Void> updateArtifact(@RequestBody @NotNull @Valid Artifact artifact){
        artifactService.updateArtifact(artifact);

        return Result.ok();
    }

    @RequestMapping(path="/deleteArtifact",method = RequestMethod.POST)
    @ApiMethod(name = "deleteArtifact",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteArtifact(@NotNull String id){
        artifactService.deleteArtifact(id);

        return Result.ok();
    }

    @RequestMapping(path="/findArtifact",method = RequestMethod.POST)
    @ApiMethod(name = "findArtifact",desc = "通过id 查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Artifact> findArtifact(@NotNull String id){
        Artifact artifact = artifactService.findArtifact(id);

        return Result.ok(artifact);
    }

    @RequestMapping(path="/findAllArtifact",method = RequestMethod.POST)
    @ApiMethod(name = "findAllArtifact",desc = "查询所有查询")
    public Result<List<Artifact>> findAllArtifact(){
        List<Artifact> artifactList = artifactService.findAllArtifact();

        return Result.ok(artifactList);
    }

    @RequestMapping(path = "/findArtifactList",method = RequestMethod.POST)
    @ApiMethod(name = "findArtifactList",desc = "通过条件查询")
    @ApiParam(name = "artifactQuery",desc = "artifactQuery",required = true)
    public Result<List<Artifact>> findArtifactList(@RequestBody @Valid @NotNull ArtifactQuery artifactQuery){
        List<Artifact> artifactList = artifactService.findArtifactList(artifactQuery);

        return Result.ok(artifactList);
    }

    @RequestMapping(path = "/findArtifactPage",method = RequestMethod.POST)
    @ApiMethod(name = "findArtifactPage",desc = "通过条件分页查询")
    @ApiParam(name = "artifactQuery",desc = "artifactQuery",required = true)
    public Result<Pagination<Artifact>> findArtifactPage(@RequestBody @Valid @NotNull ArtifactQuery artifactQuery){
        Pagination<Artifact> pagination = artifactService.findArtifactPage(artifactQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findNewArtifactPage",method = RequestMethod.POST)
    @ApiMethod(name = "findNewArtifactPage",desc = "通过条件分页查询最新的版本 没有版本的插件不查询出来")
    @ApiParam(name = "artifactQuery",desc = "artifactQuery",required = true)
    public Result<Pagination<Artifact>> findNewArtifactPage(@RequestBody @Valid @NotNull ArtifactQuery artifactQuery){
        Pagination<Artifact> pagination = artifactService.findNewArtifactPage(artifactQuery);

        return Result.ok(pagination);
    }

}
