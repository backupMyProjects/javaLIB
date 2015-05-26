/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javalib.test;

/**
 * 
 * @author leo created at 2015/1/28 上午 12:48:34
 */
class Animal<Type> {
    private Type age;
    private Type weight;
     
    public Animal(Type a, Type w) {
        setAge(a);
        setWeight(w);
    }
     
    public Type getAge() {
        return age;
    }
 
    public void setAge(Type n) {
        System.out.println(n.getClass().getName());
        age = n;
    }
     
    public Type getWeight() {
        return weight;
    }
     
    public void setWeight(Type n) {
        System.out.println(n.getClass().getName());
        weight = n;
    }
     
    public void speak() {
        System.out.println("哈囉，我已經" + getAge() + "歲，有" + getWeight() + "公斤重");
    }
}
 
class GenericDemo {
    public static void main(String[] args) {
        Animal puppy1, puppy2; 
        puppy1 = new Animal(6, "70");
        puppy2 = new Animal("八十八", "五千");
         
        puppy1.speak();
        puppy2.speak();
    }
}
