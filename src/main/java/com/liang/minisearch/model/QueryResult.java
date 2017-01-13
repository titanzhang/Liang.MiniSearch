/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liang.minisearch.model;

import java.util.List;

/**
 *
 * @author zhangliang
 */
public class QueryResult {
    boolean status;
    int numDocs;
    List<QueryDocument> documents;

    public int getNumDocs() {
        return numDocs;
    }

    public void setNumDocs(int numDocs) {
        this.numDocs = numDocs;
    }

    public List<QueryDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<QueryDocument> documents) {
        this.documents = documents;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
