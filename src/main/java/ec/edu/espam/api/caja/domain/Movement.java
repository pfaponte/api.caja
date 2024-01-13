package ec.edu.espam.api.caja.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "movement")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date")
    @NotNull(message = "Date is required")
    private LocalDate fecha;

    @Column(name = "tipo")
    @NotNull(message = "Type Movement is required")
    @Enumerated(EnumType.STRING)
    private Type tipo;

    @Column(name = "amount")
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @Column(name = "balance")
    @NotNull(message = "Balance is required")
    private BigDecimal balance;

    @NotNull(message = "Account is required")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public enum Type {
        DEBIT, CREDIT;
    }
}
