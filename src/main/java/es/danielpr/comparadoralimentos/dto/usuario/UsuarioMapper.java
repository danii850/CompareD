package es.danielpr.comparadoralimentos.dto.usuario;

import es.danielpr.comparadoralimentos.entidades.Usuario;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface UsuarioMapper {

    UsuarioSignupDto toDto(Usuario entity);

    Usuario toEntity(UsuarioSignupDto dto);
}