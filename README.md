# NOTE
*The Web part is missing completely. Coming soon!*

## LiquidLicense
 Addon for Licensing your Plugins for Minecraft

### Setup with Maven
NOTE: repository is currently down, sorry for trouble
1. add Repository:
```xml
<repositories>
   <repository>
      <id>minemix</id>
      <url>http://oss.minemix.de/repository/maven-releases/</url>
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

## Bukkit Usage

```java
import net.liquid.liquidlicense.types.LiquidLicense;
import net.liquid.liquidlicense.types.DenyType;
import net.liquid.liquidlicense.LiquidLicenseLib;
import org.bukkit.plugin.java.JavaPlugin;

public final class YourBukkitPlugin extends JavaPlugin implements LiquidLicensed {

   @Override
   public void onEnable() {

      //Check if License is Valid (put this always at the top from onEnable)
      new LiquidLicenseLib("AUTHENTICATION_SERVER_URI", new LiquidLicense("LICENSEKEY"), this, this);

   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onLicenseVerified(License liquidLicense) {
      //Executed if License is valid 
      System.out.println("License Valid!");
   }

   @Override
   public void onLicenseDenied(License liquidLicense, DenyType denyType) {
      //Executed if License is dont valid
      System.out.println("License not Valid. Reason: " + denyType.toString());
      Bukkit.shutdown();
   }

   @Override
   public void onLicenseError(LicenseException e) {
      //Executed if a Licensing error occurs
      e.printStackTrace();
   }
}
```

## BungeeCord Usage

```java
import net.liquid.liquidlicense.types.LiquidLicense;
import net.liquid.liquidlicense.types.DenyType;
import net.liquid.liquidlicense.LiquidLicenseLib;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class YourBungeeCordPlugin extends Plugin implements LiquidLicensed {

   @Override
   public void onEnable() {

      //Check if License is Valid (put this always at the top from onEnable)
      new LiquidLicenseLib("AUTHENTICATION_SERVER_URI", new LiquidLicense("LICENSEKEY"), this, this);

   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onLicenseVerified(License liquidLicense) {
      //Executed if License is valid 
      System.out.println("License Valid!");
   }

   @Override
   public void onLicenseDenied(License liquidLicense, DenyType denyType) {
      //Executed if License is dont valid
      System.out.println("License not Valid. Reason: " + denyType.toString());
      ProxyServer.getInstance().stop();
   }

   @Override
   public void onLicenseError(LicenseException e) {
      //Executed if a Licensing error occurs
      e.printStackTrace();
   }
}
```

[releases]: <https://github.com/liquiddevelopmentnet/LiquidLicense/releases>
[clone]: <https://github.com/liquiddevelopmentnet/LiquidLicense.git>
