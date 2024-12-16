package es.danielpr.comparadoralimentos.dto.producto;

import es.danielpr.comparadoralimentos.entidades.Producto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface ProductoMapper {

    ProductoResponseDto toDto(Producto entity);
    Producto toEntity(ProductoCreateDto productoDto);

    List<ProductoResponseDto> toDtoList(List<Producto> list);

}
