package vn.elca.training.service;

import java.util.List;

import vn.elca.training.dom.Group;

public interface IGroupService {
    List<Group> findAll();
    Group getById(Long id);
}
