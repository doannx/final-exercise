package vn.elca.training.service;

import java.util.List;

import vn.elca.training.dom.Member;

public interface IMemberService {
    List<Member> findAll();

    List<Member> convert(List<Member> lst);
}
