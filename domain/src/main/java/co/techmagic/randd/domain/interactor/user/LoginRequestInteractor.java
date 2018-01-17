package co.techmagic.randd.domain.interactor.user;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.network.exception.NoNetworkException;
import co.techmagic.randd.data.request.LoginRequest;
import co.techmagic.randd.data.repository.impl.UserRepositoryImpl;
import co.techmagic.randd.domain.interactor.BaseDataInteractor;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class LoginRequestInteractor extends BaseDataInteractor<LoginRequest, UserApp, UserRepositoryImpl> {

    public LoginRequestInteractor(UserRepositoryImpl userApiManager) {
        super(userApiManager);
    }

    @Override
    protected Observable<UserApp> buildObservable(LoginRequest requestData) {
        if (networkManager.isNetworkAvailable()) {
            repository.logIn(requestData);
        }

        return Observable.error(new NoNetworkException());
    }
}