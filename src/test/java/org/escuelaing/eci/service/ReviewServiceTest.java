package org.escuelaing.eci.service;


import org.escuelaing.eci.repository.review.Review;
import org.escuelaing.eci.repository.review.ReviewDto;
import org.escuelaing.eci.repository.review.ReviewMongoRepository;
import org.escuelaing.eci.service.review.ReviewServiceMongoDb;
import org.escuelaing.eci.repository.location.LocationA;
import org.escuelaing.eci.repository.place.Place;
import org.escuelaing.eci.repository.rating.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(properties = {"spring.data.mongodb.uri=mongodb://localhost/testdb"})
public class ReviewServiceTest {
 
    @Mock
    private ReviewMongoRepository reviewMongoRepository;

    @InjectMocks
    private ReviewServiceMongoDb reviewServiceMongoDb;

    @Test
    @Order(1)
    public void testFindAllReviews() {
        float lat = (float) 2.4 ;
        float lon = (float) 4.5 ;
        Rating rating = new Rating(null, null, null);
        LocationA location = new LocationA("1", lat, lon, "calle 4" );
        List<Review> reviewsListMock = Arrays.asList(
                new Review("1", null, new Place("1", "PizzaKamilo", "Las mejores pizzas de zipaquira","pizzaKamilo", "pizza","5",location, rating))

        );
        Mockito.when(reviewMongoRepository.findAll()).thenReturn(reviewsListMock);
        List<Review> reviews = reviewServiceMongoDb.all();
        Assertions.assertNotNull(reviews);
        Assertions.assertEquals(1, reviews.size());
        Assertions.assertEquals("PizzaKamilo", reviews.get(0).getPlace().getName());
    }

    @Test
    @Order(2)
    public void testFindReviewById() {
        float lat = (float) 2.4 ;
        float lon = (float) 4.5 ;
        Rating rating = new Rating(null, null, null);
        LocationA location = new LocationA("1", lat, lon, "calle 4" );
        Optional<Review> reviewMock = Optional.of(new Review("1", null, new Place("1", "PizzaKamilo", "Las mejores pizzas de zipaquira","pizzaKamilo", "pizza","5",location, rating)));
        Mockito.when(reviewMongoRepository.findById("1")).thenReturn(reviewMock);
        Optional<Review> review = reviewServiceMongoDb.findById("1");
        Assertions.assertTrue(review.isPresent());
        Assertions.assertEquals("PizzaKamilo", review.get().getPlace().getName());
    }

    @Test
    @Order(3)
    public void testCreateReview() {
        float lat = (float) 2.4 ;
        float lon = (float) 4.5 ;
        Rating rating = new Rating(null, null, null);
        LocationA location = new LocationA("1", lat, lon, "calle 4" );
        ReviewDto reviewDto = new ReviewDto(new Review(), new Place("1", "PizzaKamilo", "Las mejores pizzas de zipaquira","pizzaKamilo", "pizza","5",location, rating));
        Review reviewMock = new Review(reviewDto);
        Mockito.when(reviewMongoRepository.save(reviewMock)).thenReturn(reviewMock);
        Review savedReview = reviewServiceMongoDb.save(reviewMock);
        Assertions.assertNotNull(savedReview);
        Assertions.assertEquals("PizzaKamilo", savedReview.getPlace().getName());
    }

    @Test
    @Order(4)
    public void testDeleteReviewById() {
        float lat = (float) 2.4 ;
        float lon = (float) 4.5 ;
        Rating rating = new Rating(null, null, null);
        LocationA location = new LocationA("1", lat, lon, "calle 4" );
        Optional<Review> reviewToDelete = Optional.of(new Review("1", null, new Place("1", "PizzaKamilo", "Las mejores pizzas de zipaquira","pizzaKamilo", "pizza","5",location, rating)));
        Mockito.when(reviewMongoRepository.findById("1")).thenReturn(reviewToDelete);
        reviewServiceMongoDb.deleteById("1");
        Mockito.verify(reviewMongoRepository, Mockito.times(1)).deleteById("1");
    }
}
