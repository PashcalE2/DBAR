package main.isbd.sequrity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ActorDetails implements UserDetails {

    private final String actorName;
    private final String actorJwt;
    private final List<GrantedAuthority> authorities;


    public ActorDetails(String actorName, String actorJwt, List<String> roles) {
        this.actorName = actorName;
        this.actorJwt = actorJwt;
        this.authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.actorJwt;
    }

    @Override
    public String getUsername() {
        return this.actorName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
