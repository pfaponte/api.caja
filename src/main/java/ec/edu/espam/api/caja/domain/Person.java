package ec.edu.espam.api.caja.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dni")
    @NotEmpty(message = "Dni is required")
    private String dni;

    @Column(name = "name")
    @NotEmpty(message = "Name is required")
    private String name;

    @Column(name = "gender")
    @NotEmpty(message = "Gender is required")
    private String gender;

    @Column(name = "address")
    @NotEmpty(message = "address is required")
    private String address;

    @Column(name = "phone")
    @NotEmpty(message = "Phone is required")
    private String phone;

    @Column(name = "age")
    @NotNull(message = "Age is required")
    private Integer age;
}
