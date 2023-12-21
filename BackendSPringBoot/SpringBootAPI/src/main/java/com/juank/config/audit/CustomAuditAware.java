package com.juank.config.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class CustomAuditAware implements AuditorAware<String>{

	 @Override
    public Optional<String> getCurrentAuditor() {
   
	 return Optional.of("JuanK");
	 }

}
