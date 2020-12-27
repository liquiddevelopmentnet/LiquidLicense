# NOTE
this project is in heavy development. errors and bugs can occur!
*Webinterface coming soon!*

## LiquidLicense
 Addon for Licensing your Plugins for Minecraft

### Setup with Maven
1. add Repository:
```xml
<repositories>
   <repository>
      <id>mymavenrepo</id>
      <url>https://mymavenrepo.com/repo/uTSc4cUWcx6WGgE8eSGR/</url>
   </repository>
</repositories>
```
2. add Dependency
```xml
<dependencies>
   <dependency>
      <groupId>net.liquid</groupId>
      <artifactId>LiquidLicense</artifactId>
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
import net.liquid.liquidlicense.types.LiquidLicense;
import net.liquid.liquidlicense.types.DenyType;
import net.liquid.liquidlicense.LiquidLicenseLib;

public final class YourPlugin extends JavaPlugin implements LiquidLicensed {

   @Override
   public void onEnable() {

      new LiquidLicenseLib("AUTHENTICATION_SERVER_URI", new LiquidLicense("LICENSEKEY"), this, this);

   }

   @Override
   public void onDisable() {}

   @Override
   public void onLicenseVerified(License liquidLicense) {
      System.out.println("License Valid!");
   }

   @Override
   public void onLicenseDenied(License liquidLicense, DenyType denyType) {
      System.out.println("License not Valid. Reason: " + denyType.toString());
      Bukkit.shutdown();
   }

   @Override
   public void onLicenseError(LicenseException e) {
      e.printStackTrace();
   }
}
```

[releases]: <https://github.com/liquiddevelopmentnet/LiquidLicense/releases>
[clone]: <https://github.com/liquiddevelopmentnet/LiquidLicense.git>
