package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.HuespedDTO;
import co.edu.unbosque.proyecto_bd1.mappers.HuespedMapper;
import co.edu.unbosque.proyecto_bd1.model.Huesped;
import co.edu.unbosque.proyecto_bd1.repository.HuespedRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HuespedService {

    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    public HuespedService(HuespedRepository huespedRepository,
                          HuespedMapper huespedMapper) {
        this.huespedRepository = huespedRepository;
        this.huespedMapper = huespedMapper;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<HuespedDTO> listarTodos() {
        List<Huesped> huespedes = huespedRepository.listarTodos();
        return huespedMapper.aDTOLista(huespedes);
    }

    @Transactional(readOnly = true)
    public HuespedDTO buscarPorId(Integer id) {
        Optional<Huesped> opt = huespedRepository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Huesped con id " + id + " no encontrado");
        }
        return huespedMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public HuespedDTO buscarPorDocumento(String tipoDocumento, String numeroDocumento) {
        Optional<Huesped> opt = huespedRepository.buscarPorDocumento(tipoDocumento, numeroDocumento);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "Huesped con documento " + tipoDocumento + "-" + numeroDocumento + " no encontrado");
        }
        return huespedMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<HuespedDTO> buscarPorNacionalidad(String nacionalidad) {
        List<Huesped> huespedes = huespedRepository.buscarPorNacionalidad(nacionalidad);
        return huespedMapper.aDTOLista(huespedes);
    }

    @Transactional(readOnly = true)
    public List<HuespedDTO> buscarPorApellido(String apellido) {
        List<Huesped> huespedes = huespedRepository.buscarPorApellido(apellido);
        return huespedMapper.aDTOLista(huespedes);
    }

    // ====== CREATE ======

    @Transactional
    public void crear(HuespedDTO dto) {
        huespedRepository.insertar(
            dto.getNombre(),
            dto.getApellido(),
            dto.getTipoDocumento().name(),
            dto.getNumeroDocumento(),
            dto.getNacionalidad(),
            dto.getFechaNacimiento()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, HuespedDTO dto) {
        int filasAfectadas = huespedRepository.actualizar(
            id,
            dto.getNombre(),
            dto.getApellido(),
            dto.getTipoDocumento().name(),
            dto.getNumeroDocumento(),
            dto.getNacionalidad(),
            dto.getFechaNacimiento()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Huesped con id " + id + " no encontrado");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = huespedRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Huesped con id " + id + " no encontrado");
        }
    }
}