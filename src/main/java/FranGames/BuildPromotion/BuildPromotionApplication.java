package FranGames.BuildPromotion;


import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.CDL;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuildPromotionApplication {

	public static void main(String[] args) throws IOException, JSONException {
		SpringApplication.run(BuildPromotionApplication.class, args);
		Map<String,String> example = new HashMap<String,String>();
		JSONObject jsonObject = new JSONObject();
		String jogoURL = "https://store.playstation.com/store/api/chihiro/00_09_000/container/br/pt/999/";
		String buscaURL = "https://store.playstation.com/store/api/chihiro/00_09_000/tumbler/br/pt/999/";
		GetJSON j = new GetJSON();
		
		List<String> jogos = new Jogos().getJogos();

		for(int i = 0; i < jogos.size(); i++){
			example = j.readDataFromURL(buscaURL + jogos.get(i));
			for (String name: example.keySet()){
				String id = example.get(name);
				JSONObject p = j.readGameFromURL(jogoURL + id, name);
				if(JSONObject.getNames(p) != null)
					jsonObject.append("jogo", p);
			}
		}
		System.out.println(jsonObject);
		try {
			String csv = CDL.toString(jsonObject.getJSONArray("jogo"));
			FileWriter file = new FileWriter("C:/Users/Unknown/Documents/GitHub/BuildPromotion/ps4.csv");
			file.write(csv);
			file.close();
			System.out.println(csv);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("erro");
		} 
	}

}
