package com.pbrs.mybatis.pojo;
/*
Date: 5/24/2024
Author: lu0qlng
Description: 数据库抽象的对象，包含各数据库column，以及get/set，toString()方法，填在resource下的DataMapper.xml中用于接收数据
*/


import java.lang.reflect.Field;

//Alt+Insert -> Generate Functions
public class Data {
    private Integer index;
    private Integer serial_number;
    private Integer have_iconic_items;

    private String book_name;
    private String author;
    private String cover_color;
    private String awards;
    private String age_appropriate;
    private String introduction;
    private String main_character_book_type;
    private String main_character_animals;
    private String main_character_plants;
    private String main_character_figures;
    private String main_character_foods;
    private String main_character_vehicles;
    private String main_character_others;
    private String protagonist_features;
    private String protagonist_relations;
    private String time_factors;
    private String location_factors;


    private Double page_number;
    private Double is_series;
    private Double have_cover_pic;

    public Data(Integer index, Integer serial_number, Integer have_iconic_items, String book_name, String author, String cover_color, String awards, String age_appropriate, String introduction, String main_character_book_type, String main_character_animals, String main_character_plants, String main_character_figures, String main_character_foods, String main_character_vehicles, String main_character_others, String protagonist_features, String protagonist_relations, String time_factors, String location_factors, Double page_number, Double is_series, Double have_cover_pic) {
        this.index = index;
        this.serial_number = serial_number;
        this.have_iconic_items = have_iconic_items;
        this.book_name = book_name;
        this.author = author;
        this.cover_color = cover_color;
        this.awards = awards;
        this.age_appropriate = age_appropriate;
        this.introduction = introduction;
        this.main_character_book_type = main_character_book_type;
        this.main_character_animals = main_character_animals;
        this.main_character_plants = main_character_plants;
        this.main_character_figures = main_character_figures;
        this.main_character_foods = main_character_foods;
        this.main_character_vehicles = main_character_vehicles;
        this.main_character_others = main_character_others;
        this.protagonist_features = protagonist_features;
        this.protagonist_relations = protagonist_relations;
        this.time_factors = time_factors;
        this.location_factors = location_factors;
        this.page_number = page_number;
        this.is_series = is_series;
        this.have_cover_pic = have_cover_pic;
    }

    public Data() {
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(Integer serial_number) {
        this.serial_number = serial_number;
    }

    public Integer getHave_iconic_items() {
        return have_iconic_items;
    }

    public void setHave_iconic_items(Integer have_iconic_items) {
        this.have_iconic_items = have_iconic_items;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover_color() {
        return cover_color;
    }

    public void setCover_color(String cover_color) {
        this.cover_color = cover_color;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getAge_appropriate() {
        return age_appropriate;
    }

    public void setAge_appropriate(String age_appropriate) {
        this.age_appropriate = age_appropriate;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMain_character_book_type() {
        return main_character_book_type;
    }

    public void setMain_character_book_type(String main_character_book_type) {
        this.main_character_book_type = main_character_book_type;
    }

    public String getMain_character_animals() {
        return main_character_animals;
    }

    public void setMain_character_animals(String main_character_animals) {
        this.main_character_animals = main_character_animals;
    }

    public String getMain_character_plants() {
        return main_character_plants;
    }

    public void setMain_character_plants(String main_character_plants) {
        this.main_character_plants = main_character_plants;
    }

    public String getMain_character_figures() {
        return main_character_figures;
    }

    public void setMain_character_figures(String main_character_figures) {
        this.main_character_figures = main_character_figures;
    }

    public String getMain_character_foods() {
        return main_character_foods;
    }

    public void setMain_character_foods(String main_character_foods) {
        this.main_character_foods = main_character_foods;
    }

    public String getMain_character_vehicles() {
        return main_character_vehicles;
    }

    public void setMain_character_vehicles(String main_character_vehicles) {
        this.main_character_vehicles = main_character_vehicles;
    }

    public String getMain_character_others() {
        return main_character_others;
    }

    public void setMain_character_others(String main_character_others) {
        this.main_character_others = main_character_others;
    }

    public String getProtagonist_features() {
        return protagonist_features;
    }

    public void setProtagonist_features(String protagonist_features) {
        this.protagonist_features = protagonist_features;
    }

    public String getProtagonist_relations() {
        return protagonist_relations;
    }

    public void setProtagonist_relations(String protagonist_relations) {
        this.protagonist_relations = protagonist_relations;
    }

    public String getTime_factors() {
        return time_factors;
    }

    public void setTime_factors(String time_factors) {
        this.time_factors = time_factors;
    }

    public String getLocation_factors() {
        return location_factors;
    }

    public void setLocation_factors(String location_factors) {
        this.location_factors = location_factors;
    }

    public Double getPage_number() {
        return page_number;
    }

    public void setPage_number(Double page_number) {
        this.page_number = page_number;
    }

    public Double getIs_series() {
        return is_series;
    }

    public void setIs_series(Double is_series) {
        this.is_series = is_series;
    }

    public Double getHave_cover_pic() {
        return have_cover_pic;
    }

    public void setHave_cover_pic(Double have_cover_pic) {
        this.have_cover_pic = have_cover_pic;
    }

    public void fillNone(){
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields){
            try {
                field.setAccessible(true);
                if (field.get(this) == null){
                    Class<?> fieldType = field.getType();
                    if (fieldType == String.class) {
                        field.set(this, "");
                    } else if (fieldType == Integer.class) {
                        field.set(this, 0);
                    } else if (fieldType == Double.class) {
                        field.set(this, 0.0);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public String toString() {
        return "Data{" +
                "index=" + index +
                ", serial_number=" + serial_number +
                ", have_iconic_items=" + have_iconic_items +
                ", book_name='" + book_name + '\'' +
                ", author='" + author + '\'' +
                ", cover_color='" + cover_color + '\'' +
                ", awards='" + awards + '\'' +
                ", age_appropriate='" + age_appropriate + '\'' +
                ", introduction='" + introduction + '\'' +
                ", main_character_book_type='" + main_character_book_type + '\'' +
                ", main_character_animals='" + main_character_animals + '\'' +
                ", main_character_plants='" + main_character_plants + '\'' +
                ", main_character_figures='" + main_character_figures + '\'' +
                ", main_character_foods='" + main_character_foods + '\'' +
                ", main_character_vehicles='" + main_character_vehicles + '\'' +
                ", main_character_others='" + main_character_others + '\'' +
                ", protagonist_features='" + protagonist_features + '\'' +
                ", protagonist_relations='" + protagonist_relations + '\'' +
                ", time_factors='" + time_factors + '\'' +
                ", location_factors='" + location_factors + '\'' +
                ", page_number=" + page_number +
                ", is_series=" + is_series +
                ", have_cover_pic=" + have_cover_pic +
                '}';
    }
}
