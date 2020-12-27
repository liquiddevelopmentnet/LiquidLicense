package net.liquid.liquidlicense;/*

 *	Urheberrechtshinweis														*
 *																				*
 *	Copyright © Finn Behrend 2020										        *
 *	Erstellt: 25.12.2020 / 14:00											    *
 *																				*
 *	Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.			*
 *	Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,	*
 *	bei Finn Behrend. Alle Rechte vorbehalten.								    *
 *																				*
 *	Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,			*
 *	öffentlichen Zugänglichmachung oder anderer Nutzung							*
 *	bedarf der ausdrücklichen, schriftlichen Zustimmung von Finn Behrend    	*
 */

import net.liquid.liquidlicense.types.*;
import net.liquid.liquidlicense.utils.HostUtils;
import net.liquid.liquidlicense.utils.RequestMaker;
import org.bukkit.plugin.java.JavaPlugin;

public class LiquidLicenseLib {

    private LiquidLicensed plugin;
    private JavaPlugin javaPlugin;
    private String uri;

    public LiquidLicenseLib(String uri, LiquidLicense liquidLicense, LiquidLicensed plugin, JavaPlugin javaPlugin) throws LicenseException {
        this.plugin = plugin;
        this.javaPlugin = javaPlugin;
        this.uri = uri;
        license(liquidLicense);
    }

    private void license(LiquidLicense liquidLicense) throws LicenseException {

        String response;
        LicenseResponseType licenseResponseType = null;

        try {
            response = new RequestMaker().makeRequest(this.uri + "?key=" + liquidLicense.getLicenseKey() + "&plugin=" + javaPlugin.getName() + "&mac=" + HostUtils.getMacAdress());
            if (response.contains("LICENSE_VALID"))
                licenseResponseType = LicenseResponseType.LICENSE_VALID;
            if (response.contains("LICENSE_INVALID"))
                licenseResponseType = LicenseResponseType.LICENSE_INVALID;
            if (response.contains("LICENSE_EXPIRED"))
                licenseResponseType = LicenseResponseType.LICENSE_EXPIRED;
            if (response.contains("WRONG_IP"))
                licenseResponseType = LicenseResponseType.WRONG_IP;
            if (response.contains("WRONG_PLUGIN_NAME"))
                licenseResponseType = LicenseResponseType.WRONG_PLUGIN_NAME;
            if (response.contains("FORCE_DELETE"))
                licenseResponseType = LicenseResponseType.FORCE_DELETE;
            if (response.contains("INTERNAL_ERROR"))
                licenseResponseType = LicenseResponseType.INTERNAL_ERROR;
        } catch (Exception exception) {
            if (exception.getMessage().contains("protocol")) {
                throw new LicenseException("Please Specify a Protocol like http://" + this.uri, new Throwable(String.valueOf(LicenseErrorType.INTERNAL_ERROR)), LicenseErrorType.INTERNAL_ERROR);
            }
            exception.printStackTrace();
            throw new LicenseException("Make sure the LiquidLicenseServer on " + this.uri + " is running and up to date!", new Throwable(String.valueOf(LicenseErrorType.SERVER_DOWN)), LicenseErrorType.SERVER_DOWN);
        }
        if(licenseResponseType == null) {
            throw new LicenseException("Make sure the LiquidLicenseServer on " + this.uri + " is running and up to date!", new Throwable(String.valueOf(LicenseErrorType.SERVER_DOWN)), LicenseErrorType.SERVER_DOWN);
        } else if (licenseResponseType.equals(LicenseResponseType.LICENSE_VALID)) {
            this.plugin.onLicenseVerified(liquidLicense);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.LICENSE_INVALID)) {
            this.plugin.onLicenseDenied(liquidLicense, DenyType.INVALID);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.LICENSE_EXPIRED)) {
            this.plugin.onLicenseDenied(liquidLicense, DenyType.EXPIRED);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.WRONG_PLUGIN_NAME)) {
            this.plugin.onLicenseDenied(liquidLicense, DenyType.WRONG_PLUGIN_NAME);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.WRONG_IP)) {
            this.plugin.onLicenseDenied(liquidLicense, DenyType.WRONG_IP);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.FORCE_DELETE)) {
            // Future function
            return;
        }

    }

}
