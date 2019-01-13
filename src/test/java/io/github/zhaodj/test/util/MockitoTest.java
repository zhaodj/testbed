package io.github.zhaodj.test.util;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    public void testVerifyBehaviour(){
        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void testStub(){
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
        when(mockedList.get(0)).thenReturn("First");

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        //System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
        verify(mockedList).get(0);
    }

    @Test
    public void testArgumentMatchers(){
        List<String> mockedList = (List<String>)mock(List.class);

        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        when(mockedList.contains(argThat(isValid()))).thenReturn(true);

        when(mockedList.set(anyInt(), eq("element new"))).thenReturn("element");

        //following prints "element"
        System.out.println(mockedList.get(999));

        mockedList.add("element added");

        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        //argument matchers can also be written as Java 8 Lambdas
        verify(mockedList).add(argThat(someString -> someString.length() > 5));

    }

    private ArgumentMatcher<String> isValid(){
        return new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.length() > 5;
            }
        };
    }

    @Test
    public void testArgumentCaptor(){
        Boo mock = mock(Boo.class);
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);

        new Foo().foo(mock);

        verify(mock).doSomething(argument.capture());
        Assert.assertEquals("John", argument.getValue().getName());
    }

    public static class Foo {
        public void foo(Boo other) {
            Person person = new Person("John");
            other.doSomething(person);
        }
    }

    public static class Boo{

        public void doSomething(Person person){
            System.out.println(person.getName());
        }

    }
    public static class Person{
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



}
