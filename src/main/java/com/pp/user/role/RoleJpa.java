package com.pp.user.role;


import com.pp.database.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="role")
public class RoleJpa extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column
    private String name;

    public String getName() {return name;}

    public Role toDomain() { return Role.valueOf(name); }

    public static Set<Role> toDomain(Set<RoleJpa> jpas) {
        return jpas.stream()
                .map(jpa -> jpa.toDomain())
                .collect(Collectors.toSet());
    }

}
