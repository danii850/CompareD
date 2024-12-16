package es.danielpr.comparadoralimentos.servicios;

import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.repositorios.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepository repositorio;

    public List<Producto> findAll() {
        return repositorio.findAll();
    }

    public Optional<Producto> findById(Long id) {
        return repositorio.findById(id);
    }

    public Producto save(Producto p) { return repositorio.save(p); }

    public void deleteById(Long id) {
        repositorio.deleteById(id);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return repositorio.findByNombreContainingIgnoreCase(nombre);
    }
}
