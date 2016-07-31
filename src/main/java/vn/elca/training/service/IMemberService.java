package vn.elca.training.service;

import java.util.List;

import vn.elca.training.dom.Member;
import vn.elca.training.model.MemberVO;

public interface IMemberService {
    List<Member> findAll();
    List<MemberVO> convert(List<Member> lst);
}
