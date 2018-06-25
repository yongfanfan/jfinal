package com.haitai.haitaitv.component.constant.operator;

import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.VodConsts;

/**
 * 杭州华数hzdtv相关的常量
 *
 * @author liuzhou
 *         create at 2017-05-04 10:26
 */
public class HzdtvConsts {

    public static final String OPERATOR_ID = "310051";
    public static final boolean FLAG = OPERATOR_ID.equals(ConfigConsts.COMMERCE_OPERATOR_ID);
    public static final String FOLDER_CONTENTS_INTERFACE =
            // http://hd2.hzdtv.tv/dataquery/folderContents?folderCode=p_28_12
            VodConsts.VOD_URL + "folderContents?folderCode=" + VodConsts.VOD_PORTAL_ID;
    public static final String CONTENT_PLAY_URL_INTERFACE =
            // http://hd2.hzdtv.tv/dataquery/contentPlayUrl?folderCode=p_28_12
            VodConsts.VOD_URL + "contentPlayUrl?folderCode=" + VodConsts.VOD_PORTAL_ID;

    private HzdtvConsts() {
    }
}
