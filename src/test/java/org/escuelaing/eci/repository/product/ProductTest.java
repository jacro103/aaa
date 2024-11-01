package org.escuelaing.eci.repository.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@AutoConfigureMockMvc
public class ProductTest {

    @Autowired
    private ProductMongoRepository productMongoRepository;

    @BeforeEach
    public void setUp() {
        productMongoRepository.deleteAll(); // Limpiar la colección antes de cada prueba
    }

    @Test
    public void testProductCreation() {
        // Crear un objeto Product
        Product product = new Product("1", "Pizza", "Delicious cheese pizza", "Food", 12.99);
        product.setTags(Arrays.asList("Italian", "Cheese"));

        // Guardar el Product
        Product savedProduct = productMongoRepository.save(product);

        // Verificar que se haya guardado correctamente
        assertNotNull(savedProduct);
        assertEquals("1", savedProduct.getId());
        assertEquals("Pizza", savedProduct.getName());
        assertEquals("Delicious cheese pizza", savedProduct.getDescription());
        assertEquals("Food", savedProduct.getCategory());
        assertEquals(12.99, savedProduct.getPrice());
        assertEquals(Arrays.asList("Italian", "Cheese"), savedProduct.getTags());
    }

    @Test
    public void testFindProductById() {
        // Crear un objeto Product
        Product product = new Product("1", "Pizza", "Delicious cheese pizza", "Food", 12.99);
        productMongoRepository.save(product);

        // Buscar el Product por ID
        Optional<Product> foundProduct = productMongoRepository.findById(product.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getName(), foundProduct.get().getName());
    }

    @Test
    public void testUpdateProduct() {
        // Crear un objeto Product
        Product product = new Product("1", "Pizza", "Delicious cheese pizza", "Food", 12.99);
        productMongoRepository.save(product);

        // Actualizar el Product
        product.setName("Veggie Pizza");
        product.setDescription("Delicious veggie pizza with fresh vegetables");
        product.setPrice(14.99);
        productMongoRepository.save(product);

        // Verificar la actualización
        Optional<Product> updatedProduct = productMongoRepository.findById(product.getId());
        assertTrue(updatedProduct.isPresent());
        assertEquals("Veggie Pizza", updatedProduct.get().getName());
        assertEquals("Delicious veggie pizza with fresh vegetables", updatedProduct.get().getDescription());
        assertEquals(14.99, updatedProduct.get().getPrice());
    }

    @Test
    public void testDeleteProduct() {
        // Crear y guardar un objeto Product
        Product product = new Product("1", "Pizza", "Delicious cheese pizza", "Food", 12.99);
        productMongoRepository.save(product);

        // Eliminar el Product
        productMongoRepository.delete(product);

        // Verificar que el Product ya no esté presente
        Optional<Product> deletedProduct = productMongoRepository.findById(product.getId());
        assertFalse(deletedProduct.isPresent());
    }
}
