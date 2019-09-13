package com.udacity.vehicles;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehiclesApiApplicationIntegrationTests {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PriceClient priceClient;

    @MockBean
    private MapsClient mapsClient;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void getCars() throws Exception{
        ResultActions result = mockMvc.perform(get("/cars")).andExpect(status()
                .isOk());

        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void getACar() throws Exception{
        mockMvc.perform(get("/cars/1002")).andExpect(status()
                .isOk());
    }

    @Test
    public void getSwagger() throws Exception{
        mockMvc.perform(get("http://localhost:8080/swagger-ui.html"))
                .andExpect(status().isOk());
    }

    @Test
    public void postCars() throws Exception{
        mockMvc.perform(post("http://localhost:8080/cars/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "       \"createdAt\"  : \"2019-09-05T01:01:01\", \n" +
                        "       \"modifiedAt\" :\"\", \n" +
                        "       \"condition\"\t: \"NEW\",\n" +
                        "       \"details\" : {\n" +
                        "\t       \"body\"\t\t: \"PICK UP\",\n" +
                        "\t       \"model\"\t\t: \"Super Model\",\n" +
                        "\t       \"manufacturer\" : {\n" +
                        "\t       \t\t\"code\" : 1\n" +
                        "\t       },\t\t\n" +
                        "\t       \"numberOfDoors\" : 5,\n" +
                        "\t       \"fuelType\"\t: \"Uranium\",\n" +
                        "\t       \"engine\"\t\t: \"reactor\",\n" +
                        "\t       \"modelYear\"\t: 1985,\n" +
                        "\t       \"productionYear\" : 1985,\n" +
                        "\t       \"externalColor\" : \"WHITE, BLACK STRIPES\"\n" +
                        "       \t},\n" +
                        "       \"lat\"\t: 20,\n" +
                        "       \"lon\"\t: 30\n" +
                        "}                "))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateCars() throws Exception{
        mockMvc.perform(put("http://localhost:8080/cars/1002")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "       \"id\" : 1002,\n" +
                        "       \"createdAt\"  : \"2019-09-05T01:01:01\", \n" +
                        "       \"modifiedAt\" :\"\", \n" +
                        "       \"condition\"\t: \"USED\",\n" +  //changed
                        "       \"details\" : {\n" +
                        "\t       \"body\"\t\t: \"PICK UP\",\n" +
                        "\t       \"model\"\t\t: \"Super Model\",\n" +
                        "\t       \"manufacturer\" : {\n" +
                        "\t       \t\t\"code\" : 1\n" +
                        "\t       },\t\t\n" +
                        "\t       \"numberOfDoors\" : 5,\n" +
                        "\t       \"fuelType\"\t: \"Uranium\",\n" +
                        "\t       \"engine\"\t\t: \"reactor\",\n" +
                        "\t       \"modelYear\"\t: 1985,\n" +
                        "\t       \"productionYear\" : 1985,\n" +
                        "\t       \"externalColor\" : \"WHITE, BLACK STRIPES\"\n" +
                        "       \t},\n" +
                        "       \"lat\"\t: 20,\n" +
                        "       \"lon\"\t: 30\n" +
                        "}                "))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCars() throws Exception{
        mockMvc.perform(delete("http://localhost:8080/cars/1002"))
                .andExpect(status().isOk());

        //this tests that Car has been deleted
        mockMvc.perform(get("http://localhost:8080/cars/1002"))
                .andExpect(status().isNotFound());
    }
}
