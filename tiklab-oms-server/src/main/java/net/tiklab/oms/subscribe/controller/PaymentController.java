package  net.tiklab.oms.subscribe.controller;


import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.subscribe.payment.model.Payment;
import  net.tiklab.subscribe.payment.model.PaymentQuery;
import  net.tiklab.subscribe.payment.service.PaymentService;
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
 * PaymentController
 */
@RestController
@RequestMapping("/payment")
@Api(name = "PaymentController",desc = "支付管理表")
public class PaymentController {

    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private PaymentService paymentService;


    @RequestMapping(path="/createPayment",method = RequestMethod.POST)
    @ApiMethod(name = "createPayment",desc = "创建")
    @ApiParam(name = "payment",desc = "payment",required = true)
    public Result<String> createPayment(@RequestBody @NotNull @Valid Payment payment){
        String id = paymentService.createPayment(payment);

        return Result.ok(id);
    }

    @RequestMapping(path="/updatePayment",method = RequestMethod.POST)
    @ApiMethod(name = "updatePayment",desc = "修改")
    @ApiParam(name = "payment",desc = "payment",required = true)
    public Result<Void> updatePayment(@RequestBody @NotNull @Valid Payment payment){
        paymentService.updatePayment(payment);

        return Result.ok();
    }

    @RequestMapping(path="/deletePayment",method = RequestMethod.POST)
    @ApiMethod(name = "deletePayment",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deletePayment(@NotNull String id){
        paymentService.deletePayment(id);

        return Result.ok();
    }

    @RequestMapping(path="/findPayment",method = RequestMethod.POST)
    @ApiMethod(name = "findPayment",desc = "查询id")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Payment> findPayment(@NotNull String id){
        Payment payment = paymentService.findPayment(id);

        return Result.ok(payment);
    }

    @RequestMapping(path="/findAllPayment",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPayment",desc = "查询所有")
    public Result<List<Payment>> findAllPayment(){
        List<Payment> paymentList = paymentService.findAllPayment();

        return Result.ok(paymentList);
    }

    @RequestMapping(path = "/findPaymentList",method = RequestMethod.POST)
    @ApiMethod(name = "findPaymentList",desc = "通过条件查询")
    @ApiParam(name = "paymentQuery",desc = "paymentQuery",required = true)
    public Result<List<Payment>> findPaymentList(@RequestBody @Valid @NotNull PaymentQuery paymentQuery){
        List<Payment> paymentList = paymentService.findPaymentList(paymentQuery);

        return Result.ok(paymentList);
    }

    @RequestMapping(path = "/findPaymentPage",method = RequestMethod.POST)
    @ApiMethod(name = "findPaymentPage",desc = "通过条件分页查询")
    @ApiParam(name = "paymentQuery",desc = "paymentQuery",required = true)
    public Result<Pagination<Payment>> findPaymentPage(@RequestBody @Valid @NotNull PaymentQuery paymentQuery){
        Pagination<Payment> pagination = paymentService.findPaymentPage(paymentQuery);

        return Result.ok(pagination);
    }


    @RequestMapping(path = "/getPlatformCer",method = RequestMethod.POST)
    @ApiMethod(name = "getPlatformCer",desc = "获取微信平台证书")
    public Result<String> getPlatformCer(){
       String platformCer= paymentService.getPlatformCer();
        return Result.ok(platformCer);
    }

    @RequestMapping(path = "/verifyPublicTra",method = RequestMethod.POST)
    @ApiMethod(name = "verifyPublicTra",desc = "确认对公转账收款修改订单和支付记录方法")
    public Result<Void> verifyPublicTra(@RequestBody @NotNull @Valid Payment payment){
         paymentService.verifyPublicTra(payment);
        return Result.ok();
    }
}
