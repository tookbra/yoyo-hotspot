package com.mars.yoyo.hotspot.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author tookbra
 * @date 2018/7/10
 * @description
 */
public class CTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入红包金额：");
        double money = scan.nextDouble();
        System.out.println("请输入红包数量：");
        int count = scan.nextInt();

        List<Double> list = openRedPacket(money, count);
        Double [] redMoney = new Double[count];
        for(int i = 0; i < list.size(); i ++){
            redMoney[i] = list.get(i);
        }
        for(int i = 0; i < count; i ++){
            System.out.println("用户"+i+"得到红包"+redMoney[i]+"元");
        }
    }

    private static final double MIN_MONEY = 0.01;
    private static final double MAX_MONEY = 10;

    /**
     *
     * randomRedPacket 核心随机生成红包算法
     * @param totalMoney 红包金额
     * @param totalCount 红包数量
     * @param minMoney 最小红包
     * @param maxMoney 最大红包
     * @return
     */
    public static double randomRedPacket(double totalMoney,int totalCount,double minMoney,double maxMoney){
        //如果是单发红包，则对方全领
        if(totalCount == 1){
            return (double)Math.round(totalMoney*100) / 100;
        }
        //如果最小红包和最大红包相等，默认最小红包
        if(minMoney == maxMoney){
            return minMoney;
        }
        //设置最大值，防止红包溢出
        double maxMoney_ = maxMoney > totalMoney ? totalMoney :maxMoney;
        //随机分配红包(生成随机红包范围：minMoney < distributionMoney < maxMoney_)
        double distributionMoney = (double)Math.round(((double)Math.random() * (maxMoney_ - minMoney) + minMoney )*100) / 100;
        //得到剩余红包
        double remainMoney = totalMoney - distributionMoney;
        //红包是否领完
        if(isFinished(remainMoney, totalCount-1)){
            return distributionMoney;
        }else{ //剩余红包随机分配
            double avg = remainMoney / (totalCount -1);
            if(avg < MIN_MONEY){
                return randomRedPacket(totalMoney, totalCount, minMoney, distributionMoney);
            }else if(avg > MAX_MONEY){
                return randomRedPacket(totalMoney, totalCount, distributionMoney, maxMoney);
            }
        }
        return distributionMoney;
    }

    /**
     *
     * isFinished 判断红包是否领取完  根据平均金额判断红包内的金钱是否发放完
     * @param currentMoney 当前金额
     * @param currentCount 当前领取红包数
     * @return
     */
    public static boolean isFinished(double currentMoney,int currentCount){
        double avg = currentMoney / currentCount;
        if(avg < MIN_MONEY){
            return false;
        }else if(avg > MAX_MONEY){
            return false;
        }
        return true;
    }


    private static final float TIMES = 2.1f;

    /**
     *
     * openRedPacket 用户红包分配详情
     * @param totalMoney
     * @param totalCount
     * @return
     */
    public static List<Double> openRedPacket(double totalMoney, int totalCount){
        if(!isFinished(totalMoney, totalCount)){
            return null;
        }
        List<Double> distributionMoneyList = new ArrayList<Double>();
        //如果随机生成的红包大于指定最大值，则默认
        double max = (double) totalMoney * TIMES / totalCount > MAX_MONEY ? MAX_MONEY : (double) totalMoney * TIMES / totalCount;
        //将获得的红包放入集合
        for(int i = 0; i  < totalCount ; i ++){
            double distributionMoney = randomRedPacket(totalMoney, totalCount - i, MIN_MONEY, max);
            distributionMoneyList.add(distributionMoney);
            totalMoney -= distributionMoney;
        }
        return distributionMoneyList;
    }
}
