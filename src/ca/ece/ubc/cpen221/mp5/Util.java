package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Util {
    
    public static JSONObject jsonCopier(JSONObject obj){
        Map<Object, Object> jsonMap = new HashMap<Object, Object>();
        for (Object key : obj.keySet()){
            jsonMap.put(key, obj.get(key));
        }
        return new JSONObject(jsonMap);
    }
    
    //NOT NECESSARY I DIDNT KNOW ABOUT KEYSET
    
    public static Map<String, Object> jsonToMap(JSONObject object) /*throws JSONException */ {
        Map<String, Object> map = new HashMap<String, Object>();

        if(object != null) {
            for(String key : getJSONKeys(object)) {
                Object value = object.get(key);

                if(value instanceof JSONArray) {
                    value = jsonToList((JSONArray) value);
                }

                else if(value instanceof JSONObject) {
                    value = jsonToMap((JSONObject) value);
                }
                map.put(key, value);
            }
        }
        return map;
    }


    public static List<Object> jsonToList(JSONArray array) /* throws JSONException */{
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static Set<String> getJSONKeys(JSONObject obj){
        Set<String> keys = new HashSet<String>();
        String objString = obj.toString();
        String delim = "[: | ,]";
        String[] tokens = objString.split(delim);
        //TODO
        return keys;
    }
}
