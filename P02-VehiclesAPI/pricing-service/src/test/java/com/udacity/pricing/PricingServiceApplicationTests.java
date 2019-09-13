package com.udacity.pricing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.json.JSONObject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PricingServiceApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

	@Test
	public void getPrices() throws Exception{
		mockMvc.perform(get("/prices")).andExpect(status()
                .isOk())
				.andExpect(content().json("{\"_embedded\" : { \"prices\" : [{},{},{},{},{},{},{}," +
						"{},{},{},{},{}, {}, {}]}}"));
	}

    @Test
    public void postPrice() throws Exception{
        String PAYLOAD = "{\"VEHICLE_ID\": 1005, \"CURRENCY\": \"PHP\", " +
                "\"PRICE\": 2000000}";

        mockMvc.perform(post("/prices").content(PAYLOAD))
                .andExpect(status().isCreated());
    }

    @Test
    public void getPriceById() throws Exception{
        MvcResult result = mockMvc.perform(get("/prices/1001")).andExpect(status()
                .isOk()).andReturn();
        JSONObject json =  new JSONObject(result.getResponse().getContentAsString());
        Assert.assertEquals(75900, json.getInt("price"));
    }

    @Test
    public void getPriceByInvalidId() throws Exception{
        mockMvc.perform(get("/prices/1003")).andExpect(status()
                .isNotFound());
    }

}
