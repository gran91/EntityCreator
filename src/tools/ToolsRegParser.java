/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Jeremy.CHAUT
 */
public class ToolsRegParser {

    public static String getURLContent(String url, String login, String mdp) throws IOException {
        String content = "";
        HttpClient client = new DefaultHttpClient(); //or any method to get a client instance, with a 'threadsafe' connection manager or otherwise
        Credentials credentials = new UsernamePasswordCredentials(login, mdp);
        ((DefaultHttpClient) client).getCredentialsProvider()
                .setCredentials(AuthScope.ANY, credentials);

        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
        } else {
            System.err.println("Erreur rencontré:" + response.getStatusLine().getStatusCode() + " (" + response.getStatusLine().getReasonPhrase() + ")");
        }
        return content;
    }

    public static String getURLContent(String url) throws IOException {
        return getURLContent(url, "", "");
    }

    public static ArrayList<String> parseContent(String content, String regex, int group) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        ArrayList a = new ArrayList();
        while (m.find()) {
            content = m.group(group);
            a.add(content);
            System.out.println(content);
        }
        return a;
    }
}
