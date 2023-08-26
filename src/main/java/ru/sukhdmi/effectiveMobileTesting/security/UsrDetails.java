package ru.sukhdmi.effectiveMobileTesting.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sukhdmi.effectiveMobileTesting.models.User;

import java.util.Collection;
import java.util.Collections;

public class UsrDetails implements UserDetails {
    private final User user;

    public UsrDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//аккаунт действителен
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//аккаунт не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//пароль не просроччен
    }

    @Override
    public boolean isEnabled() {
        return true;//аккаунт доступен
    }//класс-обертка которая предоставляет детали о пользователе

    public User getUser() {
        return this.user;//нужно чтобы получать данные аутентифицированного пользователя
    }
}
