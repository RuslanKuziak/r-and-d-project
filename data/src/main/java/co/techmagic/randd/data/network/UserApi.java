package co.techmagic.randd.data.network;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.network.request.LoginRequest;
import rx.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public interface UserApi {

    Observable<UserApp> logIn(LoginRequest request);
}