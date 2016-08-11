package com.weidu.weiduyelp;

/**
 * Created by adimv on 2016/7/29.
 */
public class Restaurant {
    public int image;
    public String name;
    public String location;
    public String food_type;
    public String cost;

    public Restaurant(){
        super();
    }
    public Restaurant(int image,String name,String location,String food_type,String cost){
        this.image = image;
        this.name = name;
        this.location = location;
        this.food_type = food_type;
        this.cost = cost;
    }
}
