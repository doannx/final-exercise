package vn.elca.training.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.elca.training.dao.IMemberRepository;
import vn.elca.training.dom.Member;

@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberRepository repository;

    @Override
    public List<Member> findAll() {
        List<Member> lst = new ArrayList<Member>();
        Iterator<Member> iter = this.repository.findAll().iterator();
        while (iter.hasNext()) {
            lst.add(iter.next());
        }
        // return sorted list
        return this.sortByName(lst, "asc");
    }

    @Override
    public List<Member> convert(List<Member> lst) {
        List<Member> lstVo = new ArrayList<Member>();
        for (Member m : lst) {
            lstVo.add(new Member(m.getVisa(), m.getVisa() + ":" + m.getName()));
        }
        return lstVo;
    }

    public List<Member> sortByName(List<Member> lst, final String ordering) {
        lst.sort(new Comparator<Member>() {
            @Override
            public int compare(Member o1, Member o2) {
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
