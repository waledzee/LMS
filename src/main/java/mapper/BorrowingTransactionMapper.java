package mapper;

import dto.BorrowingTransactionDto;
import entity.BorrowingTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowingTransactionMapper {

    @Mapping(target = "transactionId", source = "id")
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "member", ignore = true)
    BorrowingTransaction toEntity(BorrowingTransactionDto dto);

    @Mapping(target = "id", source = "transactionId")
    @Mapping(target = "bookId", source = "book.bookId")
    @Mapping(target = "memberId", source = "member.memberId")
    BorrowingTransactionDto toDto(BorrowingTransaction entity);
}