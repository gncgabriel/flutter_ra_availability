# Flutter AR Availability

A flutter plugin that check if AR is available for Android and IOS. To check for android we use
com.google.ar.core and to check for IOS we use ARKit.

## Getting Started

You have to put in your AndroidManifest.xml inside application &lt;meta-data android:name="com.google.ar.core" android:value="required" /&gt; or &lt;meta-data android:name="com.google.ar.core" android:value="optional" /&gt;

### Android

If device supported RA and already have Google Play Services for RA installed, the method RaAvailability.isSupported returns true,
if Google Play Services for RA isn't installed yet, we ask the user to install, open the Google 
Play Services for RA page at play store and RaAvailability.isSupported  returns false.  
After that, if the device is able to install and the user install, the RaAvailability.isSupported 
will return true. If the user dont install, the RaAvailability.isSupported will keep returning false.

### IOS
For Ios if device have IOS 11 or higher we check ARKit.isSupported, otherwise will return false.

## Example
See the example file.
