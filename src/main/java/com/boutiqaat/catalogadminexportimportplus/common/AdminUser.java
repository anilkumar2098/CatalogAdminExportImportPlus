package com.boutiqaat.catalogadminexportimportplus.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;


@Entity
@Table(name = "admin_user")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@NamedEntityGraph(name = "idAndEmailGraph", attributeNodes = {
        @NamedAttributeNode("id"), @NamedAttributeNode("email")})
public class AdminUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "extra")
    private String extra;

    @Column(name = "failures_num")
    private short failuresNum;

    @Column(name = "first_failure")
    private Timestamp firstFailure;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "interface_locale")
    private String interfaceLocale;

    @Column(name = "is_active")
    private short isActive;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "lock_expires")
    private Timestamp lockExpires;

    @Column(name = "logdate")
    private Timestamp logDate;

    @Column(name = "lognum")
    private int logNum;

    @Column(name = "modified")
    private Timestamp modified;

    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "reload_acl_flag")
    private short reloadAclFlag;

    @Lob
    @Column(name = "rp_token")
    private String rpToken;

    @Column(name = "rp_token_created_at")
    private Timestamp rpTokenCreatedAt;

    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
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

