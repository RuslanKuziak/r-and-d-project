package co.techmagic.randd.domain.interactor;

import co.techmagic.randd.data.repository.BaseRepository;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ruslankuziak on 12/26/17.
 */

public abstract class BaseInteractor<RESPONSE, REPOSITORY extends BaseRepository> extends BaseRequestInteractor<Void, RESPONSE, REPOSITORY> {

    public BaseInteractor(REPOSITORY repository) {
        super(repository);
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