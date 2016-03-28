package com.twtstudio.coder.saishi_android.ui.main.drawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.ImageInfo;
import com.twtstudio.coder.saishi_android.bean.MenuModel;
import com.twtstudio.coder.saishi_android.support.DeviceUtils;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;
import com.twtstudio.coder.saishi_android.ui.common.ImageHelper;
import com.twtstudio.coder.saishi_android.ui.common.ListViewDIY;
import com.twtstudio.coder.saishi_android.ui.common.OnGetImageCallback;
import com.twtstudio.coder.saishi_android.ui.common.OnItemClickListener;
import com.twtstudio.coder.saishi_android.ui.file.FileFragment;
import com.twtstudio.coder.saishi_android.ui.main.MainActivity;
import com.twtstudio.coder.saishi_android.ui.main.MainView;
import com.twtstudio.coder.saishi_android.ui.main.list.DataFragment;
import com.twtstudio.coder.saishi_android.ui.schedule.ScheduleFragment;
import com.twtstudio.coder.saishi_android.ui.settings.SettingsActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by clifton on 16-2-28.
 */
public class MenuFragment extends Fragment implements OnItemClickListener, OnGetImageCallback {
    private static final String LOG_TAG = MenuFragment.class.getSimpleName();

    @Bind(R.id.iv_drawer_header)
    ImageView mIvHeader;
    @Bind(R.id.lv_menu)
    ListViewDIY lv_menu;
    @Bind(R.id.user_profile_image)
    CircleImageView iv_profile_icon;
    @Bind(R.id.tv_username)
    TextView tv_name;
    @Bind(R.id.tv_user_profile_name)
    TextView tv_profile_username;
    @Bind(R.id.tv_user_profile_position)
    TextView tv_profile_position;
    @Bind(R.id.tv_version)
    TextView tv_vertion_name;

    private final static String HUIWU = "Huiwu";
    private final static String DONGTAI = "Dongtai";
    private final static String TONGZHI = "Tongzhi";
    private final static String RICHENG = "Richeng";
    private List<MenuModel> list;
    private MenuAdapter adapter;
    private int save = -1;
    private int tag = 0;
    private int tagg = 0;
    private TreeSet<Integer> tags = new TreeSet<>();

    private DataFragment dataFragment_one;
    private DataFragment dataFragment_two;
    private DataFragment dataFragment_three;
    private ScheduleFragment scheduleFragment;
    private FileFragment fileFragment;
    private Fragment fragment = null;
    private ImageView ivItemIcon;
    private ImageView ivItemGo;
    private TextView tvItemText;
    private View view;
    private MainView mainView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu, container, false);
        ButterKnife.bind(this, view);
        mainView = (MainActivity)getActivity();

        if(PrefUtils.getPrefHeader().equals("0")){
            mIvHeader.setImageResource(R.mipmap.ic_drawer_header_old);
        }

        tv_vertion_name.setText("版本号: " + DeviceUtils.getVersionName());
        tv_name.setText(PrefUtils.getPrefUsername());
        tv_profile_username.setText("电话:" + PrefUtils.getPrefPhone());
        tv_profile_position.setText("单位:" + PrefUtils.getPrefDanwei());
        ImageHelper.downLoadImage(PrefUtils.getPrefIcon(), this);

        initData();
        adapter = new MenuAdapter(this.getActivity(), list, this);
        lv_menu.setAdapter(adapter);
        onItemClicked(null, 2);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //设置页面返回时设置item的背景颜色为默认颜色
        LogHelper.e(LOG_TAG, "This is onPause() function　　" + "save == " + save);
        if(save == 5 && this.view != null){
            ((ViewGroup)view).getChildAt(save).setBackgroundColor(Color.parseColor("#ffffff"));
            ivItemGo.setColorFilter(getResources().getColor(R.color.icon_normal_grey));
            ivItemIcon.setColorFilter(getResources().getColor(R.color.icon_normal_grey));
            tvItemText.setTextColor(getResources().getColor(R.color.icon_normal_black));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initData(){
        list = new ArrayList<>();
        list.add(new MenuModel(R.mipmap.ic_menu_notice, R.mipmap.ic_item_go, "通知"));
        list.add(new MenuModel(R.mipmap.ic_menu_explore, R.mipmap.ic_item_go, "动态"));
        list.add(new MenuModel(R.mipmap.ic_menu_schedule, R.mipmap.ic_item_go, "日程"));
        list.add(new MenuModel(R.mipmap.ic_menu_file, R.mipmap.ic_item_go, "文件"));
        list.add(new MenuModel(R.mipmap.ic_menu_conference, R.mipmap.ic_item_go, "会务"));
        list.add(new MenuModel(R.mipmap.ic_settings, R.mipmap.ic_item_go, "设置"));
    }

    @Override
    public void onItemClicked(View view, int position) {
        if(view != null) {
            if ((save != -1 && save != position)) {
                ivItemGo = (ImageView) ((ViewGroup) view).getChildAt(save).findViewById(R.id.nav_item_go);
                ivItemIcon = (ImageView) ((ViewGroup) view).getChildAt(save).findViewById(R.id.nav_item_icon);
                tvItemText = (TextView) ((ViewGroup) view).getChildAt(save).findViewById(R.id.nav_item_text);
                ivItemGo.setColorFilter(getResources().getColor(R.color.icon_normal_grey));
                ivItemIcon.setColorFilter(getResources().getColor(R.color.icon_normal_grey));
                tvItemText.setTextColor(getResources().getColor(R.color.icon_normal_black));
                ((ViewGroup) view).getChildAt(save).setBackgroundColor(
                        Color.parseColor("#ffffff"));
            }
            this.view = view;
            ((ViewGroup) view).getChildAt(position).setBackgroundColor(
                    Color.parseColor("#E1F5FE"));
            ivItemGo = (ImageView) ((ViewGroup) view).getChildAt(position).findViewById(R.id.nav_item_go);
            ivItemIcon = (ImageView) ((ViewGroup) view).getChildAt(position).findViewById(R.id.nav_item_icon);
            tvItemText = (TextView) ((ViewGroup) view).getChildAt(position).findViewById(R.id.nav_item_text);
            ivItemGo.setColorFilter(getResources().getColor(R.color.icon_press_blue));
            ivItemIcon.setColorFilter(getResources().getColor(R.color.icon_press_blue));
            tvItemText.setTextColor(getResources().getColor(R.color.icon_press_blue));
        }

        save = position;
        mainView.setFragment(save);

        String type ;
        Intent intent ;
        switch (position){
            case 5:
                intent = new Intent(getActivity(), SettingsActivity.class);
                mainView.closeMenu();
                startActivity(intent);
                break;
            case 0:
                type = TONGZHI;
                tagg = 1;
                intiFragment(type);
                tag = 1;
                break;
            case 1:
                type = DONGTAI;
                tagg = 2;
                intiFragment(type);
                tag = 2;
                break;
            case 2:
                if(scheduleFragment == null){
                    scheduleFragment = new ScheduleFragment();
                    LogHelper.e(LOG_TAG, "这是初始化日程" + tag);
                }
                fragment = scheduleFragment;
                mainView.setToolbar("日程");
                tagg = 3;
                changeFragment(fragment);
                tag = 3;
                break;
            case 3:
                if(fileFragment == null){
                    fileFragment = FileFragment.getInstance();
                    fragment = fileFragment;
                }
                fragment = fileFragment;
                mainView.setToolbar("文件");
                LogHelper.e(LOG_TAG, "这里是文件fragment2");
                tagg = 4;
                changeFragment(fragment);
                tag = 4;
                break;
            case 4:
                type = HUIWU;
                tagg = 5;
                intiFragment(type);
                tag = 5;
                break;
        }
        tags.add(tag);

    }

    private void intiFragment(String type){

        switch (type){
            case TONGZHI:
                mainView.setToolbar("通知");
                if(dataFragment_one == null){
                    dataFragment_one = DataFragment.getInstance(type);
                }
                changeFragment(dataFragment_one);
                break;
            case DONGTAI:
                mainView.setToolbar("动态");
                if(dataFragment_two == null){
                    dataFragment_two = DataFragment.getInstance(type);
                }
                changeFragment(dataFragment_two);
                break;
            case HUIWU:
                mainView.setToolbar("会务");
                if(dataFragment_three == null){
                    dataFragment_three = DataFragment.getInstance(type);
                }
                changeFragment(dataFragment_three);
                break;
        }
        mainView.closeMenu();
    }
    private void changeFragment(Fragment fragment){
        if(tags.contains(tagg)){
            replaceFragment(fragment);
        }else {
            addFragment(fragment);
        }
        mainView.closeMenu();
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (tag){
            case 1:
                transaction.hide(dataFragment_one).show(fragment).commit();
                break;
            case 2:
                transaction.hide(dataFragment_two).show(fragment).commit();
                break;
            case 3:
                transaction.hide(scheduleFragment).show(fragment).commit();
                break;
            case 4:
                transaction.hide(fileFragment).show(fragment).commit();
                break;
            case 5:
                transaction.hide(dataFragment_three).show(fragment).commit();
                break;
            default:
                transaction.add(R.id.fl_content, fragment).commit();
                break;
        }
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (tag){
            case 1:
                transaction.hide(dataFragment_one).add(R.id.fl_content, fragment).commit();
                break;
            case 2:
                transaction.hide(dataFragment_two).add(R.id.fl_content, fragment).commit();
                break;
            case 3:
                transaction.hide(scheduleFragment).add(R.id.fl_content, fragment).commit();
                break;
            case 4:
                transaction.hide(fileFragment).add(R.id.fl_content, fragment).commit();
                break;
            case 5:
                transaction.hide(dataFragment_three).add(R.id.fl_content, fragment).commit();
                break;
            default:
                transaction.add(R.id.fl_content, fragment).commit();
                break;
        }
    }

    @Override
    public void onSuccess(ImageInfo imageInfo) {
        PrefUtils.setDefaultPrefUserIcon(imageInfo.url);
        ImageHelper.getImageLoder().displayImage(
                ApiClient.getBaseUrl() + imageInfo.url,
                iv_profile_icon, ImageHelper.getDisplayImageOptions());
    }

    @Override
    public void onFailure(String errorString) {

    }

}
