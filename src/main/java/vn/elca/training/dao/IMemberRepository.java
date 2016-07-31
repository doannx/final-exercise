package vn.elca.training.dao;

import org.springframework.data.repository.CrudRepository;

import vn.elca.training.dom.Member;

public interface IMemberRepository extends CrudRepository<Member, String>{

}
