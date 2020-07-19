package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value ( "${server.port}" )
    private String port;

    @RequestMapping("/create")
    public CommonResult<Integer> create( @RequestBody Payment payment ) {
        Integer result = paymentService.create ( payment );
        log.info ( "****更新行数****:" + result );
        if (result > 0) {
            return new CommonResult<Integer> ( 200, "添加成功", result );
        } else {
            return new CommonResult<Integer> ( 400, "添加失败", result );
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> get( @PathVariable("id") Long id ) {
        Payment payment = paymentService.getPaymentById ( id );
        if (payment != null) {
            return new CommonResult<Payment> ( 200, "查询成功,端口:" + port, payment );
        } else {
            return new CommonResult<Payment> ( 400, "查询失败", null );
        }
    }

}
