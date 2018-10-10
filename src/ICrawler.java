import java.io.IOException;

public interface ICrawler<T> {
    /*
        Given a Crawler Controller this method runs the crawler
        and checks and on each iteration checks wether the crawling should be stopped
     */
    void crawl(ICrawlerController<UrlWrapper> controller) throws InterruptedException, IOException;
    /*
       Given a Crawler Controller fetches the next required document from the frontier
       parses it for valid links and adds them to the frontier (also calculates distances for each link
       from the title)

    */
    void writeFileFromGetRequest(ICrawlerController<UrlWrapper> cntrl);
}
