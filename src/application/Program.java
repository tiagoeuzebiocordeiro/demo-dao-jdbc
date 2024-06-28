package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        /*My program doesn't know the impl, only knows the interface, this is a
        method to make a
        dependecy injection
         hiding the impl.*/
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("========================");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        System.out.println(list);

        System.out.println("====================");
        list = sellerDao.findAll();
        for (Seller s : list) {
            System.out.println(s);
        }
    }
}
