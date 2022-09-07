package utils;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RetrieveUtil {

    public static <T> T retrieveResourceFromResponse(HttpResponse response, final Class<T> clazz) throws IOException {
        String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseStr);
        Gson gson = new Gson();

        return gson.fromJson(responseStr, clazz);
    }
}
