package com.system.banking.repo;

import com.system.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long accountNumber);

    Account findByCustomerId(Long customerId);
}
