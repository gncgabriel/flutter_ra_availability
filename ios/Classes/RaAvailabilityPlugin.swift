import Flutter
import UIKit
import ARKit

public class RaAvailabilityPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "ra_availability", binaryMessenger: registrar.messenger())
    let instance = RaAvailabilityPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if #available(iOS 11, *) {
      result(ARConfiguration.isSupported)
    } else {
      result(false)
    }
  }
}
