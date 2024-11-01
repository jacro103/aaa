package org.escuelaing.eci.controller;

import org.escuelaing.eci.Controller.PreferenceController;
import org.escuelaing.eci.repository.preference.Preference;
import org.escuelaing.eci.service.preference.PreferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PreferenceControllerTest {

    @InjectMocks
    private PreferenceController preferenceController;

    @Mock
    private PreferenceService preferenceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPreferences() {
        // Arrange
        ArrayList<Preference> preferences = new ArrayList<>();
        preferences.add(new Preference("1", "Vegan", 5));
        when(preferenceService.all()).thenReturn(preferences);

        // Act
        ArrayList<Preference> result = preferenceController.getPreferences();

        // Assert
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        verify(preferenceService, times(1)).all();
    }

    @Test
    void testSavePreference() {
        // Arrange
        Preference preference = new Preference("1", "Vegan", 5);
        when(preferenceService.save(any(Preference.class))).thenReturn(preference);

        // Act
        Preference result = preferenceController.savePreference(preference);

        // Assert
        assertEquals(preference, result);
        verify(preferenceService, times(1)).save(preference);
    }

    @Test
    void testGetPreferenceById() {
        // Arrange
        Preference preference = new Preference("1", "Vegan", 5);
        when(preferenceService.findById("1")).thenReturn(Optional.of(preference));

        // Act
        Optional<Preference> result = preferenceController.getPreference("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(preference, result.get());
        verify(preferenceService, times(1)).findById("1");
    }

    @Test
    void testCreatePreference() {
        // Arrange
        Preference preference = new Preference("1", "Vegan", 5);
        when(preferenceService.save(any(Preference.class))).thenReturn(preference);

        // Act
        Preference result = preferenceController.createPreference(preference);

        // Assert
        assertEquals(preference, result);
        verify(preferenceService, times(1)).save(preference);
    }

    @Test
    void testUpdatePreference() {
        // Arrange
        Preference preference = new Preference("1", "Vegan", 5);
        when(preferenceService.update(any(Preference.class), eq("1"))).thenReturn(preference);

        // Act
        Preference result = preferenceController.updatePreference(preference, "1");

        // Assert
        assertEquals(preference, result);
        verify(preferenceService, times(1)).update(preference, "1");
    }

    @Test
    void testDeletePreference() {
        // Arrange
        Preference preference = new Preference("1", "Vegan", 5);
        when(preferenceService.deleteById("1")).thenReturn(preference);

        // Act
        Preference result = preferenceController.deletePreference("1");

        // Assert
        assertEquals(preference, result);
        verify(preferenceService, times(1)).deleteById("1");
    }

    @Test
    void testTestEndpoint() {
        // Act
        String result = preferenceController.testEndpoint();

        // Assert
        assertEquals("PreferenceController is working!", result);
    }
}
