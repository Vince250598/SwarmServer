package swarm.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import swarm.server.domains.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("Select p from Product p, Task t, Session s where t.product.id = p.id and s.task.id = t.id and s.developer.id = :developerId")
    Iterable<Product> findByDeveloperId(@Param("developerId") Long developerId);

}
