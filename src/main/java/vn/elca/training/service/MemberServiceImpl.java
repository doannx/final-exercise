package vn.elca.training.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.elca.training.dao.IMemberRepository;
import vn.elca.training.dom.Employee;

@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberRepository repository;

    @Override
    public List<Employee> findAll() {
        List<Employee> lst = new ArrayList<Employee>();
        Iterator<Employee> iter = this.repository.findAll().iterator();
        while (iter.hasNext()) {
            lst.add(iter.next());
        }
        // return sorted list
        return this.sortByName(lst, "asc");
    }

    public List<Employee> sortByName(List<Employee> lst, final String ordering) {
        lst.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                if ("asc".equals(ordering)) {
                    return o1.getFirstName().compareTo(o2.getFirstName());
                } else {
                    return o2.getFirstName().compareTo(o1.getFirstName());
                }
            }
        });
        return lst;
    }
}
