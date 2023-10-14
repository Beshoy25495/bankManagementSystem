package com.bwagih.bank.management.system.entity;

import com.bwagih.bank.management.system.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "transaction")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_number", nullable = false, unique = true)
    private Long transactionNumber;

    private String type;

    private Date executionDate;

    private BigDecimal amount;

    @ManyToOne(cascade = {CascadeType.DETACH , CascadeType.REFRESH , CascadeType.PERSIST , CascadeType.MERGE})
    @JoinColumn(name = "cash_account_id", referencedColumnName = "cash_account_id")
    private CashAccount cashAccount;
}


 //TODO create 2 event handler after create trans -> add in blockAmount ,
// TODO after agreement approved create user new
//TODO add scheduler execute for execution date EOD