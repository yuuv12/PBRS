package com.pbrs.mybatis.pojo;
/*
Date: 6/14/2024
Author: lu0qlng
Description: 抽象类，用于存储最最相似的书籍编号
*/

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Similarity {

    private Integer colla_1;
    private Integer colla_2;
    private Integer colla_3;
    private Integer colla_4;
    private Integer colla_5;
    private Integer content_1;
    private Integer content_2;
    private Integer content_3;
    private Integer content_4;
    private Integer content_5;
    private Integer pic_1;
    private Integer pic_2;
    private Integer pic_3;
    private Integer pic_4;
    private Integer pic_5;
    private Integer index;

    public Similarity(Integer colla_1, Integer colla_2, Integer colla_3, Integer colla_4, Integer colla_5, Integer content_1, Integer content_2, Integer content_3, Integer content_4, Integer content_5, Integer pic_1, Integer pic_2, Integer pic_3, Integer pic_4, Integer pic_5, Integer index) {
        this.colla_1 = colla_1;
        this.colla_2 = colla_2;
        this.colla_3 = colla_3;
        this.colla_4 = colla_4;
        this.colla_5 = colla_5;
        this.content_1 = content_1;
        this.content_2 = content_2;
        this.content_3 = content_3;
        this.content_4 = content_4;
        this.content_5 = content_5;
        this.pic_1 = pic_1;
        this.pic_2 = pic_2;
        this.pic_3 = pic_3;
        this.pic_4 = pic_4;
        this.pic_5 = pic_5;
        this.index = index;
    }

    public Similarity() {
    }

    public Integer getColla_1() {
        return colla_1;
    }

    public void setColla_1(Integer colla_1) {
        this.colla_1 = colla_1;
    }

    public Integer getColla_2() {
        return colla_2;
    }

    public void setColla_2(Integer colla_2) {
        this.colla_2 = colla_2;
    }

    public Integer getColla_3() {
        return colla_3;
    }

    public void setColla_3(Integer colla_3) {
        this.colla_3 = colla_3;
    }

    public Integer getColla_4() {
        return colla_4;
    }

    public void setColla_4(Integer colla_4) {
        this.colla_4 = colla_4;
    }

    public Integer getColla_5() {
        return colla_5;
    }

    public void setColla_5(Integer colla_5) {
        this.colla_5 = colla_5;
    }

    public Integer getContent_1() {
        return content_1;
    }

    public void setContent_1(Integer content_1) {
        this.content_1 = content_1;
    }

    public Integer getContent_2() {
        return content_2;
    }

    public void setContent_2(Integer content_2) {
        this.content_2 = content_2;
    }

    public Integer getContent_3() {
        return content_3;
    }

    public void setContent_3(Integer content_3) {
        this.content_3 = content_3;
    }

    public Integer getContent_4() {
        return content_4;
    }

    public void setContent_4(Integer content_4) {
        this.content_4 = content_4;
    }

    public Integer getContent_5() {
        return content_5;
    }

    public void setContent_5(Integer content_5) {
        this.content_5 = content_5;
    }

    public Integer getPic_1() {
        return pic_1;
    }

    public void setPic_1(Integer pic_1) {
        this.pic_1 = pic_1;
    }

    public Integer getPic_2() {
        return pic_2;
    }

    public void setPic_2(Integer pic_2) {
        this.pic_2 = pic_2;
    }

    public Integer getPic_3() {
        return pic_3;
    }

    public void setPic_3(Integer pic_3) {
        this.pic_3 = pic_3;
    }

    public Integer getPic_4() {
        return pic_4;
    }

    public void setPic_4(Integer pic_4) {
        this.pic_4 = pic_4;
    }

    public Integer getPic_5() {
        return pic_5;
    }

    public void setPic_5(Integer pic_5) {
        this.pic_5 = pic_5;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Similarity{" +
                "colla_1=" + colla_1 +
                ", colla_2=" + colla_2 +
                ", colla_3=" + colla_3 +
                ", colla_4=" + colla_4 +
                ", colla_5=" + colla_5 +
                ", content_1=" + content_1 +
                ", content_2=" + content_2 +
                ", content_3=" + content_3 +
                ", content_4=" + content_4 +
                ", content_5=" + content_5 +
                ", pic_1=" + pic_1 +
                ", pic_2=" + pic_2 +
                ", pic_3=" + pic_3 +
                ", pic_4=" + pic_4 +
                ", pic_5=" + pic_5 +
                ", index=" + index +
                '}';
    }

    public List<Integer> getList(){
        List<Integer> idxList = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields){
            try {
                field.setAccessible(true);
                if (field.get(this) != null && !field.getName().equals("index")){
                    idxList.add((Integer) field.get(this));
;                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        return idxList;
    }


}
