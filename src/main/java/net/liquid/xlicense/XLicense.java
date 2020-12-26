package net.liquid.xlicense;/*

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

import net.liquid.xlicense.types.*;
import net.liquid.xlicense.utils.HostUtils;
import net.liquid.xlicense.utils.RequestMaker;

public class XLicense {

    private net.liquid.xlicense.XLicensedPlugin plugin;

    public XLicense(XLicensedPlugin plugin) {
        this.plugin = plugin;
    }

    public void license(String url, License license) throws LicenseException {

        String response;
        LicenseResponseType licenseResponseType = null;

        try {
            response = new RequestMaker().makeRequest(url + "?key=" + license.getLicenseKey() + "&plugin=" + license.getPluginName() + "&mac=" + HostUtils.getMacAdress());
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
                throw new LicenseException("Please Specify a Protocol like http://" + url, new Throwable(String.valueOf(LicenseErrorType.INTERNAL_ERROR)), LicenseErrorType.INTERNAL_ERROR);
            }
            exception.printStackTrace();
            throw new LicenseException("Make sure the XLicenseServer on " + url + " is running and up to date!", new Throwable(String.valueOf(LicenseErrorType.SERVER_DOWN)), LicenseErrorType.SERVER_DOWN);
        }
        if(licenseResponseType == null) {
            throw new LicenseException("Make sure the XLicenseServer on " + url + " is running and up to date!", new Throwable(String.valueOf(LicenseErrorType.SERVER_DOWN)), LicenseErrorType.SERVER_DOWN);
        } else if (licenseResponseType.equals(LicenseResponseType.LICENSE_VALID)) {
            this.plugin.onLicenseVerified(license);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.LICENSE_INVALID)) {
            this.plugin.onLicenseDenied(license, DenyType.INVALID);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.LICENSE_EXPIRED)) {
            this.plugin.onLicenseDenied(license, DenyType.EXPIRED);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.WRONG_PLUGIN_NAME)) {
            this.plugin.onLicenseDenied(license, DenyType.WRONG_PLUGIN_NAME);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.WRONG_IP)) {
            this.plugin.onLicenseDenied(license, DenyType.WRONG_IP);
            return;
        } else if (licenseResponseType.equals(LicenseResponseType.FORCE_DELETE)) {
            // Future function
            return;
        }

    }

}
