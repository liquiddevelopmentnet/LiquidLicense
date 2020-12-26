# NOTE
this project is in heavy development. errors and bugs can occur!
*Webinterface coming soon!*

## XLicense
 Addon for Licensing your Plugins for Minecraft

### Setup with Maven
1. add Repository:
```xml
<repositories>
   <repository>
      <id>myMavenRepo.read</id>
      <url>https://mymavenrepo.com/repo/uTSc4cUWcx6WGgE8eSGR/</url>
   </repository>
</repositories>
```
2. add Dependency
```xml
<dependencies>
   <dependency>
      <groupId>net.liquid</groupId>
      <artifactId>XLicense</artifactId>
      <version>latest</version>
   </dependency>
</dependencies>
```

### Setup without Maven
1. [Download][releases] the latest release.
2. #### IntelliJ Idea
    - Go to Project Structure > Modules > Dependencies > + > select the release.
   #### Eclipse
    - Properties (Alt+Enter) > Java Build Path > Libraries > Add External JAR's.. > select the release.

## Usage
```java
public final class YourPlugin extends JavaPlugin implements XLicensedPlugin {

    @Override
    public void onEnable() {

        XLicense licenseLib = new XLicense(this); // Initalize XLicenseLib
        try {
            licenseLib.license("URL", new License("LICENSEKEY", "PLUGINNAME"));
        } catch (LicenseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onLicenseVerified(License license) {
        System.out.println("License Valid!");
    }

    @Override
    public void onLicenseDenied(License license, DenyType denyType) {
        System.out.println("License not Valid. Reason: "+denyType.toString());
        Bukkit.shutdown();
    }

    @Override
    public void onLicenseError(LicenseException e) {
       e.printStackTrace();
    }
}
```

[releases]: <https://github.com/liquiddevelopmentnet/XLicense/releases>
[clone]: <https://github.com/liquiddevelopmentnet/XLicense.git>
