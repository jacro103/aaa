package org.escuelaing.eci.service;

import org.escuelaing.eci.repository.place.Place;
import org.escuelaing.eci.repository.place.PlaceRepository;
import org.escuelaing.eci.service.place.PlaceServiceMongoDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaceServiceMongoDbTest {

    @InjectMocks
    private PlaceServiceMongoDb placeService;

    @Mock
    private PlaceRepository placeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        Place place = new Place("1", "Place 1", "Description 1", "password", "Food Type 1", "Value 1", null, null);
        when(placeRepository.save(place)).thenReturn(place);

        Place savedPlace = placeService.save(place);

        assertNotNull(savedPlace);
        assertEquals("1", savedPlace.getId());
        assertEquals("Place 1", savedPlace.getName());
        verify(placeRepository, times(1)).save(place);
    }

    @Test
    public void testFindById() {
        Place place = new Place("1", "Place 1", "Description 1", "password", "Food Type 1", "Value 1", null, null);
        when(placeRepository.findById("1")).thenReturn(Optional.of(place));

        Optional<Place> foundPlace = placeService.findById("1");

        assertTrue(foundPlace.isPresent());
        assertEquals("Place 1", foundPlace.get().getName());
    }

    @Test
    public void testFindById_NotFound() {
        when(placeRepository.findById("2")).thenReturn(Optional.empty());

        Optional<Place> foundPlace = placeService.findById("2");

        assertFalse(foundPlace.isPresent());
    }

    @Test
    public void testAll() {
        List<Place> places = new ArrayList<>();
        places.add(new Place("1", "Place 1", "Description 1", "password", "Food Type 1", "Value 1", null, null));
        places.add(new Place("2", "Place 2", "Description 2", "password", "Food Type 2", "Value 2", null, null));

        when(placeRepository.findAll()).thenReturn(places);

        List<Place> allPlaces = placeService.all();

        assertNotNull(allPlaces);
        assertEquals(2, allPlaces.size());
        verify(placeRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById_Success() {
        Place place = new Place("1", "Place 1", "Description 1", "password", "Food Type 1", "Value 1", null, null);
        when(placeRepository.findById("1")).thenReturn(Optional.of(place));

        placeService.deleteById("1");

        verify(placeRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteById_NotFound() {
        when(placeRepository.findById("2")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            placeService.deleteById("2");
        });

        assertEquals("Place with ID 2 not found", exception.getMessage());
    }

    @Test
    public void testUpdate_Success() {
        Place existingPlace = new Place("1", "Place 1", "Description 1", "password", "Food Type 1", "Value 1", null, null);
        Place updatedPlace = new Place("1", "Updated Place", "Updated Description", "password", "Updated Food Type", "Updated Value", null, null);
        
        when(placeRepository.findById("1")).thenReturn(Optional.of(existingPlace));
        when(placeRepository.save(existingPlace)).thenReturn(existingPlace);

        Place result = placeService.update(updatedPlace, "1");

        assertNotNull(result);
        assertEquals("Updated Place", result.getName());
        verify(placeRepository, times(1)).save(existingPlace);
    }

    @Test
    public void testUpdate_NotFound() {
        Place updatedPlace = new Place("1", "Updated Place", "Updated Description", "password", "Updated Food Type", "Updated Value", null, null);
        
        when(placeRepository.findById("1")).thenReturn(Optional.empty());

        Place result = placeService.update(updatedPlace, "1");

        assertNull(result);
        verify(placeRepository, times(0)).save(any());
    }
}
