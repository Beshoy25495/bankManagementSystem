package com.bwagih.bank.management.system.entity;

import com.bwagih.bank.management.system.commons.BaseEntity;
import com.bwagih.bank.management.system.validator.PasswordsEqualConstraint;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "service_agreement")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@PasswordsEqualConstraint
public class ServiceAgreement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id", nullable = false, unique = true)
    private Long agreementId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Transient
    private String confirmPassword;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    //    @JsonIgnore
    @JsonManagedReference
    @JoinTable(name = "service_role", joinColumns = {
            @JoinColumn(name = "agreement_id", referencedColumnName = "agreement_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    @ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Role> roles;


}
