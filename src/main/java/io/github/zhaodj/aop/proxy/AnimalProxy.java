package io.github.zhaodj.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AnimalProxy implements InvocationHandler {

    private Animal animal;

    public AnimalProxy(Animal animal) {
        this.animal = animal;
    }

    public Animal create(){
        return (Animal)Proxy.newProxyInstance(animal.getClass().getClassLoader(), animal.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            System.out.println(proxy.getClass().getName());
            System.out.println("before say");
            return method.invoke(animal, args);
        } finally {
            System.out.println("after say");
        }
    }

    public static class TimeHandler implements InvocationHandler{

        private Object target;

        public TimeHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long start = System.currentTimeMillis();
            try {
                System.out.println(proxy.getClass().getName());
                return method.invoke(target, args);
            } finally {
                long cost = System.currentTimeMillis() - start;
                System.out.println("cost " + cost + "ms");
            }
        }
    }

    public static void main(String[] args){
        Cat cat = new Cat();
        Animal animal = new AnimalProxy(cat).create();
        animal = (Animal) Proxy.newProxyInstance(animal.getClass().getClassLoader(), animal.getClass().getInterfaces(), new TimeHandler(animal));
        animal.say();
    }

}
