package co.techmagic.randd.domain.interactor.user;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.network.exception.NoNetworkException;
import co.techmagic.randd.data.network.manager.UserApiManager;
import co.techmagic.randd.data.network.request.LoginRequest;
import co.techmagic.randd.domain.interactor.BaseRequestInteractor;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class LoginRequestInteractor extends BaseRequestInteractor<LoginRequest, UserApp, UserApiManager> {

    public LoginRequestInteractor(UserApiManager userApiManager) {
        super(userApiManager);
    }

    @Override
    protected Observable<UserApp> buildObservable(LoginRequest requestData) {
        if (networkManager.isNetworkAvailable()) {
            manager.logIn(requestData);
        }

        return Observable.error(new NoNetworkException());
    }
}