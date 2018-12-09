/**
 * 
 */
package com.crossover.techtrial.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;

/**
 * @author ashutosh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RideControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private RideController rideController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Autowired
  RideRepository rideRepository;
  
  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
  }
  
  @Ignore
  @Test
  public void testRideShouldBeRegistered() throws Exception {
    HttpEntity<Object> ride = getHttpEntity(
        "{\n" + 
        "    \"id\": 101,\n" + 
        "    \"startTime\": \"2018-12-01T07:30:00\",\n" + 
        "    \"endTime\": \"2018-12-01T09:30:00\",\n" + 
        "    \"distance\": 10,\n" + 
        "    \"driver\": {\n" + 
        "        \"id\": 2,\n" + 
        "        \"name\": \"Ram\",\n" + 
        "        \"email\": \"ram@gmail.com\",\n" + 
        "        \"registrationNumber\": \"200\"\n" + 
        "    },\n" + 
        "    \"rider\": {\n" + 
        "        \"id\": 5,\n" + 
        "        \"name\": \"Preety\",\n" + 
        "        \"email\": \"preety@gmail.com\",\n" + 
        "        \"registrationNumber\": \"600\"\n" + 
        "    }\n" + 
        "}");
    ResponseEntity<Ride> response = template.postForEntity(
        "/api/ride", ride, Ride.class);
   
    Assert.assertEquals("Ram", response.getBody().getDriver().getName());
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

}
