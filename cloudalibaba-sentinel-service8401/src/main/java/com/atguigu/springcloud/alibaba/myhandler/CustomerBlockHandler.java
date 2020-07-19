package com.atguigu.springcloud.alibaba.myhandler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.*;

import java.util.ArrayList;

public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(2020, "自定义限流处理信息....handlerException");
    }
}
 
