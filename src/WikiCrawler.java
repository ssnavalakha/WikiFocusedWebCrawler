import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// represents an actual crawler
public class WikiCrawler extends Crawler{
    // the crawler controller this class uses
    public ICrawlerController<UrlWrapper> controller=new CrawlerController();

    public void crawl(String seed, List<String> keywords) throws InterruptedException, IOException {
        // set the seed
        controller.setSeed(new UrlWrapper(1,seed,null));
        // set the stopping condition
        controller.setStoppingCondition(()->
                controller.getVisitedCount()<1000
                        && controller.getCurrentFrontierCount()!=0
        );

        // set the link evaluator
        controller.setLinkEvaluator((uw)->{
            // checks if the link has already been added to the frontier
            if(controller.alreadyAddedToFrontier(uw))
                return false;
            // checks if the url has a depth greater than 6
            if(uw.depth>6)
                return false;
            // checks if the url belongs to wiki
            if(!uw.url.contains("https://en.wikipedia.org/")
                    || uw.url.equals("https://en.wikipedia.org/wiki/Main_Page"))
                return false;

            // checks if the url contains semicolons except for the first https:
            String temp;
            temp=uw.url;
            int index = temp.indexOf(":");
            int count = 0;
            while (index != -1) {
                count++;
                temp = temp.substring(index + 1);
                index = temp.indexOf(":");
            }
            if (count>1)
                return false;
            // check if the url has hash tags
            if (uw.url.contains("#"))
                return false;

            // handles keywords for focused crawling
            // checks if the url or a link tet has any of the keywords
            // if yes fetches all that match and adds them to the list of valid keywords
            if (keywords.size()!=0)
            {
                if (!keywords.stream().parallel().anyMatch(uw.url::contains)
                        &&!keywords.stream().parallel().anyMatch(uw.linkElement.text().toLowerCase()::contains))
                    return false;

                // regex to fetch all the words with substring green
                // in the action link text
                // if they match add it to the list of valid keywords
                Pattern p = Pattern.compile("[a-zA-Z]+");
                Matcher m1 = p.matcher(uw.linkElement.text());
                while (m1.find())
                {
                    String foundWord = m1.group();
                    if (keywords.stream().parallel().anyMatch(foundWord.toLowerCase()::contains)
                            && !keywords.contains(foundWord))
                        keywords.add(foundWord.toLowerCase());
                }
            }
            return true;
        });
        // call the base class method
        super.crawl(controller);
    }

    public void crawl(String seed) throws InterruptedException, IOException {
        crawl(seed,new ArrayList<String>());
    }

    // returns the crawler controller
    public ICrawlerController<UrlWrapper> getController()
    {
        return controller;
    }
}
