/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhangliang
 */
public class EngineManager {
    protected static Map<String, Engine> map = new HashMap<>();
    
    public static Engine getEngine(String indexName) {
        Engine engine = map.get(indexName);
        if (engine == null) {
            engine = new Engine(indexName);
            map.put(indexName, engine);
        }
        return engine;
    }
}
