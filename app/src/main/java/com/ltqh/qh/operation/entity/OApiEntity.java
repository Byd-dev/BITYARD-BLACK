package com.ltqh.qh.operation.entity;

import java.io.Serializable;
import java.util.List;

public class OApiEntity implements Serializable {

    @Override
    public String toString() {
        return "OApiEntity{" +
                "msgCount=" + msgCount +
                ", foreignCommds=" + foreignCommds +
                ", stockIndexCommds=" + stockIndexCommds +
                ", domesticCommds=" + domesticCommds +
                ", digitalCommds=" + digitalCommds +
                ", metalCommds=" + metalCommds +
                ", contracts='" + contracts + '\'' +
                ", app='" + app + '\'' +
                ", hasGeneralize=" + hasGeneralize +
                ", quoteDomain='" + quoteDomain + '\'' +
                ", time=" + time +
                ", session=" + session +
                ", code=" + code +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }

    private int msgCount;
    private List<ForeignCommdsBean> foreignCommds;
    private List<StockIndexCommdsBean> stockIndexCommds;
    private List<DomesticCommdsBean> domesticCommds;
    private List<DigitalCommdsBean> digitalCommds;
    private List<?> metalCommds;
    private String contracts;
    private String app;
    private boolean hasGeneralize;
    private String quoteDomain;
    private long time;
    private SessionBean session;
    private int code;
    private String resultMsg;


    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
    public String getContracts() {
        return contracts;
    }

    public void setContracts(String contracts) {
        this.contracts = contracts;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public boolean isHasGeneralize() {
        return hasGeneralize;
    }

    public void setHasGeneralize(boolean hasGeneralize) {
        this.hasGeneralize = hasGeneralize;
    }

    public String getQuoteDomain() {
        return quoteDomain;
    }

    public void setQuoteDomain(String quoteDomain) {
        this.quoteDomain = quoteDomain;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public List<ForeignCommdsBean> getForeignCommds() {
        return foreignCommds;
    }

    public void setForeignCommds(List<ForeignCommdsBean> foreignCommds) {
        this.foreignCommds = foreignCommds;
    }

    public List<StockIndexCommdsBean> getStockIndexCommds() {
        return stockIndexCommds;
    }

    public void setStockIndexCommds(List<StockIndexCommdsBean> stockIndexCommds) {
        this.stockIndexCommds = stockIndexCommds;
    }

    public List<DomesticCommdsBean> getDomesticCommds() {
        return domesticCommds;
    }

    public void setDomesticCommds(List<DomesticCommdsBean> domesticCommds) {
        this.domesticCommds = domesticCommds;
    }

    public List<DigitalCommdsBean> getDigitalCommds() {
        return digitalCommds;
    }

    public void setDigitalCommds(List<DigitalCommdsBean> digitalCommds) {
        this.digitalCommds = digitalCommds;
    }

    public List<?> getMetalCommds() {
        return metalCommds;
    }

    public void setMetalCommds(List<?> metalCommds) {
        this.metalCommds = metalCommds;
    }

    public static class SessionBean implements Serializable{
        @Override
        public String toString() {
            return "SessionBean{" +
                    "PP='" + PP + '\'' +
                    ", ETCUSD='" + ETCUSD + '\'' +
                    ", MHI='" + MHI + '\'' +
                    ", YM='" + YM + '\'' +
                    ", IC='" + IC + '\'' +
                    ", IF='" + IF + '\'' +
                    ", IH='" + IH + '\'' +
                    ", AD='" + AD + '\'' +
                    ", AG='" + AG + '\'' +
                    ", MDAX='" + MDAX + '\'' +
                    ", AP='" + AP + '\'' +
                    ", RB='" + RB + '\'' +
                    ", AU='" + AU + '\'' +
                    ", RU='" + RU + '\'' +
                    ", M='" + M + '\'' +
                    ", BP='" + BP + '\'' +
                    ", SC='" + SC + '\'' +
                    ", P='" + P + '\'' +
                    ", BU='" + BU + '\'' +
                    ", SI='" + SI + '\'' +
                    ", Y='" + Y + '\'' +
                    ", SM='" + SM + '\'' +
                    ", SR='" + SR + '\'' +
                    ", CD='" + CD + '\'' +
                    ", CL='" + CL + '\'' +
                    ", CN='" + CN + '\'' +
                    ", ZECUSD='" + ZECUSD + '\'' +
                    ", CU='" + CU + '\'' +
                    ", TF='" + TF + '\'' +
                    ", DAX='" + DAX + '\'' +
                    ", DSHUSD='" + DSHUSD + '\'' +
                    ", MA='" + MA + '\'' +
                    ", EC='" + EC + '\'' +
                    ", HSI='" + HSI + '\'' +
                    ", LTCUSD='" + LTCUSD + '\'' +
                    ", BCHUSD='" + BCHUSD + '\'' +
                    ", ES='" + ES + '\'' +
                    ", OMGUSD='" + OMGUSD + '\'' +
                    ", NE='" + NE + '\'' +
                    ", NG='" + NG + '\'' +
                    ", NI='" + NI + '\'' +
                    ", NK='" + NK + '\'' +
                    ", FG='" + FG + '\'' +
                    ", NQ='" + NQ + '\'' +
                    ", BTCUSD='" + BTCUSD + '\'' +
                    ", ETHUSD='" + ETHUSD + '\'' +
                    ", GC='" + GC + '\'' +
                    ", EOSUSD='" + EOSUSD + '\'' +
                    ", HC='" + HC + '\'' +
                    ", HG='" + HG + '\'' +
                    '}';
        }

        /**
         * PP : 0100-0215,0230-0330,0530-0700,1300-1500
         * ETCUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * MHI : 0000-1700,0115-0400,0500-0830,0915-0000|0115-0400,0500-0830,0915-0000:2|0000-1700:7
         * YM : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * IC : 0130-0330,0500-0700
         * IF : 0130-0330,0500-0700
         * IH : 0130-0330,0500-0700
         * AD : 0000-0359,0400-1559,1600-2200,2300-0000|0400-1559,1600-2200,2300-0000:2|0000-0359:7
         * AG : 0000-0215,0100-0215,0230-0330,0530-0700,1300-0000|0100-0215,0230-0330,0530-0700,1300-0000:2|0000-0215:7
         * MDAX : 0600-1559,1600-2000
         * AP : 0100-0215,0230-0330,0530-0700
         * RB : 0100-0215,0230-0330,0530-0700,1300-1500
         * AU : 0000-0215,0100-0215,0230-0330,0530-0700,1300-0000|0100-0215,0230-0330,0530-0700,1300-0000:2|0000-0215:7
         * RU : 0100-0215,0230-0330,0530-0700,1300-1500
         * M : 0100-0215,0230-0330,0530-0700,1300-1530
         * BP : 0000-0359,0400-1559,1600-2200,2300-0000|0400-1559,1600-2200,2300-0000:2|0000-0359:7
         * SC : 0000-0215,0100-0215,0230-0330,0530-0700,1300-0000|0100-0215,0230-0330,0530-0700,1300-0000:2|0000-0215:7
         * P : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * BU : 0100-0215,0230-0330,0530-0700,1300-1500
         * SI : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * Y : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * SM : 0100-0215,0230-0330,0530-0700
         * SR : 0100-0215,0230-0330,0530-0700,1300-1530
         * CD : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * CL : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * CN : 0000-2045,0100-0359,0400-0830,0900-0000|0100-0359,0400-0830,0900-0000:2|0000-2045:7
         * ZECUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * CU : 0000-0215,0100-0215,0230-0330,0530-0700,1300-0000|0100-0215,0230-0330,0530-0700,1300-0000:2|0000-0215:7
         * TF : 0115-0330,0500-0715
         * DAX : 0600-1559,1600-2000
         * DSHUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * MA : 0100-0215,0230-0330,0530-0700,1300-1530
         * EC : 0000-0359,0400-1559,1600-2200,2300-0000|0400-1559,1600-2200,2300-0000:2|0000-0359:7
         * HSI : 0000-1700,0115-0400,0500-0830,0915-0000|0115-0400,0500-0830,0915-0000:2|0000-1700:7
         * LTCUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * BCHUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * ES : 0000-0359,0400-1559,1600-2200,2300-0000|0400-1559,1600-2200,2300-0000:2|0000-0359:7
         * OMGUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * NE : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * NG : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * NI : 0000-0215,0100-0215,0230-0330,0530-0700,1300-0000|0100-0215,0230-0330,0530-0700,1300-0000:2|0000-0215:7
         * NK : 0000-2045,0400-0630,0655-0000,2330-0000|0400-0630,0655-0000,2330-0000:2|0000-2045:7
         * FG : 0100-0215,0230-0330,0530-0700,1300-1530
         * NQ : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * BTCUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * ETHUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * GC : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         * EOSUSD : 0000-0359,0400-1559,1700-2159,2200-0000|0400-1559,1700-2159,2200-0000:2|0000-0359:7
         * HC : 0100-0215,0230-0330,0530-0700,1300-1500
         * HG : 0000-0359,0400-1559,1600-2100,2200-0000|0400-1559,1600-2100,2200-0000:2|0000-0359:7
         */



        private String PP;
        private String ETCUSD;
        private String MHI;
        private String YM;
        private String IC;
        private String IF;
        private String IH;
        private String AD;
        private String AG;
        private String MDAX;
        private String AP;
        private String RB;
        private String AU;
        private String RU;
        private String M;
        private String BP;
        private String SC;
        private String P;
        private String BU;
        private String SI;
        private String Y;
        private String SM;
        private String SR;
        private String CD;
        private String CL;
        private String CN;
        private String ZECUSD;
        private String CU;
        private String TF;
        private String DAX;
        private String DSHUSD;
        private String MA;
        private String EC;
        private String HSI;
        private String LTCUSD;
        private String BCHUSD;
        private String ES;
        private String OMGUSD;
        private String NE;
        private String NG;
        private String NI;
        private String NK;
        private String FG;
        private String NQ;
        private String BTCUSD;
        private String ETHUSD;
        private String GC;
        private String EOSUSD;
        private String HC;
        private String HG;

        public String getPP() {
            return PP;
        }

        public void setPP(String PP) {
            this.PP = PP;
        }

        public String getETCUSD() {
            return ETCUSD;
        }

        public void setETCUSD(String ETCUSD) {
            this.ETCUSD = ETCUSD;
        }

        public String getMHI() {
            return MHI;
        }

        public void setMHI(String MHI) {
            this.MHI = MHI;
        }

        public String getYM() {
            return YM;
        }

        public void setYM(String YM) {
            this.YM = YM;
        }

        public String getIC() {
            return IC;
        }

        public void setIC(String IC) {
            this.IC = IC;
        }

        public String getIF() {
            return IF;
        }

        public void setIF(String IF) {
            this.IF = IF;
        }

        public String getIH() {
            return IH;
        }

        public void setIH(String IH) {
            this.IH = IH;
        }

        public String getAD() {
            return AD;
        }

        public void setAD(String AD) {
            this.AD = AD;
        }

        public String getAG() {
            return AG;
        }

        public void setAG(String AG) {
            this.AG = AG;
        }

        public String getMDAX() {
            return MDAX;
        }

        public void setMDAX(String MDAX) {
            this.MDAX = MDAX;
        }

        public String getAP() {
            return AP;
        }

        public void setAP(String AP) {
            this.AP = AP;
        }

        public String getRB() {
            return RB;
        }

        public void setRB(String RB) {
            this.RB = RB;
        }

        public String getAU() {
            return AU;
        }

        public void setAU(String AU) {
            this.AU = AU;
        }

        public String getRU() {
            return RU;
        }

        public void setRU(String RU) {
            this.RU = RU;
        }

        public String getM() {
            return M;
        }

        public void setM(String M) {
            this.M = M;
        }

        public String getBP() {
            return BP;
        }

        public void setBP(String BP) {
            this.BP = BP;
        }

        public String getSC() {
            return SC;
        }

        public void setSC(String SC) {
            this.SC = SC;
        }

        public String getP() {
            return P;
        }

        public void setP(String P) {
            this.P = P;
        }

        public String getBU() {
            return BU;
        }

        public void setBU(String BU) {
            this.BU = BU;
        }

        public String getSI() {
            return SI;
        }

        public void setSI(String SI) {
            this.SI = SI;
        }

        public String getY() {
            return Y;
        }

        public void setY(String Y) {
            this.Y = Y;
        }

        public String getSM() {
            return SM;
        }

        public void setSM(String SM) {
            this.SM = SM;
        }

        public String getSR() {
            return SR;
        }

        public void setSR(String SR) {
            this.SR = SR;
        }

        public String getCD() {
            return CD;
        }

        public void setCD(String CD) {
            this.CD = CD;
        }

        public String getCL() {
            return CL;
        }

        public void setCL(String CL) {
            this.CL = CL;
        }

        public String getCN() {
            return CN;
        }

        public void setCN(String CN) {
            this.CN = CN;
        }

        public String getZECUSD() {
            return ZECUSD;
        }

        public void setZECUSD(String ZECUSD) {
            this.ZECUSD = ZECUSD;
        }

        public String getCU() {
            return CU;
        }

        public void setCU(String CU) {
            this.CU = CU;
        }

        public String getTF() {
            return TF;
        }

        public void setTF(String TF) {
            this.TF = TF;
        }

        public String getDAX() {
            return DAX;
        }

        public void setDAX(String DAX) {
            this.DAX = DAX;
        }

        public String getDSHUSD() {
            return DSHUSD;
        }

        public void setDSHUSD(String DSHUSD) {
            this.DSHUSD = DSHUSD;
        }

        public String getMA() {
            return MA;
        }

        public void setMA(String MA) {
            this.MA = MA;
        }

        public String getEC() {
            return EC;
        }

        public void setEC(String EC) {
            this.EC = EC;
        }

        public String getHSI() {
            return HSI;
        }

        public void setHSI(String HSI) {
            this.HSI = HSI;
        }

        public String getLTCUSD() {
            return LTCUSD;
        }

        public void setLTCUSD(String LTCUSD) {
            this.LTCUSD = LTCUSD;
        }

        public String getBCHUSD() {
            return BCHUSD;
        }

        public void setBCHUSD(String BCHUSD) {
            this.BCHUSD = BCHUSD;
        }

        public String getES() {
            return ES;
        }

        public void setES(String ES) {
            this.ES = ES;
        }

        public String getOMGUSD() {
            return OMGUSD;
        }

        public void setOMGUSD(String OMGUSD) {
            this.OMGUSD = OMGUSD;
        }

        public String getNE() {
            return NE;
        }

        public void setNE(String NE) {
            this.NE = NE;
        }

        public String getNG() {
            return NG;
        }

        public void setNG(String NG) {
            this.NG = NG;
        }

        public String getNI() {
            return NI;
        }

        public void setNI(String NI) {
            this.NI = NI;
        }

        public String getNK() {
            return NK;
        }

        public void setNK(String NK) {
            this.NK = NK;
        }

        public String getFG() {
            return FG;
        }

        public void setFG(String FG) {
            this.FG = FG;
        }

        public String getNQ() {
            return NQ;
        }

        public void setNQ(String NQ) {
            this.NQ = NQ;
        }

        public String getBTCUSD() {
            return BTCUSD;
        }

        public void setBTCUSD(String BTCUSD) {
            this.BTCUSD = BTCUSD;
        }

        public String getETHUSD() {
            return ETHUSD;
        }

        public void setETHUSD(String ETHUSD) {
            this.ETHUSD = ETHUSD;
        }

        public String getGC() {
            return GC;
        }

        public void setGC(String GC) {
            this.GC = GC;
        }

        public String getEOSUSD() {
            return EOSUSD;
        }

        public void setEOSUSD(String EOSUSD) {
            this.EOSUSD = EOSUSD;
        }

        public String getHC() {
            return HC;
        }

        public void setHC(String HC) {
            this.HC = HC;
        }

        public String getHG() {
            return HG;
        }

        public void setHG(String HG) {
            this.HG = HG;
        }
    }

    public static class ForeignCommdsBean implements Serializable{
        @Override
        public String toString() {
            return "ForeignCommdsBean{" +
                    "amClearingTime='" + amClearingTime + '\'' +
                    ", amCloseTime='" + amCloseTime + '\'' +
                    ", amOpenTime='" + amOpenTime + '\'' +
                    ", amTradeTime='" + amTradeTime + '\'' +
                    ", amWarningTime='" + amWarningTime + '\'' +
                    ", classifyCode='" + classifyCode + '\'' +
                    ", classifyName='" + classifyName + '\'' +
                    ", code='" + code + '\'' +
                    ", commodityId='" + commodityId + '\'' +
                    ", contractCode='" + contractCode + '\'' +
                    ", createTime=" + createTime +
                    ", currency='" + currency + '\'' +
                    ", exchange='" + exchange + '\'' +
                    ", favor=" + favor +
                    ", foreign=" + foreign +
                    ", holiday='" + holiday + '\'' +
                    ", id=" + id +
                    ", moneyType=" + moneyType +
                    ", name='" + name + '\'' +
                    ", niteClearingTime='" + niteClearingTime + '\'' +
                    ", niteCloseTime='" + niteCloseTime + '\'' +
                    ", niteOpenTime='" + niteOpenTime + '\'' +
                    ", niteTradeTime='" + niteTradeTime + '\'' +
                    ", niteWarningTime='" + niteWarningTime + '\'' +
                    ", offset=" + offset +
                    ", order=" + order +
                    ", pmClearingTime='" + pmClearingTime + '\'' +
                    ", pmCloseTime='" + pmCloseTime + '\'' +
                    ", pmOpenTime='" + pmOpenTime + '\'' +
                    ", pmTradeTime='" + pmTradeTime + '\'' +
                    ", pmWarningTime='" + pmWarningTime + '\'' +
                    ", price=" + price +
                    ", priceChange=" + priceChange +
                    ", priceDigit=" + priceDigit +
                    ", range=" + range +
                    ", remark='" + remark + '\'' +
                    ", spread=" + spread +
                    ", valid=" + valid +
                    '}';
        }

        /**
         * amClearingTime : 11:59:59
         * amCloseTime : 11:59:59
         * amOpenTime : 06:00:00
         * amTradeTime : 06:00:00
         * amWarningTime : 11:59:59
         * classifyCode : 0
         * classifyName : 外盘商品
         * code : CL
         * commodityId : 10016
         * contractCode :
         * createTime : {"date":25,"day":5,"hours":12,"minutes":40,"month":2,"seconds":2,"time":1458880802067,"timezoneOffset":-480,"year":116}
         * currency : USD
         * exchange : 纽交所
         * favor : false
         * foreign : true
         * holiday : 2019-04-19 06:00:00,2019-04-20 05:00:00;2019-01-01 07:00:00,2019-01-02 06:00:00
         * id : 10060
         * moneyType : 0
         * name : 美原油
         * niteClearingTime : 04:55:00
         * niteCloseTime : 05:00:00
         * niteOpenTime : 00:00:00
         * niteTradeTime : 00:00:00
         * niteWarningTime : 04:50:00
         * offset : 0
         * order : 60
         * pmClearingTime : 23:59:59
         * pmCloseTime : 23:59:59
         * pmOpenTime : 12:00:00
         * pmTradeTime : 12:00:00
         * pmWarningTime : 23:59:59
         * price : 7000
         * priceChange : 0.01
         * priceDigit : 2
         * range : false
         * remark : 单点价格1000美元，汇率7
         * spread : 0
         * valid : true
         */



        private String amClearingTime;
        private String amCloseTime;
        private String amOpenTime;
        private String amTradeTime;
        private String amWarningTime;
        private String classifyCode;
        private String classifyName;
        private String code;
        private String commodityId;
        private String contractCode;
        private CreateTimeBean createTime;
        private String currency;
        private String exchange;
        private boolean favor;
        private boolean foreign;
        private String holiday;
        private int id;
        private int moneyType;
        private String name;
        private String niteClearingTime;
        private String niteCloseTime;
        private String niteOpenTime;
        private String niteTradeTime;
        private String niteWarningTime;
        private int offset;
        private int order;
        private String pmClearingTime;
        private String pmCloseTime;
        private String pmOpenTime;
        private String pmTradeTime;
        private String pmWarningTime;
        private String price;
        private String priceChange;
        private int priceDigit;
        private boolean range;
        private String remark;
        private double spread;
        private boolean valid;

        public String getAmClearingTime() {
            return amClearingTime;
        }

        public void setAmClearingTime(String amClearingTime) {
            this.amClearingTime = amClearingTime;
        }

        public String getAmCloseTime() {
            return amCloseTime;
        }

        public void setAmCloseTime(String amCloseTime) {
            this.amCloseTime = amCloseTime;
        }

        public String getAmOpenTime() {
            return amOpenTime;
        }

        public void setAmOpenTime(String amOpenTime) {
            this.amOpenTime = amOpenTime;
        }

        public String getAmTradeTime() {
            return amTradeTime;
        }

        public void setAmTradeTime(String amTradeTime) {
            this.amTradeTime = amTradeTime;
        }

        public String getAmWarningTime() {
            return amWarningTime;
        }

        public void setAmWarningTime(String amWarningTime) {
            this.amWarningTime = amWarningTime;
        }

        public String getClassifyCode() {
            return classifyCode;
        }

        public void setClassifyCode(String classifyCode) {
            this.classifyCode = classifyCode;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public CreateTimeBean getCreateTime() {
            return createTime;
        }

        public void setCreateTime(CreateTimeBean createTime) {
            this.createTime = createTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public boolean isFavor() {
            return favor;
        }

        public void setFavor(boolean favor) {
            this.favor = favor;
        }

        public boolean isForeign() {
            return foreign;
        }

        public void setForeign(boolean foreign) {
            this.foreign = foreign;
        }

        public String getHoliday() {
            return holiday;
        }

        public void setHoliday(String holiday) {
            this.holiday = holiday;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMoneyType() {
            return moneyType;
        }

        public void setMoneyType(int moneyType) {
            this.moneyType = moneyType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNiteClearingTime() {
            return niteClearingTime;
        }

        public void setNiteClearingTime(String niteClearingTime) {
            this.niteClearingTime = niteClearingTime;
        }

        public String getNiteCloseTime() {
            return niteCloseTime;
        }

        public void setNiteCloseTime(String niteCloseTime) {
            this.niteCloseTime = niteCloseTime;
        }

        public String getNiteOpenTime() {
            return niteOpenTime;
        }

        public void setNiteOpenTime(String niteOpenTime) {
            this.niteOpenTime = niteOpenTime;
        }

        public String getNiteTradeTime() {
            return niteTradeTime;
        }

        public void setNiteTradeTime(String niteTradeTime) {
            this.niteTradeTime = niteTradeTime;
        }

        public String getNiteWarningTime() {
            return niteWarningTime;
        }

        public void setNiteWarningTime(String niteWarningTime) {
            this.niteWarningTime = niteWarningTime;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getPmClearingTime() {
            return pmClearingTime;
        }

        public void setPmClearingTime(String pmClearingTime) {
            this.pmClearingTime = pmClearingTime;
        }

        public String getPmCloseTime() {
            return pmCloseTime;
        }

        public void setPmCloseTime(String pmCloseTime) {
            this.pmCloseTime = pmCloseTime;
        }

        public String getPmOpenTime() {
            return pmOpenTime;
        }

        public void setPmOpenTime(String pmOpenTime) {
            this.pmOpenTime = pmOpenTime;
        }

        public String getPmTradeTime() {
            return pmTradeTime;
        }

        public void setPmTradeTime(String pmTradeTime) {
            this.pmTradeTime = pmTradeTime;
        }

        public String getPmWarningTime() {
            return pmWarningTime;
        }

        public void setPmWarningTime(String pmWarningTime) {
            this.pmWarningTime = pmWarningTime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceChange() {
            return priceChange;
        }

        public void setPriceChange(String priceChange) {
            this.priceChange = priceChange;
        }

        public int getPriceDigit() {
            return priceDigit;
        }

        public void setPriceDigit(int priceDigit) {
            this.priceDigit = priceDigit;
        }

        public boolean isRange() {
            return range;
        }

        public void setRange(boolean range) {
            this.range = range;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getSpread() {
            return spread;
        }

        public void setSpread(double spread) {
            this.spread = spread;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public static class CreateTimeBean {
            /**
             * date : 25
             * day : 5
             * hours : 12
             * minutes : 40
             * month : 2
             * seconds : 2
             * time : 1458880802067
             * timezoneOffset : -480
             * year : 116
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }

    public static class StockIndexCommdsBean implements Serializable{
        @Override
        public String toString() {
            return "StockIndexCommdsBean{" +
                    "amClearingTime='" + amClearingTime + '\'' +
                    ", amCloseTime='" + amCloseTime + '\'' +
                    ", amOpenTime='" + amOpenTime + '\'' +
                    ", amTradeTime='" + amTradeTime + '\'' +
                    ", amWarningTime='" + amWarningTime + '\'' +
                    ", classifyCode='" + classifyCode + '\'' +
                    ", classifyName='" + classifyName + '\'' +
                    ", code='" + code + '\'' +
                    ", commodityId='" + commodityId + '\'' +
                    ", contractCode='" + contractCode + '\'' +
                    ", createTime=" + createTime +
                    ", currency='" + currency + '\'' +
                    ", exchange='" + exchange + '\'' +
                    ", favor=" + favor +
                    ", foreign=" + foreign +
                    ", holiday='" + holiday + '\'' +
                    ", id=" + id +
                    ", moneyType=" + moneyType +
                    ", name='" + name + '\'' +
                    ", niteClearingTime='" + niteClearingTime + '\'' +
                    ", niteCloseTime='" + niteCloseTime + '\'' +
                    ", niteOpenTime='" + niteOpenTime + '\'' +
                    ", niteTradeTime='" + niteTradeTime + '\'' +
                    ", niteWarningTime='" + niteWarningTime + '\'' +
                    ", offset=" + offset +
                    ", order=" + order +
                    ", pmClearingTime='" + pmClearingTime + '\'' +
                    ", pmCloseTime='" + pmCloseTime + '\'' +
                    ", pmOpenTime='" + pmOpenTime + '\'' +
                    ", pmTradeTime='" + pmTradeTime + '\'' +
                    ", pmWarningTime='" + pmWarningTime + '\'' +
                    ", price=" + price +
                    ", priceChange=" + priceChange +
                    ", priceDigit=" + priceDigit +
                    ", range=" + range +
                    ", remark='" + remark + '\'' +
                    ", spread=" + spread +
                    ", valid=" + valid +
                    '}';
        }

        /**
         * amClearingTime : 11:30:00
         * amCloseTime : 11:30:00
         * amOpenTime : 09:30:00
         * amTradeTime : 09:30:00
         * amWarningTime : 11:30:00
         * classifyCode : 2
         * classifyName : 股指期货
         * code : IF
         * commodityId : 10001
         * contractCode :
         * createTime : {"date":11,"day":4,"hours":14,"minutes":13,"month":5,"seconds":41,"time":1434003221557,"timezoneOffset":-480,"year":115}
         * currency : CNY
         * exchange : 中金所
         * favor : false
         * foreign : false
         * holiday : 2019-05-01 09:30:00,2019-05-04 14:55:00
         * id : 10066
         * moneyType : 0
         * name : 沪深300
         * niteClearingTime : 23:59:59
         * niteCloseTime : 23:59:59
         * niteOpenTime : 23:59:59
         * niteTradeTime : 23:59:59
         * niteWarningTime : 23:59:59
         * offset : 0
         * order : 0
         * pmClearingTime : 14:55:00
         * pmCloseTime : 15:00:00
         * pmOpenTime : 13:00:00
         * pmTradeTime : 13:00:00
         * pmWarningTime : 14:50:00
         * price : 300
         * priceChange : 0.2
         * priceDigit : 1
         * range : false
         * remark :
         * spread : 0
         * valid : true
         */

        private String amClearingTime;
        private String amCloseTime;
        private String amOpenTime;
        private String amTradeTime;
        private String amWarningTime;
        private String classifyCode;
        private String classifyName;
        private String code;
        private String commodityId;
        private String contractCode;
        private CreateTimeBeanX createTime;
        private String currency;
        private String exchange;
        private boolean favor;
        private boolean foreign;
        private String holiday;
        private int id;
        private int moneyType;
        private String name;
        private String niteClearingTime;
        private String niteCloseTime;
        private String niteOpenTime;
        private String niteTradeTime;
        private String niteWarningTime;
        private int offset;
        private int order;
        private String pmClearingTime;
        private String pmCloseTime;
        private String pmOpenTime;
        private String pmTradeTime;
        private String pmWarningTime;
        private String price;
        private String priceChange;
        private int priceDigit;
        private boolean range;
        private String remark;
        private double spread;
        private boolean valid;

        public String getAmClearingTime() {
            return amClearingTime;
        }

        public void setAmClearingTime(String amClearingTime) {
            this.amClearingTime = amClearingTime;
        }

        public String getAmCloseTime() {
            return amCloseTime;
        }

        public void setAmCloseTime(String amCloseTime) {
            this.amCloseTime = amCloseTime;
        }

        public String getAmOpenTime() {
            return amOpenTime;
        }

        public void setAmOpenTime(String amOpenTime) {
            this.amOpenTime = amOpenTime;
        }

        public String getAmTradeTime() {
            return amTradeTime;
        }

        public void setAmTradeTime(String amTradeTime) {
            this.amTradeTime = amTradeTime;
        }

        public String getAmWarningTime() {
            return amWarningTime;
        }

        public void setAmWarningTime(String amWarningTime) {
            this.amWarningTime = amWarningTime;
        }

        public String getClassifyCode() {
            return classifyCode;
        }

        public void setClassifyCode(String classifyCode) {
            this.classifyCode = classifyCode;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public CreateTimeBeanX getCreateTime() {
            return createTime;
        }

        public void setCreateTime(CreateTimeBeanX createTime) {
            this.createTime = createTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public boolean isFavor() {
            return favor;
        }

        public void setFavor(boolean favor) {
            this.favor = favor;
        }

        public boolean isForeign() {
            return foreign;
        }

        public void setForeign(boolean foreign) {
            this.foreign = foreign;
        }

        public String getHoliday() {
            return holiday;
        }

        public void setHoliday(String holiday) {
            this.holiday = holiday;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMoneyType() {
            return moneyType;
        }

        public void setMoneyType(int moneyType) {
            this.moneyType = moneyType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNiteClearingTime() {
            return niteClearingTime;
        }

        public void setNiteClearingTime(String niteClearingTime) {
            this.niteClearingTime = niteClearingTime;
        }

        public String getNiteCloseTime() {
            return niteCloseTime;
        }

        public void setNiteCloseTime(String niteCloseTime) {
            this.niteCloseTime = niteCloseTime;
        }

        public String getNiteOpenTime() {
            return niteOpenTime;
        }

        public void setNiteOpenTime(String niteOpenTime) {
            this.niteOpenTime = niteOpenTime;
        }

        public String getNiteTradeTime() {
            return niteTradeTime;
        }

        public void setNiteTradeTime(String niteTradeTime) {
            this.niteTradeTime = niteTradeTime;
        }

        public String getNiteWarningTime() {
            return niteWarningTime;
        }

        public void setNiteWarningTime(String niteWarningTime) {
            this.niteWarningTime = niteWarningTime;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getPmClearingTime() {
            return pmClearingTime;
        }

        public void setPmClearingTime(String pmClearingTime) {
            this.pmClearingTime = pmClearingTime;
        }

        public String getPmCloseTime() {
            return pmCloseTime;
        }

        public void setPmCloseTime(String pmCloseTime) {
            this.pmCloseTime = pmCloseTime;
        }

        public String getPmOpenTime() {
            return pmOpenTime;
        }

        public void setPmOpenTime(String pmOpenTime) {
            this.pmOpenTime = pmOpenTime;
        }

        public String getPmTradeTime() {
            return pmTradeTime;
        }

        public void setPmTradeTime(String pmTradeTime) {
            this.pmTradeTime = pmTradeTime;
        }

        public String getPmWarningTime() {
            return pmWarningTime;
        }

        public void setPmWarningTime(String pmWarningTime) {
            this.pmWarningTime = pmWarningTime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceChange() {
            return priceChange;
        }

        public void setPriceChange(String priceChange) {
            this.priceChange = priceChange;
        }

        public int getPriceDigit() {
            return priceDigit;
        }

        public void setPriceDigit(int priceDigit) {
            this.priceDigit = priceDigit;
        }

        public boolean isRange() {
            return range;
        }

        public void setRange(boolean range) {
            this.range = range;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getSpread() {
            return spread;
        }

        public void setSpread(double spread) {
            this.spread = spread;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public static class CreateTimeBeanX implements Serializable{
            /**
             * date : 11
             * day : 4
             * hours : 14
             * minutes : 13
             * month : 5
             * seconds : 41
             * time : 1434003221557
             * timezoneOffset : -480
             * year : 115
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            @Override
            public String toString() {
                return "CreateTimeBeanX{" +
                        "date=" + date +
                        ", day=" + day +
                        ", hours=" + hours +
                        ", minutes=" + minutes +
                        ", month=" + month +
                        ", seconds=" + seconds +
                        ", time=" + time +
                        ", timezoneOffset=" + timezoneOffset +
                        ", year=" + year +
                        '}';
            }

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }

    public static class DomesticCommdsBean implements Serializable{
        @Override
        public String toString() {
            return "DomesticCommdsBean{" +
                    "amClearingTime='" + amClearingTime + '\'' +
                    ", amCloseTime='" + amCloseTime + '\'' +
                    ", amOpenTime='" + amOpenTime + '\'' +
                    ", amTradeTime='" + amTradeTime + '\'' +
                    ", amWarningTime='" + amWarningTime + '\'' +
                    ", classifyCode='" + classifyCode + '\'' +
                    ", classifyName='" + classifyName + '\'' +
                    ", code='" + code + '\'' +
                    ", commodityId='" + commodityId + '\'' +
                    ", contractCode='" + contractCode + '\'' +
                    ", createTime=" + createTime +
                    ", currency='" + currency + '\'' +
                    ", exchange='" + exchange + '\'' +
                    ", favor=" + favor +
                    ", foreign=" + foreign +
                    ", holiday='" + holiday + '\'' +
                    ", id=" + id +
                    ", moneyType=" + moneyType +
                    ", name='" + name + '\'' +
                    ", niteClearingTime='" + niteClearingTime + '\'' +
                    ", niteCloseTime='" + niteCloseTime + '\'' +
                    ", niteOpenTime='" + niteOpenTime + '\'' +
                    ", niteTradeTime='" + niteTradeTime + '\'' +
                    ", niteWarningTime='" + niteWarningTime + '\'' +
                    ", offset=" + offset +
                    ", order=" + order +
                    ", pmClearingTime='" + pmClearingTime + '\'' +
                    ", pmCloseTime='" + pmCloseTime + '\'' +
                    ", pmOpenTime='" + pmOpenTime + '\'' +
                    ", pmTradeTime='" + pmTradeTime + '\'' +
                    ", pmWarningTime='" + pmWarningTime + '\'' +
                    ", price=" + price +
                    ", priceChange=" + priceChange +
                    ", priceDigit=" + priceDigit +
                    ", range=" + range +
                    ", remark='" + remark + '\'' +
                    ", spread=" + spread +
                    ", valid=" + valid +
                    '}';
        }

        /**
         * amClearingTime : 11:30:00
         * amCloseTime : 11:30:00
         * amOpenTime : 09:00:00
         * amTradeTime : 09:00:00
         * amWarningTime : 11:30:00
         * classifyCode : 1
         * classifyName : 内盘商品
         * code : RU
         * commodityId : 10007
         * contractCode :
         * createTime : {"date":26,"day":1,"hours":19,"minutes":51,"month":9,"seconds":14,"time":1445860274427,"timezoneOffset":-480,"year":115}
         * currency : CNY
         * exchange : 上期所
         * favor : false
         * foreign : false
         * holiday : 2019-05-01 09:00:00,2019-05-04 22:55:00
         * id : 10076
         * moneyType : 0
         * name : 天然橡胶
         * niteClearingTime : 22:55:00
         * niteCloseTime : 23:00:00
         * niteOpenTime : 21:00:00
         * niteTradeTime : 21:00:00
         * niteWarningTime : 22:50:00
         * offset : 0
         * order : 4
         * pmClearingTime : 14:55:00
         * pmCloseTime : 15:00:00
         * pmOpenTime : 13:30:00
         * pmTradeTime : 13:30:00
         * pmWarningTime : 14:50:00
         * price : 10
         * priceChange : 5
         * priceDigit : 0
         * range : false
         * remark :
         * spread : 0
         * valid : true
         */

        private String amClearingTime;
        private String amCloseTime;
        private String amOpenTime;
        private String amTradeTime;
        private String amWarningTime;
        private String classifyCode;
        private String classifyName;
        private String code;
        private String commodityId;
        private String contractCode;
        private CreateTimeBeanXX createTime;
        private String currency;
        private String exchange;
        private boolean favor;
        private boolean foreign;
        private String holiday;
        private int id;
        private int moneyType;
        private String name;
        private String niteClearingTime;
        private String niteCloseTime;
        private String niteOpenTime;
        private String niteTradeTime;
        private String niteWarningTime;
        private int offset;
        private int order;
        private String pmClearingTime;
        private String pmCloseTime;
        private String pmOpenTime;
        private String pmTradeTime;
        private String pmWarningTime;
        private String price;
        private String priceChange;
        private int priceDigit;
        private boolean range;
        private String remark;
        private double spread;
        private boolean valid;

        public String getAmClearingTime() {
            return amClearingTime;
        }

        public void setAmClearingTime(String amClearingTime) {
            this.amClearingTime = amClearingTime;
        }

        public String getAmCloseTime() {
            return amCloseTime;
        }

        public void setAmCloseTime(String amCloseTime) {
            this.amCloseTime = amCloseTime;
        }

        public String getAmOpenTime() {
            return amOpenTime;
        }

        public void setAmOpenTime(String amOpenTime) {
            this.amOpenTime = amOpenTime;
        }

        public String getAmTradeTime() {
            return amTradeTime;
        }

        public void setAmTradeTime(String amTradeTime) {
            this.amTradeTime = amTradeTime;
        }

        public String getAmWarningTime() {
            return amWarningTime;
        }

        public void setAmWarningTime(String amWarningTime) {
            this.amWarningTime = amWarningTime;
        }

        public String getClassifyCode() {
            return classifyCode;
        }

        public void setClassifyCode(String classifyCode) {
            this.classifyCode = classifyCode;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public CreateTimeBeanXX getCreateTime() {
            return createTime;
        }

        public void setCreateTime(CreateTimeBeanXX createTime) {
            this.createTime = createTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public boolean isFavor() {
            return favor;
        }

        public void setFavor(boolean favor) {
            this.favor = favor;
        }

        public boolean isForeign() {
            return foreign;
        }

        public void setForeign(boolean foreign) {
            this.foreign = foreign;
        }

        public String getHoliday() {
            return holiday;
        }

        public void setHoliday(String holiday) {
            this.holiday = holiday;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMoneyType() {
            return moneyType;
        }

        public void setMoneyType(int moneyType) {
            this.moneyType = moneyType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNiteClearingTime() {
            return niteClearingTime;
        }

        public void setNiteClearingTime(String niteClearingTime) {
            this.niteClearingTime = niteClearingTime;
        }

        public String getNiteCloseTime() {
            return niteCloseTime;
        }

        public void setNiteCloseTime(String niteCloseTime) {
            this.niteCloseTime = niteCloseTime;
        }

        public String getNiteOpenTime() {
            return niteOpenTime;
        }

        public void setNiteOpenTime(String niteOpenTime) {
            this.niteOpenTime = niteOpenTime;
        }

        public String getNiteTradeTime() {
            return niteTradeTime;
        }

        public void setNiteTradeTime(String niteTradeTime) {
            this.niteTradeTime = niteTradeTime;
        }

        public String getNiteWarningTime() {
            return niteWarningTime;
        }

        public void setNiteWarningTime(String niteWarningTime) {
            this.niteWarningTime = niteWarningTime;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getPmClearingTime() {
            return pmClearingTime;
        }

        public void setPmClearingTime(String pmClearingTime) {
            this.pmClearingTime = pmClearingTime;
        }

        public String getPmCloseTime() {
            return pmCloseTime;
        }

        public void setPmCloseTime(String pmCloseTime) {
            this.pmCloseTime = pmCloseTime;
        }

        public String getPmOpenTime() {
            return pmOpenTime;
        }

        public void setPmOpenTime(String pmOpenTime) {
            this.pmOpenTime = pmOpenTime;
        }

        public String getPmTradeTime() {
            return pmTradeTime;
        }

        public void setPmTradeTime(String pmTradeTime) {
            this.pmTradeTime = pmTradeTime;
        }

        public String getPmWarningTime() {
            return pmWarningTime;
        }

        public void setPmWarningTime(String pmWarningTime) {
            this.pmWarningTime = pmWarningTime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceChange() {
            return priceChange;
        }

        public void setPriceChange(String priceChange) {
            this.priceChange = priceChange;
        }

        public int getPriceDigit() {
            return priceDigit;
        }

        public void setPriceDigit(int priceDigit) {
            this.priceDigit = priceDigit;
        }

        public boolean isRange() {
            return range;
        }

        public void setRange(boolean range) {
            this.range = range;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getSpread() {
            return spread;
        }

        public void setSpread(double spread) {
            this.spread = spread;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public static class CreateTimeBeanXX implements Serializable{
            /**
             * date : 26
             * day : 1
             * hours : 19
             * minutes : 51
             * month : 9
             * seconds : 14
             * time : 1445860274427
             * timezoneOffset : -480
             * year : 115
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            @Override
            public String toString() {
                return "CreateTimeBeanXX{" +
                        "date=" + date +
                        ", day=" + day +
                        ", hours=" + hours +
                        ", minutes=" + minutes +
                        ", month=" + month +
                        ", seconds=" + seconds +
                        ", time=" + time +
                        ", timezoneOffset=" + timezoneOffset +
                        ", year=" + year +
                        '}';
            }

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }



    public static class DigitalCommdsBean implements Serializable{
        @Override
        public String toString() {
            return "DomesticCommdsBean{" +
                    "amClearingTime='" + amClearingTime + '\'' +
                    ", amCloseTime='" + amCloseTime + '\'' +
                    ", amOpenTime='" + amOpenTime + '\'' +
                    ", amTradeTime='" + amTradeTime + '\'' +
                    ", amWarningTime='" + amWarningTime + '\'' +
                    ", classifyCode='" + classifyCode + '\'' +
                    ", classifyName='" + classifyName + '\'' +
                    ", code='" + code + '\'' +
                    ", commodityId='" + commodityId + '\'' +
                    ", contractCode='" + contractCode + '\'' +
                    ", createTime=" + createTime +
                    ", currency='" + currency + '\'' +
                    ", exchange='" + exchange + '\'' +
                    ", favor=" + favor +
                    ", foreign=" + foreign +
                    ", holiday='" + holiday + '\'' +
                    ", id=" + id +
                    ", moneyType=" + moneyType +
                    ", name='" + name + '\'' +
                    ", niteClearingTime='" + niteClearingTime + '\'' +
                    ", niteCloseTime='" + niteCloseTime + '\'' +
                    ", niteOpenTime='" + niteOpenTime + '\'' +
                    ", niteTradeTime='" + niteTradeTime + '\'' +
                    ", niteWarningTime='" + niteWarningTime + '\'' +
                    ", offset=" + offset +
                    ", order=" + order +
                    ", pmClearingTime='" + pmClearingTime + '\'' +
                    ", pmCloseTime='" + pmCloseTime + '\'' +
                    ", pmOpenTime='" + pmOpenTime + '\'' +
                    ", pmTradeTime='" + pmTradeTime + '\'' +
                    ", pmWarningTime='" + pmWarningTime + '\'' +
                    ", price=" + price +
                    ", priceChange=" + priceChange +
                    ", priceDigit=" + priceDigit +
                    ", range=" + range +
                    ", remark='" + remark + '\'' +
                    ", spread=" + spread +
                    ", valid=" + valid +
                    '}';
        }

        /**
         * amClearingTime : 11:30:00
         * amCloseTime : 11:30:00
         * amOpenTime : 09:00:00
         * amTradeTime : 09:00:00
         * amWarningTime : 11:30:00
         * classifyCode : 1
         * classifyName : 内盘商品
         * code : RU
         * commodityId : 10007
         * contractCode :
         * createTime : {"date":26,"day":1,"hours":19,"minutes":51,"month":9,"seconds":14,"time":1445860274427,"timezoneOffset":-480,"year":115}
         * currency : CNY
         * exchange : 上期所
         * favor : false
         * foreign : false
         * holiday : 2019-05-01 09:00:00,2019-05-04 22:55:00
         * id : 10076
         * moneyType : 0
         * name : 天然橡胶
         * niteClearingTime : 22:55:00
         * niteCloseTime : 23:00:00
         * niteOpenTime : 21:00:00
         * niteTradeTime : 21:00:00
         * niteWarningTime : 22:50:00
         * offset : 0
         * order : 4
         * pmClearingTime : 14:55:00
         * pmCloseTime : 15:00:00
         * pmOpenTime : 13:30:00
         * pmTradeTime : 13:30:00
         * pmWarningTime : 14:50:00
         * price : 10
         * priceChange : 5
         * priceDigit : 0
         * range : false
         * remark :
         * spread : 0
         * valid : true
         */

        private String amClearingTime;
        private String amCloseTime;
        private String amOpenTime;
        private String amTradeTime;
        private String amWarningTime;
        private String classifyCode;
        private String classifyName;
        private String code;
        private String commodityId;
        private String contractCode;
        private CreateTimeBeanXX createTime;
        private String currency;
        private String exchange;
        private boolean favor;
        private boolean foreign;
        private String holiday;
        private int id;
        private int moneyType;
        private String name;
        private String niteClearingTime;
        private String niteCloseTime;
        private String niteOpenTime;
        private String niteTradeTime;
        private String niteWarningTime;
        private int offset;
        private int order;
        private String pmClearingTime;
        private String pmCloseTime;
        private String pmOpenTime;
        private String pmTradeTime;
        private String pmWarningTime;
        private String price;
        private String priceChange;
        private int priceDigit;
        private boolean range;
        private String remark;
        private double spread;
        private boolean valid;

        public String getAmClearingTime() {
            return amClearingTime;
        }

        public void setAmClearingTime(String amClearingTime) {
            this.amClearingTime = amClearingTime;
        }

        public String getAmCloseTime() {
            return amCloseTime;
        }

        public void setAmCloseTime(String amCloseTime) {
            this.amCloseTime = amCloseTime;
        }

        public String getAmOpenTime() {
            return amOpenTime;
        }

        public void setAmOpenTime(String amOpenTime) {
            this.amOpenTime = amOpenTime;
        }

        public String getAmTradeTime() {
            return amTradeTime;
        }

        public void setAmTradeTime(String amTradeTime) {
            this.amTradeTime = amTradeTime;
        }

        public String getAmWarningTime() {
            return amWarningTime;
        }

        public void setAmWarningTime(String amWarningTime) {
            this.amWarningTime = amWarningTime;
        }

        public String getClassifyCode() {
            return classifyCode;
        }

        public void setClassifyCode(String classifyCode) {
            this.classifyCode = classifyCode;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public CreateTimeBeanXX getCreateTime() {
            return createTime;
        }

        public void setCreateTime(CreateTimeBeanXX createTime) {
            this.createTime = createTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public boolean isFavor() {
            return favor;
        }

        public void setFavor(boolean favor) {
            this.favor = favor;
        }

        public boolean isForeign() {
            return foreign;
        }

        public void setForeign(boolean foreign) {
            this.foreign = foreign;
        }

        public String getHoliday() {
            return holiday;
        }

        public void setHoliday(String holiday) {
            this.holiday = holiday;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMoneyType() {
            return moneyType;
        }

        public void setMoneyType(int moneyType) {
            this.moneyType = moneyType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNiteClearingTime() {
            return niteClearingTime;
        }

        public void setNiteClearingTime(String niteClearingTime) {
            this.niteClearingTime = niteClearingTime;
        }

        public String getNiteCloseTime() {
            return niteCloseTime;
        }

        public void setNiteCloseTime(String niteCloseTime) {
            this.niteCloseTime = niteCloseTime;
        }

        public String getNiteOpenTime() {
            return niteOpenTime;
        }

        public void setNiteOpenTime(String niteOpenTime) {
            this.niteOpenTime = niteOpenTime;
        }

        public String getNiteTradeTime() {
            return niteTradeTime;
        }

        public void setNiteTradeTime(String niteTradeTime) {
            this.niteTradeTime = niteTradeTime;
        }

        public String getNiteWarningTime() {
            return niteWarningTime;
        }

        public void setNiteWarningTime(String niteWarningTime) {
            this.niteWarningTime = niteWarningTime;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getPmClearingTime() {
            return pmClearingTime;
        }

        public void setPmClearingTime(String pmClearingTime) {
            this.pmClearingTime = pmClearingTime;
        }

        public String getPmCloseTime() {
            return pmCloseTime;
        }

        public void setPmCloseTime(String pmCloseTime) {
            this.pmCloseTime = pmCloseTime;
        }

        public String getPmOpenTime() {
            return pmOpenTime;
        }

        public void setPmOpenTime(String pmOpenTime) {
            this.pmOpenTime = pmOpenTime;
        }

        public String getPmTradeTime() {
            return pmTradeTime;
        }

        public void setPmTradeTime(String pmTradeTime) {
            this.pmTradeTime = pmTradeTime;
        }

        public String getPmWarningTime() {
            return pmWarningTime;
        }

        public void setPmWarningTime(String pmWarningTime) {
            this.pmWarningTime = pmWarningTime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceChange() {
            return priceChange;
        }

        public void setPriceChange(String priceChange) {
            this.priceChange = priceChange;
        }

        public int getPriceDigit() {
            return priceDigit;
        }

        public void setPriceDigit(int priceDigit) {
            this.priceDigit = priceDigit;
        }

        public boolean isRange() {
            return range;
        }

        public void setRange(boolean range) {
            this.range = range;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getSpread() {
            return spread;
        }

        public void setSpread(double spread) {
            this.spread = spread;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public static class CreateTimeBeanXX implements Serializable{
            /**
             * date : 26
             * day : 1
             * hours : 19
             * minutes : 51
             * month : 9
             * seconds : 14
             * time : 1445860274427
             * timezoneOffset : -480
             * year : 115
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            @Override
            public String toString() {
                return "CreateTimeBeanXX{" +
                        "date=" + date +
                        ", day=" + day +
                        ", hours=" + hours +
                        ", minutes=" + minutes +
                        ", month=" + month +
                        ", seconds=" + seconds +
                        ", time=" + time +
                        ", timezoneOffset=" + timezoneOffset +
                        ", year=" + year +
                        '}';
            }

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }

}
