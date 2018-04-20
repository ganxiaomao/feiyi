package com.github.springcloud.stockcrawler.common;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.math.BigDecimal;

/**
 * Created by ganzhen on 19/04/2018.
 */
public class MathUtils {

    /**
     * 计算pb或者pe的温度
     * @param values 所有历史pb或者pb的值
     * @param current 当前要计算的pb或者pb的值
     * @param begin 指定从values的第几个值开始算，一般来说，这个值默认为values的第一个值
     * @param length 指定计算到values第几个元素，指current当前所处于values中的下标
     * @return
     */
    public static double pbOrPeDegree(double[] values, double current, int begin, int length){
        //1.先计算算术平均值
        Mean mean = new Mean();
        double average = mean.evaluate(values,begin, length);
        //2.计算标准偏差
        StandardDeviation standardDeviation = new StandardDeviation();
        double sd = standardDeviation.evaluate(values,begin,length);
        //3.计算正态分布值
        NormalDistribution normalDistribution = new NormalDistribution(average, sd);
        double degree = normalDistribution.cumulativeProbability(current);
        return degree;
    }

    /**
     * 根据PB和PE的温度，计算对应的股票温度
     * @param pbDegree
     * @param peDegree
     * @return
     */
    public static double stockOrIndicsDegree(double pbDegree, double peDegree){
        double degree = (pbDegree + peDegree)/2;
        return degree*100;
    }

    public static void main(String[] args){
        double[] values = {3.9807,3.9198};
        double current = 3.9198;
        int begin = 0;
        int length = values.length;
        double degree = pbOrPeDegree(values,current,begin,length);
        System.out.println("温度="+degree);
    }
}