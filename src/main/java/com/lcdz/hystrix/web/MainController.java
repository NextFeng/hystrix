package com.lcdz.hystrix.web;

import com.lcdz.hystrix.tools.WebUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * @author LinFeng
 * @date 2019/11/20 21:36
 */


@RestController
@RequestMapping("/")
@Slf4j
public class MainController {

    //@HystrixCommand(fallbackMethod = "fallback")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//10个例子
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//错误率60
    },fallbackMethod = "fallback")
    @RequestMapping("/hys")
    public String hystrix(HttpServletRequest request){

        Map<String,String> map = WebUtils.getParameterMap(request);

        if("1".equals(map.get("code"))){
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("【正常输出】" + WebUtils.getDateTime());

        return "SUCCESS";

    }



    private String fallback(HttpServletRequest request){

        log.info("【服务容错】" + WebUtils.getDateTime());

        return "太拥挤了，请稍等";
    }

}
