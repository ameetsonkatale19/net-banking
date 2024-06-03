package com.nationalbank.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nationalbank.model.Account;
import com.nationalbank.model.Transaction;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findBySourceAccount(Account account);
    List<Transaction> findByDestinationAccount(Account account);
    
    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount = :account OR t.destinationAccount = :account")
    List<Transaction> findAllByAccount(@Param("account") Account account);
    @Query("SELECT t FROM Transaction t WHERE (t.sourceAccount = :account OR t.destinationAccount = :account) AND t.transaction_date BETWEEN :startDate AND :endDate")
    List<Transaction> findAllByAccountAndDateRange(@Param("account") Account account, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
}
