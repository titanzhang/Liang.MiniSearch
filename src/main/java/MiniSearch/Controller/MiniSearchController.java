/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniSearch.Controller;

import MiniSearch.Model.Index;
import Search.Engine;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author zhangliang
 */
@RestController
public class MiniSearchController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "Hello world!";
    }
    
    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
    public Index getIndexJson() {
        Index index = new Index();
        index.setContent("Hello world!");
        return index;
    }

    @RequestMapping(value = "/parse/{url:.+}", method = RequestMethod.GET)
    public String parseUrl(@PathVariable String url) {
        try {
            PageDownloader page = new PageDownloader();
            Engine indexer = new Engine();
            int maxDoc = indexer.createIndex(url, page.get(url));
            return String.valueOf(maxDoc);
        } catch(Exception e) {
            return "-1";
        }
    }
}
