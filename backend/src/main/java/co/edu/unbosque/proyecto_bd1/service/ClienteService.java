package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.EmpresaDTO;
import co.edu.unbosque.proyecto_bd1.dto.PersonaDTO;
import co.edu.unbosque.proyecto_bd1.mappers.ClienteMapper;
import co.edu.unbosque.proyecto_bd1.model.Cliente;
import co.edu.unbosque.proyecto_bd1.model.Empresa;
import co.edu.unbosque.proyecto_bd1.model.Persona;
import co.edu.unbosque.proyecto_bd1.repository.ClienteRepository;
import co.edu.unbosque.proyecto_bd1.repository.EmpresaRepository;
import co.edu.unbosque.proyecto_bd1.repository.PersonaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final EmpresaRepository empresaRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository,
                          PersonaRepository personaRepository,
                          EmpresaRepository empresaRepository,
                          ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
        this.empresaRepository = empresaRepository;
        this.clienteMapper = clienteMapper;
    }

    // ====== READ - PERSONAS ======

    @Transactional(readOnly = true)
    public List<PersonaDTO> listarPersonas() {
        List<Persona> personas = personaRepository.listarTodos();
        List<PersonaDTO> resultado = new ArrayList<>();
        for (int i = 0; i < personas.size(); i++) {
            Persona p = personas.get(i);
            Optional<Cliente> cliente = clienteRepository.buscarPorId(p.getIdCliente());
            if (cliente.isPresent()) {
                resultado.add(clienteMapper.aPersonaDTO(cliente.get(), p));
            }
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public PersonaDTO buscarPersonaPorId(Integer id) {
        Optional<Cliente> cliente = clienteRepository.buscarPorId(id);
        Optional<Persona> persona = personaRepository.buscarPorIdCliente(id);
        if (cliente.isEmpty() || persona.isEmpty()) {
            throw new NoSuchElementException("Persona con id " + id + " no encontrada");
        }
        return clienteMapper.aPersonaDTO(cliente.get(), persona.get());
    }

    @Transactional(readOnly = true)
    public PersonaDTO buscarPersonaPorDocumento(String tipoDoc, String numeroDoc) {
        Optional<Persona> persona = personaRepository.buscarPorDocumento(tipoDoc, numeroDoc);
        if (persona.isEmpty()) {
            throw new NoSuchElementException(
                "Persona con documento " + tipoDoc + "-" + numeroDoc + " no encontrada");
        }
        Optional<Cliente> cliente = clienteRepository.buscarPorId(persona.get().getIdCliente());
        if (cliente.isEmpty()) {
            throw new IllegalStateException(
                "Inconsistencia: existe Persona pero no su Cliente padre");
        }
        return clienteMapper.aPersonaDTO(cliente.get(), persona.get());
    }

    // ====== READ - EMPRESAS ======

    @Transactional(readOnly = true)
    public List<EmpresaDTO> listarEmpresas() {
        List<Empresa> empresas = empresaRepository.listarTodos();
        List<EmpresaDTO> resultado = new ArrayList<>();
        for (int i = 0; i < empresas.size(); i++) {
            Empresa e = empresas.get(i);
            Optional<Cliente> cliente = clienteRepository.buscarPorId(e.getIdCliente());
            if (cliente.isPresent()) {
                resultado.add(clienteMapper.aEmpresaDTO(cliente.get(), e));
            }
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public EmpresaDTO buscarEmpresaPorId(Integer id) {
        Optional<Cliente> cliente = clienteRepository.buscarPorId(id);
        Optional<Empresa> empresa = empresaRepository.buscarPorIdCliente(id);
        if (cliente.isEmpty() || empresa.isEmpty()) {
            throw new NoSuchElementException("Empresa con id " + id + " no encontrada");
        }
        return clienteMapper.aEmpresaDTO(cliente.get(), empresa.get());
    }

    @Transactional(readOnly = true)
    public EmpresaDTO buscarEmpresaPorNit(String nit) {
        Optional<Empresa> empresa = empresaRepository.buscarPorNit(nit);
        if (empresa.isEmpty()) {
            throw new NoSuchElementException("Empresa con NIT " + nit + " no encontrada");
        }
        Optional<Cliente> cliente = clienteRepository.buscarPorId(empresa.get().getIdCliente());
        if (cliente.isEmpty()) {
            throw new IllegalStateException(
                "Inconsistencia: existe Empresa pero no su Cliente padre");
        }
        return clienteMapper.aEmpresaDTO(cliente.get(), empresa.get());
    }

    // ====== CREATE ======

    @Transactional
    public Integer crearPersona(PersonaDTO dto) {
        // 1. Insertar CLIENTE padre (ignoramos idCliente y fechaRegistro del DTO)
        clienteRepository.insertar(dto.getEstado().name());

        // 2. Recuperar el id generado
        Integer idGenerado = clienteRepository.ultimoIdGenerado();

        // 3. Insertar PERSONA hija con ese id
        personaRepository.insertar(
            idGenerado,
            dto.getTipoDocumento().name(),
            dto.getNumeroDocumento(),
            dto.getNombre(),
            dto.getApellido(),
            dto.getFechaNacimiento(),
            dto.getNacionalidad()
        );

        return idGenerado;
    }

    @Transactional
    public Integer crearEmpresa(EmpresaDTO dto) {
        clienteRepository.insertar(dto.getEstado().name());
        Integer idGenerado = clienteRepository.ultimoIdGenerado();
        empresaRepository.insertar(
            idGenerado,
            dto.getNit(),
            dto.getRazonSocial(),
            dto.getRepresentanteLegal(),
            dto.getSectorEconomico()
        );
        return idGenerado;
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizarPersona(Integer id, PersonaDTO dto) {
        int filasCliente = clienteRepository.actualizarEstado(id, dto.getEstado().name());
        if (filasCliente == 0) {
            throw new NoSuchElementException("Cliente con id " + id + " no encontrado");
        }
        int filasPersona = personaRepository.actualizar(
            id,
            dto.getTipoDocumento().name(),
            dto.getNumeroDocumento(),
            dto.getNombre(),
            dto.getApellido(),
            dto.getFechaNacimiento(),
            dto.getNacionalidad()
        );
        if (filasPersona == 0) {
            throw new NoSuchElementException("Persona con id " + id + " no encontrada");
        }
    }

    @Transactional
    public void actualizarEmpresa(Integer id, EmpresaDTO dto) {
        int filasCliente = clienteRepository.actualizarEstado(id, dto.getEstado().name());
        if (filasCliente == 0) {
            throw new NoSuchElementException("Cliente con id " + id + " no encontrado");
        }
        int filasEmpresa = empresaRepository.actualizar(
            id,
            dto.getNit(),
            dto.getRazonSocial(),
            dto.getRepresentanteLegal(),
            dto.getSectorEconomico()
        );
        if (filasEmpresa == 0) {
            throw new NoSuchElementException("Empresa con id " + id + " no encontrada");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        // ON DELETE CASCADE en BD se encarga de borrar las hijas (Persona o Empresa)
        int filasAfectadas = clienteRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Cliente con id " + id + " no encontrado");
        }
    }
}