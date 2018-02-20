package com.github.springcloud.stockcrawler.common;

import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;
import com.github.springcloud.stockcrawler.pageenity.BaiduStockDetail;

/**
 * Created by ganzhen on 14/02/2018.
 */
public class EntityConvertUtils {

    public static StockDetailDayRecordEntity convertBaiduStockDetail2StockDetailDayRecord(BaiduStockDetail detail){
        StockDetailDayRecordEntity entity = null;
        if(detail != null){
            entity = new StockDetailDayRecordEntity();
            entity.setChengJiaoE(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getCheng_jiao_e(),"亿","")));
            entity.setChengJiaoLiang(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getCheng_jiao_liang(),"万手","")));
            entity.setCreateTime(null);
            entity.setDieTing(BaseClassConvertUtils.string2BigDecimal(detail.getDie_ting()));
            entity.setHuanShouLv(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getHuan_shou_lv(),"%","")));
            entity.setId(null);
            entity.setJinKai(BaseClassConvertUtils.string2BigDecimal(detail.getJin_kay()));
            entity.setLiangBi(BaseClassConvertUtils.string2BigDecimal(detail.getLiang_bi()));
            entity.setLiuTongGuBen(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getLiu_tong_gu_ben(),"亿","")));
            entity.setLiuTongShiZhi(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getLiu_tong_shi_zhi(),"亿","")));
            entity.setMeiGuJingZiChan(BaseClassConvertUtils.string2BigDecimal(detail.getMei_gu_jing_zi_chan()));
            entity.setMeiGuShouYi(BaseClassConvertUtils.string2BigDecimal(detail.getMei_gu_shou_yi()));
            entity.setNeiPan(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getNei_pan_shou_shu(),"万手","")));
            entity.setShiJingLv(BaseClassConvertUtils.string2BigDecimal(detail.getShi_jing_lv()));
            entity.setShiYingLv(BaseClassConvertUtils.string2BigDecimal(detail.getShi_ying_lv()));
            entity.setWaiPan(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getWai_pan_shou_shu(),"万手","")));
            entity.setWeiBi(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getWei_bi(),"%","")));
            entity.setZhangTing(BaseClassConvertUtils.string2BigDecimal(detail.getZhang_ting()));
            entity.setZhenFu(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getZhen_fu(),"%","")));
            entity.setZongGuBen(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getZong_gu_ben(),"亿","")));
            entity.setZongShiZhi(BaseClassConvertUtils.string2BigDecimal(MyStringUtils.replaceAllByStr(detail.getZong_shi_zhi(),"亿","")));
            entity.setZuiDi(BaseClassConvertUtils.string2BigDecimal(detail.getZui_di()));
            entity.setZuiGao(BaseClassConvertUtils.string2BigDecimal(detail.getZui_gao()));
            entity.setZuoShou(BaseClassConvertUtils.string2BigDecimal(detail.getZuo_shou()));

        }
        return entity;
    }
}
