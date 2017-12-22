package co.techmagic.randd.data.network.repository;

import co.techmagic.randd.data.network.entity.LoginResponse;
import co.techmagic.randd.data.network.request.LoginRequest;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ruslankuziak on 12/18/17.
 */

public interface UserRepository {

    @POST
    Observable<LoginResponse> login(LoginRequest request);
}