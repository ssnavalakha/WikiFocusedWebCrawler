1. This Crawler crawls to a maximum depth of 5 (seed link being at depth 1)
2. The Crawler crawls 1000 Unique URLs at max
3. Wikipedia has a lot of links, so Crawler stop when it reaches 1000 unique URLs.
4. A time delay of 1sec is maintained between 2 consecutive HTTP requests (politeness factor)
5. In case of crawling with a Keyword , If the Keyword is not present, it stops crawling the subsequent children
6. The Crawler only crawls links with http://en.wikipedia.org/wiki/. In other words, do not follow links to non-English articles or to non-Wikipedia pages.
7. The Crawler does not follow links with a colon (:) in the rest of the URL. This will help filter out Wikipedia help and administration pages.
8. BFS is used as the traversing algorithm 
9. The Crawler has 2 features :
<a> Traverses without Keyword / searchWord (brings out the first 1000 URLs that it crawls)
<b> Traverses with Keyword / searchWord (brings out the URLs of the pages that contains the Keyword )
10. The Links crawled (relevant links retrieved , in case of Keyword Search) with the time taken for the crawl is printed in a file
<< Links_Crawled_WITHOUT_Keyword.txt ---> Search without keyword >>
<< Links_Crawled_WITH_Keyword.txt ----> Search with keyword >>

A Java version : using jsoup
