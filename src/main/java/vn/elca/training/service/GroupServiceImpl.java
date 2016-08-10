package vn.elca.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.dom.Group;

@Service
public class GroupServiceImpl implements IGroupService {
    @Autowired
    private IGroupRepository repository;

    @Override
    public List<Group> findAll() {
        return this.repository.findAll(new Sort(Direction.ASC, "name"));
    }

    @Override
    public Group getById(Long id) {
        return this.repository.getOne(id);
    }

}
