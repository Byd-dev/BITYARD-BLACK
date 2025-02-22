package com.ltqh.qh.utils;

import android.util.Log;

import com.ltqh.qh.Api.TradeResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeUtil {
    private static String TAG = "TradeUtil";

    public static double scale(int priceDigit) {
        if (priceDigit == 1) {
            return 0.1;
        } else if (priceDigit == 2) {
            return 0.01;
        } else if (priceDigit == 3) {
            return 0.001;
        } else if (priceDigit == 4) {
            return 0.0001;
        } else if (priceDigit == 5) {
            return 0.00001;
        } else if (priceDigit == 6) {
            return 0.000001;
        } else if (priceDigit == 7) {
            return 0.0000001;
        } else if (priceDigit == 9) {
            return 0.00000001;
        } else {
            return 0;
        }
    }

    /*保留两位小数*/
    public static String getNumberFormat(double value, int scale) {
        BigDecimal bd = new BigDecimal(value);
        String mon = bd.setScale(scale, RoundingMode.DOWN).toString();//保留两位数字，并且是截断不进行四舍五
        return mon;
    }

    /*long转时间*/
    public static String dateToStamp(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String numberHalfUp(double value, int scale) {
        BigDecimal bd = new BigDecimal(value);
        String mon = bd.setScale(scale, RoundingMode.HALF_DOWN).toString();//保留两位数字，四舍五
        return mon;
    }

    /*止损价*/
    public static String StopLossPrice(boolean isBusy, double price, int priceDigit, double lever, double margin, double stopLoss) {
        String stopLossPrice;
        if (isBusy == true) {
            //买多 止损价  =  开仓价 - （开仓价 / 杠杆 * 止损比例）
            stopLossPrice = getNumberFormat(sub(price, mul(div(price, lever, 10), div(stopLoss, margin, 10))), priceDigit);
        } else {
            //买空 止损价  =  开仓价 +（开仓价 / 杠杆 * 止损比例）
            stopLossPrice = getNumberFormat(add(price, mul(div(price, lever, 10), div(stopLoss, margin, 10))), priceDigit);
        }
        return stopLossPrice;
    }

    /*止盈价*/
    public static String StopProfitPrice(boolean isBusy, double price, int priceDigit, double lever, double margin, double stopProfit) {
        String stopProfitPrice;
        if (isBusy == true) {
            //买多 止盈价  =  开仓价 + （开仓价 / 杠杆 * 止损比例）
            stopProfitPrice = getNumberFormat(add(price, mul(div(price, lever, 10), div(stopProfit, margin, 10))), priceDigit);
        } else {
            //买空 止盈价  =  开仓价 -（开仓价 / 杠杆 * 止损比例）
            stopProfitPrice = getNumberFormat(sub(price, mul(div(price, lever, 10), div(stopProfit, margin, 10))), priceDigit);
        }
        return stopProfitPrice;
    }

    /*盈利金额*/
    public static String ProfitAmount(boolean isBusy, double price, int priceDigit, double lever, double margin, double stopProfitPrice) {
        //盈利 金额
        String profitAmount;

        if (isBusy == true) {
            // (stopProfitPrice-price)/(price/lever)  *margin;
            double sub = sub(stopProfitPrice, price);
            double div = div(Math.abs(sub), div(price, lever, 10), 10);
            double mul = mul(div, margin);
            profitAmount = getNumberFormat(mul, 2);

        } else {
            double sub = sub(price, stopProfitPrice);
            double div = div(Math.abs(sub), div(price, lever, 10), 10);
            double mul = mul(div, margin);
            profitAmount = getNumberFormat(mul, 2);

        }

        return profitAmount;
    }


    /*亏损金额*/
    public static String lossAmount(boolean isBusy, double price, int priceDigit, double lever, double margin, double stopProfitPrice) {
        //盈利 金额

        String lossAmount;
        Log.d(TAG, "lossAmount: " + price + "  stopProfitPrice:" + stopProfitPrice + "  lever:" + lever + "  margin:" + margin);

        if (isBusy == true) {
            // (stopProfitPrice-price)/(price/lever)  *margin;
            double sub = sub(price, stopProfitPrice);
            double div = div(Math.abs(sub), div(price, lever, 20), 20);
            double mul = mul(div, margin);
            lossAmount = getNumberFormat(mul, 2);

        } else {
            double sub = sub(stopProfitPrice, price);
            double div = div(Math.abs(sub), div(price, lever, 20), 20);
            double mul = mul(div, margin);

            lossAmount = getNumberFormat(mul, 2);

        }
        Log.d(TAG, "lossAmount:134:  " + lossAmount);
        return lossAmount;
    }


    /*订单盈亏*/
    public static String income(boolean isBuy, double price, double opPrice, double volume) {
        String income;
        if (opPrice == 0) {
            return "0";
        } else {
            if (isBuy) {
                income = getNumberFormat(mul(sub(price, opPrice), volume), 2);
            } else {
                income = getNumberFormat(mul(sub(opPrice, price), volume), 2);
            }
            return income;
        }


    }

    /*浮动盈亏 需要的订单盈亏*/
    public static String incomeAdd(boolean isBuy, double price, double opPrice, double volume) {
        String income;
        if (opPrice == 0) {
            return "0";
        } else {
            if (isBuy) {
                income = getNumberFormat(mul(sub(price, opPrice), volume), 2);
            } else {
                income = getNumberFormat(mul(sub(opPrice, price), volume), 2);
            }
            return income;
        }

    }


    /*加法*/
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /*减法*/
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /*乘法*/
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    /*除法*/
    public static double div(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /*价格*/
    public static void price(List<String> quoteList, String contractCode, TradeResult result) {
        if (quoteList != null) {
            for (String value : quoteList) {
                String[] split1 = value.split(",");
                if (contractCode.equals(split1[0])) {
                    result.setResult(split1[2]);
                }
            }
        }


    }

    /*净盈亏*/
    public static String netIncome(double income, double service) {
        String netIncome;
        netIncome = getNumberFormat((sub(income, service)), 2);
        return netIncome;
    }

    /*盈利百分比=盈利/保证金*/
    public static String profitRate(double content, double margin) {
        Log.d(TAG, "profitRate: " + content + "  --  " + margin);
        double div = div(content, margin, 10);
        double mul = mul(div, 100);
        String numberFormat = getNumberFormat(mul, 2);
        return numberFormat + "%";
    }

    public static String profitRateWithout(double content, double margin) {
        double div = div(content, margin, 10);
        String numberFormat = numberHalfUp(div, 2);
        return numberFormat;
    }


    /*亏损百分比=亏损/保证金*/
    public static String lossRate(double content, double margin) {
        Log.d(TAG, "profitRate: " + content + "  --  " + margin);
        double div = div(content, margin, 20);
        double mul = mul(div, 100);
        String numberFormat = getNumberFormat(mul, 2);
        return numberFormat + "%";
    }

    public static String lossRateWithout(double content, double margin) {
        Log.d(TAG, "profitRate: " + content + "  --  " + margin);
        double div = div(content, margin, 20);
        String numberFormat = numberHalfUp(div, 2);
        return numberFormat;
    }

    /* 杠杆 = 仓位*开仓价格/保证金 */
    public static String lever(double margin, double opPrice, double volume) {
        double mul = mul(volume, opPrice);
        double div = div(mul, margin, 10);
        return getNumberFormat(div, 2);
    }

    /* 仓位 =  杠杆 * 保证金 / 开仓价格*/
    public static String volume(double lever, String margin, double opPrice) {
        if (margin == null) {
            return null;
        }
        double mul = mul(lever, Double.parseDouble(margin));
        double div = div(mul, opPrice, 10);
        return numberHalfUp(div, 4);
    }

    /*保证金=仓位*开仓价格/杠杆*/
    public static String maxMargin(double lever, double margin, double opPrice, double volume, double deposit) {
        //Wilde, [15.05.20 15:06]
        //我用 最大保证金减去当前保证金  做一个值
        //Wilde, [15.05.20 15:06]
        //用最小杠杆· 带入当前的仓位去算保证金 然后减去当前保证金
        //Wilde, [15.05.20 15:06]
        //取两个里面的最小值
        double mul = mul(volume, opPrice);
        double div = div(mul, lever, 10);
        double sub = sub(div, margin);
        double sub1 = sub(deposit, margin);
        double small = small(sub, sub1);
        return getNumberFormat(small, 2);
    }

    public static String minMargin(double margin, double opPrice, double volume) {
        double big = big(sub(div(mul(volume, opPrice), 100, 10), margin), 0);
        return getNumberFormat(big, 2);
    }


    public static double big(double a, double b) {
        BigDecimal a1 = new BigDecimal(a);
        BigDecimal b1 = new BigDecimal(b);
        int bj = a1.compareTo(b1);
        if (bj > 0) {
            return a;
        } else {
            return b;
        }
    }

    public static double small(double a, double b) {
        BigDecimal a1 = new BigDecimal(a);
        BigDecimal b1 = new BigDecimal(b);
        int bj = a1.compareTo(b1);
        if (bj > 0) {
            return b;
        } else {
            return a;
        }
    }


    /*总计支付*/
    public static String total(String margin, String service, String deduction) {
        double marginDou = Double.parseDouble(margin);
        double serviceDou = Double.parseDouble(service);
        double deductionDou = Double.parseDouble(deduction);

        double add = TradeUtil.add(marginDou, serviceDou);
        double sub = TradeUtil.sub(add, deductionDou);
        return TradeUtil.numberHalfUp(sub, 2);
    }

    /*抵扣*/
    public static String deduction(String margin, String prizeTrade, String service) {
        String s = numberHalfUp(TradeUtil.mul(Double.parseDouble(margin), Double.parseDouble(prizeTrade)), 2);
        double add = add(Double.parseDouble(s), Double.parseDouble(service));
        return numberHalfUp(add, 2);
    }


    /*是否递延*/
    public static String defer(String tradeType, boolean isDefer) {
        String defer;
        if (tradeType.equals("1")) {
            defer = isDefer ? "true" : "false";
        } else {
            defer = "false";
        }
        return defer;
    }

    /*下单保证金*/
    public static String marginOrder(String orderType, String marginMarket, String marginLimit) {
        String margin;
        if (orderType.equals("0")) {
            if (marginMarket.length() == 0) {
                margin = null;
            } else {
                margin = marginMarket;
            }
        } else {
            if (marginLimit.length() == 0) {
                margin = null;
            } else {
                margin = marginLimit;
            }
        }
        return margin;
    }

    /*下单 价格*/
    public static String priceOrder(String orderType, String limitPrice) {
        String priceOrder;
        if (orderType.equals("0")) {
            priceOrder = "0";
        } else {
            if (limitPrice.length() == 0) {
                priceOrder = null;
            } else {
                priceOrder = limitPrice;
            }
        }
        return priceOrder;
    }

    /*计算递延费   保证金*杠杆*费率 *0.00045*/
    public static String deferFee(int priceDigit, String defer, double deferBase, String margin, double lever) {
        if (margin == null) {
            return null;
        }
        String deferFee;
        if (defer.equals("true")) {
            deferFee = TradeUtil.numberHalfUp(mul(mul(Double.parseDouble(margin), lever), deferBase), 4);
        } else {
            deferFee = "0";
        }
        return deferFee;

    }


    /*价格从小到大*/
    public static List<String> priceLowToHigh(List<String> quoteList) {
        List<String> quoteList2 = new ArrayList<>();
        Collections.sort(quoteList, (o1, o2) -> {
            String[] split1 = o1.split(",");
            String[] split2 = o2.split(",");
            double sub = TradeUtil.sub(Double.parseDouble(split1[2]), Double.parseDouble(split2[2]));
            if (sub == 0) {
                return (int) TradeUtil.sub(Double.parseDouble(split2[2]), Double.parseDouble(split1[2]));
            }
            return (int) sub;
        });
        for (String quote : quoteList) {
            quoteList2.add(quote);
        }

        return quoteList2;
    }

    /*BTC BCH ETH*/
    public static List<String> homeHot(List<String> quoteList) {
        List<String> quoteList2 = new ArrayList<>();
        for (String quote : quoteList) {
            String[] split = quote.split(",");
            if (split[0].contains("ETH") || split[0].contains("BCH") || split[0].contains("BTC")) {
                quoteList2.add(quote);
            }
        }

        return quoteList2;
    }

    /*除去 BTC BCH ETH*/
    public static List<String> homeList(List<String> quoteList) {
        List<String> quoteList2 = new ArrayList<>();
        for (String quote : quoteList) {
            String[] split = quote.split(",");
            if (!split[0].contains("ETH") && !split[0].contains("BCH") && !split[0].contains("BTC")) {
                quoteList2.add(quote);
            }
        }

        return quoteList2;
    }

    /*字母a-z*/
    public static List<String> nameLowToHigh(List<String> quoteList) {
        List<String> quoteList2 = new ArrayList<>();
        Collections.sort(quoteList, (o1, o2) -> {
            String[] split1 = o1.split(",");
            String[] split2 = o2.split(",");
            int i = split1[0].compareTo(split2[0]);
            if (i > 0) {
                return 1;
            } else {
                return -1;
            }
        });
        for (String quote : quoteList) {
            quoteList2.add(quote);
        }

        return quoteList2;
    }

    /*字母z-a*/
    public static List<String> nameHighToLow(List<String> quoteList) {
        List<String> quoteList2 = new ArrayList<>();
        Collections.sort(quoteList, (o1, o2) -> {
            String[] split1 = o1.split(",");
            String[] split2 = o2.split(",");
            int i = split1[0].compareTo(split2[0]);
            if (i > 0) {
                return -1;
            } else {
                return 1;
            }
        });
        for (String quote : quoteList) {
            quoteList2.add(quote);
        }

        return quoteList2;
    }

    /*价格从大到小*/
    public static List<String> priceHighToLow(List<String> quoteList) {
        List<String> quoteList2 = new ArrayList<>();
        Collections.sort(quoteList, (o1, o2) -> {
            String[] split1 = o1.split(",");
            String[] split2 = o2.split(",");
            double sub = TradeUtil.sub(Double.parseDouble(split2[2]), Double.parseDouble(split1[2]));
            if (sub == 0) {
                return (int) TradeUtil.sub(Double.parseDouble(split1[2]), Double.parseDouble(split2[2]));
            }
            return (int) sub;
        });
        for (String quote : quoteList) {
            quoteList2.add(quote);
        }

        return quoteList2;
    }

    /*涨跌幅从小到大*/
    public static List<String> rangeLowToHigh(List<String> quoteList) {
        List<String> quoteList2 = new ArrayList<>();
        Collections.sort(quoteList, (o1, o2) -> {
            String[] split1 = o1.split(",");
            String[] split2 = o2.split(",");
            double v = Double.valueOf(split1[2]);
            double v1 = Double.valueOf(split1[3]);
            double mul = TradeUtil.mul(TradeUtil.div(TradeUtil.sub(v, v1), v, 2), 100);
            double v2 = Double.valueOf(split2[2]);
            double v3 = Double.valueOf(split2[3]);
            double mul2 = TradeUtil.mul(TradeUtil.div(TradeUtil.sub(v2, v3), v2, 2), 100);

            double sub = TradeUtil.sub(mul, mul2);
            if (sub == 0) {
                return (int) TradeUtil.sub(mul2, mul);
            }
            return (int) sub;
        });
        for (String quote : quoteList) {
            quoteList2.add(quote);
        }

        return quoteList2;
    }

    /*涨跌幅从大到小*/
    public static List<String> rangeHighToLow(List<String> quoteList) {
        List<String> quoteList2 = new ArrayList<>();
        Collections.sort(quoteList, (o1, o2) -> {
            String[] split1 = o1.split(",");
            String[] split2 = o2.split(",");
            double v = Double.valueOf(split1[2]);
            double v1 = Double.valueOf(split1[3]);
            double mul = TradeUtil.mul(TradeUtil.div(TradeUtil.sub(v, v1), v, 2), 100);
            double v2 = Double.valueOf(split2[2]);
            double v3 = Double.valueOf(split2[3]);
            double mul2 = TradeUtil.mul(TradeUtil.div(TradeUtil.sub(v2, v3), v2, 2), 100);

            double sub = TradeUtil.sub(mul2, mul);
            if (sub == 0) {
                return (int) TradeUtil.sub(mul, mul2);
            }
            return (int) sub;
        });
        for (String quote : quoteList) {
            quoteList2.add(quote);
        }

        return quoteList2;
    }


    /*根据合约号获取相应的实时行情*/
    public static String itemQuote(String contCode, List<String> quoteList) {
        if (quoteList == null) {
            return null;
        } else {
            for (String quote : quoteList) {
                String[] split = quote.split(",");
                if (contCode.equals(split[0])) {
                    return quote;
                }
            }
        }
        return null;
    }

    /*获取当前行情的ContCode*/
    public static String itemQuoteContCode(String quote) {
        if (quote == null) {
            return null;
        } else {
            String[] split = quote.split(",");
            return split[0];
        }

    }

    /*获取当前行情的Code*/
    public static String itemQuoteCode(String quote) {
        if (quote == null) {
            return null;
        } else {
            String[] split = quote.split(",");
            Log.d(TAG, "itemQuoteCode:550: " + split[0].replaceAll("[^a-z^A-Z]", ""));
            return split[0].replaceAll("[^a-z^A-Z]", "");
        }

    }

    /*当前是否涨跌*/
    public static String itemQuoteIsRange(String quote) {
        String[] split = quote.split(",");
        if (sub(Double.parseDouble(split[9]), Double.parseDouble(split[6])) > 0) {
            return "1";
        } else if (sub(Double.parseDouble(split[9]), Double.parseDouble(split[6])) == 0) {
            return "0";
        } else {
            return "-1";
        }
    }

    /*当前是否涨跌*/
    public static String listQuoteIsRange(String quote) {
        String[] split = quote.split(",");
        return split[1];
    }


    /*获取当前价格*/
    public static String itemQuotePrice(String quote) {
        String[] split = quote.split(",");
        return split[9];
    }


    /*获取单个的手续费*/


    /*获取保证金范围*/
    public static String deposit(List<Integer> depositList) {
        String deposit = null;
        if (depositList.size() == 0) {
            deposit = null;
        } else if (depositList.size() == 1) {
            deposit = depositList.get(0) + "";
        } else if (depositList.size() == 2) {
            deposit = depositList.get(0) + "~" + depositList.get(1);
        }
        return deposit;
    }

    public static String depositMin(List<Integer> depositList) {
        Log.d("print", "depositMin:847: " + depositList);
        String deposit = null;
        if (depositList.size() == 0) {
            deposit = null;
        } else if (depositList.size() == 1) {
            deposit = depositList.get(0) + "";
        } else if (depositList.size() == 2) {
            deposit = depositList.get(0) + "";
        }
        return deposit;
    }

    public static String marginMin(List<Integer> depositList) {
        String deposit = null;
        if (depositList.size() == 0) {
        } else {
            deposit = depositList.get(0) + "";
        }
        return deposit;
    }

    public static String marginMax(List<Integer> depositList) {
        String deposit = null;
        if (depositList.size() == 0) {
        } else {
            deposit = depositList.get(1) + "";
        }
        return deposit;
    }

    /*获取做多价格*/
    public static String itemQuoteBuyMuchPrice(String quote, int spread) {
        String[] split = quote.split(",");
        if (spread == 0) {
            return split[1];
        } else if (spread == -1) {
            return split[9];
        } else {
            return getNumberFormat(sub(Double.parseDouble(split[9]), spread), decimalPoint(split[9]));
        }
    }

    /*获取做空价格*/
    public static String itemQuoteBuyEmptyPrice(String quote, int spread) {
        String[] split = quote.split(",");
        if (spread == 0) {
            return split[3];
        } else if (spread == -1) {
            return split[9];
        } else {
            return getNumberFormat(add(Double.parseDouble(split[9]), spread), decimalPoint(split[9]));
        }
    }

    /*今日最高价格*/
    public static String itemQuoteMaxPrice(String quote) {
        String[] split = quote.split(",");
        return split[7];
    }

    /*今日最低价格*/
    public static String itemQuoteMinPrice(String quote) {
        String[] split = quote.split(",");
        return split[8];
    }

    /*24H成交量*/
    public static String itemQuoteVolume(String quote) {
        String[] split = quote.split(",");
        return split[12];
    }

    /*名称*/
    public static String listQuoteName(String quote) {
        String[] split = quote.split(",");

        String s = quoteList(split[0]);
        if (s != null) {
            String[] split1 = s.split(",");
            return split1[0];

        } else {
            return null;
        }
    }

    /*usdt*/
    public static String listQuoteUSD(String quote) {
        String[] split = quote.split(",");


        String[] split1 = quoteList(split[0]).split(",");
        return split1[1];
    }

    /*名称合起来*/
    public static String listQuoteNameUSD(String quote) {
        String[] split = quote.split(",");
        return quoteNme(split[0]);
    }

    /*获取当前价格*/
    public static String listQuotePrice(String quote) {
        String[] split = quote.split(",");
        return split[2];
    }


    /*开盘价*/
    public static String itemQuoteTodayPrice(String quote) {
        String[] split = quote.split(",");
        return split[6];
    }

    /*开盘价*/
    public static String listQuoteTodayPrice(String quote) {
        String[] split = quote.split(",");
        return split[3];
    }


    /*判断当前有几位小数*/
    public static int decimalPoint(String price) {
        if (price.contains(".")) {
            return price.length() - price.indexOf(".") - 1;
        } else {
            return 0;
        }
    }

    /*价格变化*/
    public static String quoteChange(String price, String todayPrice) {
        double sub = sub(Double.parseDouble(price), Double.parseDouble(todayPrice));
        String result = null;
        if (sub > 0) {
            result = "+" + numberHalfUp(sub, decimalPoint(price));
        } else if (sub < 0) {
            result = numberHalfUp(sub, decimalPoint(price));
        } else if (sub == 0) {
            result = numberHalfUp(sub, decimalPoint(price));
        }
        return result;
    }

    /*涨跌幅*/
    public static String quoteRange(String price, String todayPrice) {
        double sub = sub(Double.parseDouble(price), Double.parseDouble(todayPrice));

        double div = div(sub, Double.parseDouble(price), 10);
        double mul = mul(div, 100);
        String numberFormat = getNumberFormat(mul, 2);
        String result = null;
        if (sub > 0) {
            result = "+" + numberFormat + "%";
        } else if (sub == 0) {
            result = numberFormat + "%";
        } else if (sub < 0) {
            result = numberFormat + "%";
        }

        return result;
    }


    /*盈亏比*/
    public static String ratio(double income, double margin) {
        double div = div(income, margin, 10);
        double mul = mul(div, 100);
        return getNumberFormat(mul, 2) + "%";
    }

    public static double ratioDouble(double income, double margin) {
        double div = div(income, margin, 10);
        double mul = mul(div, 100);
        return mul;
    }

    public static String quoteList(String content) {
        String name;

        StringBuilder stringBuilder;
        if (content == null) {
            return null;
        } else {
            ArrayList<String> allSatisfyStr = getAllSatisfyStr(content, "[a-zA-Z]");
            stringBuilder = new StringBuilder();
            for (int i = 0; i < allSatisfyStr.size(); i++) {
                stringBuilder.append(allSatisfyStr.get(i));
            }
            if (stringBuilder.toString().contains("USDT")) {
                name = stringBuilder.substring(0, content.length() - 8) + "," + stringBuilder.substring(content.length() - 8, content.length() - 4);
            } else {
                name = stringBuilder.toString() + "," + "null";
            }
            return name;
        }
    }

    public static String quoteNme(String content) {
        String name;
        StringBuilder stringBuilder;

        if (content == null) {
            return null;
        } else {
            ArrayList<String> allSatisfyStr = getAllSatisfyStr(content, "[a-zA-Z]");
            stringBuilder = new StringBuilder();
            for (int i = 0; i < allSatisfyStr.size(); i++) {
                stringBuilder.append(allSatisfyStr.get(i));
            }
            if (stringBuilder.toString().contains("USDT")) {
                name = content.substring(0, content.length() - 8) + "/" + content.substring(content.length() - 8, content.length() - 4);
            } else {
                name = stringBuilder.toString() + "," + "null";
            }
            return name;
        }
    }


    public static ArrayList<String> getAllSatisfyStr(String str, String regex) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        ArrayList<String> allSatisfyStr = new ArrayList<>();
        if (regex == null || regex.isEmpty()) {
            allSatisfyStr.add(str);
            return allSatisfyStr;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            allSatisfyStr.add(matcher.group());
        }
        return allSatisfyStr;
    }

    public static List<String> quoteResult(String content) {
        List<String> quoteList = new ArrayList<>();
        String[] split = content.split(";");
        if (split.length > 0) {
            for (String a : split) {
                quoteList.add(a);
            }
            return quoteList;
        } else {
            return null;
        }
    }
}
