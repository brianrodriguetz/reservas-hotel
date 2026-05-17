package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.ContactoDTO;
import co.edu.unbosque.proyecto_bd1.mappers.ContactoMapper;
import co.edu.unbosque.proyecto_bd1.model.Contacto;
import co.edu.unbosque.proyecto_bd1.repository.ClienteRepository;
import co.edu.unbosque.proyecto_bd1.repository.ContactoRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final ContactoMapper contactoMapper;
    private final ClienteRepository clienteRepository;

    public ContactoService(ContactoRepository contactoRepository,
                           ContactoMapper contactoMapper,
                           ClienteRepository clienteRepository) {
        this.contactoRepository = contactoRepository;
        this.contactoMapper = contactoMapper;
        this.clienteRepository = clienteRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<ContactoDTO> listarTodos() {
        List<Contacto> contactos = contactoRepository.listarTodos();
        return contactoMapper.aDTOLista(contactos);
    }

    @Transactional(readOnly = true)
    public ContactoDTO buscarPorId(Integer id) {
        Optional<Contacto> opt = contactoRepository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Contacto con id " + id + " no encontrado");
        }
        return contactoMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<ContactoDTO> buscarPorCliente(Integer idCliente) {
        List<Contacto> contactos = contactoRepository.buscarPorCliente(idCliente);
        return contactoMapper.aDTOLista(contactos);
    }

    @Transactional(readOnly = true)
    public ContactoDTO buscarPrincipalDeCliente(Integer idCliente) {
        Optional<Contacto> opt = contactoRepository.buscarPrincipalDeCliente(idCliente);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "El cliente " + idCliente + " no tiene contacto principal");
        }
        return contactoMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<ContactoDTO> buscarPorTipo(String tipo) {
        List<Contacto> contactos = contactoRepository.buscarPorTipo(tipo);
        return contactoMapper.aDTOLista(contactos);
    }

    // ====== CREATE ======

    @Transactional
    public void crear(ContactoDTO dto) {
        validarClienteExiste(dto.getIdCliente());

        // Regla de negocio: solo un contacto principal por cliente
        if (Boolean.TRUE.equals(dto.getEsPrincipal())) {
            contactoRepository.desmarcarPrincipalesDeCliente(dto.getIdCliente());
        }

        contactoRepository.insertar(
            dto.getTipoContacto().name(),
            dto.getValor(),
            dto.getEsPrincipal(),
            dto.getIdCliente()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, ContactoDTO dto) {
        validarClienteExiste(dto.getIdCliente());

        // Si se marca como principal, desmarcar los otros del mismo cliente
        if (Boolean.TRUE.equals(dto.getEsPrincipal())) {
            contactoRepository.desmarcarPrincipalesDeCliente(dto.getIdCliente());
        }

        int filasAfectadas = contactoRepository.actualizar(
            id,
            dto.getTipoContacto().name(),
            dto.getValor(),
            dto.getEsPrincipal(),
            dto.getIdCliente()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Contacto con id " + id + " no encontrado");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = contactoRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Contacto con id " + id + " no encontrado");
        }
    }

    // ===== Helpers privados =====

    private void validarClienteExiste(Integer idCliente) {
        if (clienteRepository.buscarPorId(idCliente).isEmpty()) {
            throw new NoSuchElementException(
                "El cliente con id " + idCliente + " no existe");
        }
    }
}