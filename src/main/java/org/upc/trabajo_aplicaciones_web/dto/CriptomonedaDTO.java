package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CriptomonedaDTO {
    private Long criptoId;
    private String codigo;
    private String nombre;
    private Integer decimales = 8;
    private Boolean activa = true;
    private BigDecimal precioUSD = BigDecimal.ZERO;

    public CriptomonedaDTO() {
    }

    public CriptomonedaDTO(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
}