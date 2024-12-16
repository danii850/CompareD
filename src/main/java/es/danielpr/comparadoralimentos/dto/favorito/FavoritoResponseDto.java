package es.danielpr.comparadoralimentos.dto.favorito;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoritoResponseDto {

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
}
