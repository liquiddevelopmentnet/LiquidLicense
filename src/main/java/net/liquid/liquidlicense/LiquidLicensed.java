package net.liquid.liquidlicense;/*

 *	Urheberrechtshinweis														*
 *																				*
 *	Copyright © Finn Behrend 2020										        *
 *	Erstellt: 25.12.2020 / 13:47											    *
 *																				*
 *	Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.			*
 *	Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,	*
 *	bei Finn Behrend. Alle Rechte vorbehalten.								    *
 *																				*
 *	Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,			*
 *	öffentlichen Zugänglichmachung oder anderer Nutzung							*
 *	bedarf der ausdrücklichen, schriftlichen Zustimmung von Finn Behrend    	*
 */

import net.liquid.liquidlicense.types.DenyType;
import net.liquid.liquidlicense.types.LiquidLicense;
import net.liquid.liquidlicense.types.LicenseException;

public interface LiquidLicensed {

    public abstract void onLicenseVerified(LiquidLicense liquidLicense);
    public abstract void onLicenseDenied(LiquidLicense licence, DenyType denyType);
    public abstract void onLicenseError(LicenseException licenseException);

}
