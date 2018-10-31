package io.github.zhaodj.spring.lifecycle;

import io.github.zhaodj.util.Log;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class CallbackBean {

    @PostConstruct
    public void init() {
        Log.print("init");
    }

    @PreDestroy
    public void destroy() {
        Log.print("destory");
    }

}
