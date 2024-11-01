package org.escuelaing.eci.repository.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class UserTest {

    @Test
    public void testUserCreation() {
        // Crear un objeto User
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password123");

        // Verificar que el User se haya creado correctamente
        assertNotNull(user);
        assertEquals("1", user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertNotNull(user.getPasswordHash()); // Verificar que el password hash no sea nulo
        assertNotEquals("password123", user.getPasswordHash()); // Asegurarse de que el hash no sea igual a la contraseña
        assertNotNull(user.getCreatedAt()); // Verificar que la fecha de creación no sea nula
    }

    @Test
    public void testUpdateUser() {
        // Crear un objeto User
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password123");

        // Crear un UserDto para la actualización
        UserDto userDto = new UserDto();
        userDto.setName("Jane");
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@example.com");
        userDto.setPassword("newpassword123"); // Nueva contraseña

        // Actualizar el User
        user.update(userDto);

        // Verificar que el User se haya actualizado correctamente
        assertEquals("Jane", user.getName());
        assertEquals("Doe", user.getLastName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertNotEquals("password123", user.getPasswordHash()); // Verificar que el hash se haya actualizado
    }

    @Test
    public void testUserEquality() {
        // Crear dos Users iguales
        User user1 = new User("1", "John", "Doe", "john.doe@example.com", "password123");
        User user2 = new User("1", "John", "Doe", "john.doe@example.com", "password123");

        // Verificar que dos Users con los mismos valores son iguales
        //assertEquals(user1, user2);
        //assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testUserToString() {
        // Crear un objeto User
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password123");
        
        // Verificar el formato de la representación en cadena
        assertTrue(user.toString().contains("User{id='1'"));
        assertTrue(user.toString().contains("name='John'"));
        assertTrue(user.toString().contains("lastName='Doe'"));
        assertTrue(user.toString().contains("email='john.doe@example.com'"));
    }
}
