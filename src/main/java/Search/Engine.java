/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author zhangliang
 */
public class Engine {
    public static final String FIELD_URL = "url";
    public static final String FIELD_CONTENT = "content";
    private static final String INDEX_PATH = "index";
    
    public int createIndex(String url, String content) throws IOException {
        int maxDoc = 0;
        IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
        try (IndexWriter indexWriter = new IndexWriter(FSDirectory.open(Paths.get(INDEX_PATH)), conf)) {
            Document doc = new Document();
            doc.add(new StoredField(FIELD_URL, url));
            doc.add(new TextField(FIELD_CONTENT, content, Field.Store.NO));
            
            indexWriter.addDocument(doc);
            indexWriter.commit();
            maxDoc = indexWriter.maxDoc();
            indexWriter.close();
        }
        return maxDoc;
    }
}
