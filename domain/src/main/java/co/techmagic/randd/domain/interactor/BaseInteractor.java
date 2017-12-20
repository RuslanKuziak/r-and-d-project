package co.techmagic.randd.domain.interactor;

import co.techmagic.randd.data.network.manager.BaseManager;

/**
 * Created by ruslankuziak on 12/18/17.
 */

public abstract class BaseInteractor<RESPONSE, MANAGER extends BaseManager> extends RequestBaseInteractor<Void, RESPONSE, MANAGER> {

    public BaseInteractor(MANAGER manager) {
        super(manager);
    }
}