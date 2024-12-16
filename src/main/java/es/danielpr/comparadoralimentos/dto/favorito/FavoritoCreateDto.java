package es.danielpr.comparadoralimentos.dto.favorito;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoritoCreateDto {

    private Long usuario_id;
    private Long producto_id;
}
