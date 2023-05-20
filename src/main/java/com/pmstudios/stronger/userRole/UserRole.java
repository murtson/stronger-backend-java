package com.pmstudios.stronger.userRole;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "roles")
@RequiredArgsConstructor
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;

}
