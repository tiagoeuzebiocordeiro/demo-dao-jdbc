package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import java.util.Date;

public class Program {
    public static void main(String[] args) {

        Department obj = new Department(1, "Books");
        System.out.println(obj);
        Seller obj2 = new Seller(1, "Tiago", "tiago@mail.com",new Date(), 12000.00, obj);
        System.out.println(obj2);

        /*My program doesn't know the impl, only knows the interface, this is a
        method to make a
        dependecy injection
         hiding the impl.*/
        SellerDao dao = DaoFactory.createSellerDao();
    }
}
