package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTests {

    @Test
    public void creation(){
        Restaurant restaurant = new Restaurant(1004L, "Bob zip" , "seoul");
        assertEquals("Bob zip" , restaurant.getName());
        assertEquals(1004L , restaurant.getId());
    }

    @Test
    public void information(){
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "seoul");
        assertEquals("Bob zip in seoul" , restaurant.getName() + " in " + restaurant.getAddress());
    }
}