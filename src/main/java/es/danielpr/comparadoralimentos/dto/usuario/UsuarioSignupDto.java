package es.danielpr.comparadoralimentos.dto.usuario;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioSignupDto {

        private String nombre;
        private String email;
        private String contraseña;
        private String role;

}
