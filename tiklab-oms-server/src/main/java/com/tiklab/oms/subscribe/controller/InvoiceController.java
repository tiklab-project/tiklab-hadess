package com.tiklab.oms.subscribe.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.rpc.annotation.Reference;
import com.tiklab.subscribe.invoice.model.Invoice;
import com.tiklab.subscribe.invoice.model.InvoiceQuery;
import com.tiklab.subscribe.invoice.service.InvoiceService;
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
 * InvoiceController
 */
@RestController
@RequestMapping("/invoice")
@Api(name = "InvoiceController",desc = "发票")
public class InvoiceController {

    private static Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private InvoiceService invoiceService;

    @RequestMapping(path="/createInvoice",method = RequestMethod.POST)
    @ApiMethod(name = "createInvoice",desc = "创建发票")
    @ApiParam(name = "invoice",desc = "invoice",required = true)
    public Result<String> createInvoice(@RequestBody @NotNull @Valid Invoice invoice){
        String id = invoiceService.createInvoice(invoice);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateInvoice",method = RequestMethod.POST)
    @ApiMethod(name = "updateInvoice",desc = "修改发票")
    @ApiParam(name = "invoice",desc = "invoice",required = true)
    public Result<Void> updateInvoice(@RequestBody @NotNull @Valid Invoice invoice){
        invoiceService.updateInvoice(invoice);

        return Result.ok();
    }

    @RequestMapping(path="/deleteInvoice",method = RequestMethod.POST)
    @ApiMethod(name = "deleteInvoice",desc = "删除发票")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteInvoice(@NotNull String id){
        invoiceService.deleteInvoice(id);

        return Result.ok();
    }

    @RequestMapping(path="/findInvoice",method = RequestMethod.POST)
    @ApiMethod(name = "findInvoice",desc = "通过id查询发票")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Invoice> findInvoice(@NotNull String id){
        Invoice invoice = invoiceService.findInvoice(id);

        return Result.ok(invoice);
    }

    @RequestMapping(path="/findAllInvoice",method = RequestMethod.POST)
    @ApiMethod(name = "findAllInvoice",desc = "查询所有")
    public Result<List<Invoice>> findAllInvoice(){
        List<Invoice> invoiceList = invoiceService.findAllInvoice();

        return Result.ok(invoiceList);
    }

    @RequestMapping(path = "/findInvoiceList",method = RequestMethod.POST)
    @ApiMethod(name = "findInvoiceList",desc = "通过条件查询")
    @ApiParam(name = "invoiceQuery",desc = "invoiceQuery",required = true)
    public Result<List<Invoice>> findInvoiceList(@RequestBody @Valid @NotNull InvoiceQuery invoiceQuery){
        List<Invoice> invoiceList = invoiceService.findInvoiceList(invoiceQuery);

        return Result.ok(invoiceList);
    }

    @RequestMapping(path = "/findInvoicePage",method = RequestMethod.POST)
    @ApiMethod(name = "findInvoicePage",desc = "通过条件分页查询")
    @ApiParam(name = "invoiceQuery",desc = "invoiceQuery",required = true)
    public Result<Pagination<Invoice>> findInvoicePage(@RequestBody @Valid @NotNull InvoiceQuery invoiceQuery){
        Pagination<Invoice> pagination = invoiceService.findInvoicePage(invoiceQuery);

        return Result.ok(pagination);
    }
    @RequestMapping(path = "/findOpenInvoice",method = RequestMethod.POST)
    @ApiMethod(name = "findOpenInvoice",desc = "条件查询发票  已申请发票;已开发票")
    @ApiParam(name = "invoiceQuery",desc = "invoiceQuery",required = true)
    public Result<List<Invoice>> findOpenInvoice(@RequestBody @Valid @NotNull InvoiceQuery invoiceQuery){
        List<Invoice> InvoiceList = invoiceService.findOpenInvoice(invoiceQuery);

        return Result.ok(InvoiceList);
    }

    @RequestMapping(path = "/findOpenInvoicePage",method = RequestMethod.POST)
    @ApiMethod(name = "findOpenInvoicePage",desc = "条件分页查询发票  已申请发票;已开发票")
    @ApiParam(name = "invoiceQuery",desc = "invoiceQuery",required = true)
    public Result<Pagination<Invoice>> findOpenInvoicePage(@RequestBody @Valid @NotNull InvoiceQuery invoiceQuery){
        Pagination<Invoice> pagination = invoiceService.findOpenInvoicePage(invoiceQuery);

        return Result.ok(pagination);
    }

}
