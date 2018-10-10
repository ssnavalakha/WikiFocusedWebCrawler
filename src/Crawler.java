import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

/*
This is an abstract class which represents a crawler
 */
public abstract class Crawler implements ICrawler<UrlWrapper> {

    /*
        Given a Crawler Controller this method runs the crawler
        and checks and on each iteration checks wether the crawling should be stopped
     */
    @Override
    public void crawl(ICrawlerController<UrlWrapper> cntrl) throws InterruptedException, IOException {
        while (cntrl.evaluateStoppingCondition())
        {
            try {
                writeFileFromGetRequest(cntrl);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw e;
            }
        }

    }

    /*
        Given a Crawler Controller fetches the next required document from the frontier
        parses it for valid links and adds them to the frontier (also calculates distances for each link
        from the title)

     */
    @Override
    public void writeFileFromGetRequest(ICrawlerController<UrlWrapper> cntrl) {
        try {
            //retrieves the next link to visit from the queue
            UrlWrapper uw = cntrl.getFromFrontier();
            // connects and fetches the document
            Document doc = Jsoup.connect(uw.url).get();
            // saves the file to the disk
            saveFile(doc,uw);
            //cleans the document of external links
            doc = cleanDocument(doc);
            //loops through each link in the parsed document to fetch action links
            for (Element link : doc.select("a[href]")) {
                //gets the absolute value of a url
                    String absHref = link.attr("abs:href");
                    UrlWrapper newUw = new UrlWrapper(uw.depth + 1, absHref, link);
                    //evaluates if the url is valid
                    if (cntrl.evaluateLink(newUw)) {
                        // checks if the link has already been visited
                        if(!cntrl.alreadyVisited(newUw))
                        {
                            // if not yet visited calculates its distance from the title
                            newUw.distanceFromTitle=calculateLinkDistance(link,doc);
                            //adds it to the back of the queue
                            cntrl.addToFrontier(newUw);
                        }
                    }
                }
                //adds the current link to the list of visited links
            cntrl.addToVisitedLinks(uw);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // searches the element external links and deletes every dom element
    // below the external link dom element
    protected Document cleanDocument(Document doc) {
        Element externalLink = doc.getElementById("External_links");
        if (externalLink != null) {
            Element removeLinks = externalLink.parent();
            while (removeLinks != null) {
                //moves to the next sibling dom element for removal
                Element temp = removeLinks.nextElementSibling();
                removeLinks.remove();
                removeLinks = temp;
            }
        }
        return doc;
    }

    // calculates distance based on the number of dom elements between
    // the title and the current link
    protected int calculateLinkDistance(Element link,Document doc)
    {
        int count=0;
        // if the current link is inside a another dom element
        // moves to the parent and adds one count for each
        while (link.parent()!=null&&link.parent()!=doc.getElementById("bodyContent"))
        {
            count++;
            link=link.parent();
        }

        // keeps moving towards the top of the content block and increases
        // count by one each time
        while (link!=null)
        {
            count++;
            link=link.previousElementSibling();
        }
        return count;
    }

    // takes a document and a url which is to be written to the disk
    // replace everything which is not a AlphaNumeric Character by _ for file name
    // this is done as different OS have Different FIle Name criteria
    protected void saveFile(Document doc, UrlWrapper crawledElement) throws IOException {
        DocCreator dc = new DocCreator();
        String fileName =
                crawledElement.url.replaceAll("[^A-Za-z0-9]", "_")
                        +".txt";
        dc.createDoc(doc, fileName);
    }
}

