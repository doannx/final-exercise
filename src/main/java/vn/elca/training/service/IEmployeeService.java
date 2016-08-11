package vn.elca.training.service;

import java.util.List;

import vn.elca.training.dom.Employee;

public interface IEmployeeService {
    List<Employee> findAll();

    Employee getById(Long id);
}
