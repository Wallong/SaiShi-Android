package cn.edu.twt.saishi_android.ui.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import cn.edu.twt.saishi_android.ui.schedule.ScheduleActivity;
import cn.edu.twt.saishi_android.ui.settings.SettingsActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by clifton on 16-2-28.
 */
public class MenuFragment extends Fragment implements View.OnClickListener, OnGetImageCallback {

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

    private Activity mActivity;
    private DataFragment dataFragment;
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
        tv_profile_position.setText("单位职务:" + PrefUtils.getPrefDanwei() + PrefUtils.getPrefZhiwu());
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
                intiFragment(type);
                break;
            case R.id.nav_explore:
                type = DONGTAI;
                intiFragment(type);
                break;
            case R.id.nav_schedule:
                ((MainActivity)mActivity).closeMenu();
                intent = new Intent(this.getActivity(), ScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_file:
                fileFragment = FileFragment.getInstance();
                ((MainActivity)mActivity).closeMenu();
                fragment = fileFragment;
                changeFragment(fragment);
                break;
            case R.id.nav_conference:
                type = HUIWU;
                intiFragment(type);
                break;
        }

    }

    private void intiFragment(String type){
        dataFragment = DataFragment.getInstance(type);
        switch (type){
            case TONGZHI:
                ((MainActivity)mActivity).setToolbar("通知");
                break;
            case DONGTAI:
                ((MainActivity)mActivity).setToolbar("动态");
                break;
            case HUIWU:
                ((MainActivity)mActivity).setToolbar("会务");
                break;
        }
        ((MainActivity)mActivity).closeMenu();
        fragment = dataFragment;
        changeFragment(fragment);
    }
    private void changeFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content,
                        fragment).commit();
        ((MainActivity)mActivity).closeMenu();
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
