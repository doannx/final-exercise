package vn.elca.training.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.dom.Department;

@Service
public class GroupServiceImpl implements IGroupService {
    @Autowired
    private IGroupRepository repository;

    @Override
    public List<Department> findAll() {
        List<Department> lst = new ArrayList<Department>();
        Iterator<Department> iter = this.repository.findAll().iterator();
        while (iter.hasNext()) {
            lst.add(iter.next());
        }
        // return sorted list
        return this.sortByName(lst, "asc");
    }
    
    @Override
    public Department getById(Long id) {
        return this.repository.getOne(id);
    }

    public List<Department> sortByName(List<Department> lst, final String ordering) {
        lst.sort(new Comparator<Department>() {
            @Override
            public int compare(Department o1, Department o2) {
                if ("asc".equals(ordering)) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return o2.getName().compareTo(o1.getName());
                }
            }
        });
        return lst;
    }

}
