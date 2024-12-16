package es.danielpr.comparadoralimentos.controladores.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import es.danielpr.comparadoralimentos.entidades.Producto;
import es.danielpr.comparadoralimentos.repositorios.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.github.database.rider.spring.api.DBRider;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DBRider
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;
}
