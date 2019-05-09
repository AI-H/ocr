package com.feiek.cloud.entity;


import java.util.List;

public class ImageWord {

    private String item;
    private String itemstring;
    private List<Coord> itemcoord;

    private List<Word> words;


    public String getItemstring() {
        return itemstring;
    }

    public void setItemstring(String itemstring) {
        this.itemstring = itemstring;
    }

    public List<Coord> getItemcoord() {
        return itemcoord;
    }

    public void setItemcoord(List<Coord> itemcoord) {
        this.itemcoord = itemcoord;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    class Word{
        private String cha;
        private String weizhi;

        public String getCha() {
            return cha;
        }

        public void setCha(String cha) {
            this.cha = cha;
        }

        public String getWeizhi() {
            return weizhi;
        }

        public void setWeizhi(String weizhi) {
            this.weizhi = weizhi;
        }
    }

    class Coord{
        private String zuobiao;
        private Integer values;

        public String getZuobiao() {
            return zuobiao;
        }

        public void setZuobiao(String zuobiao) {
            this.zuobiao = zuobiao;
        }

        public Integer getValues() {
            return values;
        }

        public void setValues(Integer values) {
            this.values = values;
        }
    }

}



