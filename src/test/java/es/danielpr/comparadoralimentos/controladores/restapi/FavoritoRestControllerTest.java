package es.danielpr.comparadoralimentos.controladores.restapi;

import es.danielpr.comparadoralimentos.controladores.favorito.FavoritoRestController;
import es.danielpr.comparadoralimentos.controladores.producto.ProductoRestController;
import es.danielpr.comparadoralimentos.dto.favorito.FavoritoMapper;
import es.danielpr.comparadoralimentos.dto.producto.ProductoMapper;
import es.danielpr.comparadoralimentos.entidades.Favorito;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.servicios.FavoritoService;
import es.danielpr.comparadoralimentos.servicios.ProductoService;
import es.danielpr.comparadoralimentos.servicios.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FavoritoRestController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class FavoritoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoritoService favoritoService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private FavoritoMapper mapper;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public FavoritoMapper dtoMapper() {
            return Mappers.getMapper( FavoritoMapper.class );
        }
    }

    @Test
    void getFavoritos_ShouldReturnFavoritos_WhenUsuarioHasFavoritos() throws Exception {
        // Arrange
        Favorito favorito = new Favorito();
        Producto producto = Producto.builder()
                .id(1L)
                .nombre("Manzana")
                .marca("Marca A")
                .precio(BigDecimal.valueOf(100))
                .precioKilo(BigDecimal.valueOf(200))
                .calorias(BigDecimal.valueOf(52))
                .azucares(BigDecimal.valueOf(14))
                .grasas(BigDecimal.valueOf(0))
                .proteinas(BigDecimal.valueOf(0))
                .carbohidratos(BigDecimal.valueOf(14))
                .build();
        favorito.setProducto(producto);

        List<Favorito> favoritos = List.of(favorito);

        when(favoritoService.findByUsuarioId(1L)).thenReturn(favoritos);

        String expectedJson = """
        {
            "productosFavoritos": [
                {
                    "id": 1,
                    "nombre": "Manzana",
                    "marca": "Marca A",
                    "precio": 100,
                    "precioKilo": 200,
                    "calorias": 52,
                    "azucares": 14,
                    "grasas": 0,
                    "proteinas": 0,
                    "carbohidratos": 14
                }
            ]
        }
        """;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/favoritos/usuario/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(print());
    }

}
