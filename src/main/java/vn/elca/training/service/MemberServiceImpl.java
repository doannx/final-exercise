package vn.elca.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import vn.elca.training.dao.IEmployeeRepository;
import vn.elca.training.dom.Employee;

@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private IEmployeeRepository repository;

    @Override
    public List<Employee> findAll() {
        return this.repository.findAll(new Sort(Direction.ASC, "firstName"));
    }
}
