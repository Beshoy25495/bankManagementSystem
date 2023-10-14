package com.bwagih.bank.management.system.mapper;

import com.bwagih.bank.management.system.entity.Client;
import com.bwagih.bank.management.system.entity.ServiceAgreement;
import com.bwagih.bank.management.system.entity.Users;
import org.mapstruct.*;

import java.util.Objects;

public interface UserAgreementMapper {

    Users mapAgreementToUser(ServiceAgreement agreement);


    @AfterMapping
    default void mapClientToUser(@MappingTarget Users users, ServiceAgreement agreement) {

        users.setFirstName(getClient(agreement).getFirstName());
        users.setLastName(getClient(agreement).getLastName());
        users.setEmail(getClient(agreement).getEmail());
        users.addRoles(agreement.getRoles());
        users.setStatus("A");
    }

    private static Client getClient(ServiceAgreement agreement) {
        return Objects.nonNull(agreement.getClient()) ? agreement.getClient() : new Client();
    }

}
