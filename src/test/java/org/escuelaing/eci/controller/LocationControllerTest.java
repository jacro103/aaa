package org.escuelaing.eci.controller;

import org.escuelaing.eci.Controller.LocationController;
import org.escuelaing.eci.repository.location.LocationA;
import org.escuelaing.eci.service.Location.LocationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Test
    public void testGetLocations() throws Exception {
        ArrayList<LocationA> locations = new ArrayList<>();
        locations.add(new LocationA("1", 12.345f, 67.890f, "123 Main St"));

        when(locationService.all()).thenReturn(locations);

        mockMvc.perform(get("/location"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"));
    }

    @Test
    public void testSaveLocation() throws Exception {
        LocationA location = new LocationA("2", 12.345f, 67.890f, "123 Main St");

        when(locationService.save(Mockito.any(LocationA.class))).thenReturn(location);

        mockMvc.perform(post("/location")
                .contentType("application/json")
                .content("{ \"id\": \"2\", \"address\": \"123 Main St\", \"lat\": 12.345, \"lon\": 67.890 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }
}
