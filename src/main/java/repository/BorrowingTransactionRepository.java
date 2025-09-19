package repository;

import entity.BorrowingTransaction;
import entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByStatusAndDueDateBefore(BorrowingTransaction.Status status, LocalDate date);
    List<BorrowingTransaction> findByMemberOrderByBorrowDateDesc(Member member);
    long countByMemberAndStatus(Member member, BorrowingTransaction.Status status);
    long countByBookBookIdAndStatus(Long bookId, BorrowingTransaction.Status status);
}
