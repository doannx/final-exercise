package vn.elca.training.service;

import java.util.List;

import vn.elca.training.dom.Employee;

public interface IMemberService {
    List<Employee> findAll();
}
