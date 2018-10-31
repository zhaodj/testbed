package io.github.zhaodj.spring.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("io.github.zhaodj.spring.lifecycle")
public class Config {

    @Bean
    public LifecycleBean lifecycleBean() {
        return new LifecycleBean();
    }

    @Bean
    public CallbackBean callbackBean(){
        return new CallbackBean();
    }

}
