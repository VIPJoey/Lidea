/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.test;

import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.TimeUtil;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

/**
 * @author Joey
 * @date 2019/8/5 23:49
 */
public class ByteTest {

    public static void main(String[] args) {

        String json =
                "{\"data\":{\"storeId\":\"1078823925183295617\",\"couponId\":\"\",\"commissionRate\":20.0,\"commissionAmount\":0.02,\"\n"
                        + "searchTag\":\"1078823925183295617 P0测试用例店铺1 13304952 13304955 多规格会员价商品2 10003762_-12154_13304955\",\"storeName\":\"P0测试用例店铺1\",\"stdFirstCategoryId\":\"\",\"stdFirstCategoryName\":\"\",\"stdSecondCategoryId\":\"\"\n"
                        + ",\"stdThirdCategoryId\":\"\",\"promoteStatus\":1,\"amountCoupon\":0.0,\"customTags\":{},\"customTagsV2\":[],\"buyPrice\":0.1,\"buyCommissionAmount\":0.02,\"organizeIds\":[10003762,10006481],\"delFlag2\":0,\"hasCoupon\":\"2\",\"esName\":\"y_c\n"
                        + "ps_sku_v1\",\"id\":183031530,\"routekey\":\"10003762_-12154_13304955\",\"isAvailable\":1,\"kaId\":10002039,\"organizeId\":10003762,\"catalogId\":-12154,\"skuId\":\"13304955\",\"spuId\":\"13304952\",\"skuNameChinese\":\"多规格会员价商品2\",\"m\n"
                        + "ediaInfo\":\"{\\\"has_detail\\\":\\\"2\\\",\\\"primary_imgs\\\":{\\\"img_url\\\":\\\"https://bizmid-material-qa-1302115263.file.myqcloud.com/persist/lwgzkb1h9fuu/lwgzkb1h9fuu-ade28caf.jpg\\\"},\\\"imgs\\\":[{\\\"img_url\\\":\\\"https://bizmid-mat\n"
                        + "erial-qa-1302115263.file.myqcloud.com/persist/lwgzkb1h9fuu/lwgzkb1h9fuu-ade28caf.jpg\\\"}],\\\"prop_imgs\\\":[],\\\"detail_imgs\\\":[],\\\"videos\\\":[]}\",\"categoryInfo\":\"[{\\\"category_type\\\":2,\\\"category_level_1_id\\\":\\\"\\\",\\\"cate\n"
                        + "gory_level_1_name\\\":\\\"\\\",\\\"category_level_2_id\\\":\\\"\\\",\\\"category_level_2_name\\\":\\\"\\\",\\\"category_level_3_id\\\":\\\"\\\",\\\"category_level_3_name\\\":\\\"\\\"}]\",\"salesInfo\":\"[{\\\"is_available\\\":true,\\\"sku_stock_status\\\":1,\\\"curr\n"
                        + "ent_price\\\":0.1,\\\"sku_price\\\":0.0,\\\"url_miniprogram\\\":\\\"supermarket/pages/goods/detail/detail?spuId\\u003d13304952\\\",\\\"miniprogram_appid\\\":\\\"wx23030d73563f52c0\\\",\\\"miniprogram_username\\\":\\\"gh_f56940588c92\\\",\\\"extern\n"
                        + "al_store_id\\\":\\\"1078823925183295617\\\",\\\"store_name\\\":\\\"P0测试用例店铺1\\\",\\\"logo\\\":\\\"https://bizmid-material-qa-1302115263.file.myqcloud.com/persist/lrory6wsbep3/b55eadfb.jpeg\\\",\\\"url_store_h5\\\":\\\"https://wxaurl.cn/\n"
                        + "Ub7PyAcsT1j?sid\\u003d1078823925183295617\\\",\\\"url_store_miniprogram\\\":\\\"supermarket/pages/home/home/home\\\",\\\"url_h5\\\":\\\"https://wxaurl.cn/97mF57OyKti?gid\\u003d13304952\\u0026sid\\u003d1078823925183295617\\\",\\\"shop_oper\n"
                        + "ation_model\\\":0}]\",\"couponInfo\":\"[]\",\"thirdPromotionInfo\":\"{\\\"commission_rate\\\":20.0,\\\"promote_status\\\":1,\\\"commission_amount\\\":0.02,\\\"start_time\\\":\\\"1665642764000\\\",\\\"end_time\\\":\\\"2660781898000\\\",\\\"inner_commissio\n"
                        + "n_type\\\":0}\",\"promotionInfo\":\"[]\",\"extInfo\":\"{\\\"ka_name\\\":\\\"云mall—商铺五号\\\",\\\"product_sub_source\\\":\\\"云mall\\\",\\\"custom_category_info\\\":[{\\\"category_type\\\":2,\\\"category_level1_id\\\":\\\"1279508\\\",\\\"category_level1_na\n"
                        + "me\\\":\\\"虚拟充值\\\",\\\"category_level2_id\\\":\\\"\\\",\\\"category_level2_name\\\":\\\"\\\",\\\"category_level3_id\\\":\\\"\\\",\\\"category_level3_name\\\":\\\"\\\"}]}\",\"insertTime\":1673320461000,\"updateTime\":1673334935000,\"delFlag\":0,\"afterSale\n"
                        + "Info\":\"{\\\"service_type\\\":1}\",\"nluInfo\":\"{\\\"std_first_category_id\\\":\\\"\\\",\\\"std_first_category_name\\\":\\\"\\\",\\\"std_second_category_id\\\":\\\"\\\",\\\"std_second_category_name\\\":\\\"\\\",\\\"std_third_category_id\\\":\\\"\\\",\\\"std_third_\n"
                        + "category_name\\\":\\\"\\\",\\\"std_fourth_category_id\\\":\\\"\\\",\\\"std_fourth_category_name\\\":\\\"\\\",\\\"ams_first_category_id\\\":\\\"\\\",\\\"ams_first_category_name\\\":\\\"\\\",\\\"ams_second_category_id\\\":\\\"\\\",\\\"ams_second_category_name\\\":\\\"\n"
                        + "\\\",\\\"ams_third_category_id\\\":\\\"\\\",\\\"ams_third_category_name\\\":\\\"\\\",\\\"ams_fourth_category_id\\\":\\\"\\\",\\\"ams_fourth_category_name\\\":\\\"\\\",\\\"product_id\\\":0,\\\"core_product\\\":\\\"\\\",\\\"product_word\\\":\\\"\\\",\\\"attributes\\\":\\\"\\\",\n"
                        + "\\\"brand_word\\\":\\\"\\\",\\\"brand_word_chs\\\":\\\"\\\",\\\"brand_word_eng\\\":\\\"\\\",\\\"brand_info\\\":{\\\"brand_word\\\":\\\"\\\",\\\"brand_word_chs\\\":\\\"\\\",\\\"brand_word_eng\\\":\\\"\\\"}}\",\"commentInfo\":\"{\\\"comment_num\\\":0,\\\"positive_comment_rating\n"
                        + "\\\":0.0}\",\"skuPrice\":0.0,\"currentPrice\":0.1,\"warehouseId\":0,\"productSource\":\"YMALL\",\"productInfo\":\"{\\\"brand_name\\\":\\\"P0测试用例店铺1\\\",\\\"color\\\":{\\\"color_rgb\\\":\\\"\\\",\\\"color_name\\\":\\\"\\\"},\\\"prop_info\\\":[{\\\"prop_id\\\":1\n"
                        + "0922095,\\\"prop_name\\\":\\\"颜色\\\",\\\"value_id\\\":10922097,\\\"value_name\\\":\\\"白\\\"},{\\\"prop_id\\\":10922092,\\\"prop_name\\\":\\\"大小\\\",\\\"value_id\\\":10922093,\\\"value_name\\\":\\\"大\\\"}]}\",\"auditFlag\":1,\"skuFlag\":2,\"dataSourceType\":\"s\n"
                        + "r_access_sku\",\"adFeatGoodInfo\":\"{\\\"id\\\":\\\"\\\",\\\"title\\\":\\\"多规格会员价商品2\\\",\\\"cat1\\\":\\\"电脑办公\\\",\\\"cat2\\\":\\\"文具耗材\\\",\\\"cat3\\\":\\\"财会用品\\\",\\\"seg_title\\\":\\\"多 规 规格 格 会 会员 会员价 价 商 商品 品 2\\\",\\\"brand\\\n"
                        + "\":\\\"\\\",\\\"nlu_brands\\\":\\\"\\\",\\\"nlu_coreprods\\\":\\\"\\\",\\\"word_score\\\":[{\\\"word\\\":\\\"多\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"规\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"规格\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"格\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"会\\\",\\\n"
                        + "\"score\\\":0.0},{\\\"word\\\":\\\"会员\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"会员价\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"价\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"商\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"商品\\\",\\\"score\\\":0.0},{\\\"word\\\":\\\"品\\\",\\\"score\\\":0.0},\n"
                        + "{\\\"word\\\":\\\"2\\\",\\\"score\\\":0.14999998}],\\\"rel_qp\\\":\\\"{\\\\\\\"single\\\\\\\":[],\\\\\\\"extend\\\\\\\":[],\\\\\\\"synonym\\\\\\\":[],\\\\\\\"seg\\\\\\\":[\\\\\\\"多规格\\\\\\\",\\\\\\\"会员价\\\\\\\",\\\\\\\"商品2\\\\\\\"],\\\\\\\"x_seg\\\\\\\":[\\\\\\\"商品2#\\\\\\\"],\\\\\\\"index_seg\\\\\\\":[\\\\\\\"多规格\\\\\\\",\\\\\\\"多规\\\\\\\",\\\\\\\"规格\\\\\\\",\\\\\\\"会员价\\\\\\\",\\\\\\\"会员\\\\\\\",\\\\\\\"商品2\\\\\\\",\\\\\\\"商品\\\\\\\"],\\\\\\\"bigram\\\\\\\":[\\\\\\\"格会\\\\\\\",\\\\\\\"员价\\\\\\\",\\\\\\\"价商\\\\\\\"]}\\\",\\\"jd_cate1\\\":\\\"4938\\\",\\\"jd_cate2\\\":\\\"9394\\\",\\\"jd_cate3\\\":\\\"9392\\\",\\\"version\\\":1673320515226,\\\"basic_seg_title\\\":\\\"多 规格 会员 价 商 品 2\\\",\\\"std_seg_title\\\":\\\"多规格 会员价 商品 2\\\",\\\"algorithmic_mark_info_json\\\":\\\"{\\\\\\\"amsCategoryId1V3\\\\\\\":133,\\\\\\\"amsCategoryId2V3\\\\\\\":133283,\\\\\\\"amsCategoryId3V3\\\\\\\":1332831657,\\\\\\\"nluCoreProdsV3\\\\\\\":null,\\\\\\\"nluCoreProdsV4\\\\\\\":null,\\\\\\\"nluBrandsV3\\\\\\\":null,\\\\\\\"nluBrandsV4\\\\\\\":null,\\\\\\\"nluOtherProdsV3\\\\\\\":null,\\\\\\\"wordScores\\\\\\\":\\\\\\\"多规格:2;;会员价:1;;商品:1;;2:0\\\\\\\",\\\\\\\"basicSegTitleV2\\\\\\\":\\\\\\\"多 规格 会员 价 商 品 2\\\\\\\",\\\\\\\"stdSegTitleV2\\\\\\\":\\\\\\\"多规格 会员价 商品 2\\\\\\\",\\\\\\\"basicSegProductDesc\\\\\\\":null,\\\\\\\"stdSegProductDesc\\\\\\\":null}\\\"}\",\"skuStockStatus\":1,\"vv\":1},\"header\":{\"traceId\":\"260a70785e1044ccb800220ebf1dafc2\",\"timestamp\":1673334934701,\"channel\":100,\"seq\":287608,\"repushFlag\":false}}";

        System.out.println(json.getBytes(StandardCharsets.UTF_8).length);

    }


    @Test
    public void test() {


        byte[] bb = hexStringToBytes("7FFFFE939DFBECAF");

        long l = BytesUtils.bytesToLong(bb, 0);
        System.out.println(l);

        System.out.println(TimeUtil.reverseTimeMillis(9223370471842245807L));

        System.out.println(TimeUtil.timestampToString(1565012530000L, TimeUtil.yyyyMMddHHmmssSSS));
        System.out.println(TimeUtil.timestampToString(1565012530000L));

    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


}
