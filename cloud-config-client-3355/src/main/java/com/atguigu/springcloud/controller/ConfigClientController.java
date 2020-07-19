package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//实时监控配置中心的配置变化
@RefreshScope
public class ConfigClientController {

    @Value("${user.uname}")
    private String configInfo;

    @Value("${app.version}")
    private String version;


    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo+version;
    }
}
 
 
