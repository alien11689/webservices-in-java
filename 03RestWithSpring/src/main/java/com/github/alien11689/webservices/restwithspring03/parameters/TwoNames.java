package com.github.alien11689.webservices.restwithspring03.parameters;

import javax.ws.rs.QueryParam;

public class TwoNames {
    @QueryParam("name1")
    private String name1;

    @QueryParam("name2")
    private String name2;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
