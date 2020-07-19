package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger ( 0 );

    //获取下一个请求数的值
    private final int getAndIncrement() {
        int current;
        int next;
        //自旋锁+CAS，直到比较成功并设置数据才返回
        for (; ; ) {
            current = this.atomicInteger.get ();
            next = current + 1;
            if (atomicInteger.compareAndSet ( current, next )) {
                System.out.println ( "**访问数:" + next );
                return next;
            }
        }
    }

    @Override
    public ServiceInstance instance( List<ServiceInstance> serviceInstances ) {
        //请求数
        int andIncrement = getAndIncrement ();
        //服务器集群数量
        int size = serviceInstances.size ();
        //服务器下标=请求数%服务器集群数量
        int index = andIncrement % size;
        ServiceInstance serviceInstance = serviceInstances.get ( index );
        return serviceInstance;
    }
}
