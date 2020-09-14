package com.example.addfoods;


public class Food {
    public String name;
    public String price;
    public String desc;
    String img;


    public Food() { }

    public Food (String name, String price, String desc, String img){
        this.name=name;
        this.price=price;
        this.desc=desc;
        this.img = img;
    }

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPrice(){
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc(){
        return desc;
    }
    public void setDesc(String desc){ this.desc=desc;}

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
