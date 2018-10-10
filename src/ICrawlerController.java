import java.util.LinkedHashSet;
import java.util.function.Predicate;
import java.util.function.Supplier;

// represent all the functions required to control a crawler
public interface ICrawlerController<T> {

    //// add to the queue
    boolean addToFrontier(T o);
    // fetches from the queue
    T getFromFrontier();
    //gets the count of the queue
    int getCurrentFrontierCount();
    // adds to the visited links
    boolean addToVisitedLinks (T o);
    // checks is link has already been visited
    boolean alreadyVisited(T uw);
    //sets the stopping condition of the crawler
    void setStoppingCondition(Supplier<Boolean> x);
    // evaluates the stopping condition
    boolean evaluateStoppingCondition();
    // checks if the link is valid
    boolean evaluateLink(T s);
    // sets the link evaluator
    void setLinkEvaluator(Predicate<T> evaluator);
    // gets the number of links visited
    int getVisitedCount();
    // gets the list of urls visted
    LinkedHashSet<T> getVisitedUrls();
    // sets the seed
    void setSeed(T s);
    // gets the seed
    T getSeed();
    // checks if an element has already been added to the queue
    boolean alreadyAddedToFrontier(T o);
}
