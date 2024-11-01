package org.escuelaing.eci.repository.rating;

import org.escuelaing.eci.repository.place.Place;
import org.escuelaing.eci.repository.review.Review;
import org.escuelaing.eci.repository.user.UserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    @Test
    public void testRatingCreation() {
        // Crear objetos Review y Place necesarios
        Review review = new Review(); // Inicializa con valores apropiados
        Place place = new Place("1", "Best Restaurant", "A great place to eat", "password123", "Italian", "$$$", null, null);

        // Crear un objeto Rating
        Rating rating = new Rating("1", review, place);

        // Verificar que el Rating se haya creado correctamente
        assertNotNull(rating);
        assertEquals("1", rating.getId());
        assertEquals(review, rating.getReview());
        assertEquals(place, rating.getPlace());
    }

    @Test
    public void testUpdateRating() {
        // Crear objetos Review y Place iniciales
        Review review1 = new Review(); // Inicializa con valores apropiados
        Place place1 = new Place("1", "Best Restaurant", "A great place to eat", "password123", "Italian", "$$$", null, null);

        // Crear un objeto Rating
        Rating rating = new Rating("1", review1, place1);

        // Crear nuevos objetos Review y Place para la actualización
        Review review2 = new Review(); // Inicializa con valores apropiados
        Place place2 = new Place("2", "New Restaurant", "Another great place to eat", "password123", "Mexican", "$", null, null);

        // Crear un RatingDto para la actualización
        RatingDto ratingDto = new RatingDto();
        ratingDto.setReview(review2);
        ratingDto.setPlace(place2);

        // Actualizar el Rating
        rating.update(ratingDto);

        // Verificar que el Rating se haya actualizado correctamente
        assertEquals(review2, rating.getReview());
        assertEquals(place2, rating.getPlace());
    }

    @Test
    public void testRatingEquality() {
        Review review = new Review(); // Inicializa con valores apropiados
        Place place = new Place("1", "Best Restaurant", "A great place to eat", "password123", "Italian", "$$$", null, null);

        Rating rating1 = new Rating("1", review, place);
        Rating rating2 = new Rating("1", review, place);

        // Verificar que dos Ratings con los mismos valores son iguales
        assertEquals(rating1, rating2);
        assertEquals(rating1.hashCode(), rating2.hashCode());
    }

    @Test
    public void testRatingToString() {
        Review review = new Review(); // Inicializa con valores apropiados
        Place place = new Place("1", "Best Restaurant", "A great place to eat", "password123", "Italian", "$$$", null, null);

        Rating rating = new Rating("1", review, place);
        
        // Verificar el formato de la representación en cadena
        assertTrue(rating.toString().contains("Rating{id='1'"));
        assertTrue(rating.toString().contains("review=" + review));
        assertTrue(rating.toString().contains("place=" + place));
    }
}
