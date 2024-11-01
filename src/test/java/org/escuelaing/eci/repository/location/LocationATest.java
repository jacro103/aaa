package org.escuelaing.eci.repository.location;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationATest {

    @Test
    public void testLocationACreation() {
        LocationDto locationDto = new LocationDto(40.7128f, -74.0060f, "New York, NY");
        LocationA location = new LocationA(locationDto);

        assertNull(location.getId()); // ID should be null initially
        assertEquals(40.7128f, location.getLat());
        assertEquals(-74.0060f, location.getLon());
        assertEquals("New York, NY", location.getAddress());
    }

    @Test
    public void testLocationAUpdate() {
        LocationA location = new LocationA("1", 40.7128f, -74.0060f, "New York, NY");
        LocationDto newLocationDto = new LocationDto(34.0522f, -118.2437f, "Los Angeles, CA");

        location.update(newLocationDto);

        assertEquals(34.0522f, location.getLat());
        assertEquals(-118.2437f, location.getLon());
        assertEquals("Los Angeles, CA", location.getAddress());
    }

    @Test
    public void testEqualsAndHashCode() {
        LocationA location1 = new LocationA("1", 40.7128f, -74.0060f, "New York, NY");
        LocationA location2 = new LocationA("1", 40.7128f, -74.0060f, "New York, NY");
        LocationA location3 = new LocationA("2", 34.0522f, -118.2437f, "Los Angeles, CA");

        assertEquals(location1, location2);
        assertNotEquals(location1, location3);
        assertEquals(location1.hashCode(), location2.hashCode());
    }

    @Test
    public void testToString() {
        LocationA location = new LocationA("1", 40.7128f, -74.0060f, "New York, NY");
        String expectedString = "Location{id=1, lat=40.7128, lon=-74.006, address='New York, NY'}";
        assertEquals(expectedString, location.toString());
    }
}
