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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class RequestMaker {

    public static void main(String[] args) throws Exception {
        new RequestMaker().makeResponseRequest("https://httpreq.com/black-breeze-0fr3a087/record");
    }

    public String makeResponseRequest(String urlString) throws Exception {

        Properties prop = new Properties();
        String propFileName = ".properties";

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        }

        String version = prop.getProperty("version");

        URL url = new URL(urlString);

        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "LiquidLicense/"+version+" (liquiddevelopmentnet; U; LiquidLicense; en-US; rv:1.9.2.2) Gecko/20100316 LiquidLicense/"+version);

        return urlConnection.getHeaderField("response");

    }

}
