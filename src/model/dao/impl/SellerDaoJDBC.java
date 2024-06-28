package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);

        }
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*, department.Name as DepName FROM " +
                    "seller INNER JOIN department ON " +
                    "seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));

                Seller sel = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"),
                        rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);

                return sel;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName FROM " +
                    "seller INNER JOIN department on " +
                    "seller.DepartmentId = department.Id " +
                    "ORDER BY department.Name");
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                // dep == null means it doesn't exists in map, so instantiate it
                if (dep == null) {
                    dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller sel = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"),
                        rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);
                list.add(sel);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName FROM " +
                    "seller INNER JOIN department on " +
                    "seller.DepartmentId = department.Id WHERE DepartmentId = ? " +
                    "ORDER BY department.Name");
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                // dep == null means it doesn't exists in map, so instantiate it
                if (dep == null) {
                    dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller sel = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"),
                        rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);
                list.add(sel);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
