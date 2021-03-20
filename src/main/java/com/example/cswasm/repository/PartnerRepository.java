package com.example.cswasm.repository;

import com.example.cswasm.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Boolean existsByAccountNumber(String accountNumber);
    Partner findByAccountNumber(String accountNumber);
}
