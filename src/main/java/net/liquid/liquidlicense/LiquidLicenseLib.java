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
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class LiquidLicenseLib {

    private LiquidLicensed plugin;
    private JavaPlugin bukkit = null;
    private Plugin bungee = null;
    private String uri;

    public LiquidLicenseLib(String uri, LiquidLicense liquidLicense, LiquidLicensed plugin, JavaPlugin javaPlugin) throws LicenseException {
        this.plugin = plugin;
        this.bukkit = javaPlugin;
        this.uri = uri;
        license(liquidLicense);
    }

    public LiquidLicenseLib(String uri, LiquidLicense liquidLicense, LiquidLicensed plugin, Plugin bungeePlugin) throws LicenseException {
        this.plugin = plugin;
        this.bungee = bungeePlugin;
        this.uri = uri;
        license(liquidLicense);
    }

    public LiquidLicenseLib() {}

    private void license(LiquidLicense liquidLicense) throws LicenseException {

        String response;
        LicenseResponseType licenseResponseType = null;

        try {
            response = new RequestMaker().makeResponseRequest(this.uri + "?key=" + liquidLicense.getLicenseKey() + "&plugin=" + bukkit == null ? bungee.getDescription().getName() : bukkit.getName() + "&mac=" + HostUtils.getMacAdress());
            licenseResponseType = LicenseResponseType.valueOf(response);
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
