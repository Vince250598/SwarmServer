package swarm.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import swarm.server.domains.Product;
import swarm.server.repositories.ProductRepository;
import swarm.server.services.ProductService;

public class ProductServiceIntegrationTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void whenProductByDeveloperId_thenReturnProducts() {

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("productName"));

        when(productRepository.findByDeveloperId(1L)).thenReturn(productList);

        Iterable<Product> found = productService.products(1L);

        int i = 0;
        for (Product pro : found) {
            assertEquals(productList.get(i), pro);
            i++;
        }
    }
}