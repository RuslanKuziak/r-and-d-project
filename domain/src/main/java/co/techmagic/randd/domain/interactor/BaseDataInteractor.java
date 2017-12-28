package co.techmagic.randd.domain.interactor;

import co.techmagic.randd.data.repository.BaseRepository;
import co.techmagic.randd.data.network.NetworkManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public abstract class BaseDataInteractor<DATA, RESPONSE, REPOSITORY extends BaseRepository> {

    protected REPOSITORY repository;
    protected NetworkManager networkManager = NetworkManager.get();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public BaseDataInteractor(REPOSITORY repository) {
        this.repository = repository;
    }

    protected abstract Observable<RESPONSE> buildObservable(DATA requestData);

    public void execute(DATA data, DisposableObserver<RESPONSE> disposableObserver) {
        disposables.add(buildObservable(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver));
    }

    public boolean isDisposed() {
        return disposables.isDisposed();
    }

    public void dispose() {
        if (!isDisposed()) {
            disposables.dispose();
        }
    }
}