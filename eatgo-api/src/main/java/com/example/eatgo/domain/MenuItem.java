package com.example.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    private  String name;

    @Transient  //디비에 넣지 않는다.
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) // false가 아니라면 제이슨에서 넣는다.
    private boolean destroy;

    public MenuItem(String name){
        this.name = name;
    }

    public MenuItem() {

    }

    public String getName(){
        return name;
    }
}
