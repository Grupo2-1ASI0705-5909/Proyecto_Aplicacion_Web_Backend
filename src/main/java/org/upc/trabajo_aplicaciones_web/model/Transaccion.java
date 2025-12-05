package org.upc.trabajo_aplicaciones_web.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transacciones")
@Data
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaccionid")
    private Long transaccionId;

    @ManyToOne
    @JoinColumn(name = "usuarioid", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "comercioid", nullable = true)
    private Comercio comercio;

    @ManyToOne
    @JoinColumn(name = "metodopagoid", nullable = false)
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "criptoid")
    private Criptomoneda criptomoneda;

    @Column(length = 10, name = "codigomoneda")
    private String codigoMoneda;

    @Column(precision = 18, scale = 2, name = "montototalfiat")
    private BigDecimal montoTotalFiat;

    @Column(precision = 18, scale = 8, name = "montototalcripto")
    private BigDecimal montoTotalCripto;

    @Column(precision = 28, scale = 12, name = "tasaaplicada")
    private BigDecimal tasaAplicada;

    @Column(length = 100, name = "txhash")
    private String txHash;

    @ManyToOne
    @JoinColumn(name = "tipocambioid")
    private TipoCambio tipoCambio;

    @Column(nullable = false, name = "fechatransaccion")
    private LocalDateTime fechaTransaccion = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    private String estado;

    // RELACIONES
    @JsonManagedReference
    @OneToMany(mappedBy = "transaccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanPago> planesPago = new ArrayList<>();
}