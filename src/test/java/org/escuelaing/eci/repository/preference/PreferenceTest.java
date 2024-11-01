package org.escuelaing.eci.repository.preference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@AutoConfigureMockMvc
public class PreferenceTest {

    @Autowired
    private PreferenceMongoRepository preferenceMongoRepository;

    @BeforeEach
    public void setUp() {
        preferenceMongoRepository.deleteAll(); // Limpiar la colección antes de cada prueba
    }

    @Test
    public void testPreferenceCreation() {
        // Crear un objeto Preference
        Preference preference = new Preference("1", "Italian", 5);

        // Guardar el Preference
        Preference savedPreference = preferenceMongoRepository.save(preference);

        // Verificar que se haya guardado correctamente
        assertNotNull(savedPreference);
        assertEquals("1", savedPreference.getId());
        assertEquals("Italian", savedPreference.getFoodType());
        assertEquals(5, savedPreference.getValue());
    }

    @Test
    public void testFindPreferenceById() {
        // Crear un objeto Preference
        Preference preference = new Preference("1", "Italian", 5);
        preferenceMongoRepository.save(preference);

        // Buscar el Preference por ID
        Optional<Preference> foundPreference = preferenceMongoRepository.findById(preference.getId());
        assertTrue(foundPreference.isPresent());
        assertEquals(preference.getFoodType(), foundPreference.get().getFoodType());
    }

    @Test
    public void testUpdatePreference() {
        // Crear un objeto Preference
        Preference preference = new Preference("1", "Italian", 5);
        preferenceMongoRepository.save(preference);

        // Actualizar el Preference
        preference.setFoodType("Mexican");
        preference.setValue(10);
        preferenceMongoRepository.save(preference);

        // Verificar la actualización
        Optional<Preference> updatedPreference = preferenceMongoRepository.findById(preference.getId());
        assertTrue(updatedPreference.isPresent());
        assertEquals("Mexican", updatedPreference.get().getFoodType());
        assertEquals(10, updatedPreference.get().getValue());
    }

    @Test
    public void testDeletePreference() {
        // Crear y guardar un objeto Preference
        Preference preference = new Preference("1", "Italian", 5);
        preferenceMongoRepository.save(preference);

        // Eliminar el Preference
        preferenceMongoRepository.delete(preference);

        // Verificar que el Preference ya no esté presente
        Optional<Preference> deletedPreference = preferenceMongoRepository.findById(preference.getId());
        assertFalse(deletedPreference.isPresent());
    }
}
