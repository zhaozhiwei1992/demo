package com.example.aop;

/**
 * 使用原生AspectJ实现创建裁判, 并且可以将spring bean 注入
 */
public aspect Umpire {
    public Umpire(){}

    pointcut runPoint(): execution (* com.example.domain..*.run());

    afterReturning(): runPoint(){
        System.out.println("我是通过原生aspectj实现的");
    }

    private Rabbit rabbit;

    public void setRabbit(Rabbit rabbit){
        this.rabbit = rabbit;
    }
}
