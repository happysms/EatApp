package com.example.eatgo.application;

import com.example.eatgo.domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach  // 모든 테스트가 실행되기 전에 한번 씩 실행되게끔한다.
    public void setUp(){
        MockitoAnnotations.initMocks(this); // 테스트 객체에 있는 @Mock이 붙은 객체를 초기화하는 객체

        mockRestaurantRepository();
        mockMenuItemRepository();

        restaurantService = new RestaurantService(restaurantRepository , menuItemRepository);
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("kimchi"));

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant(1004L ,"Bob zip" , "seoul");
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);

        given(restaurantRepository.findById((1004L))).willReturn(Optional.of(restaurant));


    }

    @Test
    public void getRestaurantExisted(){

        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);

        assertEquals(restaurant.getId() , 1004L);

        assertEquals(menuItem.getName() , "kimchi");
    }

    @Test
    public void getRestaurantNotExisted(){
        assertThrows(RestaurantNotFoundException.class , () ->{
            restaurantService.getRestaurant(404L);
        });
    }




    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);

        assertEquals(1004L, restaurant.getId());
    }

    @Test
    public void addRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

//        Restaurant saved = Restaurant.builder()
//                .id(1234L)
//                .name("BeRyong")
//                .address("Busan")
//                .build();

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant1 = invocation.getArgument(0);
            restaurant1.setId(1234L);
            return restaurant1;
        });

        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertEquals(1234L, created.getId());
    }

    @Test
    public void updateRestaurant(){
        Restaurant restaurant = new Restaurant(1004L , "Bob zip", "Seoul");

        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L , "sool zip" , "Busan");

        assertEquals("sool zip", restaurant.getName());
        assertEquals("Busan", restaurant.getAddress());
    }
}