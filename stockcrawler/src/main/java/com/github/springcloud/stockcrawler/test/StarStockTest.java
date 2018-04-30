package com.github.springcloud.stockcrawler.test;

import com.eaio.uuid.UUID;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.SetUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by ganzhen on 11/02/2018.
 */
public class StarStockTest {
    public static void main(String[] args){
        HttpRequest request = new HttpGetRequest("https://gupiao.baidu.com/stock/sh601766.html");
        //查看网站的编码，填充在这里
        //request.setCharset("gb2312");
//        GeccoEngine.create()
//                //如果pipeline和htmlbean不在同一个包，classpath就要设置到他们的共同父包
//                .classpath("com.github.springcloud.stockcrawler")
//                .start("https://gupiao.baidu.com/stock/sh601766.html","https://gupiao.baidu.com/stock/sh601989.html")
//                .thread(1)
//                .interval(2000)
//                .loop(false)
//                .mobile(false)
//                .run();
        Set<String> list1 = Sets.newHashSet();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        Set<String> list2 = Sets.newHashSet();
        list2.add("2");
        list2.add("3");
        list2.add("4");

        List<String> tmp = Lists.newArrayList();
        //交集
        tmp.addAll(list1);
        tmp.retainAll(list2);
        System.out.println("交集"+ SetUtils.intersection(list1,list2));
        //list1-list2
        tmp.clear();
        tmp.addAll(list1);
        tmp.removeAll(list2);
        System.out.println("list1-list2"+SetUtils.difference(list1,list2));
        //list2-list1
        tmp.clear();
        tmp.addAll(list2);
        tmp.removeAll(list1);
        System.out.println("list2-list1"+SetUtils.difference(list2,list1));
    }
}
