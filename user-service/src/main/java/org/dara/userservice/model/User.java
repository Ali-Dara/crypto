package org.dara.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private UUID userUUID;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private LocalDate birthDate;
    @Column(nullable = false, updatable = false)
    private LocalDate registerDate;

    public User(UUID userUUID){
        registerDate = LocalDate.now();
    }
}
