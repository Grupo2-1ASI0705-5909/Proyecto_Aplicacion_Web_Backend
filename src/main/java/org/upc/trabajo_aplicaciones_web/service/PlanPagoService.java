package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.PlanPagoDTO;
import org.upc.trabajo_aplicaciones_web.dto.TransaccionDTO;
import org.upc.trabajo_aplicaciones_web.model.Cuota;
import org.upc.trabajo_aplicaciones_web.model.PlanPago;
import org.upc.trabajo_aplicaciones_web.model.Transaccion;
import org.upc.trabajo_aplicaciones_web.repository.CuotaRepository;
import org.upc.trabajo_aplicaciones_web.repository.PlanPagoRepository;
import org.upc.trabajo_aplicaciones_web.repository.TransaccionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanPagoService {
    private final PlanPagoRepository planPagoRepository;
    private final TransaccionRepository transaccionRepository;
    private final CuotaRepository cuotaRepository; // NUEVO: Para crear cuotas

    @Transactional // IMPORTANTE: Asegurar transaccionalidad
    public PlanPagoDTO crear(PlanPagoDTO planPagoDTO) {
        Transaccion transaccion = transaccionRepository.findById(planPagoDTO.getTransaccionId())
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        PlanPago planPago = new PlanPago();
        planPago.setTransaccion(transaccion);
        planPago.setNumeroCuotas(planPagoDTO.getNumeroCuotas());
        planPago.setMontoPorCuota(planPagoDTO.getMontoPorCuota());
        planPago.setInteres(planPagoDTO.getInteres());
        planPago.setFechaInicio(planPagoDTO.getFechaInicio() != null ? planPagoDTO.getFechaInicio() : LocalDate.now());

        if (planPago.getFechaInicio() != null && planPago.getNumeroCuotas() != null) {
            planPago.setFechaFin(planPago.getFechaInicio().plusMonths(planPago.getNumeroCuotas()));
        }

        // Guardar primero el plan para obtener su ID
        planPago = planPagoRepository.save(planPago);

        // LÓGICA DE GENERACIÓN DE CUOTAS (NUEVO)
        BigDecimal montoPorCuota = planPago.getMontoPorCuota();
        LocalDate fechaVencimientoBase = planPago.getFechaInicio();

        List<Cuota> cuotasGeneradas = new ArrayList<>();
        for (int i = 1; i <= planPago.getNumeroCuotas(); i++) {
            Cuota cuota = new Cuota();
            cuota.setPlanPago(planPago);
            cuota.setNumeroCuota(i);
            cuota.setMonto(montoPorCuota);
            cuota.setFechaVencimiento(fechaVencimientoBase.plusMonths(i)); // Vence al mes siguiente
            cuota.setEstado("PENDIENTE");
            cuotasGeneradas.add(cuota);
        }

        // Guardar todas las cuotas generadas
        cuotaRepository.saveAll(cuotasGeneradas);

        return convertirAPlanPagoDTO(planPago);
    }

    public List<PlanPagoDTO> obtenerTodos() {
        List<PlanPago> planesPago = planPagoRepository.findAll();
        List<PlanPagoDTO> planPagoDTOs = new ArrayList<>();
        for (PlanPago plan : planesPago) {
            planPagoDTOs.add(convertirAPlanPagoDTO(plan));
        }
        return planPagoDTOs;
    }

    public PlanPagoDTO obtenerPorId(Long id) {
        PlanPago planPago = planPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de pago no encontrado"));
        return convertirAPlanPagoDTO(planPago);
    }

    public PlanPagoDTO actualizar(Long id, PlanPagoDTO planPagoDTO) {
        PlanPago planExistente = planPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de pago no encontrado"));

        planExistente.setNumeroCuotas(planPagoDTO.getNumeroCuotas());
        planExistente.setMontoPorCuota(planPagoDTO.getMontoPorCuota());
        planExistente.setInteres(planPagoDTO.getInteres());
        planExistente.setFechaInicio(planPagoDTO.getFechaInicio());
        planExistente.setFechaFin(planPagoDTO.getFechaFin());

        planExistente = planPagoRepository.save(planExistente);
        return convertirAPlanPagoDTO(planExistente);
    }

    public void eliminar(Long id) {
        if (!planPagoRepository.existsById(id)) {
            throw new RuntimeException("Plan de pago no encontrado");
        }
        planPagoRepository.deleteById(id);
    }

    public List<PlanPagoDTO> obtenerPorTransaccion(Long transaccionId) {
        List<PlanPago> planesPago = planPagoRepository.findByTransaccionTransaccionId(transaccionId);
        List<PlanPagoDTO> planPagoDTOs = new ArrayList<>();
        for (PlanPago plan : planesPago) {
            planPagoDTOs.add(convertirAPlanPagoDTO(plan));
        }
        return planPagoDTOs;
    }

    public List<PlanPagoDTO> obtenerPlanesActivos() {
        List<PlanPago> planesPago = planPagoRepository.findPlanesActivos(LocalDate.now());
        List<PlanPagoDTO> planPagoDTOs = new ArrayList<>();
        for (PlanPago plan : planesPago) {
            planPagoDTOs.add(convertirAPlanPagoDTO(plan));
        }
        return planPagoDTOs;
    }

    public List<PlanPagoDTO> obtenerPlanesVencidos() {
        List<PlanPago> planesPago = planPagoRepository.findPlanesVencidos(LocalDate.now());
        List<PlanPagoDTO> planPagoDTOs = new ArrayList<>();
        for (PlanPago plan : planesPago) {
            planPagoDTOs.add(convertirAPlanPagoDTO(plan));
        }
        return planPagoDTOs;
    }

    public List<PlanPagoDTO> obtenerPorUsuario(Long usuarioId) {
        List<PlanPago> planesPago = planPagoRepository.findByUsuarioId(usuarioId);
        List<PlanPagoDTO> planPagoDTOs = new ArrayList<>();
        for (PlanPago plan : planesPago) {
            planPagoDTOs.add(convertirAPlanPagoDTO(plan));
        }
        return planPagoDTOs;
    }

    public List<PlanPagoDTO> obtenerPlanesConCuotasPendientes() {
        List<PlanPago> planesPago = planPagoRepository.findPlanesConCuotasPendientes();
        List<PlanPagoDTO> planPagoDTOs = new ArrayList<>();
        for (PlanPago plan : planesPago) {
            planPagoDTOs.add(convertirAPlanPagoDTO(plan));
        }
        return planPagoDTOs;
    }

    public Double calcularInteresTotal() {
        return planPagoRepository.calcularInteresTotal();
    }

    private PlanPagoDTO convertirAPlanPagoDTO(PlanPago planPago) {
        PlanPagoDTO dto = new PlanPagoDTO();
        dto.setPlanPagoId(planPago.getPlanPagoId());
        dto.setTransaccionId(planPago.getTransaccion().getTransaccionId());
        dto.setNumeroCuotas(planPago.getNumeroCuotas());
        dto.setMontoPorCuota(planPago.getMontoPorCuota());
        dto.setInteres(planPago.getInteres());
        dto.setFechaInicio(planPago.getFechaInicio());
        dto.setFechaFin(planPago.getFechaFin());

        // ✅ Incluir datos básicos de la transacción (opcional)
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        transaccionDTO.setTransaccionId(planPago.getTransaccion().getTransaccionId());
        transaccionDTO.setMontoTotalFiat(planPago.getTransaccion().getMontoTotalFiat());
        transaccionDTO.setEstado(planPago.getTransaccion().getEstado());
        transaccionDTO.setFechaTransaccion(planPago.getTransaccion().getFechaTransaccion());
        dto.setTransaccion(transaccionDTO);

        return dto;
    }
}