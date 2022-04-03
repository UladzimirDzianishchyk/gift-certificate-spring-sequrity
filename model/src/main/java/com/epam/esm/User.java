package com.epam.esm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "registration_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.ms")
    private LocalDateTime registrationDate = LocalDateTime.now();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("user")
    private Set<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return isActive == user.isActive && Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(registrationDate, user.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, isActive, registrationDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
