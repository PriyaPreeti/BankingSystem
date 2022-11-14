package com.system.banking.repo;

import com.system.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
  //  @Query("SELECT t FROM transaction t join account a on a.account_number=t.account_number WHERE t.account_number = :accountNumber")
//  @Query( "FROM transaction t WHERE t.account_number = :account_number")
    List<Transaction> findByAccount_id(Long account_number);
}
