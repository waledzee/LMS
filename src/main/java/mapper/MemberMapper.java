package mapper;


import dto.MemberDto;
import entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "memberId", source = "id")
    Member toEntity(MemberDto dto);

    @Mapping(target = "id", source = "memberId")
    MemberDto toDto(Member entity);
}

