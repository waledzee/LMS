package controller;

import dto.BorrowingTransactionDto;
import entity.BorrowingTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import service.BorrowingTransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BorrowingTransactionController {

    private final BorrowingTransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<BorrowingTransaction> createTransaction(@RequestBody BorrowingTransactionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<BorrowingTransaction> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<BorrowingTransaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<BorrowingTransaction> updateTransaction(@PathVariable Long id, @RequestBody BorrowingTransactionDto dto) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<BorrowingTransaction> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.returnBook(id));
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<List<BorrowingTransaction>> getOverdueBooks() {
        return ResponseEntity.ok(transactionService.getOverdueBooks());
    }

    @GetMapping("/member/{memberId}/history")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<BorrowingTransaction>> getMemberBorrowingHistory(@PathVariable Long memberId) {
        return ResponseEntity.ok(transactionService.getMemberBorrowingHistory(memberId));
    }

    @GetMapping("/book/{bookId}/availability")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<Boolean> checkBookAvailability(@PathVariable Long bookId) {
        return ResponseEntity.ok(transactionService.isBookAvailable(bookId));
    }
}

