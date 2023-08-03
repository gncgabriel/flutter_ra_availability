import 'dart:async';

import 'package:flutter/services.dart';

class RaAvailability {
  static const MethodChannel _channel = MethodChannel('ra_availability');

  static Future<bool?> get isSupported async {
    final bool? supported = await _channel.invokeMethod('isSupported');
    return supported;
  }
}
