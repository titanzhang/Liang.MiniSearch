/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniSearch.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author zhangliang
 */
public class PageDownloader {
    public String get(String url) throws Exception {
//        System.out.println(url);
        StringBuilder buf = new StringBuilder();
        URL http = new URL(url);

        BufferedReader reader = new BufferedReader(new InputStreamReader(http.openStream(), "UTF-8"));
        for (String line; (line = reader.readLine()) != null;) {
            buf.append(line);
            buf.append("\n");
//            System.out.println(line);
        }
        reader.close();
        
        return buf.toString();
    }
}
