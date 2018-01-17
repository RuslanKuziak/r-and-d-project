package co.techmagic.randd.data.repository;

import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.data.request.LoginRequest;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public interface UserRepository {

    Observable<UserApp> logIn(LoginRequest request);
}