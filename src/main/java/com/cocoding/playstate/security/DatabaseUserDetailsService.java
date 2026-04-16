package com.cocoding.playstate.security;

import com.cocoding.playstate.repository.UserAccountRepository;
import java.util.Locale;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

  private final UserAccountRepository userAccountRepository;

  public DatabaseUserDetailsService(UserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userAccountRepository
        .findByUsernameIgnoreCase(username.trim())
        .map(
            account -> {
              String principal = normalizedLibraryPrincipal(account.getEmail(), account.getUsername());
              return User.withUsername(principal)
                  .password(account.getPasswordHash())
                  .roles("USER")
                  .build();
            })
        .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
  }

  private static String normalizedLibraryPrincipal(String email, String username) {
    String candidate = email != null && !email.isBlank() ? email : username;
    return candidate == null ? "" : candidate.trim().toLowerCase(Locale.ROOT);
  }
}
