package com.nationalbank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nationalbank.model.Account;
import com.nationalbank.model.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByAccountNumber(String accountNumber);
	
	Optional<Account> findByUserId(Long userId);
	
}
