package com.tiklab.oms.doc.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.doc.document.model.Document;
import com.tiklab.doc.document.model.DocumentQuery;
import com.tiklab.doc.document.service.DocumentService;
import com.tiklab.rpc.annotation.Reference;
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
 * DocumentController
 */
@RestController
@RequestMapping("/document")
@Api(name = "DocumentController",desc = "文档管理")
public class DocumentController {

    private static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private DocumentService documentService;

    @RequestMapping(path="/createDocument",method = RequestMethod.POST)
    @ApiMethod(name = "createDocument",desc = "创建")
    @ApiParam(name = "document",desc = "document",required = true)
    public Result<String> createDocument(@RequestBody @NotNull @Valid Document document){
        String id = documentService.createDocument(document);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateDocument",method = RequestMethod.POST)
    @ApiMethod(name = "updateDocument",desc = "修改")
    @ApiParam(name = "document",desc = "document",required = true)
    public Result<Void> updateDocument(@RequestBody @NotNull @Valid Document document){
        documentService.updateDocument(document);

        return Result.ok();
    }

    @RequestMapping(path="/deleteDocument",method = RequestMethod.POST)
    @ApiMethod(name = "deleteDocument",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteDocument(@NotNull String id){
        documentService.deleteDocument(id);

        return Result.ok();
    }

    @RequestMapping(path="/findDocument",method = RequestMethod.POST)
    @ApiMethod(name = "findDocument",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Document> findDocument(@NotNull String id){
        Document document = documentService.findDocument(id);

        return Result.ok(document);
    }

    @RequestMapping(path="/findAllDocument",method = RequestMethod.POST)
    @ApiMethod(name = "findAllDocument",desc = "查询所有")
    public Result<List<Document>> findAllDocument(){
        List<Document> documentList = documentService.findAllDocument();

        return Result.ok(documentList);
    }

    @RequestMapping(path = "/findDocumentList",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentList",desc = "通过条件查询")
    @ApiParam(name = "documentQuery",desc = "documentQuery",required = true)
    public Result<List<Document>> findDocumentList(@RequestBody @Valid @NotNull DocumentQuery documentQuery){
        List<Document> documentList = documentService.findDocumentList(documentQuery);

        return Result.ok(documentList);
    }

    @RequestMapping(path = "/findDocumentPage",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentPage",desc = "通过条件分页查询")
    @ApiParam(name = "documentQuery",desc = "documentQuery",required = true)
    public Result<Pagination<Document>> findDocumentPage(@RequestBody @Valid @NotNull DocumentQuery documentQuery){
        Pagination<Document> pagination = documentService.findDocumentPage(documentQuery);

        return Result.ok(pagination);
    }

}
