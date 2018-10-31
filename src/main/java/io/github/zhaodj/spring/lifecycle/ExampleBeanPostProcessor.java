package io.github.zhaodj.spring.lifecycle;

import io.github.zhaodj.util.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class ExampleBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Log.print("before initialization: " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Log.print("after initialization: " + beanName);
		return bean;
	}

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
