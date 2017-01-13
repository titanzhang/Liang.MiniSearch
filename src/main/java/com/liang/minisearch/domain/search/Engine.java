/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liang.minisearch.domain.search;

import com.liang.minisearch.model.QueryDocument;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author zhangliang
 */
public class Engine implements AutoCloseable {
    protected static final String FIELD_URL = "url";
    protected static final String FIELD_CONTENT = "content";
    protected static final String INDEX_BASE_PATH = "index";
    protected static final int NUM_TOP_RESULT = 10;
    
    protected String indexName;
    protected Directory indexDirectory;
    protected IndexWriter indexWriter;
    protected DirectoryReader indexReader;
    
    public Engine(String indexName) {
        this.indexName = indexName;
    }
    
    public int createIndex(String url, String content) throws IOException {
        int maxDoc = 0;
        
        Document doc = new Document();
        doc.add(new StoredField(FIELD_URL, url));
        doc.add(new TextField(FIELD_CONTENT, content, Field.Store.NO));

        this.getWriter().addDocument(doc);
        this.getWriter().commit();
        maxDoc = this.getWriter().maxDoc();
        
        // Cleanup resources
//        this.closeWriter();

        return maxDoc;
    }

// Syntax of query string: https://lucene.apache.org/core/6_3_0/queryparser/org/apache/lucene/queryparser/classic/package-summary.html
    public List<QueryDocument> search(String queryString) throws ParseException, IOException {
        List<QueryDocument> results = new ArrayList<>();

        IndexSearcher searcher = new IndexSearcher(this.getReader());

        // Parse user input query
        Query query = new QueryParser(FIELD_CONTENT, new StandardAnalyzer()).parse(queryString);

        // Get top n matching documents
        TopDocs topDocs = searcher.search(query, NUM_TOP_RESULT);
        ScoreDoc[] hits = topDocs.scoreDocs;
        for (ScoreDoc hit : hits) {
            Document doc = searcher.doc(hit.doc);
            QueryDocument result = new QueryDocument();
            result.setUrl(doc.get(FIELD_URL));
            result.setScore(hit.score);

            results.add(result);
        }
        
        return results;
    }
    
    protected Directory getDirectory() throws IOException {
        if (this.indexDirectory == null) {
            this.indexDirectory = FSDirectory.open(Paths.get(INDEX_BASE_PATH, "/", this.indexName));
        }
        return this.indexDirectory;
    }
    
    protected IndexWriter getWriter() throws IOException {
        if (this.indexWriter == null) {
            IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
            this.indexWriter = new IndexWriter(
                    getDirectory(),
                    conf);
        }
        return this.indexWriter;
    }
    
    protected DirectoryReader getReader() throws IOException {
        if (this.indexReader == null) {
            this.indexReader = DirectoryReader.open(getDirectory());
        } else {
            DirectoryReader newReader = DirectoryReader.openIfChanged(this.indexReader);
            if (newReader != null) {
                this.indexReader = newReader;
            }
        }
        
        return this.indexReader;
    }
    
    @Override
    public void close() throws Exception {
        if (this.indexWriter != null) {
            this.indexWriter.close();
        }
        if (this.indexReader != null) {
            this.indexReader.close();
        }
        if (this.indexDirectory != null) {
            this.indexDirectory.close();
        }
    }
    
}
