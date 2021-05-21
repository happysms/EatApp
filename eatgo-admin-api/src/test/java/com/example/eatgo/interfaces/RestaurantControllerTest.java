package com.example.eatgo.interfaces;
;
import com.example.eatgo.application.RestaurantService;
import com.example.eatgo.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class) // 레스토랑 컨트롤러를 테스트
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean  //가짜 객체로 만든다.
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L , "Zocker house" , "seoul"));
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString( "\"name\":\"Zocker house\"")))
                .andExpect(content().string(
                        containsString( "\"id\":1004"))
        );
    }

    @Test
    public void detailWithExisted() throws Exception {

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("joker house")
                .address("seoul")
                .build();

        MenuItem menuItem = MenuItem.builder()
                .name("kim chi")
                .build();

        restaurant.setMenuItem(Arrays.asList(menuItem));

        Review review = Review.builder()
                .name("joker")
                .score(5)
                .description("great!")
                .build();

        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString( "\"id\":1004")))
                .andExpect(content().string(
                        containsString( "\"name\":\"joker house\"")))
                .andExpect(content().string(
                        containsString("kim chi")

                ));

    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L)).willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidData() throws Exception {

        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        mvc.perform(post(
                "/restaurants")
                .contentType(APPLICATION_JSON)
                .content("{\"name\" : \"BeRyong\",\"address\":\"Seoul\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"));


        verify(restaurantService).addRestaurant(any());  //any 는 어떤 객체가와도 실행 여부를 알려줌. 코드를 성공시킴.
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(APPLICATION_JSON)
                .content("{\"name\" :\"JOKER Bar\", \"address\" : \"Busan\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "JOKER Bar" , "Busan");
    }

    @Test
    public void createWithInValidData() throws Exception {
        mvc.perform(post(
                "/restaurants")
                .contentType(APPLICATION_JSON)
                .content("{\"name\" : \"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateWithInValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(APPLICATION_JSON)
                .content("{\"name\" :\"\", \"address\" : \"\"}"))
                .andExpect(status().isBadRequest());
    }
}