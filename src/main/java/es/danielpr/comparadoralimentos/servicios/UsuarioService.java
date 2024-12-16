package es.danielpr.comparadoralimentos.servicios;

import es.danielpr.comparadoralimentos.dto.usuario.UsuarioMapper;
import es.danielpr.comparadoralimentos.dto.usuario.UsuarioSignupDto;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.entidades.Usuario;
import es.danielpr.comparadoralimentos.repositorios.ProductoRepository;
import es.danielpr.comparadoralimentos.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repositorio;
    private final UsuarioMapper mapper;

    public Usuario save(Usuario usuario) {
        return repositorio.save(usuario);
    }

    public Optional<Usuario> buscarPorEmailYContraseña(String email, String contraseña) {
        return repositorio.findByEmailAndPassword(email, contraseña);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return repositorio.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {
        return repositorio.findById(id);
    }
}
