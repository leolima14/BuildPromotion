

package FranGames.BuildPromotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class GetJSON {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }
    
      public  JSONObject readGameFromURL(String url, String nome) throws IOException, JSONException {
        
        JSONObject jsonObject = new JSONObject();
        int platform = 0;
        try {
          InputStream is = new URL(url).openStream();
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONObject json = new JSONObject(jsonText);
          int price = json.getJSONArray("skus").getJSONObject(0).getJSONArray("rewards").getJSONObject(0).getInt("price");
          String prazo = json.getJSONArray("skus").getJSONObject(0).getJSONArray("rewards").getJSONObject(0).get("end_date").toString();
          boolean b = true;
          for(int i = 0; b ; i++){
            try {
              String teste = json.getJSONObject("default_sku").getJSONArray("entitlements").getJSONObject(i).get("packageType").toString();
              if(!teste.equals("null") || !teste.equals("PSTRACK")){
                String p = json.getJSONObject("default_sku").getJSONArray("entitlements").getJSONObject(0).getJSONArray("packages").getJSONObject(0).getString("platformName");
                if(p.equals("ps4") && platform != 4)
                  platform += 4;
                else if(p.equals("ps5") && platform != 5 )
                  platform += 5;
              }
              if(platform >= 9)
                b = false;
            } catch (Exception e) {
              b = false;
              //System.err.println(e);
            }
          }
          if(prazo != null){
            jsonObject.put("nome", nome);
            jsonObject.put("price", price);
            jsonObject.put("prazo", prazo);
            if(platform == 4)
              jsonObject.put("platform", "PS4");
            else if(platform == 5)
              jsonObject.put("platform", "PS5");
            else
              jsonObject.put("platform", "PS4/5");
          }
          is.close();
        }
        catch(Exception e){
            //System.out.println("e");
        } 
        return jsonObject;
      }

      public  Map<String, String> readDataFromURL(String url) throws IOException, JSONException {
        Map<String,String> example = new HashMap<String,String>();
        try{
          InputStream is = new URL(url).openStream();
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONObject json = new JSONObject(jsonText);
          boolean b = true;
          for(int i = 0; b; i++){
            try{
              String id = json.getJSONArray("links").getJSONObject(i).get("id").toString();
              String name = json.getJSONArray("links").getJSONObject(i).get("name").toString();
              String game_contentType = json.getJSONArray("links").getJSONObject(i).get("game_contentType").toString();
              if(game_contentType.equals("Jogo completo") || game_contentType.equals("Conjunto"))
                example.put(name, id);
            }catch(Exception e){
              b = false;
            }
          }
          is.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        return example;
      }
}
