package org.escuelaing.eci.repository.place;

import org.escuelaing.eci.repository.location.LocationA;
import org.escuelaing.eci.repository.rating.Rating;
import org.escuelaing.eci.repository.rating.RatingDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceTest {

    @Test
    public void testPlaceCreation() {
        LocationA location = new LocationA("1", 40.7128f, -74.0060f, "New York, NY");
        
        // Crear un RatingDto válido
        RatingDto ratingDto = new RatingDto(); // Asegúrate de que RatingDto tenga un constructor adecuado
        Rating rating = new Rating(ratingDto); // Usar el RatingDto para crear el Rating

        Place place = new Place("1", "Best Restaurant", "A great place to eat", "password123", 
                                "Italian", "$$$", location, rating);
        
        assertEquals("1", place.getId());
        assertEquals("Best Restaurant", place.getName());
        assertEquals("A great place to eat", place.getDescription());
        assertEquals("Italian", place.getFoodType());
        assertEquals("$$$", place.getValue());
        assertNotNull(place.getPasswordHash()); // Verifica que el hash de la contraseña no sea nulo
    }

    @Test
    public void testToString() {
        LocationA location = new LocationA("1", 40.7128f, -74.0060f, "New York, NY");
        Rating rating = new Rating(null); // Asumiendo que Rating tiene un constructor vacío
        Place place = new Place("1", "Best Restaurant", "A great place to eat", "password123", 
                                "Italian", "$$$", location, rating);
        
        String expectedString = "Place{id='1', name='Best Restaurant', description='A great place to eat', " +
                                "foodType='Italian', value='$$$', location=Location{id=1, lat=40.7128, lon=-74.0060, address='New York, NY'}, " +
                                "rating=" + rating + "}";
        assertEquals(expectedString, place.toString());
    }
}
