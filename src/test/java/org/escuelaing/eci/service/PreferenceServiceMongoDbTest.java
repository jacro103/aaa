package org.escuelaing.eci.service;

import org.escuelaing.eci.repository.preference.Preference;
import org.escuelaing.eci.repository.preference.PreferenceMongoRepository;
import org.escuelaing.eci.service.preference.PreferenceServiceMongoDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PreferenceServiceMongoDbTest {

    @InjectMocks
    private PreferenceServiceMongoDb preferenceService;

    @Mock
    private PreferenceMongoRepository preferenceMongoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePreference() {
        // Arrange
        Preference preference = new Preference("1", "Vegan", 5);
        when(preferenceMongoRepository.save(any(Preference.class))).thenReturn(preference);

        // Act
        Preference result = preferenceService.save(preference);

        // Assert
        assertEquals(preference, result);
        verify(preferenceMongoRepository, times(1)).save(preference);
    }

    @Test
    void testFindByIdFound() {
        // Arrange
        Preference preference = new Preference("1", "Vegan", 5);
        when(preferenceMongoRepository.findById("1")).thenReturn(Optional.of(preference));

        // Act
        Optional<Preference> result = preferenceService.findById("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(preference, result.get());
        verify(preferenceMongoRepository, times(1)).findById("1");
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(preferenceMongoRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Optional<Preference> result = preferenceService.findById("1");

        // Assert
        assertFalse(result.isPresent());
        verify(preferenceMongoRepository, times(1)).findById("1");
    }

    @Test
    void testAllPreferences() {
        // Arrange
        List<Preference> preferences = new ArrayList<>();
        preferences.add(new Preference("1", "Vegan", 5));
        preferences.add(new Preference("2", "Vegetarian", 3));
        when(preferenceMongoRepository.findAll()).thenReturn(preferences);

        // Act
        List<Preference> result = preferenceService.all();

        // Assert
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        verify(preferenceMongoRepository, times(1)).findAll();
    }

    @Test
    void testDeleteByIdFound() {
        // Arrange
        Preference preference = new Preference("1", "Vegan", 5);
        when(preferenceMongoRepository.findById("1")).thenReturn(Optional.of(preference));
        doNothing().when(preferenceMongoRepository).deleteById("1");

        // Act
        Preference result = preferenceService.deleteById("1");

        // Assert
        assertEquals(preference, result);
        verify(preferenceMongoRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteByIdNotFound() {
        // Arrange
        when(preferenceMongoRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            preferenceService.deleteById("1");
        });
        assertEquals("Preference with ID 1 not found", exception.getMessage());
        verify(preferenceMongoRepository, times(1)).findById("1");
        verify(preferenceMongoRepository, never()).deleteById(anyString());
    }

    @Test
    void testUpdateFound() {
        // Arrange
        Preference existingPreference = new Preference("1", "Vegan", 5);
        Preference updatedPreference = new Preference("1", "Vegetarian", 7);
        when(preferenceMongoRepository.findById("1")).thenReturn(Optional.of(existingPreference));
        when(preferenceMongoRepository.save(any(Preference.class))).thenReturn(updatedPreference);

        // Act
        Preference result = preferenceService.update(updatedPreference, "1");

        // Assert
        assertEquals(updatedPreference, result);
        verify(preferenceMongoRepository, times(1)).findById("1");
        verify(preferenceMongoRepository, times(1)).save(any(Preference.class));
    }

    @Test
    void testUpdateNotFound() {
        // Arrange
        Preference updatedPreference = new Preference("1", "Vegetarian", 7);
        when(preferenceMongoRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Preference result = preferenceService.update(updatedPreference, "1");

        // Assert
        assertNull(result);
        verify(preferenceMongoRepository, times(1)).findById("1");
        verify(preferenceMongoRepository, never()).save(any(Preference.class));
    }
}
