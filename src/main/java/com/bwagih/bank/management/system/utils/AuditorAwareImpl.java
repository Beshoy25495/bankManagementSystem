package com.bwagih.bank.management.system.utils;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		
		// should get userName from spring security
		return Optional.of("test user");
	}

}
