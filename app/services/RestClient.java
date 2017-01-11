package services;
import java.io.*;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by user on 11/01/17.
 */


public class RestClient {

    public static String getRecipe(String params) {
        StringBuilder url = new StringBuilder();
        url.append("https://api.edamam.com/search?q=");
        url.append(params);
        url.append("&app_id=c539b7bc&app_key=437ce9213d97768f3d6afd126f6df213&from=0&to=3&health=alcohol-free");

        String recipeUrl = "https://api.edamam.com/search?q=chicken,peas&app_id=c539b7bc&app_key=437ce9213d97768f3d6afd126f6df213&from=0&to=3&health=alcohol-free";
        String distanceURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=BH10+5NE&destinations=BH22+9QT&mode=walking&language=en";

        StringBuilder sb = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGetRequest = new HttpGet(url.toString());
            HttpResponse httpResponse = httpClient.execute(httpGetRequest);

            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            System.out.println("----------------------------------------");

            HttpEntity entity = httpResponse.getEntity();

            byte[] buffer = new byte[1024];
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                try {
                    int bytesRead = 0;
                    BufferedInputStream bis = new BufferedInputStream(
                            inputStream);
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        String chunk = new String(buffer, 0, bytesRead);
                        sb.append(chunk);
                        //System.out.println(chunk);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (Exception ignore) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }


        return (sb.toString());
    }


    public final static void main(String[] args) {


    }
}