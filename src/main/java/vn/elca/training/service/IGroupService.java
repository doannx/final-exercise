package vn.elca.training.service;

import java.util.List;

import vn.elca.training.dom.Department;

public interface IGroupService {
    List<Department> findAll();
    Department getById(Long id);
}
