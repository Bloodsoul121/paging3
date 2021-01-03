package com.blood.jetpackpaging3.test;

/*
 *  @项目名：  JetpackPaging3
 *  @包名：    com.blood.jetpackpaging3.test
 *  @文件名:   JavaTest
 *  @创建者:   bloodsoul
 *  @创建时间:  2021/1/3 16:38
 *  @描述：    TODO
 */

import java.util.ArrayList;
import java.util.List;

public class JavaTest {

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("aaa");
        list1.add("bbb");
        list1.add("ccc");

        List<String> temp = list1;

        List<String> list2 = new ArrayList<>();
        list2.add("aaa");
        list2.add("bbb");
        list2.add("ccc");

        System.out.println("result " + (temp == list1));
        System.out.println("result " + (list1 == list2));
    }

}
