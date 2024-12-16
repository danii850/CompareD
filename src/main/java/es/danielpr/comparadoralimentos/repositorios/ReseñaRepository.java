package es.danielpr.comparadoralimentos.repositorios;

import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.entidades.Reseña;
import es.danielpr.comparadoralimentos.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReseñaRepository extends JpaRepository<Reseña, Long> {

    @Query("SELECT r FROM Reseña r WHERE r.producto.id = :producto_id")
    List<Reseña> findByProductoId(@Param("producto_id") Long producto_id);

    @Query("SELECT COUNT(r) > 0 FROM Reseña r WHERE r.usuario = :usuario AND r.producto = :producto")
    boolean existsByUsuarioAndProducto(@Param("usuario") Usuario usuario, @Param("producto") Producto producto);
}
