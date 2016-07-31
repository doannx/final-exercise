package vn.elca.training.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.dom.Group;

@Service
public class GroupServiceImpl implements IGroupService {
    @Autowired
    private IGroupRepository repository;

    @Override
    public List<Group> findAll() {
        List<Group> lst = new ArrayList<Group>();
        Iterator<Group> iter = this.repository.findAll().iterator();
        while (iter.hasNext()) {
            lst.add(iter.next());
        }
        // return sorted list
        return this.sortByName(lst, "asc");
    }

    public List<Group> sortByName(List<Group> lst, final String ordering) {
        lst.sort(new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
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
