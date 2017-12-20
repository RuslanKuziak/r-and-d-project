package co.techmagic.randd.domain.interactor.user;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.network.manager.UserApiManager;
import co.techmagic.randd.data.network.request.LoginRequest;
import co.techmagic.randd.domain.interactor.RequestBaseInteractor;
import rx.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class LoginInteractor extends RequestBaseInteractor<LoginRequest, UserApp, UserApiManager> {

    public LoginInteractor(UserApiManager userApiManager) {
        super(userApiManager);
    }

    @Override
    protected Observable<UserApp> buildObservable(LoginRequest requestData) {
        return manager.logIn(requestData);
    }
}