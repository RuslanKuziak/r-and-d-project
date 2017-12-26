package co.techmagic.randd.domain.interactor;

import co.techmagic.randd.data.network.manager.BaseManager;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ruslankuziak on 12/26/17.
 */

public abstract class BaseInteractor<RESPONSE, MANAGER extends BaseManager> extends BaseRequestInteractor<Void, RESPONSE, MANAGER> {

    public BaseInteractor(MANAGER manager) {
        super(manager);
    }

    protected abstract Observable<RESPONSE> buildObservable();

    @Override
    protected Observable<RESPONSE> buildObservable(Void requestData) {
        return buildObservable();
    }

    public void execute(DisposableObserver<RESPONSE> disposableObserver) {
        super.execute(null, disposableObserver);
    }
}