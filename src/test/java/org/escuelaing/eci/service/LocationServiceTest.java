package org.escuelaing.eci.service;

import org.escuelaing.eci.repository.location.LocationA;
import org.escuelaing.eci.repository.location.LocationRepository;
import org.escuelaing.eci.service.Location.LocationServiceMongoDb; // Asegúrate de que el import sea correcto
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceMongoDb locationService; 

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testSaveLocation() {
        LocationA location = new LocationA("1", 12.345f, 67.890f, "Location 1");

        when(locationRepository.save(any(LocationA.class))).thenReturn(location);

        LocationA savedLocation = locationService.save(location);

        assertEquals("1", savedLocation.getId());
        assertEquals("Location 1", savedLocation.getAddress()); 
    }

    @Test
    public void testFindById() {
        LocationA location = new LocationA("1", 12.345f, 67.890f, "Location 1");

        when(locationRepository.findById("1")).thenReturn(Optional.of(location));

        Optional<LocationA> foundLocation = locationService.findById("1");

        assertTrue(foundLocation.isPresent());
        assertEquals("Location 1", foundLocation.get().getAddress()); 
    }
}
