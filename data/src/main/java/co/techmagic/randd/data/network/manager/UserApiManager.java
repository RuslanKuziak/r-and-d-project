package co.techmagic.randd.data.network.manager;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.network.UserApi;
import co.techmagic.randd.data.network.client.ApiClient;
import co.techmagic.randd.data.network.entity.LoginResponse;
import co.techmagic.randd.data.network.request.LoginRequest;
import co.techmagic.randd.data.network.service.UserRepository;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class UserApiManager extends BaseManager implements UserApi {

    private UserRepository repository;

    public UserApiManager() {
        repository = ApiClient.getUserRepository();
    }

    @Override
    public Observable<UserApp> logIn(LoginRequest request) {
        return repository.login(request)
                .map(new Func1<LoginResponse, UserApp>() {
                    @Override
                    public UserApp call(LoginResponse loginResponse) {
                        UserApp user = mapUser(loginResponse);
                        return user;
                    }
                });
    }

    private UserApp mapUser(LoginResponse loginResponse) {
        UserApp userApp = new UserApp();
        return userApp;
    }
}