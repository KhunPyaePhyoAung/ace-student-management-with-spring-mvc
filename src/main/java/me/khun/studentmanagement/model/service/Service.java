package me.khun.studentmanagement.model.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface Service {
	default Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	default boolean hasAnyAuthority(String...auths) {
		return getAuthentication()
				.getAuthorities()
				.stream()
				.anyMatch(a -> {
					for (var au : auths) {
						if (a.getAuthority().equals(au)) {
							return true;
						}
					}
					return false;
				});
	}
}
