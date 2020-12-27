package net.liquid.liquidlicense.types;/*

 *	Urheberrechtshinweis														*
 *																				*
 *	Copyright © Finn Behrend 2020										        *
 *	Erstellt: 25.12.2020 / 13:52											    *
 *																				*
 *	Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.			*
 *	Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,	*
 *	bei Finn Behrend. Alle Rechte vorbehalten.								    *
 *																				*
 *	Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,			*
 *	öffentlichen Zugänglichmachung oder anderer Nutzung							*
 *	bedarf der ausdrücklichen, schriftlichen Zustimmung von Finn Behrend    	*
 */

public class LicenseException extends Exception {

    protected LicenseErrorType licenseErrorType;

    public LicenseException(String errorMessage, Throwable err, LicenseErrorType licenseErrorType) {
        super(errorMessage, err);
        this.licenseErrorType = licenseErrorType;

    }

    public LicenseErrorType getLicenseErrorType() {
        return licenseErrorType;
    }
}
