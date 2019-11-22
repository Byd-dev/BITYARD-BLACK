package com.ltqh.qh.operation.quotebase;

import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OBankListEntity;
import com.ltqh.qh.operation.entity.OBaseMineEntity;
import com.ltqh.qh.operation.entity.OMineEntity;
import com.ltqh.qh.operation.entity.OPositionEntity;
import com.ltqh.qh.operation.entity.ORechargeEntity;
import com.ltqh.qh.operation.entity.OTradeListEntity;
import com.ltqh.qh.operation.entity.OwithdrawEntity;
import com.ltqh.qh.operation.utils.OUtil;

import java.util.List;
import java.util.Set;

public class QuoteProxy {

    private static QuoteProxy instance;


    public static QuoteProxy getInstance() {
        if (instance == null) {
            instance = new QuoteProxy();
        }
        return instance;
    }


    private ORechargeEntity oRechargeEntity;
    private OBaseMineEntity oBaseMineEntity;

    private OwithdrawEntity owithdrawEntity;

    public OwithdrawEntity getOwithdrawEntity() {
        return owithdrawEntity;
    }

    public void setOwithdrawEntity(OwithdrawEntity owithdrawEntity) {
        this.owithdrawEntity = owithdrawEntity;
    }

    public OBaseMineEntity getoBaseMineEntity() {
        return oBaseMineEntity;
    }

    public void setoBaseMineEntity(OBaseMineEntity oBaseMineEntity) {
        this.oBaseMineEntity = oBaseMineEntity;
    }

    private String MoniDeposit;
    private String ShipanDeposit;

    private String MoniService;
    private String ShipanService;

    private List<String> MoniCloseList;
    private List<String> ShipanCloseList;

    public ORechargeEntity getoRechargeEntity() {
        return oRechargeEntity;
    }

    public void setoRechargeEntity(ORechargeEntity oRechargeEntity) {
        this.oRechargeEntity = oRechargeEntity;
    }

    public List<String> getMoniCloseList() {
        return MoniCloseList;
    }

    public void setMoniCloseList(List<String> moniCloseList) {
        MoniCloseList = moniCloseList;
    }

    public List<String> getShipanCloseList() {
        return ShipanCloseList;
    }

    public void setShipanCloseList(List<String> shipanCloseList) {
        ShipanCloseList = shipanCloseList;
    }

    public String getMoniService() {
        return MoniService;
    }

    public void setMoniService(String moniService) {
        MoniService = moniService;
    }

    public String getShipanService() {
        return ShipanService;
    }

    public void setShipanService(String shipanService) {
        ShipanService = shipanService;
    }

    public String getMoniDeposit() {
        return MoniDeposit;
    }

    public void setMoniDeposit(String moniDeposit) {
        MoniDeposit = moniDeposit;
    }

    public String getShipanDeposit() {
        return ShipanDeposit;
    }

    public void setShipanDeposit(String shipanDeposit) {
        ShipanDeposit = shipanDeposit;
    }



    private double moniincome;

    private double shipanincome;

    private boolean isLogin;

    private double eagle;
    private String quoteUrl;

    public String getQuoteUrl() {
        return quoteUrl;
    }

    public void setQuoteUrl(String quoteUrl) {
        this.quoteUrl = quoteUrl;
    }

    private OMineEntity oMineEntity;

    public OMineEntity getoMineEntity() {
        return oMineEntity;
    }

    public void setoMineEntity(OMineEntity oMineEntity) {
        this.oMineEntity = oMineEntity;
    }

    public double getEagle() {
        return eagle;
    }

    public void setEagle(double eagle) {
        this.eagle = eagle;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public double getShipanincome() {
        return shipanincome;
    }


    public void setShipanincome(double shipanincome) {
        double v = OUtil.double1Point(shipanincome);
        this.shipanincome = v;
    }



    public double getMoniincome() {

        return moniincome;
    }

    public void setMoniincome(double moniincome) {
        double v = OUtil.double1Point(moniincome);
        this.moniincome = v;
    }

    private List<String> foreignList;
    private List<String> stockindexList;
    private List<String> domesList;
    private List<String> digitalList;

    public List<String> getDigitalList() {
        return digitalList;
    }

    public void setDigitalList(List<String> digitalList) {
        this.digitalList = digitalList;
    }

    private OPositionEntity oPositionEntity;

    private OBankListEntity oBankListEntity;

    public OBankListEntity getoBankListEntity() {
        return oBankListEntity;
    }

    public void setoBankListEntity(OBankListEntity oBankListEntity) {
        this.oBankListEntity = oBankListEntity;
    }

    private OPositionEntity oPositionMoniEntity;

    private OTradeListEntity oTradeListMoniEntity;

    private OTradeListEntity oTradeListEntity;

    public OTradeListEntity getoTradeListEntity() {
        return oTradeListEntity;
    }

    public void setoTradeListEntity(OTradeListEntity oTradeListEntity) {
        this.oTradeListEntity = oTradeListEntity;
    }

    public OTradeListEntity getoTradeListMoniEntity() {
        return oTradeListMoniEntity;
    }

    public void setoTradeListMoniEntity(OTradeListEntity oTradeListMoniEntity) {
        this.oTradeListMoniEntity = oTradeListMoniEntity;
    }

    public OPositionEntity getoPositionMoniEntity() {
        return oPositionMoniEntity;
    }

    public void setoPositionMoniEntity(OPositionEntity oPositionMoniEntity) {
        this.oPositionMoniEntity = oPositionMoniEntity;
    }

    public OPositionEntity getoPositionEntity() {
        return oPositionEntity;
    }

    public void setoPositionEntity(OPositionEntity oPositionEntity) {
        this.oPositionEntity = oPositionEntity;
    }

    public List<String> getForeignList() {
        return foreignList;
    }

    public void setForeignList(List<String> foreignList) {
        this.foreignList = foreignList;
    }

    public List<String> getStockindexList() {
        return stockindexList;
    }

    public void setStockindexList(List<String> stockindexList) {
        this.stockindexList = stockindexList;
    }

    public List<String> getDomesList() {
        return domesList;
    }

    public void setDomesList(List<String> domesList) {
        this.domesList = domesList;
    }



    private List<String> dataList;
    private List<String> foreigndataList;
    private List<String> stockindexdataList;
    private List<String> domesdataList;
    private List<String> digitalDataList;

    public List<String> getDigitalDataList() {
        return digitalDataList;
    }

    public void setDigitalDataList(List<String> digitalDataList) {
        this.digitalDataList = digitalDataList;
    }

    private Set<String> minelist;



    public static void setInstance(QuoteProxy instance) {
        QuoteProxy.instance = instance;
    }



    public Set<String> getMinelist() {
        return minelist;
    }

    public void setMinelist(Set<String> minelist) {
        this.minelist = minelist;
    }

    public List<String> getForeigndataList() {
        return foreigndataList;
    }

    public void setForeigndataList(List<String> foreigndataList) {
        this.foreigndataList = foreigndataList;
    }

    public List<String> getStockindexdataList() {
        return stockindexdataList;
    }

    public void setStockindexdataList(List<String> stockindexdataList) {
        this.stockindexdataList = stockindexdataList;
    }

    public List<String> getDomesdataList() {
        return domesdataList;
    }

    public void setDomesdataList(List<String> domesdataList) {
        this.domesdataList = domesdataList;
    }




    private String foreignStr;

    private OApiEntity oApiEntity;


    public List<String> getDataList() {

        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public OApiEntity getoApiEntity() {
        return oApiEntity;
    }

    public void setoApiEntity(OApiEntity oApiEntity) {
        this.oApiEntity = oApiEntity;
    }

    public String getForeignStr() {
        return foreignStr;
    }

    public void setForeignStr(String foreignStr) {
        this.foreignStr = foreignStr;
    }


}
