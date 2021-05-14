package com.example.demo.fund;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private static Date start;//开始时间 2009-09-30
    private static Date end;//结束时间，结束时间要小于2021-05-14
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar rightNow = Calendar.getInstance();
    static {
        try {
            principalSum = 100D;
            actualSum = 100D;
            principal = 100D;
            start = dateFormat.parse("2015-05-14");
            rightNow.setTime(start);
            rightNow.add(Calendar.YEAR, 1);
            end = rightNow.getTime();
//            end = dateFormat.parse("2017-05-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Entity entity = DataUtil.get("src/main/java/com/example/demo/fund/data/160119.txt");//南方中证500ETF联接A

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(2);
        for (Entity.Item item : entity.LSJZList) {
            Date FSRQ = item.FSRQ;//日期
            if (FSRQ.after(start) && FSRQ.before(end)) {
                String JZZZL = item.JZZZL;//收益率
                if (StringUtils.isEmpty(JZZZL)) {
                    System.out.println("发现一个没收益率的" + FSRQ);
                    continue;
                }
                Double rate = new Double(JZZZL);
                actualSum += actualSum * rate / 100;
                actualSum = new Double(nf.format(actualSum));
                actualSum += principal;//最终金额
                principalSum += principal;//本金
            }
        }
        System.out.println("本金："+principalSum+"    最终金额："+actualSum);
        System.out.println("收益："+ (actualSum - principalSum) +"    收益率："+ (actualSum - principalSum)/actualSum);
    }
}
