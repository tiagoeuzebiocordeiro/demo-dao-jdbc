package application;

import model.entities.Department;
import model.entities.Seller;
import java.util.Date;

public class Program {
    public static void main(String[] args) {

        Department obj = new Department(1, "Books");
        System.out.println(obj);
        Seller obj2 = new Seller(1, "Tiago", "tiago@mail.com",new Date(), 12000.00, obj);
        System.out.println(obj2);
    }
}
