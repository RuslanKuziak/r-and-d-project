package co.techmagic.randd.data.network.service;

import co.techmagic.randd.data.network.entity.LoginResponse;
import co.techmagic.randd.data.network.request.LoginRequest;
import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by ruslankuziak on 12/18/17.
 */

public interface UserService {

    @POST
    Observable<LoginResponse> login(LoginRequest request);
}