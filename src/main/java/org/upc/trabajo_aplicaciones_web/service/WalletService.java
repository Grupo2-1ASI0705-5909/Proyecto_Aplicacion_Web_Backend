package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CriptomonedaDTO;
import org.upc.trabajo_aplicaciones_web.dto.UsuarioDTO;
import org.upc.trabajo_aplicaciones_web.dto.WalletDTO;
import org.upc.trabajo_aplicaciones_web.model.Criptomoneda;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.model.Wallet;
import org.upc.trabajo_aplicaciones_web.repository.CriptomonedaRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;
import org.upc.trabajo_aplicaciones_web.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UsuarioRepository usuarioRepository;
    private final CriptomonedaRepository criptomonedaRepository;

    // ✅ MODIFICADO: Crear wallet con dirección automática y saldo 0
    public WalletDTO crear(WalletDTO walletDTO) {
        Usuario usuario = usuarioRepository.findById(walletDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Criptomoneda criptomoneda = criptomonedaRepository.findById(walletDTO.getCriptoId())
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));

        if (walletRepository.findByUsuarioUsuarioIdAndCriptomonedaCriptoId(
                walletDTO.getUsuarioId(), walletDTO.getCriptoId()).isPresent()) {
            throw new RuntimeException("El usuario ya tiene un wallet para esta criptomoneda");
        }

        Wallet wallet = new Wallet();
        wallet.setUsuario(usuario);
        wallet.setCriptomoneda(criptomoneda);

        // ✅ GENERAR DIRECCIÓN AUTOMÁTICAMENTE
        wallet.setDireccion(generarDireccion(criptomoneda));

        // ✅ SALDO SIEMPRE CERO AL CREAR
        wallet.setSaldo(BigDecimal.ZERO);

        wallet.setEstado(true);

        wallet = walletRepository.save(wallet);
        return convertirAWalletDTO(wallet);
    }

    // ✅ NUEVO MÉTODO: Generar dirección según red
    private String generarDireccion(Criptomoneda cripto) {
        String codigo = cripto.getCodigo().toUpperCase();
        String uuid = UUID.randomUUID().toString().replace("-", ""); // 32 chars

        switch (codigo) {
            case "BTC":
                // Bitcoin: 1 + 32 chars base58 (simulado)
                return "1" + uuid;
            case "ETH":
            case "USDT":
            case "USDC":
                // Ethereum: 0x + 40 chars hex
                // UUID tiene 32, necesitamos 8 más. Repetimos el inicio.
                return "0x" + uuid + uuid.substring(0, 8);
            case "BNB":
                // Binance: bnb + 39 chars
                // UUID tiene 32, necesitamos 7 más.
                return "bnb" + uuid + uuid.substring(0, 7);
            default:
                // Genérico: codigo + _ + uuid
                return cripto.getCodigo().toLowerCase() + "_" + uuid;
        }
    }

    public List<WalletDTO> obtenerTodos() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletDTO> walletDTOs = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletDTOs.add(convertirAWalletDTO(wallet));
        }
        return walletDTOs;
    }

    public WalletDTO obtenerPorId(Long id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        return convertirAWalletDTO(wallet);
    }

    public WalletDTO actualizar(Long id, WalletDTO walletDTO) {
        Wallet walletExistente = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));

        walletExistente.setDireccion(walletDTO.getDireccion());
        walletExistente.setSaldo(walletDTO.getSaldo());
        walletExistente.setEstado(walletDTO.getEstado());

        walletExistente = walletRepository.save(walletExistente);
        return convertirAWalletDTO(walletExistente);
    }

    // ✅ MODIFICADO: Validar saldo antes de eliminar
    public void eliminar(Long id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));

        // ✅ VALIDAR SALDO CERO
        if (wallet.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException(
                    "No se puede eliminar una wallet con saldo positivo. Saldo actual: " + wallet.getSaldo());
        }

        walletRepository.deleteById(id);
    }

    public WalletDTO cambiarEstado(Long id, Boolean estado) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        wallet.setEstado(estado);
        wallet = walletRepository.save(wallet);
        return convertirAWalletDTO(wallet);
    }

    public List<WalletDTO> obtenerPorUsuario(Long usuarioId) {
        List<Wallet> wallets = walletRepository.findByUsuarioUsuarioId(usuarioId);
        List<WalletDTO> walletDTOs = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletDTOs.add(convertirAWalletDTO(wallet));
        }
        return walletDTOs;
    }

    public List<WalletDTO> obtenerPorCriptomoneda(Long criptoId) {
        List<Wallet> wallets = walletRepository.findByCriptomonedaCriptoId(criptoId);
        List<WalletDTO> walletDTOs = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletDTOs.add(convertirAWalletDTO(wallet));
        }
        return walletDTOs;
    }

    public WalletDTO obtenerPorUsuarioYCripto(Long usuarioId, Long criptoId) {
        Wallet wallet = walletRepository.findByUsuarioUsuarioIdAndCriptomonedaCriptoId(usuarioId, criptoId)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        return convertirAWalletDTO(wallet);
    }

    public WalletDTO actualizarSaldo(Long id, BigDecimal nuevoSaldo) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        wallet.setSaldo(nuevoSaldo);
        wallet = walletRepository.save(wallet);
        return convertirAWalletDTO(wallet);
    }

    public BigDecimal obtenerSaldoTotalUsuario(Long usuarioId) {
        BigDecimal saldoTotal = walletRepository.calcularSaldoTotalPorUsuario(usuarioId);
        return saldoTotal != null ? saldoTotal : BigDecimal.ZERO;
    }

    // ✅ NUEVO: Calcular patrimonio en USD
    public BigDecimal calcularPatrimonioUSD(Long usuarioId) {
        List<Wallet> wallets = walletRepository.findByUsuarioUsuarioId(usuarioId);

        BigDecimal patrimonioTotal = BigDecimal.ZERO;

        for (Wallet wallet : wallets) {
            BigDecimal saldoCripto = wallet.getSaldo();
            BigDecimal precioUSD = wallet.getCriptomoneda().getPrecioUSD();
            BigDecimal valorUSD = saldoCripto.multiply(precioUSD);
            patrimonioTotal = patrimonioTotal.add(valorUSD);
        }

        return patrimonioTotal;
    }

    public List<WalletDTO> obtenerWalletsConSaldoMayorA(BigDecimal saldoMinimo) {
        List<Wallet> wallets = walletRepository.findWalletsConSaldoMayorA(saldoMinimo);
        List<WalletDTO> walletDTOs = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletDTOs.add(convertirAWalletDTO(wallet));
        }
        return walletDTOs;
    }

    private WalletDTO convertirAWalletDTO(Wallet wallet) {
        WalletDTO dto = new WalletDTO();
        dto.setWalletId(wallet.getWalletId());
        dto.setUsuarioId(wallet.getUsuario().getUsuarioId());
        dto.setCriptoId(wallet.getCriptomoneda().getCriptoId());
        dto.setDireccion(wallet.getDireccion());
        dto.setSaldo(wallet.getSaldo());
        dto.setEstado(wallet.getEstado());
        dto.setUltimaActualizacion(wallet.getUltimaActualizacion());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuarioId(wallet.getUsuario().getUsuarioId());
        usuarioDTO.setNombre(wallet.getUsuario().getNombre());
        usuarioDTO.setApellido(wallet.getUsuario().getApellido());
        usuarioDTO.setEmail(wallet.getUsuario().getEmail());
        dto.setUsuario(usuarioDTO);

        CriptomonedaDTO criptoDTO = new CriptomonedaDTO();
        criptoDTO.setCriptoId(wallet.getCriptomoneda().getCriptoId());
        criptoDTO.setCodigo(wallet.getCriptomoneda().getCodigo());
        criptoDTO.setNombre(wallet.getCriptomoneda().getNombre());
        criptoDTO.setDecimales(wallet.getCriptomoneda().getDecimales());
        criptoDTO.setActiva(wallet.getCriptomoneda().getActiva());
        criptoDTO.setPrecioUSD(wallet.getCriptomoneda().getPrecioUSD()); // ✅ AGREGADO
        dto.setCriptomoneda(criptoDTO);

        return dto;
    }
}