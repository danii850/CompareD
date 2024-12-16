package es.danielpr.comparadoralimentos.controladores.reseña;

import es.danielpr.comparadoralimentos.dto.favorito.FavoritoCreateDto;
import es.danielpr.comparadoralimentos.dto.favorito.FavoritoMapper;
import es.danielpr.comparadoralimentos.dto.producto.ProductoCreateDto;
import es.danielpr.comparadoralimentos.dto.producto.ProductoMapper;
import es.danielpr.comparadoralimentos.dto.reseña.ReseñaCreateDto;
import es.danielpr.comparadoralimentos.dto.reseña.ReseñaMapper;
import es.danielpr.comparadoralimentos.dto.reseña.ReseñaResponseDto;
import es.danielpr.comparadoralimentos.entidades.Favorito;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.entidades.Reseña;
import es.danielpr.comparadoralimentos.entidades.Usuario;
import es.danielpr.comparadoralimentos.servicios.ProductoService;
import es.danielpr.comparadoralimentos.servicios.ReseñaService;
import es.danielpr.comparadoralimentos.servicios.UsuarioService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@RestController
@RequestMapping("/api")
public class ReseñaRestController {

    private final ReseñaService reseñaService;
    private final ReseñaMapper mapper;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    @PostMapping("/reseña/new")
    public ResponseEntity<Map<String, Object>> addReseña(@RequestBody ReseñaCreateDto reseñaDto) {
        if (reseñaDto == null || reseñaDto.getUsuario_id() == null || reseñaDto.getProducto_id() == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Datos incompletos en el FavoritoDTO"),
                    HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioService.findById(reseñaDto.getUsuario_id()).orElse(null);
        Producto producto = productoService.findById(reseñaDto.getProducto_id()).orElse(null);

        if (usuario == null || producto == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Usuario o Producto no encontrados"),
                    HttpStatus.NOT_FOUND);
        }

        boolean yaValorado = reseñaService.existsByUsuarioAndProducto(usuario, producto);

        if (yaValorado) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Ya has valorado este producto."),
                    HttpStatus.CONFLICT);
        }

        Reseña reseña = new Reseña();
        reseña.setUsuario(usuario);
        reseña.setProducto(producto);
        reseña.setValoracion(reseñaDto.getValoracion());
        reseña.setComentario(reseñaDto.getComentario());

        Reseña savedFavorito = reseñaService.save(reseña);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedFavorito.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/reseña/producto/{id}")
    public ResponseEntity<List<ReseñaResponseDto>> getReseñasPorProducto(@PathVariable Long id) {
        List<ReseñaResponseDto> entity = reseñaService.buscarPorProducto(id).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/admin/delete/reseña/{id}")
    public ResponseEntity<Map<String, String>> deleteReseña(@PathVariable Long id) {
        try {
            Optional<Reseña> reseña = reseñaService.findById(id);
            if (reseña.isPresent()) {
                reseñaService.deleteById(id);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Reseña no encontrada."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al intentar eliminar la reseña."));
        }
    }
}
