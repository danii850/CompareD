package es.danielpr.comparadoralimentos.dto.reseña;

import es.danielpr.comparadoralimentos.dto.producto.ProductoCreateDto;
import es.danielpr.comparadoralimentos.dto.producto.ProductoResponseDto;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.entidades.Reseña;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface ReseñaMapper {

    @Mapping(target = "usuario_id", source = "usuario.id")
    @Mapping(target = "nombre", source = "usuario.nombre")
    ReseñaResponseDto toDto(Reseña entity);
    Reseña toEntity(ReseñaCreateDto dto);
}
