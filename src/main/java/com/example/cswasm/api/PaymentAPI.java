package com.example.cswasm.api;


import com.example.cswasm.DTO.ErrorDTO;
import com.example.cswasm.DTO.PaymentAccountDTO;
import com.example.cswasm.DTO.PaymentDTO;
import com.example.cswasm.DTO.PaymentPartnerDTO;
import com.example.cswasm.entity.Account;
import com.example.cswasm.entity.Partner;
import com.example.cswasm.service.AccountService;
import com.example.cswasm.service.PartnerService;
import com.example.cswasm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentAPI {
    private final AccountService accountService;
    private final PartnerService partnerService;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> payment(@Valid @RequestBody PaymentDTO paymentDTO) {
        if(!accountService.isAccountNumberExisted(paymentDTO.getAccountNumber())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Số tài khoản thụ hưởng không hợp lệ");
            return ResponseEntity.ok(errorDTO);
        }
        Account accountPayment = accountService.get(paymentDTO.getAccountPaymentNumber());
        if(accountPayment == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Số tài khoản thanh toán không hợp lệ");
            return ResponseEntity.ok(errorDTO);
        }
        if(accountPayment.getAccountNumber().equals(paymentDTO.getAccountNumber())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Không thể chuyển tiền dến chính tài khoản này");
            return ResponseEntity.ok(errorDTO);
        }
        if(!accountPayment.getPinNumber().equals(paymentDTO.getAccountPaymentPIN())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Mã PIN không hợp lệ");
            return ResponseEntity.ok(errorDTO);
        }
        transactionService.create(paymentDTO);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/partner")
    public ResponseEntity<?> findByPartner(@Valid @RequestBody PaymentPartnerDTO paymentPartnerDTO) {
        Partner partner = partnerService.get(paymentPartnerDTO.getAccountNumber());
        if(partner == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Tài khoản không tồn tại");
            return ResponseEntity.ok(errorDTO);
        }
        if(!partner.getPassword().equals(paymentPartnerDTO.getPassword())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Mật khẩu không đúng");
            return ResponseEntity.ok(errorDTO);
        }
        List<Account> accounts = accountService.accounts(partner);
        List<String> accountNumbers = new ArrayList<>();
        accounts.forEach(account -> {
            accountNumbers.add(account.getAccountNumber());
        });
        return ResponseEntity.ok(
            transactionService.findByToAccountNumbers(
                accountNumbers,
                paymentPartnerDTO.getStartDate(),
                paymentPartnerDTO.getEndDate()
            )
        );
    }

    @PostMapping("/account")
    public ResponseEntity<?> findByAccount(@Valid @RequestBody PaymentAccountDTO paymentAccountDTO) {
        Account account = accountService.get(paymentAccountDTO.getAccountNumber());
        if(account == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Tài khoản không tồn tại");
            return ResponseEntity.ok(errorDTO);
        }
        if(!account.getPinNumber().equals(paymentAccountDTO.getPinNumber())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Mật PIN không đúng");
            return ResponseEntity.ok(errorDTO);
        }
        return ResponseEntity.ok(
            transactionService.findByFromAccountNumber(
                account.getAccountNumber(),
                paymentAccountDTO.getStartDate(),
                paymentAccountDTO.getEndDate()
            )
        );
    }
}
