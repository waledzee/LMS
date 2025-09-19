package service.impl;

import dto.BorrowingTransactionDto;
import entity.*;
import lombok.RequiredArgsConstructor;
import mapper.BorrowingTransactionMapper;
import org.springframework.stereotype.Service;
import repository.*;
import service.BorrowingTransactionService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingTransactionServiceImpl implements BorrowingTransactionService {

    private final BorrowingTransactionRepository transactionRepository;
    private final BorrowingTransactionMapper borrowingTransactionMapper;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Override
    public BorrowingTransaction createTransaction(BorrowingTransactionDto dto) {
        // Validate book exists
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        // Validate member exists
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        
        // Check if book is available
        if (!isBookAvailable(book.getBookId())) {
            throw new RuntimeException("Book is not available for borrowing");
        }
        
        // Check member borrowing limit (max 5 books)
        long activeBorrowings = transactionRepository.countByMemberAndStatus(member, BorrowingTransaction.Status.BORROWED);
        if (activeBorrowings >= 5) {
            throw new RuntimeException("Member has reached maximum borrowing limit (5 books)");
        }
        
        BorrowingTransaction transaction = BorrowingTransaction.builder()
                .book(book)
                .member(member)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14)) // 14 days borrowing period
                .status(BorrowingTransaction.Status.BORROWED)
                .build();
        
        return transactionRepository.save(transaction);
    }

    @Override
    public BorrowingTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public List<BorrowingTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public BorrowingTransaction updateTransaction(Long id, BorrowingTransactionDto dto) {
        BorrowingTransaction existing = getTransactionById(id);
        
        if (dto.getBorrowDate() != null) existing.setBorrowDate(dto.getBorrowDate());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        if (dto.getReturnDate() != null) existing.setReturnDate(dto.getReturnDate());
        
        return transactionRepository.save(existing);
    }

    @Override
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    @Override
    public BorrowingTransaction returnBook(Long transactionId) {
        BorrowingTransaction transaction = getTransactionById(transactionId);
        
        if (transaction.getStatus() == BorrowingTransaction.Status.RETURNED) {
            throw new RuntimeException("Book has already been returned");
        }
        
        transaction.setReturnDate(LocalDate.now());
        
        // Check if book is overdue
        if (transaction.getReturnDate().isAfter(transaction.getDueDate())) {
            transaction.setStatus(BorrowingTransaction.Status.LATE);
        } else {
            transaction.setStatus(BorrowingTransaction.Status.RETURNED);
        }
        
        return transactionRepository.save(transaction);
    }

    @Override
    public List<BorrowingTransaction> getOverdueBooks() {
        return transactionRepository.findByStatusAndDueDateBefore(
                BorrowingTransaction.Status.BORROWED, 
                LocalDate.now()
        );
    }

    @Override
    public List<BorrowingTransaction> getMemberBorrowingHistory(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return transactionRepository.findByMemberOrderByBorrowDateDesc(member);
    }

    @Override
    public boolean isBookAvailable(Long bookId) {
        return transactionRepository.countByBookBookIdAndStatus(bookId, BorrowingTransaction.Status.BORROWED) == 0;
    }
}
