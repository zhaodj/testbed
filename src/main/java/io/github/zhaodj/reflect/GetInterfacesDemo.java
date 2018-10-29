package io.github.zhaodj.reflect;

import java.lang.reflect.Type;

public class GetInterfacesDemo {

    public interface Fly {
        void fly();
    }

    public interface Run {
        void run();
    }

    public interface Bird extends Fly {
        void say();
    }

    public static class Swallow implements Bird, Run {

        @Override
        public void fly() {

        }

        @Override
        public void run() {

        }

        @Override
        public void say() {

        }

    }

    public static void main(String[] args){
        for(Class<?> clazz :Swallow.class.getInterfaces()){
            System.out.println(clazz.getName());
        }
        System.out.println("-------------------");
        for(Type type :Swallow.class.getGenericInterfaces()){
            System.out.println(type.getTypeName());
        }
    }

}
