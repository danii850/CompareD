package es.danielpr.comparadoralimentos.dto.favorito;

import es.danielpr.comparadoralimentos.dto.producto.ProductoCreateDto;
import es.danielpr.comparadoralimentos.dto.producto.ProductoResponseDto;
import es.danielpr.comparadoralimentos.entidades.Favorito;
import es.danielpr.comparadoralimentos.entidades.Producto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface FavoritoMapper {

    FavoritoResponseDto toDto(Favorito entity);
    Favorito toEntity(FavoritoCreateDto dto);
}
