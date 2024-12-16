package es.danielpr.comparadoralimentos.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "producto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String marca;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column
    private BigDecimal precioKilo;

    @Column
    private BigDecimal calorias;

    @Column
    private BigDecimal azucares;

    @Column
    private BigDecimal grasas;

    @Column
    private BigDecimal proteinas;

    @Column
    private BigDecimal carbohidratos;

    @Column
    private String imagenUrl;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorito> favoritos;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reseña> reseñas;

}
