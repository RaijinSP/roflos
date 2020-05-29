package com.raijin.serializer.model;


import com.raijin.serializer.model.annotations.ObjGetter;
import com.raijin.serializer.model.annotations.ParamGetter;

public class TestClass {

    private final String name;
    private final int number;
    private final Double d;
    private final Cutee cuty;

    public TestClass(String name, int number, Double d, Cutee cuty) {
        this.name = name;
        this.number = number;
        this.d = d;
        this.cuty = cuty;
    }

    @ParamGetter
    public String getName() {
        return name;
    }

    @ParamGetter
    public int getNumber() {
        return number;
    }

    @ParamGetter
    public Double getD() {
        return d;
    }

    @ObjGetter
    public Cutee getCutee() {
        return cuty;
    }


    public static class Cutee {
        private final int num;
        private final String query;

        public Cutee(int num, String query) {
            this.num = num;
            this.query = query;
            System.out.println("CUTY");
        }


        @ParamGetter
        public int getNum() {
            return num;
        }

        @ParamGetter
        public String getQuery() {
            return query;
        }
    }
}
