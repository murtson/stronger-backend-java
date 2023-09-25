package com.pmstudios.stronger.token;

import com.pmstudios.stronger.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    // a user can only be logged in at one device
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean revoked;
    private boolean expired;

}
