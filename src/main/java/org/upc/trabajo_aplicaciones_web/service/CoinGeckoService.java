package org.upc.trabajo_aplicaciones_web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upc.trabajo_aplicaciones_web.model.TipoCambio;
import org.upc.trabajo_aplicaciones_web.repository.TipoCambioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Servicio para integración con la API de CoinGecko
 * Actualiza automáticamente los precios de criptomonedas en la base de datos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CoinGeckoService {

    private final TipoCambioRepository tipoCambioRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${coingecko.api.url}")
    private String coinGeckoApiUrl;

    // Lista de las principales criptomonedas a monitorear
    private static final List<String> CRYPTO_IDS = Arrays.asList(
            "bitcoin",
            "ethereum",
            "tether",
            "binancecoin",
            "ripple",
            "cardano",
            "solana",
            "polkadot");

    // Mapeo de IDs de CoinGecko a códigos de criptomonedas
    private static final Map<String, String> CRYPTO_CODE_MAP = new HashMap<>() {
        {
            put("bitcoin", "BTC");
            put("ethereum", "ETH");
            put("tether", "USDT");
            put("binancecoin", "BNB");
            put("ripple", "XRP");
            put("cardano", "ADA");
            put("solana", "SOL");
            put("polkadot", "DOT");
        }
    };

    /**
     * Obtiene los precios actuales de múltiples criptomonedas desde CoinGecko
     * 
     * @param cryptoIds  Lista de IDs de criptomonedas (bitcoin, ethereum, etc.)
     * @param vsCurrency Moneda de referencia (usd, eur, etc.)
     * @return Map con los precios
     */
    public Map<String, Map<String, Object>> obtenerPreciosMultiples(List<String> cryptoIds, String vsCurrency) {
        try {
            String ids = String.join(",", cryptoIds);
            String url = String.format("%s/simple/price?ids=%s&vs_currencies=%s&include_24hr_change=true",
                    coinGeckoApiUrl, ids, vsCurrency);

            log.info("Consultando CoinGecko API: {}", url);

            ResponseEntity<Map<String, Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Map<String, Object>>>() {
                    });

            return response.getBody();
        } catch (Exception e) {
            log.error("Error al obtener precios de CoinGecko: {}", e.getMessage());
            return Collections.emptyMap();
        }
    }

    /**
     * Obtiene el precio de una sola criptomoneda
     */
    public BigDecimal obtenerPrecioCripto(String cryptoId, String vsCurrency) {
        Map<String, Map<String, Object>> precios = obtenerPreciosMultiples(
                Collections.singletonList(cryptoId), vsCurrency);

        if (precios != null && precios.containsKey(cryptoId)) {
            Map<String, Object> priceData = precios.get(cryptoId);
            Object price = priceData.get(vsCurrency);
            if (price instanceof Number) {
                return new BigDecimal(price.toString());
            }
        }

        return BigDecimal.ZERO;
    }

    /**
     * Actualiza la base de datos con los precios actuales de las criptomonedas
     * Este método se ejecuta automáticamente cada 15 minutos (configurable)
     */
    @Scheduled(fixedDelayString = "${coingecko.update.interval}")
    public void actualizarPreciosCriptomonedas() {
        log.info("Iniciando actualización automática de precios de criptomonedas...");

        try {
            Map<String, Map<String, Object>> precios = obtenerPreciosMultiples(CRYPTO_IDS, "usd");

            if (precios == null || precios.isEmpty()) {
                log.warn("No se obtuvieron precios de CoinGecko");
                return;
            }

            int actualizados = 0;
            for (String cryptoId : CRYPTO_IDS) {
                if (precios.containsKey(cryptoId)) {
                    String cryptoCode = CRYPTO_CODE_MAP.get(cryptoId);
                    Map<String, Object> priceData = precios.get(cryptoId);
                    Object priceObj = priceData.get("usd");

                    if (priceObj instanceof Number) {
                        BigDecimal precio = new BigDecimal(priceObj.toString());

                        // Crear nuevo registro de tipo de cambio
                        TipoCambio tipoCambio = new TipoCambio();
                        tipoCambio.setDesdeCodigo(cryptoCode);
                        tipoCambio.setHastaCodigo("USD");
                        tipoCambio.setTasa(precio);
                        tipoCambio.setFechaHora(LocalDateTime.now());
                        tipoCambio.setFuente("CoinGecko API");

                        tipoCambioRepository.save(tipoCambio);
                        actualizados++;

                        log.debug("Actualizado {} = {} USD", cryptoCode, precio);
                    }
                }
            }

            log.info("✅ Actualización completada: {} criptomonedas actualizadas", actualizados);

        } catch (Exception e) {
            log.error("❌ Error durante la actualización automática de precios: {}", e.getMessage(), e);
        }
    }

    /**
     * Permite ejecutar manualmente la actualización de precios
     */
    public void actualizarManualmente() {
        log.info("📊 Actualización manual solicitada");
        actualizarPreciosCriptomonedas();
    }

    /**
     * Obtiene el precio más reciente de una criptomoneda desde la base de datos
     */
    public BigDecimal obtenerPrecioDesdeDB(String cryptoCode) {
        Optional<TipoCambio> tipoCambio = tipoCambioRepository.findTasaMasReciente(cryptoCode, "USD");
        return tipoCambio.map(TipoCambio::getTasa).orElse(BigDecimal.ZERO);
    }
}
