package io.github.zhaodj.aop.aspectj;

public class App {

    public void say() {
        System.out.println("App say");
    }

    public static void main(String[] args) {
        App app = new App();
        app.say();
    }

}
