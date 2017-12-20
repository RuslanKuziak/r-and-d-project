package co.techmagic.randd.domain.interactor;

import co.techmagic.randd.data.network.manager.BaseManager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public abstract class RequestBaseInteractor<REQUEST, RESPONSE, MANAGER extends BaseManager> {

    protected MANAGER manager;

    public RequestBaseInteractor(MANAGER manager) {
        this.manager = manager;
    }

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected abstract Observable<RESPONSE> buildObservable(REQUEST requestData);

    public void execute(REQUEST requestData, Subscriber<RESPONSE> useCaseSubscriber) {
        compositeSubscription
                .add(buildObservable(requestData)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(useCaseSubscriber));
    }

    public boolean isUnsubscribed() {
        return !compositeSubscription.hasSubscriptions();
    }

    public void unsubscribe() {
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.clear();
        }
    }
}