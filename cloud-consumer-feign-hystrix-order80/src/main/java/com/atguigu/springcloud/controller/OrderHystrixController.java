package com.atguigu.springcloud.controller;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand
    public String paymentInfo_TimeOut( @PathVariable("id") Integer id ) {
        String result = paymentHystrixService.paymentInfo_TimeOut ( id );
        return result;
    }

    //下面是全局fallback方法
    public String payment_Global_FallbackMethod() {
        return "Global异常处理信息，请稍后再试,(┬＿┬)";
    }


    //兜底方法
    public String paymentTimeOutFallbackMethod( Integer id ) {
        return "我是消费者80，对付支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,(┬＿┬)";
    }


    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK( @PathVariable("id") Integer id ) {
        String result = paymentHystrixService.paymentInfo_OK ( id );
        log.info ( "*******result:" + result );
        return result;
    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败率达到多少后跳闸
    })
    @GetMapping("/consumer/payment/paymentCircuitBreaker/{id}")
    public String paymentCircuitBreaker( @PathVariable("id") Integer id ) {
        if (id < 0) {
            throw new RuntimeException ( "*****id 不能负数" );
        }
        String serialNumber = IdUtil.simpleUUID ();

        return Thread.currentThread ().getName () + "\t" + "调用成功,流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback( @PathVariable("id") Integer id ) {
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " + id;
    }


}
