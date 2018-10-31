package io.github.zhaodj.spring.lifecycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifecycleApp {

    public static void main(String[] args){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
    }

}
