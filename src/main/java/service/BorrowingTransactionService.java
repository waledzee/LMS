package service;

import dto.BorrowingTransactionDto;
import entity.BorrowingTransaction;

import java.util.List;

public interface BorrowingTransactionService {
    BorrowingTransaction createTransaction(BorrowingTransactionDto transaction);
    BorrowingTransaction getTransactionById(Long id);
    List<BorrowingTransaction> getAllTransactions();
    BorrowingTransaction updateTransaction(Long id, BorrowingTransactionDto transaction);
    void deleteTransaction(Long id);
    BorrowingTransaction returnBook(Long transactionId);
    List<BorrowingTransaction> getOverdueBooks();
    List<BorrowingTransaction> getMemberBorrowingHistory(Long memberId);
    boolean isBookAvailable(Long bookId);
}
