package com.android.superli.btremote.hid;

import com.android.superli.btremote.R;
import com.android.superli.btremote.bean.KeyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: KeyConfigs
 * @Description: java类作用描述
 * @Author: taohaili
 * @CreateDate: 2021/5/5 9:38
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/5/5 9:38
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class KeyConfigs {

    public static List<KeyBean> getKeys() {
        List<KeyBean> data = new ArrayList<>();
        data.add(new KeyBean(R.id.tv_esc, "41"));
        data.add(new KeyBean(R.id.tv_f1, "58"));
        data.add(new KeyBean(R.id.tv_f2, "59"));
        data.add(new KeyBean(R.id.tv_f3, "60"));
        data.add(new KeyBean(R.id.tv_f4, "61"));
        data.add(new KeyBean(R.id.tv_f5, "62"));
        data.add(new KeyBean(R.id.tv_f6, "63"));
        data.add(new KeyBean(R.id.tv_f7, "64"));
        data.add(new KeyBean(R.id.tv_f8, "65"));
        data.add(new KeyBean(R.id.tv_f9, "66"));
        data.add(new KeyBean(R.id.tv_f10, "67"));
        data.add(new KeyBean(R.id.tv_f11, "68"));
        data.add(new KeyBean(R.id.tv_f12, "69"));
        data.add(new KeyBean(R.id.tv_del, "76"));

        data.add(new KeyBean(R.id.tv_bofang, "53"));
        data.add(new KeyBean(R.id.tv_1, "30"));
        data.add(new KeyBean(R.id.tv_2, "31"));
        data.add(new KeyBean(R.id.tv_3, "32"));
        data.add(new KeyBean(R.id.tv_4, "33"));
        data.add(new KeyBean(R.id.tv_5, "34"));
        data.add(new KeyBean(R.id.tv_6, "35"));
        data.add(new KeyBean(R.id.tv_7, "36"));
        data.add(new KeyBean(R.id.tv_8, "37"));
        data.add(new KeyBean(R.id.tv_9, "38"));
        data.add(new KeyBean(R.id.tv_10, "39"));
        data.add(new KeyBean(R.id.tv_hengkang, "45"));
        data.add(new KeyBean(R.id.tv_dengyu, "46"));
        data.add(new KeyBean(R.id.tv_backspace, "42"));

        data.add(new KeyBean(R.id.tv_tab, "43"));
        data.add(new KeyBean(R.id.tv_q, "20"));
        data.add(new KeyBean(R.id.tv_w, "26"));
        data.add(new KeyBean(R.id.tv_e, "8"));
        data.add(new KeyBean(R.id.tv_r, "21"));
        data.add(new KeyBean(R.id.tv_t, "23"));
        data.add(new KeyBean(R.id.tv_y, "28"));
        data.add(new KeyBean(R.id.tv_u, "24"));
        data.add(new KeyBean(R.id.tv_i, "12"));
        data.add(new KeyBean(R.id.tv_o, "18"));
        data.add(new KeyBean(R.id.tv_p, "19"));
        data.add(new KeyBean(R.id.tv_dakuohao1, "47"));
        data.add(new KeyBean(R.id.tv_dakuohao2, "48"));
        data.add(new KeyBean(R.id.tv_xiekuang, "49"));

        data.add(new KeyBean(R.id.tv_caps, "57"));
        data.add(new KeyBean(R.id.tv_a, "4"));
        data.add(new KeyBean(R.id.tv_s, "22"));
        data.add(new KeyBean(R.id.tv_d, "7"));
        data.add(new KeyBean(R.id.tv_f, "9"));
        data.add(new KeyBean(R.id.tv_g, "10"));
        data.add(new KeyBean(R.id.tv_h, "11"));
        data.add(new KeyBean(R.id.tv_j, "13"));
        data.add(new KeyBean(R.id.tv_k, "14"));
        data.add(new KeyBean(R.id.tv_l, "15"));
        data.add(new KeyBean(R.id.tv_maohao, "51"));
        data.add(new KeyBean(R.id.tv_yuhao, "52"));
        data.add(new KeyBean(R.id.tv_enter, "40"));


        data.add(new KeyBean(R.id.tv_shift, "M2"));
        data.add(new KeyBean(R.id.tv_z, "29"));
        data.add(new KeyBean(R.id.tv_x, "27"));
        data.add(new KeyBean(R.id.tv_c, "6"));
        data.add(new KeyBean(R.id.tv_v, "25"));
        data.add(new KeyBean(R.id.tv_b, "5"));
        data.add(new KeyBean(R.id.tv_n, "17"));
        data.add(new KeyBean(R.id.tv_m, "16"));
        data.add(new KeyBean(R.id.tv_jiankuohao1, "54"));
        data.add(new KeyBean(R.id.tv_jiankuohao2, "55"));
        data.add(new KeyBean(R.id.tv_wenhao, "56"));
        data.add(new KeyBean(R.id.tv_shift2, "M32"));

        data.add(new KeyBean(R.id.tv_ctrl, "M1"));
//        data.add(new KeyBean(R.id.tv_fn,"M32"));
        data.add(new KeyBean(R.id.tv_win, "M8"));
        data.add(new KeyBean(R.id.tv_alt, "M4"));
        data.add(new KeyBean(R.id.tv_space, "44"));
        data.add(new KeyBean(R.id.tv_alt2, "M4"));
        data.add(new KeyBean(R.id.tv_ctrl2, "M16"));
        data.add(new KeyBean(R.id.tv_xiangzuo, "80"));
        data.add(new KeyBean(R.id.tv_xiangshang, "82"));
        data.add(new KeyBean(R.id.tv_xiangxia, "81"));
        data.add(new KeyBean(R.id.tv_xiangyou, "79"));

        return data;
    }

    public static List<KeyBean> getNumKeys() {
        List<KeyBean> data = new ArrayList<>();
        data.add(new KeyBean(R.id.tv_1, "30"));
        data.add(new KeyBean(R.id.tv_2, "31"));
        data.add(new KeyBean(R.id.tv_3, "32"));
        data.add(new KeyBean(R.id.tv_4, "33"));
        data.add(new KeyBean(R.id.tv_5, "34"));
        data.add(new KeyBean(R.id.tv_6, "35"));
        data.add(new KeyBean(R.id.tv_7, "36"));
        data.add(new KeyBean(R.id.tv_8, "37"));
        data.add(new KeyBean(R.id.tv_9, "38"));
        data.add(new KeyBean(R.id.tv_0, "39"));
        data.add(new KeyBean(R.id.tv_kang, "45"));
        data.add(new KeyBean(R.id.tv_e, "119"));
        return data;
    }


    public static List<KeyBean> getKeys3() {
        List<KeyBean> data = new ArrayList<>();
        data.add(new KeyBean(R.id.iv_power, "102"));
        data.add(new KeyBean(R.id.iv_input, "88"));
        data.add(new KeyBean(R.id.iv_menu, "95"));
        return data;
    }

    public static List<KeyBean> getKeys4() {
        List<KeyBean> data = new ArrayList<>();
        data.add(new KeyBean(R.id.iv_home, "40"));
        data.add(new KeyBean(R.id.iv_mute, "127"));
        return data;
    }

    public static List<KeyBean> getKeys5() {
        List<KeyBean> data = new ArrayList<>();
        data.add(new KeyBean(R.id.iv_volume_up, "128"));
        data.add(new KeyBean(R.id.iv_volume_down, "129"));
        data.add(new KeyBean(R.id.iv_shang, "82"));
        data.add(new KeyBean(R.id.iv_xia, "81"));
        data.add(new KeyBean(R.id.iv_zuo, "80"));
        data.add(new KeyBean(R.id.iv_you, "79"));
        data.add(new KeyBean(R.id.iv_ok, "40"));
        return data;
    }


    public static List<KeyBean> getOtherKey() {
        List<KeyBean> data = new ArrayList<>();
        data.add(new KeyBean("帮助", "117"));
        data.add(new KeyBean("选择", "118"));
        data.add(new KeyBean("停止", "119"));
        data.add(new KeyBean("取消", "155"));
        data.add(new KeyBean("清除", "156"));
        data.add(new KeyBean("退格", "42"));
        data.add(new KeyBean("ESCAPE", "41"));
        data.add(new KeyBean("Space", "44"));
        data.add(new KeyBean("TAB", "43"));
        data.add(new KeyBean("APP", "101"));
        data.add(new KeyBean("Execute", "116"));
        data.add(new KeyBean("PageUP", "75"));
        data.add(new KeyBean("PageDown", "78"));
        data.add(new KeyBean("Home1", "74"));
        data.add(new KeyBean("Right Arrow", "79"));
        data.add(new KeyBean("Left Arrow", "80"));
        data.add(new KeyBean("Down Arrow", "81"));
        data.add(new KeyBean("Up Arrow", "82"));
        data.add(new KeyBean("Mvioce", "62"));
        data.add(new KeyBean("ExSel", "164"));
        return data;
    }

    public static List<KeyBean> getOtherKey2() {
        List<KeyBean> data = new ArrayList<>();
        data.add(new KeyBean(R.id.tv_bangzhu, "117"));
        data.add(new KeyBean(R.id.tv_xuanzhe, "118"));
        data.add(new KeyBean(R.id.tv_tingzhi, "119"));
        data.add(new KeyBean(R.id.tv_quxiao, "155"));
        data.add(new KeyBean(R.id.tv_qingchu, "156"));
        data.add(new KeyBean(R.id.tv_tuige, "42"));
        data.add(new KeyBean(R.id.tv_escape, "41"));
        data.add(new KeyBean(R.id.tv_space, "44"));
        data.add(new KeyBean(R.id.tv_tab, "43"));
        data.add(new KeyBean(R.id.tv_app, "101"));
        data.add(new KeyBean(R.id.tv_execute, "116"));
        data.add(new KeyBean(R.id.tv_pageup, "75"));
        data.add(new KeyBean(R.id.tv_pagedown, "78"));
        data.add(new KeyBean(R.id.tv_home1, "74"));
        data.add(new KeyBean(R.id.tv_right_arrow, "79"));
        data.add(new KeyBean(R.id.tv_left_arrow, "80"));
        data.add(new KeyBean(R.id.tv_down_arrow, "81"));
        data.add(new KeyBean(R.id.tv_up_arrow, "82"));
        data.add(new KeyBean(R.id.tv_mvioce, "62"));
        data.add(new KeyBean(R.id.tv_exsel, "164"));
        return data;
    }
}
