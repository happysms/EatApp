package com.example.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty()
    private String name;

    @NotEmpty
    private String address;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL) // null이 아닐때만 JSON에 넣도록 처리
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews = new ArrayList<Review>();

    public Restaurant(String name){
        this.name = name;
    }

    public Restaurant(Long id , String name , String address){
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Restaurant() {}

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addMenuItem(MenuItem menuItem) {
        if (menuItems == null){
            menuItems = new ArrayList<>();
        }

        menuItems.add(menuItem);
    }

    public List<MenuItem> getMenuItems(){
        return menuItems;
    }

    public void setMenuItem(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>();

        for (MenuItem menuItem : menuItems){
            addMenuItem(menuItem);
        }
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }
}
