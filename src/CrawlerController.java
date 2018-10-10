import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.function.Supplier;

// this class manages the working of a crawler
public class CrawlerController implements ICrawlerController<UrlWrapper> {
    // a queue to manage the frontier
    private Queue<UrlWrapper> frontier;
    // the function to check if the link is valid
    private Predicate<UrlWrapper> isLinkValid;
    // a linked hashset to keep the order of link visited unique and
    // chronological
    private LinkedHashSet<UrlWrapper> visitedUrls;
    // the stopping condition to find the stopping condition of the crawler
    private Supplier<Boolean> stoppingCondition;
    // the seed url to start from
    private UrlWrapper seed;

    public CrawlerController()
    {
        // initialize the lists
        visitedUrls=new LinkedHashSet<UrlWrapper>();
        frontier = new LinkedList<UrlWrapper>();
    }

    // add to the queue
    @Override
    public boolean addToFrontier(UrlWrapper o) {
        return frontier.add(o);
    }

    // fetch the queue
    @Override
    public UrlWrapper getFromFrontier() {
        return frontier.remove();
    }

    //add to visited links
    @Override
    public boolean addToVisitedLinks(UrlWrapper o) {
        return visitedUrls.add(o);
    }

    // checks if the url is already been visited
    @Override
    public boolean alreadyVisited(UrlWrapper uw)
    {
        return visitedUrls.contains(uw);
    }

    // sets the stopping condition
    @Override
    public void setStoppingCondition(Supplier<Boolean> x) {
        stoppingCondition=x;
    }

    // evaluates the condition to stop
    @Override
    public boolean evaluateStoppingCondition() {
        return stoppingCondition.get();
    }

    // evaluates the link and checks for it's validity
    @Override
    public boolean evaluateLink(UrlWrapper s){
        return isLinkValid.test(s);
    }

    // sets the links evaluator
    @Override
    public void setLinkEvaluator(Predicate<UrlWrapper> evaluator) {
        isLinkValid=evaluator;
    }

    // gets the visited link count
    @Override
    public int getVisitedCount() {
        return visitedUrls.size();
    }

    // returns the list of visited urls
    @Override
    public LinkedHashSet<UrlWrapper> getVisitedUrls() {
        return visitedUrls;
    }

    // get current count of the frontier
    @Override
    public int getCurrentFrontierCount()
    {
        return frontier.size();
    }

    // set the seed
    @Override
    public void setSeed(UrlWrapper s) {
        seed=s;
        addToFrontier(s);
    }

    // retrieve the seed
    @Override
    public UrlWrapper getSeed() {
        return seed;
    }

    // checks if the frontier contains a particular link
    @Override
    public boolean alreadyAddedToFrontier(UrlWrapper uw)
    {
        return frontier.contains(uw);
    }
}
