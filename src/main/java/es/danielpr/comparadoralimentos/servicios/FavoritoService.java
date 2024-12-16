package es.danielpr.comparadoralimentos.servicios;

import es.danielpr.comparadoralimentos.dto.favorito.FavoritoCreateDto;
import es.danielpr.comparadoralimentos.dto.favorito.FavoritoMapper;
import es.danielpr.comparadoralimentos.entidades.Favorito;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.entidades.Usuario;
import es.danielpr.comparadoralimentos.repositorios.FavoritoRepository;
import es.danielpr.comparadoralimentos.repositorios.ProductoRepository;
import es.danielpr.comparadoralimentos.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository repositorio;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FavoritoMapper favoritoMapper;

    public Favorito save(Favorito f) { return repositorio.save(f);}

    public Favorito addFavorito(FavoritoCreateDto favoritoCreateDto) {
        Usuario usuario = usuarioRepository.findById(favoritoCreateDto.getUsuario_id())
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        Producto producto = productoRepository.findById(favoritoCreateDto.getProducto_id())
                .orElse(null);

        if (producto == null) {
            return null;
        }

        Favorito favorito = favoritoMapper.toEntity(favoritoCreateDto);
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);

        return repositorio.save(favorito);
    }

    public List<Favorito> findByUsuarioId(Long id) {
        return repositorio.findByUsuarioId(id);
    }

    public boolean eliminarFavorito(Long usuarioId, Long productoId) {
        Optional<Favorito> favorito = repositorio.findByUsuarioIdAndProductoId(usuarioId, productoId);

        if (favorito.isPresent()) {
            repositorio.delete(favorito.get());
            return true;
        }

        return false;
    }

    @Transactional
    public void eliminarPorUsuarioYProducto(Long usuarioId, Long productoId) {
        repositorio.deleteByUsuarioIdAndProductoId(usuarioId, productoId);
    }
}
