package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NameAndUrl {
    String name;
    String url;
    public static void main(String[] args) throws Exception {
         NameAndUrl[] nameAndUrls = new NameAndUrl[10];
         Myclazz[] myclazzs = new Myclazz[10];
        List<NameAndUrl> arrayList = new ArrayList<NameAndUrl>();
        List<Myclazz> myclazz = new ArrayList<Myclazz>();
        myclazz.add(new Myclazz());
        arrayList.add(new NameAndUrl());
        System.out.println(arrayList);
        System.out.println(myclazz);
    }
}
