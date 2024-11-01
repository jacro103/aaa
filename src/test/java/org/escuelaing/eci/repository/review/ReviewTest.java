package org.escuelaing.eci.repository.review;

import org.escuelaing.eci.repository.place.Place;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {

    @Test
    public void testReviewCreation() {
        // Crear un objeto Place necesario
        Place place = new Place("1", "Best Restaurant", "A great place to eat", "password123", "Italian", "$$$", null, null);

        // Crear una lista de Review (puedes ajustar según tus necesidades)
        List<Review> reviews = Collections.emptyList(); // Inicializa con valores apropiados si es necesario

        // Crear un objeto Review
        Review review = new Review("1", reviews, place);

        // Verificar que el Review se haya creado correctamente
        assertNotNull(review);
        assertEquals("1", review.getId());
        assertEquals(reviews, review.getReview());
        assertEquals(place, review.getPlace());
    }

    @Test
    public void testUpdateReview() {
        // Crear un objeto Place inicial
        Place place1 = new Place("1", "Best Restaurant", "A great place to eat", "password123", "Italian", "$$$", null, null);

        // Crear un objeto Review
        Review review = new Review("1", Collections.emptyList(), place1);

        // Crear un nuevo Place para la actualización
        Place place2 = new Place("2", "New Restaurant", "Another great place to eat", "password123", "Mexican", "$", null, null);

        // Crear un ReviewDto para la actualización
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setPlace(place2);

        // Actualizar el Review
        review.update(reviewDto);

        // Verificar que el Review se haya actualizado correctamente
        assertEquals(place2, review.getPlace());
    }

    @Test
    public void testReviewEquality() {
        // Crear un objeto Place
        Place place = new Place("1", "Best Restaurant", "A great place to eat", "password123", "Italian", "$$$", null, null);

        // Crear dos Reviews iguales
        Review review1 = new Review("1", Collections.emptyList(), place);
        Review review2 = new Review("1", Collections.emptyList(), place);

        // Verificar que dos Reviews con los mismos valores son iguales
        assertEquals(review1, review2);
        assertEquals(review1.hashCode(), review2.hashCode());
    }

    @Test
    public void testReviewToString() {
        // Crear un objeto Place
        Place place = new Place("1", "Best Restaurant", "A great place to eat", "password123", "Italian", "$$$", null, null);

        // Crear un Review
        Review review = new Review("1", Collections.emptyList(), place);
        
        // Verificar el formato de la representación en cadena
        assertTrue(review.toString().contains("Review{id='1'"));
        assertTrue(review.toString().contains("place=" + place));
    }
}
