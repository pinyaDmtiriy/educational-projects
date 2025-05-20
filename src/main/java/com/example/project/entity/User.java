package com.example.project.entity;

import com.example.project.enumName.StatusName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private FirstEmails firstEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusName status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name = "user_role",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());
    }

    public User(Long id, String username, String password, FirstEmails firstEmail, StatusName status, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstEmail = firstEmail;
        this.status = status;
        this.roles = roles;
    }

    public User(String username, String password, FirstEmails firstEmail) {
        this.username = username;
        this.password = password;
        this.firstEmail = firstEmail;
    }

    public User(String username, String password, StatusName status, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    public void addProfile(Profile profile) {
        if(this.profile != null) {
         this.profile.setUser(null);
        }
        this.profile = profile;
        if(profile != null) {
            profile.setUser(this);
        }
    }

    public void addFirstEmail(FirstEmails firstEmail) {
        if(this.firstEmail != null) {
            this.firstEmail.setUser(null);
        }
        this.firstEmail = firstEmail;
        if(firstEmail != null) {
            firstEmail.setUser(this);
        }
    }

}
