package cn.edu.twt.saishi_android.ui.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.bean.ImageInfo;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.ui.common.ImageHelper;
import cn.edu.twt.saishi_android.ui.common.OnGetImageCallback;
import cn.edu.twt.saishi_android.ui.file.FileFragment;
import cn.edu.twt.saishi_android.ui.main.list.DataFragment;
import cn.edu.twt.saishi_android.ui.schedule.ScheduleFragment;
import cn.edu.twt.saishi_android.ui.settings.SettingsActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by clifton on 16-2-28.
 */
public class MenuFragment extends Fragment implements View.OnClickListener, OnGetImageCallback {
    private static final String LOG_TAG = MenuFragment.class.getSimpleName();

    @Bind(R.id.nav_settings)
    TextView mTvSettings;
    @Bind(R.id.nav_notice)
    TextView mTvNotice;
    @Bind(R.id.nav_explore)
    TextView mTvExplore;
    @Bind(R.id.nav_schedule)
    TextView mTvSchedule;
    @Bind(R.id.nav_file)
    TextView mTvFile;
    @Bind(R.id.nav_conference)
    TextView mTvConference;
    @Bind(R.id.user_profile_image)
    CircleImageView iv_profile_icon;
    @Bind(R.id.tv_username)
    TextView tv_name;
    @Bind(R.id.tv_user_profile_name)
    TextView tv_profile_username;
    @Bind(R.id.tv_user_profile_phone)
    TextView tv_profile_phone;
    @Bind(R.id.tv_user_profile_position)
    TextView tv_profile_position;

    private final static String HUIWU = "Huiwu";
    private final static String DONGTAI = "Dongtai";
    private final static String TONGZHI = "Tongzhi";
    private final static String RICHENG = "Richeng";
    private int tag = 0;
    private int tagg = 0;
    private TreeSet<Integer> tags = new TreeSet<>();

    private Activity mActivity;
    private DataFragment dataFragment_one;
    private DataFragment dataFragment_two;
    private DataFragment dataFragment_three;
    private ScheduleFragment scheduleFragment;
    private FileFragment fileFragment;
    private Fragment fragment = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu, container, false);
        ButterKnife.bind(this, view);
        mActivity = getActivity();

        tv_name.setText(PrefUtils.getPrefUsername());
        tv_profile_username.setText("帐号:" + PrefUtils.getPrefPhone());
        tv_profile_phone.setText("电话:" + PrefUtils.getPrefPhone());
        tv_profile_position.setText("单位:" + PrefUtils.getPrefDanwei());
        ImageHelper.downLoadImage(PrefUtils.getPrefIcon(), this);

        mTvSettings.setOnClickListener(this);
        mTvNotice.setOnClickListener(this);
        mTvExplore.setOnClickListener(this);
        mTvSchedule.setOnClickListener(this);
        mTvFile.setOnClickListener(this);
        mTvConference.setOnClickListener(this);



        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        String type = null;
        Intent intent = null;
        switch (v.getId()){
            case R.id.nav_settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                ((MainActivity)mActivity).closeMenu();
                startActivity(intent);
                break;
            case R.id.nav_notice:
                type = TONGZHI;
                tagg = 1;
                intiFragment(type);
                tag = 1;
                break;
            case R.id.nav_explore:
                type = DONGTAI;
                tagg = 2;
                intiFragment(type);
                tag = 2;
                break;
            case R.id.nav_schedule:
                if(scheduleFragment == null){
                    scheduleFragment = new ScheduleFragment();
                    LogHelper.e(LOG_TAG, "这是初始化日程" + tag);
                }
                fragment = scheduleFragment;
                ((MainActivity)mActivity).setToolbar("日程");
                tagg = 3;
                changeFragment(fragment);
                tag = 3;
                break;
            case R.id.nav_file:
                if(fileFragment == null){
                    fileFragment = FileFragment.getInstance();
                    fragment = fileFragment;
                }
                fragment = fileFragment;
                ((MainActivity)mActivity).setToolbar("文件");
                LogHelper.e(LOG_TAG, "这里是文件fragment2");
                tagg = 4;
                changeFragment(fragment);
                tag = 4;
                break;
            case R.id.nav_conference:
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
                ((MainActivity)mActivity).setToolbar("通知");
                if(dataFragment_one == null){
                    dataFragment_one = DataFragment.getInstance(type);
                }
                changeFragment(dataFragment_one);
                break;
            case DONGTAI:
                ((MainActivity)mActivity).setToolbar("动态");
                if(dataFragment_two == null){
                    dataFragment_two = DataFragment.getInstance(type);
                }
                changeFragment(dataFragment_two);
                break;
            case HUIWU:
                ((MainActivity)mActivity).setToolbar("会务");
                if(dataFragment_three == null){
                    dataFragment_three = DataFragment.getInstance(type);
                }
                changeFragment(dataFragment_three);
                break;
        }
        ((MainActivity)mActivity).closeMenu();
    }
    private void changeFragment(Fragment fragment){
        if(tags.contains(tagg)){
            replaceFragment(fragment);
        }else {
            addFragment(fragment);
        }
        ((MainActivity)mActivity).closeMenu();
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
