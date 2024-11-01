package org.escuelaing.eci.controller;

import org.escuelaing.eci.Controller.PlaceController;
import org.escuelaing.eci.repository.place.Place;
import org.escuelaing.eci.service.place.PlaceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;

    @Test
    public void testGetPlaces() throws Exception {
        ArrayList<Place> places = new ArrayList<>();
        places.add(new Place("1", "Place 1", "Description 1", "password", "Food Type 1", "Value 1", null, null));

        when(placeService.all()).thenReturn(places);

        mockMvc.perform(get("/place"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Place 1"));
    }

    @Test
    public void testSavePlace() throws Exception {
        Place place = new Place("2", "Place 2", "Description 2", "password", "Food Type 2", "Value 2", null, null);

        when(placeService.save(Mockito.any(Place.class))).thenReturn(place);

        mockMvc.perform(post("/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"2\", \"name\": \"Place 2\", \"description\": \"Description 2\", \"password\": \"password\", \"foodType\": \"Food Type 2\", \"value\": \"Value 2\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("Place 2"));
    }

    @Test
    public void testGetPlaceById() throws Exception {
        Place place = new Place("1", "Place 1", "Description 1", "password", "Food Type 1", "Value 1", null, null);

        when(placeService.findById("1")).thenReturn(Optional.of(place));

        mockMvc.perform(get("/place/getPlace/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Place 1"));
    }

    @Test
    public void testUpdatePlace() throws Exception {
        Place updatedPlace = new Place("1", "Updated Place", "Updated Description", "password", "Updated Food Type", "Updated Value", null, null);

        when(placeService.update(Mockito.any(Place.class), Mockito.eq("1"))).thenReturn(updatedPlace);

        mockMvc.perform(put("/place/update/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Updated Place\", \"description\": \"Updated Description\", \"password\": \"password\", \"foodType\": \"Updated Food Type\", \"value\": \"Updated Value\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Updated Place"));
    }

    @Test
    public void testDeletePlace() throws Exception {
        mockMvc.perform(delete("/place/delete/{id}", "1"))
                .andExpect(status().isOk());
    }
}
