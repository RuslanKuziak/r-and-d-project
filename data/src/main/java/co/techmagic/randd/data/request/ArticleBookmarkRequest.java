package co.techmagic.randd.data.request;

import co.techmagic.randd.data.application.ArticleApp;

/**
 * Created by ruslankuziak on 1/17/18.
 */

public class ArticleBookmarkRequest extends BaseRequest {

    private ArticleApp articleApp;

    public ArticleBookmarkRequest(ArticleApp articleApp) {
        this.articleApp = articleApp;
    }

    public ArticleApp getArticleApp() {
        return articleApp;
    }
}