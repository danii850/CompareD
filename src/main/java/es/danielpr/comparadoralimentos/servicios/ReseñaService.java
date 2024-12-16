package es.danielpr.comparadoralimentos.servicios;

import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.entidades.Reseña;
import es.danielpr.comparadoralimentos.entidades.Usuario;
import es.danielpr.comparadoralimentos.repositorios.ProductoRepository;
import es.danielpr.comparadoralimentos.repositorios.ReseñaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReseñaService {

    private final ReseñaRepository repositorio;

    public Reseña save(Reseña r) { return repositorio.save(r); }

    public Optional<Reseña> findById(Long id) {
        return repositorio.findById(id);
    }

    public List<Reseña> buscarPorProducto(Long producto_id) {
        return repositorio.findByProductoId(producto_id);
    }

    public boolean existsByUsuarioAndProducto(Usuario usuario, Producto producto) {
        return repositorio.existsByUsuarioAndProducto(usuario, producto);
    }

    public void deleteById(Long id) {
        repositorio.deleteById(id);
    }
}
