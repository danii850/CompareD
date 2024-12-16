package es.danielpr.comparadoralimentos.repositorios;

import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u " +
            "where lower(u.nombre) = ?1 or lower(u.email) = ?1")
    Optional<Usuario> buscarPorUsernameOEmail(String s);

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.contraseña = :contraseña")
    Optional<Usuario> findByEmailAndPassword(@Param("email") String email, @Param("contraseña") String contraseña);

    List<Usuario> findByFavoritosId(Long productoId);
}