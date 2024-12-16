package es.danielpr.comparadoralimentos.repositorios;

import es.danielpr.comparadoralimentos.entidades.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    public List<Producto> findByNombreStartingWithIgnoreCase (String nombre);
    Optional<Producto> findByNombre(String nombre);
    Page<Producto> findPageBy(Pageable pageable);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
