package net.liquid.xlicense.types;/*

 *	Urheberrechtshinweis														*
 *																				*
 *	Copyright © Finn Behrend 2020										        *
 *	Erstellt: 25.12.2020 / 13:48											    *
 *																				*
 *	Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.			*
 *	Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,	*
 *	bei Finn Behrend. Alle Rechte vorbehalten.								    *
 *																				*
 *	Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,			*
 *	öffentlichen Zugänglichmachung oder anderer Nutzung							*
 *	bedarf der ausdrücklichen, schriftlichen Zustimmung von Finn Behrend    	*
 */

public class License {

    protected String licenseKey;
    protected String pluginName;

    public License(String licenseKey, String pluginName) {
        this.licenseKey = licenseKey;
        this.pluginName = pluginName;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

}
