package com.example.demo.fund;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseData {
    private static Double principalSum = 100D;//总本金
    private static Double actualSum = 100D;//最终金额
    private static Double principal = 100D;//定投金额
    private static Double targetAssertionUp = 100D;//目标收益率
    private static Double targetAssertionDoen = -2D;//目标收益率
    private static Date start;//开始时间 2009-09-30
    private static Date end;//结束时间，结束时间要小于2021-05-14
    private static int interval;//间隔几年
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar rightNow = Calendar.getInstance();
    static {
        try {
            targetAssertionDoen = 100D;//止损
            targetAssertionUp = 200D;//止盈
            interval = 1;
            start = dateFormat.parse("2008-05-14");
//            start = dateFormat.parse("2008-05-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Entity entity = DataUtil.get("src/main/java/com/example/demo/fund/data/160119.txt");//南方中证500ETF联接A
        for (int i=0;i>-13;i--) {
            principalSum = 500D;
            actualSum = 500D;
            principal = 500D;
            rightNow.setTime(start);
            rightNow.add(Calendar.YEAR, interval);
            start = rightNow.getTime();
            getProfit(entity, start);
            System.out.println();
        }
    }
    private static Calendar cl = Calendar.getInstance();
    private static void getProfit(Entity entity, Date start) {
        rightNow.setTime(start);
        rightNow.add(Calendar.YEAR, interval);
        end = rightNow.getTime();

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(4);
        for (int i = entity.LSJZList.size();i>0;i--) {
            Entity.Item item = entity.LSJZList.get(i-1);
            Date FSRQ = item.FSRQ;//日期
            cl.setTime(FSRQ);
            if (FSRQ.after(start) && FSRQ.before(end)) {
                String JZZZL = item.JZZZL;//收益率
                if (StringUtils.isEmpty(JZZZL)) {
//                    System.out.println("发现一个没收益率的" + FSRQ);
                    continue;
                }
                Double rate = new Double(JZZZL);
                actualSum = ArithmeticUtils.add(actualSum,ArithmeticUtils.div(ArithmeticUtils.mul(actualSum , rate) , 100));
                actualSum = new Double(nf.format(actualSum));

                if ((cl.get(Calendar.DAY_OF_WEEK)-1)==2) {
                    actualSum = ArithmeticUtils.add(actualSum, principal);//最终金额
                    principalSum = ArithmeticUtils.add(principalSum, principal);//本金
                }

//                if (rate < targetAssertionDoen) {
//                    actualSum += principal;//最终金额
//                    principalSum += principal;//本金
//                }
                /*if (rate >  targetAssertionUp){
                    actualSum -= principal;//最终金额
                    principalSum -= principal;//本金
                }*/
            }
        }
//        System.out.print("本金：%f"+principalSum+"    最终金额："+actualSum);
        System.out.format("本金：%.4f\t    最终金额：%.4f\t    收益：%.4f\t    收益率：%.4f\t    日期：%s", principalSum,actualSum,(actualSum - principalSum),((actualSum - principalSum)/actualSum),(dateFormat.format(start)+"~"+dateFormat.format(end)));
    }
}
