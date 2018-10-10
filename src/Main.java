import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Main {

    // runs 3 carwlers
    public static void main(String[] args) throws InterruptedException, IOException {
        // Task1
        LinkedHashSet<UrlWrapper> tz=timeZoneCrawl();
        LinkedHashSet<UrlWrapper> ec=electricCarCrawl();
        LinkedHashSet<UrlWrapper> cfp=carbonCrawl();
        // Task2
        tz.addAll(ec);
        tz.addAll(cfp);
        // all urls are collected into one list first sorted by depth then by distance
        // from title
        Comparator<UrlWrapper> comparator = Comparator.comparing(uw -> uw.depth);
        comparator = comparator.thenComparing(Comparator.comparing(uw -> uw.distanceFromTitle));
        DocCreator dc = new DocCreator();
        dc.createLinkDoc(tz.stream().distinct().sorted(comparator).limit(1000)
                        .collect(Collectors.toCollection( LinkedHashSet::new )),
                "top1000.txt");
        // task 3
        LinkedHashSet<UrlWrapper> fc=focusedCrawl();

    }

    // sets and starts the crawler
    public static LinkedHashSet<UrlWrapper> focusedCrawl() throws IOException, InterruptedException {
        WikiCrawler fc = new WikiCrawler();
        ArrayList keywords=new ArrayList<String>();
        keywords.add("green");
        fc.crawl("https://en.wikipedia.org/wiki/Carbon_footprint", keywords);
        ICrawlerController<UrlWrapper> fcController=fc.getController();
        DocCreator dc = new DocCreator();
        LinkedHashSet<UrlWrapper> visitedUrls=fcController.getVisitedUrls();
        dc.createLinkDoc(visitedUrls,"focusedCarbonFoot.txt");
        return fcController.getVisitedUrls();

        }

    // sets and starts the crawler
        public static LinkedHashSet<UrlWrapper> timeZoneCrawl() throws IOException, InterruptedException {
            WikiCrawler timeZonecw = new WikiCrawler();
            timeZonecw.crawl("https://en.wikipedia.org/wiki/Time_zone");
            ICrawlerController<UrlWrapper> timezonController=timeZonecw.getController();
            DocCreator dc = new DocCreator();
            LinkedHashSet<UrlWrapper> visitedUrls=timezonController.getVisitedUrls();
            dc.createLinkDoc(visitedUrls,
                    "visitedFor"+timezonController.getSeed().url.replaceAll("[^A-Za-z0-9]", "_")
                            +".txt");
            return timezonController.getVisitedUrls();

        }
    // sets and starts the crawler
        public static LinkedHashSet<UrlWrapper> electricCarCrawl() throws IOException, InterruptedException {
            WikiCrawler electricCarcw= new WikiCrawler();
            electricCarcw.crawl("https://en.wikipedia.org/wiki/Electric_car");
            ICrawlerController<UrlWrapper> electricCarcwController=electricCarcw.getController();
            DocCreator dc = new DocCreator();
            LinkedHashSet<UrlWrapper> visitedUrls=electricCarcwController.getVisitedUrls();
            dc.createLinkDoc(visitedUrls,
                    "visitedFor"+electricCarcwController.getSeed().url.replaceAll("[^A-Za-z0-9]", "_")
                            +".txt");
            return electricCarcwController.getVisitedUrls();
    }

    public static LinkedHashSet<UrlWrapper> carbonCrawl() throws IOException, InterruptedException {
        WikiCrawler carboncw= new WikiCrawler();
        carboncw.crawl("https://en.wikipedia.org/wiki/Carbon_footprint");
        ICrawlerController<UrlWrapper> carbonController=carboncw.getController();
        DocCreator dc = new DocCreator();
        LinkedHashSet<UrlWrapper> visitedUrls=carbonController.getVisitedUrls();
        dc.createLinkDoc(visitedUrls,
                "visitedFor"+carbonController.getSeed().url.replaceAll("[^A-Za-z0-9]", "_")
                        +".txt");
        return carbonController.getVisitedUrls();
    }
}
