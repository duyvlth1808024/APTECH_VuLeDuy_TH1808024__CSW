package com.example.cswasm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
public class Partner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(unique = true)
    @Getter
    private String accountNumber;
    @Getter
    private String password;

    @OneToMany(mappedBy = "partner", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JsonIgnore
    private Set<Account> accounts;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
}
