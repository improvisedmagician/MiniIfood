package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ViaCEPService {
    public static String getAddressByCep(String cep) {
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) response.append(inputLine);
            in.close();
            JSONObject json = new JSONObject(response.toString());
            if (json.has("erro")) return "";
            return json.getString("logradouro") + ", " + json.getString("bairro") + ", " + json.getString("localidade") + " - " + json.getString("uf");
        } catch (Exception e) {
            return "";
        }
    }
}
