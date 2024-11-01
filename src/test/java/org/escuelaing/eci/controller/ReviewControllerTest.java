package org.escuelaing.eci.controller;

import org.escuelaing.eci.Controller.ReviewController;
import org.escuelaing.eci.repository.review.Review;
import org.escuelaing.eci.service.review.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReview() {
        // Arrange
        Review review1 = new Review("1", new ArrayList<>(), null);
        Review review2 = new Review("2", new ArrayList<>(), null);
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        when(reviewService.all()).thenReturn(reviews);

        // Act
        ArrayList<Review> result = reviewController.getReview();

        // Assert
        assertEquals(2, result.size());
        verify(reviewService, times(1)).all();
    }

    @Test
    void testSaveReview() {
        // Arrange
        Review review = new Review("1", new ArrayList<>(), null);
        when(reviewService.save(any(Review.class))).thenReturn(review);

        // Act
        Review result = reviewController.saveReview(review);

        // Assert
        assertEquals(review, result);
        verify(reviewService, times(1)).save(review);
    }

    @Test
    void testGetReviewByIdFound() {
        // Arrange
        Review review = new Review("1", new ArrayList<>(), null);
        when(reviewService.findById("1")).thenReturn(Optional.of(review));

        // Act
        Optional<Review> result = reviewController.getReview("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(review, result.get());
        verify(reviewService, times(1)).findById("1");
    }

    @Test
    void testGetReviewByIdNotFound() {
        // Arrange
        when(reviewService.findById("1")).thenReturn(Optional.empty());

        // Act
        Optional<Review> result = reviewController.getReview("1");

        // Assert
        assertFalse(result.isPresent());
        verify(reviewService, times(1)).findById("1");
    }

    @Test
    void testCreateReview() {
        // Arrange
        Review review = new Review("1", new ArrayList<>(), null);
        when(reviewService.save(any(Review.class))).thenReturn(review);

        // Act
        Review result = reviewController.createReview(review);

        // Assert
        assertEquals(review, result);
        verify(reviewService, times(1)).save(review);
    }

    @Test
    void testUpdateReviewFound() {
        // Arrange
        Review review = new Review("1", new ArrayList<>(), null);
        when(reviewService.update(any(Review.class), anyString())).thenReturn(review);

        // Act
        Review result = reviewController.updateUser(review, "1");

        // Assert
        assertEquals(review, result);
        verify(reviewService, times(1)).update(review, "1");
    }

    @Test
    void testUpdateReviewNotFound() {
        // Arrange
        Review review = new Review("1", new ArrayList<>(), null);
        when(reviewService.update(any(Review.class), anyString())).thenReturn(null);

        // Act
        Review result = reviewController.updateUser(review, "1");

        // Assert
        assertNull(result);
        verify(reviewService, times(1)).update(review, "1");
    }

    @Test
    void testDeleteReviewFound() {
        // Arrange
        Review review = new Review("1", new ArrayList<>(), null);
        when(reviewService.deleteById("1")).thenReturn(review);

        // Act
        Review result = reviewController.deleteReview("1");

        // Assert
        assertEquals(review, result);
        verify(reviewService, times(1)).deleteById("1");
    }

    @Test
    void testDeleteReviewNotFound() {
        // Arrange
        when(reviewService.deleteById("1")).thenThrow(new RuntimeException("Review with ID 1 not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewController.deleteReview("1");
        });
        assertEquals("Review with ID 1 not found", exception.getMessage());
        verify(reviewService, times(1)).deleteById("1");
    }

    @Test
    void testTestEndpoint() {
        // Act
        String result = reviewController.testEndpoint();

        // Assert
        assertEquals("Controller is working!", result);
    }
}
