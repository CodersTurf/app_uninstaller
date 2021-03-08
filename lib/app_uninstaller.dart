import 'dart:async';

import 'package:flutter/services.dart';

class AppUninstaller {
  static const MethodChannel _channel =
  const MethodChannel('app_uninstaller');

  static Future<bool?> Uninstall(appPackage) async {
    final bool? isUninstall =
    await _channel.invokeMethod("Uninstall", {"Package": appPackage});
    return isUninstall;
  }
}