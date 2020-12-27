package net.liquid.liquidlicense.utils;/*

 *	Urheberrechtshinweis														*
 *																				*
 *	Copyright © Finn Behrend 2020										        *
 *	Erstellt: 25.12.2020 / 14:40											    *
 *																				*
 *	Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.			*
 *	Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,	*
 *	bei Finn Behrend. Alle Rechte vorbehalten.								    *
 *																				*
 *	Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,			*
 *	öffentlichen Zugänglichmachung oder anderer Nutzung							*
 *	bedarf der ausdrücklichen, schriftlichen Zustimmung von Finn Behrend    	*
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RequestMaker {

    public String makeRequest(String urlString) throws Exception {
        StringBuilder content = new StringBuilder();

        URL url = new URL(urlString);

        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; de-DE; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            content.append(line + "\n");
        }
        bufferedReader.close();

        return content.toString();

    }

}
