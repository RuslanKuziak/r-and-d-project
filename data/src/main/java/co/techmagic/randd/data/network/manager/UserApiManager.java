package co.techmagic.randd.data.network.manager;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.network.UserApi;
import co.techmagic.randd.data.network.client.ApiClient;
import co.techmagic.randd.data.network.entity.LoginResponse;
import co.techmagic.randd.data.network.exception.NoNetworkException;
import co.techmagic.randd.data.network.request.LoginRequest;
import co.techmagic.randd.data.network.repository.UserRepository;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class UserApiManager extends BaseManager implements UserApi {

    private UserRepository repository;
    private NetworkManager networkManager;

    public UserApiManager() {
        repository = ApiClient.getUserRepository();
        networkManager = NetworkManager.get();
    }

    @Override
    public Observable<UserApp> logIn(LoginRequest request) {
        if (networkManager.isNetworkAvailable()) {
            return repository.login(request)
                    .map(new Func1<LoginResponse, UserApp>() {
                        @Override
                        public UserApp call(LoginResponse loginResponse) {
                            UserApp user = mapUser(loginResponse);
                            return user;
                        }
                    });
        }

        return Observable.error(new NoNetworkException());
    }

    private UserApp mapUser(LoginResponse loginResponse) {
        UserApp userApp = new UserApp();
        return userApp;
    }
}