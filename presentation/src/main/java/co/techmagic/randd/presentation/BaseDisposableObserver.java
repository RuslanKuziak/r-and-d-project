package co.techmagic.randd.presentation;

import android.arch.lifecycle.MutableLiveData;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import co.techmagic.randd.data.network.NetworkErrors;
import co.techmagic.randd.data.network.exception.NoNetworkException;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by ruslankuziak on 12/26/17.
 */

public class BaseDisposableObserver<RESPONSE> extends DisposableObserver<RESPONSE> {

    private MutableLiveData<NetworkErrors> errorsLiveData = null;

    public BaseDisposableObserver() {}

    public BaseDisposableObserver(MutableLiveData<NetworkErrors> errorsLiveData) {
        this.errorsLiveData = errorsLiveData;
    }

    @Override
    public void onNext(RESPONSE response) {

    }

    @Override
    public void onError(Throwable e) {
        if (errorsLiveData == null) {
            return;
        }

        if (e instanceof SocketTimeoutException || e instanceof NoNetworkException) {
            errorsLiveData.postValue(NetworkErrors.CONNECTION_ERROR);

        } else if (e instanceof HttpException) {
            handleHttpErrorCode(((HttpException) e).code());
        }
    }

    @Override
    public void onComplete() {

    }

    private void handleHttpErrorCode(int errorCode) {
        switch (errorCode) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
                errorsLiveData.postValue(NetworkErrors.BAD_REQUEST);
                break;

            case HttpURLConnection.HTTP_FORBIDDEN:
                errorsLiveData.postValue(NetworkErrors.UNAUTHORIZED);
                break;
        }
    }
}