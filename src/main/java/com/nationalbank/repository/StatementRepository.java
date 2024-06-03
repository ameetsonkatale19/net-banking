package com.nationalbank.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nationalbank.model.Statement;


@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

	Optional<Statement> findById(Long accountId);

	List<Statement> findByAccountIdAndDateBetween(Long accountId, LocalDate startDate, LocalDate endDate);
}
