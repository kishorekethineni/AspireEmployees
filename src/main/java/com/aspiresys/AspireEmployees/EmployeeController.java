package com.aspiresys.AspireEmployees;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EmployeeController {

  @GetMapping(value = "/employees",
          produces = {MediaType.APPLICATION_JSON_VALUE})
  public String getAllEmployees(@RequestParam(required = false) String id) {
    String result;
      try {
        Connection connection=DBConnection.getConnection();
        EmployeeDAO employeeDAO=new EmployeeDAO();
        List<Employee> employees;
        if (id==null) {
          employees = employeeDAO.findAll();
          result=new Gson().toJson(employees);
        }else{
          result=new Gson().toJson(employeeDAO.findById(id));
        }
      } catch (Exception e) {
        e.printStackTrace();
        result="{\"message\":\""+e.getMessage()+"\"}";
      }
      return result;
  }


  @PostMapping(value = "/employees",
          consumes = {MediaType.APPLICATION_JSON_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE})
  public String createEmployee(@RequestBody Employee employee) {
    String result;
    try {
      DBConnection.getConnection();
      EmployeeDAO employeeDAO=new EmployeeDAO();
      if (employee!=null) {
        employeeDAO.save(employee);
        result="{\"message\":\"Success\",\"employeeId\":\""+employee.getEmployeeId()+"\"}";
      }else{
        result="{\"message\":\"Data is needed\"}";
      }
    } catch (Exception e) {
      e.printStackTrace();
      result="{\"message\":\""+e.getMessage()+"\"}";
    }
    return result;
  }

  @PutMapping(value = "/updateEmployees/{EmployeeId}",
          consumes = {MediaType.APPLICATION_JSON_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE})
  public String updateEmployee(@PathVariable("EmployeeId") String id, @RequestBody Employee employee) {
    String result;
    try {
      DBConnection.getConnection();
      EmployeeDAO employeeDAO=new EmployeeDAO();
      if (employee!=null) {
        employeeDAO.update(employee);
        result="{\"message\":\"Updated\",\"employeeId\":\""+employee.getEmployeeId()+"\"}";
      }else{
        result="{\"message\":\"Data is needed\"}";
      }
    } catch (Exception e) {
      e.printStackTrace();
      result="{\"message\":\""+e.getMessage()+"\"}";
    }
   return result;
  }

  @DeleteMapping(value = "/deleteEmployee/{EmployeeId}",
          consumes = {MediaType.APPLICATION_JSON_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE})
  public String deleteEmployee(@PathVariable("EmployeeId") String id) {
    String result;
    try {
      DBConnection.getConnection();
      EmployeeDAO employeeDAO=new EmployeeDAO();
      if (id!=null) {
        employeeDAO.remove(id);
        result="{\"message\":\"Removed\",\"employeeId\":\""+id+"\"}";
      }else{
        result="{\"message\":\"Data is needed\"}";
      }
    } catch (Exception e) {
      e.printStackTrace();
      result="{\"message\":\""+e.getMessage()+"\"}";
    }
    return result;
  }

  @DeleteMapping("/Employees")
  public String deleteAllEmployees() {
    return "";

  }
}