package es.danielpr.comparadoralimentos.dto.reseña;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReseñaCreateDto {
    private Long usuario_id;
    private Long producto_id;
    private int valoracion;
    private String comentario;
}
