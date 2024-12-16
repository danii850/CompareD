package es.danielpr.comparadoralimentos.controladores.favorito;

import es.danielpr.comparadoralimentos.dto.favorito.FavoritoCreateDto;
import es.danielpr.comparadoralimentos.dto.favorito.FavoritoMapper;
import es.danielpr.comparadoralimentos.dto.favorito.FavoritoResponseDto;
import es.danielpr.comparadoralimentos.entidades.Favorito;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.entidades.Usuario;
import es.danielpr.comparadoralimentos.servicios.FavoritoService;
import es.danielpr.comparadoralimentos.servicios.ProductoService;
import es.danielpr.comparadoralimentos.servicios.UsuarioService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Data
@RestController
@RequestMapping("/api/favorito")
public class FavoritoRestController {

    private final FavoritoService favoritoService;
    private final FavoritoMapper mapper;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Map<String, Object>> getProductosFavoritos(@PathVariable Long id) {
        List<Favorito> favoritos = favoritoService.findByUsuarioId(id);

        if (favoritos.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("error", "No se encontraron favoritos para este usuario"),
                    HttpStatus.NOT_FOUND);
        }
        
        List<FavoritoResponseDto> productosFavoritosDto = favoritos.stream()
                .map(favorito -> new FavoritoResponseDto(
                        favorito.getProducto().getId(),
                        favorito.getProducto().getNombre(),
                        favorito.getProducto().getMarca(),
                        favorito.getProducto().getPrecio(),
                        favorito.getProducto().getPrecioKilo(),
                        favorito.getProducto().getCalorias(),
                        favorito.getProducto().getAzucares(),
                        favorito.getProducto().getGrasas(),
                        favorito.getProducto().getProteinas(),
                        favorito.getProducto().getCarbohidratos()))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("productosFavoritos", productosFavoritosDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> addFavorito(@RequestBody FavoritoCreateDto favoritoDto) {

        if (favoritoDto == null || favoritoDto.getUsuario_id() == null || favoritoDto.getProducto_id() == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Datos incompletos en el FavoritoDTO"),
                    HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioService.findById(favoritoDto.getUsuario_id()).orElse(null);
        Producto producto = productoService.findById(favoritoDto.getProducto_id()).orElse(null);

        if (usuario == null || producto == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Usuario o Producto no encontrados"),
                    HttpStatus.NOT_FOUND);
        }

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);

        Favorito savedFavorito = favoritoService.save(favorito);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedFavorito.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFavorito(@RequestBody FavoritoCreateDto favoritoDto) {
        try {
            boolean eliminado = favoritoService.eliminarFavorito(favoritoDto.getUsuario_id(), favoritoDto.getProducto_id());

            if (eliminado) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Producto eliminado de favoritos"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "El producto favorito no existe"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{productoId}")
    public ResponseEntity<?> eliminarFavorito(@PathVariable Long productoId, @RequestParam Long usuarioId) {
        try {
            favoritoService.eliminarPorUsuarioYProducto(usuarioId, productoId);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Favorito eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
