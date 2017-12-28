package co.techmagic.randd.data.repository.impl;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.network.client.ApiClient;
import co.techmagic.randd.data.network.entity.LoginResponse;
import co.techmagic.randd.data.network.request.LoginRequest;
import co.techmagic.randd.data.network.service.UserService;
import co.techmagic.randd.data.repository.BaseRepository;
import co.techmagic.randd.data.repository.UserRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class UserRepositoryImpl extends BaseRepository implements UserRepository {

    private UserService repository;

    public UserRepositoryImpl() {
        repository = ApiClient.getUserService();
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