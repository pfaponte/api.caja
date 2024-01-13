package ec.edu.espam.api.caja.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client extends Person {

    @Column(name = "password")
    @NotEmpty(message = "Password is required")
    @Size(min = 4, message = "Password must have at least 4 characters")
    private String password;

    @Column(name = "state")
    @NotNull(message = "State is required")
    private Boolean state;

    @OneToMany(mappedBy = "client")
    private List<Account> accounts;
}
