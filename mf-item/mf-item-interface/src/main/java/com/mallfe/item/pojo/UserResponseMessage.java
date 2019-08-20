package com.mallfe.item.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserResponseMessage implements Serializable {
    private List<CityMessage> lastVisitCityList;
    private List<CityMessage> hotCityList;
    private List<CityMessage> allCityList;
}
