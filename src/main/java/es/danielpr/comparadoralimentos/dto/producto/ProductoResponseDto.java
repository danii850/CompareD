package es.danielpr.comparadoralimentos.dto.producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoResponseDto {

    private Long id;
    private String nombre;
    private String marca;
    private BigDecimal precio;
    private BigDecimal precioKilo;
    private BigDecimal calorias;
    private BigDecimal azucares;
    private BigDecimal grasas;
    private BigDecimal proteinas;
    private BigDecimal carbohidratos;
    private String imagenUrl;
    private Boolean isFavorito;
}
