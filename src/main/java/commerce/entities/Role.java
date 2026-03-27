package commerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "roles")
@Setter
@Getter
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id;
    @Column(name = "role_name", unique = true, nullable = false, length = 50)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
