package service.impl;


import dto.MemberDto;
import entity.Member;
import lombok.RequiredArgsConstructor;
import mapper.MemberMapper;
import org.springframework.stereotype.Service;
import service.MemberService;
import repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member createMember(MemberDto dto) {
        Member member=memberMapper.toEntity(dto);
        return memberRepository.save(member);
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member updateMember(Long id, MemberDto dto) {
        Member existing = getMemberById(id);

        if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());

        return memberRepository.save(existing);
    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}

