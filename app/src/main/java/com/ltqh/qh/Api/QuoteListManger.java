package com.ltqh.qh.Api;

import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;

import com.google.gson.Gson;
import com.ltqh.qh.config.AppConfig;
import com.ltqh.qh.entity.QuoteEntity;
import com.ltqh.qh.utils.TradeUtil;
import com.ltqh.qh.utils.Util;
import com.pro.switchlibrary.SPUtils;

import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import static com.ltqh.qh.Api.NetManger.BUSY;
import static com.ltqh.qh.Api.NetManger.FAILURE;
import static com.ltqh.qh.Api.NetManger.SUCCESS;

public class QuoteListManger extends Observable {


    private static QuoteListManger quoteListManger;


    public static QuoteListManger getInstance() {
        if (quoteListManger == null) {
            synchronized (QuoteListManger.class) {
                if (quoteListManger == null) {
                    quoteListManger = new QuoteListManger();
                }
            }

        }
        return quoteListManger;

    }

    private Timer mTimer;

    public void startScheduleJob(long delay, long interval) {
        if (mTimer != null) cancelTimer();

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (handler != null) {
                    handler.sendEmptyMessage(0);
                }
            }
        }, delay, interval);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String quote_host = SPUtils.getString(AppConfig.QUOTE_HOST);
            String quote_code = SPUtils.getString(AppConfig.QUOTE_CODE);
            if (quote_host == null && quote_code == null) {
                NetManger.getInstance().initQuote();
                return;
            } else {
                quote(quote_host, quote_code);
            }
        }
    };


    public void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


    public void quote(String quote_host, String quote_code) {

        ArrayMap<String, List<String>> arrayMap = new ArrayMap<>();

        if (quote_host == null && quote_code == null) {
            NetManger.getInstance().initQuote();
        } else {
            NetManger.getInstance().getQuote(quote_host, "/quote.jsp", quote_code, (state, response) -> {
                if (state.equals(BUSY)) {

                } else if (state.equals(SUCCESS)) {
                    String jsonReplace = Util.jsonReplace(response.toString());
                    Log.d("print", "onNetResult:97: " + jsonReplace);
                    QuoteEntity quoteEntity = new Gson().fromJson(jsonReplace, QuoteEntity.class);
                    String data = quoteEntity.getData();
                    List<String>        strings = TradeUtil.quoteResult(data);

                    //价格从高到低
                    List<String> stringList = TradeUtil.priceHighToLow(strings);
                    //价格从低到高
                    List<String> stringList1 = TradeUtil.priceLowToHigh(strings);
                    //涨跌幅从高到低
                    List<String> stringList2 = TradeUtil.rangeHighToLow(strings);
                    //涨跌幅从低到高
                    List<String> stringList3 = TradeUtil.rangeLowToHigh(strings);
                    //BTC BCH ETH
                    List<String> stringList4 = TradeUtil.homeHot(strings);
                    //除去 BTC BCH ETH
                    List<String> stringList5 = TradeUtil.homeList(strings);
                    //字母a-z
                    List<String> stringList6 = TradeUtil.nameLowToHigh(strings);
                    //字母z-a
                    List<String> stringList7 = TradeUtil.nameHighToLow(strings);
                    arrayMap.put("0", strings);
                    arrayMap.put("1", stringList);
                    arrayMap.put("2", stringList1);
                    arrayMap.put("3", stringList2);
                    arrayMap.put("4", stringList3);
                    arrayMap.put("5", stringList4);
                    arrayMap.put("6", stringList5);
                    arrayMap.put("7", stringList6);
                    arrayMap.put("8", stringList7);
                    postQuote(arrayMap);

                } else if (state.equals(FAILURE)) {

                }
            });

        }

    }

    public void postQuote(ArrayMap<String, List<String>> arrayMap) {
        setChanged();
        notifyObservers(arrayMap);

    }

    /**
     * 清理消息监听
     */
    public void clear() {
        deleteObservers();
        quoteListManger = null;
    }


}
