package es.danielpr.comparadoralimentos.controladores.producto;

import es.danielpr.comparadoralimentos.dto.producto.*;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.servicios.ProductoService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@RestController
@RequestMapping("/api")
public class ProductoRestController {

    private final ProductoService productoService;
    private final ProductoMapper mapper;

    @GetMapping("/producto")
    public ResponseEntity<List<ProductoResponseDto>> getAll(@RequestParam(required = false) Long usuarioId) {
        try {
            List<Producto> productos = productoService.findAll();
            List<ProductoResponseDto> productosDto;

            if (usuarioId != null) {
                productosDto = productos.stream().map(producto -> {
                    ProductoResponseDto dto = mapper.toDto(producto);
                    dto.setImagenUrl(producto.getImagenUrl());
                    boolean isFavorito = producto.getFavoritos().stream()
                            .anyMatch(favorito -> favorito.getUsuario().getId().equals(usuarioId));
                    dto.setIsFavorito(isFavorito);
                    return dto;
                }).collect(Collectors.toList());
            } else {
                productosDto = productos.stream()
                        .map(producto -> {
                            ProductoResponseDto dto = mapper.toDto(producto);
                            dto.setImagenUrl(producto.getImagenUrl());
                            return dto;
                        })
                        .collect(Collectors.toList());
            }

            return new ResponseEntity<>(productosDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoResponseDto> getOne(@PathVariable("id") Long id) {
        try {
            Optional<Producto> producto = productoService.findById(id);

            return producto
                    .map(m -> new ResponseEntity<>(mapper.toDto(m), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping({ "/admin/producto/new" })
    public ResponseEntity<Map<String, Object>> add(@Valid @RequestBody ProductoCreateDto productoDto) {
        if (productoDto == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Datos del producto no proporcionados"), HttpStatus.BAD_REQUEST);
        }
        try {
            Producto producto = mapper.toEntity(productoDto);
            Producto nuevoProducto = productoService.save(producto);

            Map<String, Object> response = new HashMap<>();
            response.put("id", nuevoProducto.getId());
            response.put("message", "Producto creado exitosamente");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear el producto: ", e);
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Méto-do para poder añadir desde Postman varios productos a la vez
    /*@PostMapping({ "/admin/producto/new" })
    public ResponseEntity<Map<String, Object>> addMultiple(@Valid @RequestBody List<ProductoCreateDto> productosDto) {
        if (productosDto == null || productosDto.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("error", "No se enviaron productos"), HttpStatus.BAD_REQUEST);
        }
        try {
            List<Long> ids = productosDto.stream()
                    .map(productoDto -> productoService.save(mapper.toEntity(productoDto)).getId())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(Collections.singletonMap("ids", ids), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @DeleteMapping(value = "/admin/delete/producto/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        try {
            Optional<Producto> producto = productoService.findById(id);
            if (producto.isPresent()) {
                productoService.deleteById(id);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/producto/buscar")
    public List<Producto> buscarProductos(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    @PutMapping("/admin/update/producto/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoCreateDto productoDto) {
        try {
            Producto producto = productoService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            producto.setNombre(productoDto.getNombre());
            producto.setMarca(productoDto.getMarca());
            producto.setPrecio(productoDto.getPrecio());
            producto.setPrecioKilo(productoDto.getPrecioKilo());
            producto.setCalorias(productoDto.getCalorias());
            producto.setAzucares(productoDto.getAzucares());
            producto.setGrasas(productoDto.getGrasas());
            producto.setProteinas(productoDto.getProteinas());
            producto.setCarbohidratos(productoDto.getCarbohidratos());
            producto.setImagenUrl(productoDto.getImagenUrl());

            productoService.save(producto);

            return ResponseEntity.ok("Producto actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }
}
