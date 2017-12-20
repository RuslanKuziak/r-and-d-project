package co.techmagic.randd.domain.subscriber;

import rx.Subscriber;

/**
 * Created by ruslankuziak on 12/20/17.
 */

public class BaseSubscriber<RESPONSE> extends Subscriber<RESPONSE> {

    public BaseSubscriber() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(RESPONSE response) {

    }
}