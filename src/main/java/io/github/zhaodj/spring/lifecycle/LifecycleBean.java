package io.github.zhaodj.spring.lifecycle;

import io.github.zhaodj.util.Log;
import org.springframework.context.Lifecycle;

public class LifecycleBean implements Lifecycle {

    private boolean running = false;

    @Override
    public void start() {
        running = true;
        Log.print();
    }

    @Override
    public void stop() {
        running = false;
        Log.print();
    }

    @Override
    public boolean isRunning() {
        Log.print();
        return running;
    }

}
