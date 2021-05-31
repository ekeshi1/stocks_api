package com.pp.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SystemAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication==null || !authentication.isAuthenticated()){
                return Optional.of(Constants.DEFAULT_AUDITOR);
            }

            var principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                return Optional.of(((UserDetails) principal).getUsername());
            }

            if( principal instanceof String){
                return  Optional.of((String) principal);
            }

            return Optional.of(Constants.DEFAULT_AUDITOR);
        }
}
