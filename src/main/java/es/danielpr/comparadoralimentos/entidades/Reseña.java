package es.danielpr.comparadoralimentos.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reseña")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column
    private int valoracion;

    @Column
    private String comentario;

    @Override
    public String toString() {
        return "Reseña{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? "Usuario{id=" + usuario.getId() + ", nombre=" + usuario.getNombre() + "}" : "null") +
                ", producto=" + (producto != null ? "Producto{id=" + producto.getId() + ", nombre=" + producto.getNombre() + "}" : "null") +
                ", valoracion=" + valoracion +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
