package es.danielpr.comparadoralimentos.dto.reseña;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReseñaResponseDto {
    private Long id;
    private Long usuario_id;
    private String nombre;
    private int valoracion;
    private String comentario;
}
