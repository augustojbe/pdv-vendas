package io.github.pdv.sistema.pdv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "Campo nome e obrigatório.")
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    @NotBlank(message = "O campo username é obrigatório")
    private String username;

    @Column(length = 60, nullable = false)
    @NotBlank(message = "O campo senha é obrigatório")
    private String password;

    private boolean isEnabled;

    @OneToMany(mappedBy = "user")
    private List<Sale> sales;

}
