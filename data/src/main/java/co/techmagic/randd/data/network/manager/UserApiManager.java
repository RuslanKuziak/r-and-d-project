package co.techmagic.randd.data.network.manager;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.network.UserApi;
import co.techmagic.randd.data.network.client.ApiClient;
import co.techmagic.randd.data.network.entity.LoginResponse;
import co.techmagic.randd.data.network.repository.UserRepository;
import co.techmagic.randd.data.network.request.LoginRequest;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

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
                .map(new Function<LoginResponse, UserApp>() {
                    @Override
                    public UserApp apply(LoginResponse loginResponse) throws Exception {
                        UserApp userApp = mapUser(loginResponse);
                        return userApp;
                    }
                });
    }

    private UserApp mapUser(LoginResponse loginResponse) {
        UserApp userApp = new UserApp();
        return userApp;
    }
}