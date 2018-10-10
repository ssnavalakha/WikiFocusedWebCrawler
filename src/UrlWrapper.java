import org.jsoup.nodes.Element;

// a wrapper around the string urls
public class UrlWrapper{
    // represents depth of this url ie how many links are required to be visited in order to visit this link
    // from the seed
    int depth;
    // the string url
    String url;
    // the number of dom elements between the title and this a link
    int distanceFromTitle;
    // the a link element
    Element linkElement;

    UrlWrapper(int dep,String link,Element e)
    {
        url=link;
        depth=dep;
        distanceFromTitle=0;
        linkElement=e;
    }

    // the hashcode is only dependent on string value of the url
    @Override
    public int hashCode() {
        return url.hashCode();
    }

    // to UrlWrappers are equal if the string url in them are equal
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof UrlWrapper){
            UrlWrapper testObject=(UrlWrapper)o;
            if (testObject.url.equals(this.url))
                return true;
            return false;
        }
        else
            return false;
    }
}