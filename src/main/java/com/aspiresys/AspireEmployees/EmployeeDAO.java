package com.aspiresys.AspireEmployees;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

        public List<Employee> findAll() {
            List<Employee> list = new ArrayList<Employee>();
            Connection c = null;
            String sql = "SELECT * FROM Employees.Employee2";
            try {
                c = DBConnection.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql);
                while (rs.next()) {
                    list.add(processRow(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                DBConnection.close(c);
            }
            return list;
        }


        public Employee findById(String id) {
            String sql = "SELECT * FROM Employee2 WHERE EmployeeId = ?";
            Employee Employee = null;
            Connection c = null;
            try {
                c = DBConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Employee = processRow(rs);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                DBConnection.close(c);
            }
            return Employee;
        }

        public Employee save(Employee Employee) {
            return Employee.getEmployeeId()!=null ? insert(Employee) : insert(Employee);
        }

        public Employee insert(Employee employee) {
            System.out.println(employee.getEmployeeId());
            Connection c = null;
            PreparedStatement ps = null;
            try {
                c = DBConnection.getConnection();
                ps = c.prepareStatement(
                        "INSERT INTO Employee2 (EmployeeId, EmployeeName, Dob,age,PhoneNumber,Email,Doj)\n" +
                                "VALUES (?, ?, ?, ?, ?, ?, ?);",
                        new String[] { "EmployeeId" });

                ps.setString(1, employee.getEmployeeId());
                ps.setString(2, employee.getEmployeeName());
                ps.setString(3, employee.getDateOfBirth());
                ps.setInt(4,   employee.getAge());
                ps.setString(5, employee.getPhoneNumber());
                ps.setString(6, employee.getEmail());
                ps.setString(7, employee.getDateOfJoining());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                while(rs.next()){
                String id = rs.getString(0);
                    employee.setEmployeeId(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                DBConnection.close(c);
            }
            return employee;
        }

        public Employee update(Employee employee) {
            Connection c = null;
            try {
                c = DBConnection.getConnection();
                PreparedStatement ps = c
                        .prepareStatement("UPDATE Employee2 SET EmployeeName=?, Dob=?, age=?, PhoneNumber=?, Email=?, Doj=?  WHERE EmployeeId=?");
                ps.setString(1, employee.getEmployeeName());
                ps.setString(2, employee.getDateOfBirth());
                ps.setInt(3,    employee.getAge());
                ps.setString(4, employee.getPhoneNumber());
                ps.setString(5, employee.getEmail());
                ps.setString(6, employee.getDateOfJoining());
                ps.setString(7, employee.getEmployeeId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("EmployeeDAO update exception");
                throw new RuntimeException(e);
            } finally {
                DBConnection.close(c);
            }
            return employee;
        }

        public boolean remove(String id) {
            Connection c = null;
            try {
                c = DBConnection.getConnection();
                PreparedStatement ps = c
                        .prepareStatement("DELETE FROM Employee2 WHERE EmployeeId=?");
                ps.setString(1, id);
                int count = ps.executeUpdate();
                return count == 1;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                DBConnection.close(c);
            }
        }

        protected Employee processRow(ResultSet rs) throws SQLException {
            Employee Employee = new Employee();
            Employee.setEmployeeId(rs.getString("EmployeeId"));
            Employee.setEmployeeName(rs.getString("EmployeeName"));
            Employee.setDateOfBirth(rs.getString("Dob"));
            Employee.setAge(rs.getInt("age"));
            Employee.setPhoneNumber(rs.getString("PhoneNumber"));
            Employee.setEmail(rs.getString("Email"));
            Employee.setDateOfJoining(rs.getString("Doj"));
            return Employee;
        }
    }