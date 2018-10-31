package io.github.zhaodj.util;

public class Log {

    public static void print(){
        print("");
    }

    public static void print(String msg){
        StackTraceElement element = new Throwable().getStackTrace()[1];
        System.out.println(element.getClassName() + "#" + element.getMethodName() + " " + msg);
    }

}
