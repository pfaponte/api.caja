package ec.edu.espam.api.caja.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number")
    @NotEmpty(message = "Number is required")
    private String number;

    @Column(name = "type")
    @NotNull(message = "Type account is required")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "initial_balance")
    @NotNull(message = "Initial Balance is required")
    private BigDecimal initialBalance;

    @Column(name = "amount")
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @Column(name = "state")
    @NotNull(message = "State is required")
    private Boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "account")
    private List<Movement> movements;

    public enum Type {
        SAVING, CURRENT
    }
}
