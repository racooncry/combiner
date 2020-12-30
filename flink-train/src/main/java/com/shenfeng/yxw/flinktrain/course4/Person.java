package com.shenfeng.yxw.flinktrain.course4;

/**
 * @Author yangxw
 * @Date 2020-12-30 下午2:20
 * @Description
 * @Version 1.0
 */
public class Person {
    private String name;
    private int age;
    private String word;

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", word='" + word + '\'' +
                '}';
    }

    public Person(String name, int age, String word) {
        this.name = name;
        this.age = age;
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
