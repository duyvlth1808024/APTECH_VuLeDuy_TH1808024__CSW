package com.example.cswasm.api;

import com.example.cswasm.DTO.ErrorDTO;
import com.example.cswasm.DTO.PartnerAccountDTO;
import com.example.cswasm.entity.Account;
import com.example.cswasm.entity.Partner;
import com.example.cswasm.service.AccountService;
import com.example.cswasm.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@Slf4j
@RequiredArgsConstructor
public class AccountAPI {
    private final AccountService accountService;
    private final PartnerService partnerService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Account account) {
        if(accountService.isAccountNumberExisted(account.getAccountNumber())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Số tài khoản này đã tồn tại. Vui lòng nhập số khác");
            return ResponseEntity.ok(errorDTO);
        }
        Account newAccount = accountService.create(account);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping
    public ResponseEntity<?> getByPartner(@Valid @RequestBody Partner partner) {
        if(partner == null || partner.getAccountNumber() == null || partner.getPassword() == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Tài khoản không hợp lệ");
            return ResponseEntity.ok(errorDTO);
        }
        Partner partnerFound = partnerService.get(partner.getAccountNumber());
        if(partnerFound == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Tài khoản này không tồn tại");
            return ResponseEntity.ok(errorDTO);
        }
        if(!partnerFound.getPassword().equals(partner.getPassword())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Mật khẩu không đúng");
            return ResponseEntity.ok(errorDTO);
        }
        List<Account> accounts = new ArrayList<>();
        if(!accountService.accounts(partnerFound).isEmpty()) {
            accountService.accounts(partnerFound).forEach(account -> {
                account.setPinNumber(null);
                accounts.add(account);
            });
        }
        return ResponseEntity.ok(accounts);
    }
}
