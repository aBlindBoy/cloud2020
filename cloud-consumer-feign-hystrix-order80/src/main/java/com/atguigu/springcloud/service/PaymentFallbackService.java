package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {


    @Override
    public String paymentInfo_OK( Integer id ) {
        return "paymentInfo_OK：目标服务实现异常了呜呜呜";
    }

    @Override
    public String paymentInfo_TimeOut( Integer id ) {
        return "paymentInfo_TimeOut：目标服务实现异常了呜呜呜";
    }
}
 
 