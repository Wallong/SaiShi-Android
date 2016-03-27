package com.twtstudio.coder.saishi_android.ui.settings.modify;

import com.twtstudio.coder.saishi_android.bean.NormalInfo;
import com.twtstudio.coder.saishi_android.interactor.ModifyInteractor;

/**
 * Created by clifton on 16-2-29.
 */
public class ModifyPresenterImpl implements ModifyPresenter, OnGetPwdChangedCallback{

    private ModifyView mModifyView;

    private ModifyInteractor mModifyInteractor;

    public ModifyPresenterImpl( ModifyView modifyView, ModifyInteractor modifyInteractor) {
        this.mModifyInteractor = modifyInteractor;
        this.mModifyView = modifyView;
    }

    @Override
    public void validateChangePwd(String oldPwd, String newPwd) {
        mModifyInteractor.changePwd(oldPwd,newPwd,this);
    }

    @Override
    public void onSuccess(NormalInfo normalInfo) {
        mModifyView.toastMessage(normalInfo.msg);
    }

    @Override
    public void onFailure(String errorString) {
        mModifyView.toastMessage("更改失败");
    }
}
