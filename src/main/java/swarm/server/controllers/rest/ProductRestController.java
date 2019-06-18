package swarm.server.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Product;
import swarm.server.services.ProductService;

@RestController
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/products")
	public Product newProduct(@RequestBody Product product) {
		return productService.save(product);
	}
	
	@RequestMapping("/products/{id}")
	public Optional<Product> productById(@PathVariable Long id) {
		return productService.productById(id);
	}
	
	@RequestMapping("/products/all")
	public Iterable<Product> allProducts() {
		return productService.allProduct();
	}
	
	@RequestMapping("/graphByProduct.html")
	public String graphByProduct(Long productId) {
		return productService.getProductPaths(productId);
	}

}
