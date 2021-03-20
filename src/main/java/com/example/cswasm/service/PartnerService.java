package com.example.cswasm.service;

import com.example.cswasm.entity.Partner;
import com.example.cswasm.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public boolean isAccountNumberExisted(String accountNumber) {
        return partnerRepository.existsByAccountNumber(accountNumber);
    }

    public Partner create(Partner partner) {
        return partnerRepository.save(partner);
    }

    public Partner get(String accountNumber) {
        return partnerRepository.findByAccountNumber(accountNumber);
    }
}
