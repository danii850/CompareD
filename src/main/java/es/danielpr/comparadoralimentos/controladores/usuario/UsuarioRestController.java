package es.danielpr.comparadoralimentos.controladores.usuario;

import es.danielpr.comparadoralimentos.dto.usuario.UsuarioLoginDto;
import es.danielpr.comparadoralimentos.dto.usuario.UsuarioSignupDto;
import es.danielpr.comparadoralimentos.entidades.Usuario;
import es.danielpr.comparadoralimentos.servicios.UsuarioService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Data
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioRestController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registrarUsuario(@RequestBody UsuarioSignupDto registroDto) {
        Optional<Usuario> existente = usuarioService.buscarPorEmail(registroDto.getEmail());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "El email ya está registrado"));
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDto.getNombre());
        usuario.setEmail(registroDto.getEmail());
        String contraseñaEncriptada = passwordEncoder.encode(registroDto.getContraseña());
        usuario.setContraseña(contraseñaEncriptada);
        usuario.setRole(registroDto.getRole());

        // Guardar usuario
        usuarioService.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Usuario registrado con éxito"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDto loginDto) {
        try {
            Optional<Usuario> usuarioOptional = usuarioService.buscarPorEmail(
                    loginDto.getEmail()
            );

            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                if (passwordEncoder.matches(loginDto.getContraseña(), usuario.getContraseña())) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", usuario.getId());
                    response.put("nombre", usuario.getNombre());
                    response.put("email", usuario.getEmail());
                    response.put("role", usuario.getRole());
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Collections.singletonMap("error", "Contraseña Incorrecta"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Credenciales no encontrados"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
