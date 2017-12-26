package co.techmagic.randd.presentation.ui.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import co.techmagic.randd.data.network.NetworkErrors;

/**
 * Created by ruslankuziak on 12/21/17.
 */

public class BaseViewModel extends ViewModel {

    public MutableLiveData<NetworkErrors> networkErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();

    protected void showProgress() {
        progressLiveData.setValue(true);
    }

    protected void hideProgress() {
        progressLiveData.setValue(false);
    }
}