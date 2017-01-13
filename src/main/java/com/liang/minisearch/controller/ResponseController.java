/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liang.minisearch.controller;

import com.liang.minisearch.domain.network.PageDownloader;
import com.liang.minisearch.model.Index;
import com.liang.minisearch.model.QueryDocument;
import com.liang.minisearch.model.QueryResult;
import com.liang.minisearch.domain.search.EngineManager;
import java.io.IOException;
import java.util.List;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhangliang
 */
@RestController
public class ResponseController {
    
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String getIndex() {
//        return "Hello world";
//    }
    
    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
    public Index getIndexJson() {
        Index index = new Index();
        index.setContent("Hello world!");
        return index;
    }

    @RequestMapping(value = "/update/{indexName}/{url:.+}", method = RequestMethod.GET, produces = "application/json")
    public QueryResult indexUrl(@PathVariable String url, @PathVariable String indexName) {
        QueryResult result = new QueryResult();
        try {
            PageDownloader page = new PageDownloader();
            int maxDoc = EngineManager.getEngine(indexName).createIndex(url, page.get(url));
            result.setStatus(true);
            result.setNumDocs(maxDoc);
        } catch(Exception e) {
            result.setStatus(false);
            result.setNumDocs(0);
        }
        
        return result;
    }
    
    @RequestMapping(value = "/search/{indexName}/json/{queryString:.+}", method = RequestMethod.GET, produces = "application/json")
    public QueryResult search(@PathVariable String indexName, @PathVariable String queryString) {
        QueryResult result = new QueryResult();
        try {
            List<QueryDocument> docs = EngineManager.getEngine(indexName).search(queryString);
            result.setStatus(true);
            result.setDocuments(docs);
            result.setNumDocs(docs.size());
        } catch(IOException | ParseException ex) {
            result.setStatus(false);
            result.setNumDocs(0);
        }
        return result;
    }
}
