package co.techmagic.randd.domain.interactor;

import co.techmagic.randd.data.repository.BaseRepository;
import co.techmagic.randd.domain.NetworkManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public abstract class BaseRequestInteractor<REQUEST, RESPONSE, REPOSITORY extends BaseRepository> {

    protected REPOSITORY repository;
    protected NetworkManager networkManager = NetworkManager.get();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public BaseRequestInteractor(REPOSITORY repository) {
        this.repository = repository;
    }

    protected abstract Observable<RESPONSE> buildObservable(REQUEST requestData);

    public void execute(REQUEST requestData, DisposableObserver<RESPONSE> disposableObserver) {
        disposables.add(buildObservable(requestData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver));
    }

    public boolean isUnsubscribed() {
        return disposables.isDisposed();
    }

    public void unsubscribe() {
        if (!isUnsubscribed()) {
            disposables.dispose();
        }
    }
}