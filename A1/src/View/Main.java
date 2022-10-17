/*
7. A collection of several objects is given
having shapes of cubes, spheres and cylinders.
To display the objects with the larger volume
than 25cm3.
 */
package View;

import Controller.Controller;
import Model.Cube;
import Model.Cylinder;
import Model.IObject;
import Model.Sphere;
import Repository.IRepository;
import Repository.Repository;

public class Main {
    public static void main(String[] args) {
        IRepository repository = new Repository(5);
        Controller controller = new Controller(repository);

        IObject o1 = new Cube(30);
        IObject o2 = new Cylinder(10);
        IObject o3 = new Sphere(50);
        IObject o4 = new Cube(5);
        IObject o5 = new Cylinder(60);

        controller.add(o1);
        controller.add(o2);
        controller.add(o3);
        controller.add(o4);

        controller.printAll();

        System.out.println("--------------------------------------------------------");
        controller.add(o5);
        controller.printAll();

        System.out.println("--------------------------------------------------------");
        controller.delete(o3);
        controller.printAll();
    }
}