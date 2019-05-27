package swarm.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swarm.server.domains.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
