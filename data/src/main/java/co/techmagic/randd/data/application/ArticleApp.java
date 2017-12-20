package co.techmagic.randd.data.application;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class ArticleApp {

    private SourceApp sourceApp;
    private ArticleInfoApp articleInfoApp;

    public SourceApp getSourceApp() {
        return sourceApp;
    }

    public void setSourceApp(SourceApp sourceApp) {
        this.sourceApp = sourceApp;
    }

    public ArticleInfoApp getArticleInfoApp() {
        return articleInfoApp;
    }

    public void setArticleInfoApp(ArticleInfoApp articleInfoApp) {
        this.articleInfoApp = articleInfoApp;
    }
}