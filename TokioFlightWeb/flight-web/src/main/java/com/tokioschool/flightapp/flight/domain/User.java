package com.tokioschool.flightapp.flight.domain;

import com.tokioschool.flightapp.flight.generated.tsId.TSId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @TSId
    private String id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;

    @Column
    @CurrentTimestamp
    private LocalDateTime created;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column
    private boolean active;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="users_with_roles",
    joinColumns =  {@JoinColumn(name="user_id")},
    inverseJoinColumns = { @JoinColumn(name="role_id")})
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private Set<FlightBooking> flightBookings;

}