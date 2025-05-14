
package OOExamples;

// Abstraction
abstract class Animal {
    abstract void makeSound();

    void eat() {
        System.out.println("This animal eats food");
    }
}

class Dog extends Animal {
    void makeSound() {
        System.out.println("Bark");
    }
}

//public class Main {
//    public static void main(String[] args) {
//        Animal myDog = new Dog();
//        myDog.makeSound();  
//        myDog.eat();       
//    }
//}


// Inner Class

public class OuterClass {
    private String outerField = "I am outer class";

    class InnerClass {
        void display() {
            System.out.println("Access Inner Class: " + outerField);
        }
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();

        OuterClass.InnerClass inner = outer.new InnerClass();

        inner.display();
    }
}
