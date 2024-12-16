package es.danielpr.comparadoralimentos.controladores.restapi;

import es.danielpr.comparadoralimentos.DataSetConstants;
import es.danielpr.comparadoralimentos.controladores.producto.ProductoRestController;
import es.danielpr.comparadoralimentos.dto.producto.*;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.servicios.ProductoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
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
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductoRestController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ProductoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private ProductoMapper mapper;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ProductoMapper dtoMapper() {
            return Mappers.getMapper( ProductoMapper.class );
        }
    }

    @Test
    void getOne_ShouldReturnProducto_WhenProductoExists() throws Exception {
        // Arrange
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

        ProductoResponseDto responseDto = new ProductoResponseDto();
        responseDto.setId(1L);
        responseDto.setNombre("Manzana");
        responseDto.setMarca("Marca A");
        responseDto.setPrecio(BigDecimal.valueOf(100));
        responseDto.setPrecioKilo(BigDecimal.valueOf(200));
        responseDto.setCalorias(BigDecimal.valueOf(52));
        responseDto.setAzucares(BigDecimal.valueOf(14));
        responseDto.setGrasas(BigDecimal.valueOf(0));
        responseDto.setProteinas(BigDecimal.valueOf(0));
        responseDto.setCarbohidratos(BigDecimal.valueOf(14));

        when(productoService.findById(1L)).thenReturn(Optional.of(producto));
        when(mapper.toDto(producto)).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/producto/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Manzana"))
                .andExpect(jsonPath("$.marca").value("Marca A"))
                .andExpect(jsonPath("$.precio").value(100))
                .andExpect(jsonPath("$.precioKilo").value(200))
                .andExpect(jsonPath("$.calorias").value(52))
                .andExpect(jsonPath("$.azucares").value(14))
                .andExpect(jsonPath("$.grasas").value(0))
                .andExpect(jsonPath("$.proteinas").value(0))
                .andExpect(jsonPath("$.carbohidratos").value(14))
                .andDo(print());
    }

    @Test
    void getOne_ShouldReturnNotFound_WhenProductoDoesNotExist() throws Exception {

        when(productoService.findById(999L)).thenReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders.get("/api/producto/{id}", 999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getOne_ShouldReturnBadRequest_WhenIdIsInvalid() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/producto/{id}", "invalid-id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getOne_ShouldReturnInternalServerError_WhenExceptionOccurs() throws Exception {

        when(productoService.findById(1L)).thenThrow(new RuntimeException("Unexpected error"));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/producto/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    //POST

    @Test
    void add_ShouldReturnCreatedStatus_WhenProductoIsValid() throws Exception {
        // Arrange
        ProductoCreateDto productoDto = new ProductoCreateDto();
        productoDto.setNombre("Manzana");
        productoDto.setMarca("Marca");
        productoDto.setPrecio(BigDecimal.valueOf(100));
        productoDto.setPrecioKilo(BigDecimal.valueOf(200));
        productoDto.setCalorias(BigDecimal.valueOf(52));
        productoDto.setAzucares(BigDecimal.valueOf(14));
        productoDto.setGrasas(BigDecimal.valueOf(0));
        productoDto.setProteinas(BigDecimal.valueOf(0));
        productoDto.setCarbohidratos(BigDecimal.valueOf(14));

        Producto producto = Producto.builder()
                .id(1L)
                .nombre("Manzana")
                .marca("Marca")
                .precio(BigDecimal.valueOf(100))
                .precioKilo(BigDecimal.valueOf(200))
                .calorias(BigDecimal.valueOf(52))
                .azucares(BigDecimal.valueOf(14))
                .grasas(BigDecimal.valueOf(0))
                .proteinas(BigDecimal.valueOf(0))
                .carbohidratos(BigDecimal.valueOf(14))
                .build();

        when(mapper.toEntity(any(ProductoCreateDto.class))).thenReturn(producto);
        when(productoService.save(any(Producto.class))).thenReturn(producto);

        String productoDtoJson = """
        {
            "nombre": "Manzana",
            "marca": "Marca",
            "precio": 100,
            "precioKilo": 200,
            "calorias": 52,
            "azucares": 14,
            "grasas": 0,
            "proteinas": 0,
            "carbohidratos": 14
        }
        """;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/producto/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoDtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());
    }

    //DELETE

    @Test
    void delete_ShouldReturnNoContent_WhenProductoExists() throws Exception {

        Producto producto = Producto.builder().id(1L).nombre("Manzana").build();

        when(productoService.findById(1L)).thenReturn(Optional.of(producto));
        doNothing().when(productoService).deleteById(1L);


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/producto/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(productoService, times(1)).deleteById(1L);
    }
}
