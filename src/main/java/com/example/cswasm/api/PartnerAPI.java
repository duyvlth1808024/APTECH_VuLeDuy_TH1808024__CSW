package com.example.cswasm.api;

import com.example.cswasm.DTO.ConnectBankingDTO;
import com.example.cswasm.DTO.ErrorDTO;
import com.example.cswasm.entity.Account;
import com.example.cswasm.entity.Partner;
import com.example.cswasm.service.AccountService;
import com.example.cswasm.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/partner")
@Slf4j
@RequiredArgsConstructor
public class PartnerAPI {
    private final PartnerService partnerService;
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Partner partner) {
        if(partnerService.isAccountNumberExisted(partner.getAccountNumber())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Số tài khoản này đã tồn tại. Vui lòng nhập số khác");
            return ResponseEntity.ok(errorDTO);
        }
        Partner newPartner = partnerService.create(partner);
        return ResponseEntity.ok(newPartner);
    }

    @PostMapping("/connect-banking")
    public ResponseEntity<?> connectBanking(@Valid @RequestBody ConnectBankingDTO connectBankingDTO) {
        final Partner partner = partnerService.get(connectBankingDTO.getPartnerAccountNumber());
        if(partner == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Số tài khoản nhận thanh toán này không tồn tại!");
            return ResponseEntity.ok(errorDTO);
        }
        if(!partner.getPassword().equals(connectBankingDTO.getPartnerAccountPassword())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Mật khẩu tài khoản nhận thanh toán không đúng!");
            return ResponseEntity.ok(errorDTO);
        }
        if(
            connectBankingDTO.getStep() == null
            || connectBankingDTO.getStep() == 1
        ) {
            return ResponseEntity.ok().body(null);
        }
        Account account = accountService.get(connectBankingDTO.getAccountNumber());
        if(account == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Số tài khoản ngân hàng này không tồn tại!");
            return ResponseEntity.ok(errorDTO);
        }
        if(!account.getPinNumber().equals(connectBankingDTO.getAccountPINNumber())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Mã PIN tài khoản ngân hàng không đúng!");
            return ResponseEntity.ok(errorDTO);
        }
        if(account.getPartner() != null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError("Tài khoản này đã được kết nối với 1 tài khoản nhận thanh toán!");
            return ResponseEntity.ok(errorDTO);
        }
        accountService.createConnect(account, partner);
        return ResponseEntity.ok().body(null);
    }
}
