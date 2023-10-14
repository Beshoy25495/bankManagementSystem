package com.bwagih.bank.management.system.entity;

import com.bwagih.bank.management.system.commons.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "cash_account")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cash_account_id", nullable = false, unique = true)
    private Long cashAccountId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status")
    private String status;

    private String currency;

    private BigDecimal balance;

    private BigDecimal blockAmount;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private Account account;

}
