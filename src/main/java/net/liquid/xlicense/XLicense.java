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

import com.sun.istack.internal.NotNull;
import net.liquid.xlicense.types.*;
import net.liquid.xlicense.utils.HostUtils;
import net.liquid.xlicense.utils.RequestMaker;

public class XLicense {

    private net.liquid.xlicense.XLicensedPlugin plugin;

    public XLicense(XLicensedPlugin plugin) {
        this.plugin = plugin;
    }

    public void license(@NotNull String url, @NotNull License license) throws LicenseException {

        LicenseResponseType response;

        try {
            response = LicenseResponseType.valueOf(new RequestMaker().makeRequest(url+"?key="+license.getLicenseKey()+"&plugin="+license.getPluginName()+"&mac="+HostUtils.getMacAdress()));
        } catch (Exception exception) {
            if(exception.getMessage().contains("protocol")) {
                throw new LicenseException("Please Specify a Protocol like http://"+url, new Throwable(String.valueOf(LicenseErrorType.INTERNAL_ERROR)), LicenseErrorType.INTERNAL_ERROR);
            }
            throw new LicenseException("Make sure the XLicenseServer on "+url+" is running and up to date!", new Throwable(String.valueOf(LicenseErrorType.SERVER_DOWN)), LicenseErrorType.SERVER_DOWN);
        }

        if(response.equals(LicenseResponseType.LICENSE_VALID)) {
            this.plugin.onLicenseVerified(license);
            return;
        } else if (response.equals(LicenseResponseType.LICENSE_INVALID)) {
            this.plugin.onLicenseDenied(license, DenyType.INVALID);
            return;
        } else if (response.equals(LicenseResponseType.LICENSE_EXPIRED)) {
            this.plugin.onLicenseDenied(license, DenyType.EXPIRED);
            return;
        } else if (response.equals(LicenseResponseType.WRONG_PLUGIN_NAME)) {
            this.plugin.onLicenseDenied(license, DenyType.WRONG_PLUGIN_NAME);
            return;
        } else if (response.equals(LicenseResponseType.WRONG_IP)) {
            this.plugin.onLicenseDenied(license, DenyType.WRONG_IP);
            return;
        } else if (response.equals(LicenseResponseType.FORCE_DELETE)) {
            // Future function
            return;
        }

    }

}
