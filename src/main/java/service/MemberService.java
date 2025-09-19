package service;

import dto.MemberDto;
import entity.Member;

import java.util.List;

public interface MemberService {
    Member createMember(MemberDto member);
    Member getMemberById(Long id);
    List<Member> getAllMembers();
    Member updateMember(Long id, MemberDto member);
    void deleteMember(Long id);
}
