import org.jsoup.nodes.Document;
import java.io.*;
import java.util.LinkedHashSet;

/*
* This class is responsible to do all the writing to files*/
public class DocCreator {
    // writes an entire document to file
    public void createDoc(Document doc,String name) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/CollecedDocs/"+name));
            writer.write(doc.outerHtml());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
    // writes each link to file
    public void createLinkDoc(LinkedHashSet<UrlWrapper> links,String fileName) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/CollecedDocs/"+fileName));
            for (UrlWrapper uw:
                 links) {
                writer.write(uw.url + "    "+ uw.depth);
                writer.write(System.lineSeparator());
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
