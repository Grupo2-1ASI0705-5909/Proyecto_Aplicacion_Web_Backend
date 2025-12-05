package org.upc.trabajo_aplicaciones_web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * DTO para mapear la respuesta de la API de CoinGecko
 * Endpoint: /simple/price
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinGeckoPriceResponse {

    /**
     * Modelo interno para representar los datos de precio de una criptomoneda
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CryptoPriceData {
        @JsonProperty("usd")
        private BigDecimal usd;

        @JsonProperty("usd_24h_change")
        private BigDecimal usd24hChange;

        @JsonProperty("last_updated_at")
        private Long lastUpdatedAt;
    }

    // El response de CoinGecko es un Map<String, Map<String, Object>>
    // Ejemplo: {"bitcoin": {"usd": 45000.00, "usd_24h_change": 2.5}}
    private Map<String, Map<String, Object>> data;

    /**
     * Obtiene el precio en USD para una criptomoneda específica
     */
    public BigDecimal getPriceUsd(String cryptoId) {
        if (data != null && data.containsKey(cryptoId)) {
            Map<String, Object> priceData = data.get(cryptoId);
            if (priceData.containsKey("usd")) {
                Object price = priceData.get("usd");
                if (price instanceof Number) {
                    return new BigDecimal(price.toString());
                }
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * Obtiene el cambio de 24h para una criptomoneda específica
     */
    public BigDecimal getChange24h(String cryptoId) {
        if (data != null && data.containsKey(cryptoId)) {
            Map<String, Object> priceData = data.get(cryptoId);
            if (priceData.containsKey("usd_24h_change")) {
                Object change = priceData.get("usd_24h_change");
                if (change instanceof Number) {
                    return new BigDecimal(change.toString());
                }
            }
        }
        return BigDecimal.ZERO;
    }
}
