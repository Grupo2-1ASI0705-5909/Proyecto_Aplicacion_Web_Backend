package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "criptomonedas")
@Data
public class Criptomoneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "criptoid")
    private Long criptoId;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Integer decimales = 8;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal precioUSD = BigDecimal.ZERO;

    // RELACIONES
    @OneToMany(mappedBy = "criptomoneda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallets = new ArrayList<>();

    @OneToMany(mappedBy = "criptomoneda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones = new ArrayList<>();
}