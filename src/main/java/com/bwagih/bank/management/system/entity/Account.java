package com.bwagih.bank.management.system.entity;

import com.bwagih.bank.management.system.commons.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "client_account")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public Account(Long accountNumber) {
        super();
        this.accountNumber = accountNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number", nullable = false, unique = true)
    private Long accountNumber;

    private String currency;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status")
    private String status;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @JsonManagedReference
    @OneToMany(mappedBy = "account", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<CashAccount> cashAccounts = new HashSet<>();

    @Formula(value = "(SELECT sum(c.balance) FROM public.cash_account c where account_number = c.account_number)")
    private BigDecimal totalBalance;

    @Formula(value = "(SELECT sum(c.block_amount) FROM public.cash_account c where account_number = c.account_number)")
    private BigDecimal totalBlockAmount;

}
