package com.ajava8.space.patterns;

public class PrototypeDemo {
    public static void main(String[] args) {
        Circle originalCircle = new Circle(10, "red");
        Circle clonedCircle = (Circle) originalCircle.clone();

        Rectangle originalRectangle = new Rectangle(20, 30, "blue");
        Rectangle clonedRectangle = (Rectangle) originalRectangle.clone();

        // Draw original and cloned shapes
        originalCircle.draw();
        clonedCircle.draw();
        originalRectangle.draw();
        clonedRectangle.draw();

        System.out.println("After Modification of cloned objects");
        // Modify cloned shapes if necessary
         clonedCircle.setColor("Black");
         clonedRectangle.setColor("Black");

         originalCircle.draw();
         clonedCircle.draw();
         originalRectangle.draw();
         clonedRectangle.draw();
    }
}
