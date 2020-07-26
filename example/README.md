# Flutter plugin to uninstall android apps asynchronously

[![Pub](https://img.shields.io/pub/v/app_uninstaller.svg)](https://pub.dev/packages/app_uninstaller)

A plugin to uninstall android apps(asynchronously).

## Getting Started

1) import the package in your application as below:
```dart
import 'package:app_uninstaller/app_uninstaller.dart';
```

2) call the 'Uninstall' method with application's 'package name' as a parameter:

To get the list of the apps installed on the device:

```dart
var isUninstalled = await AppUninstaller.Uninstall("com.google.android.gm");
```

3 If user clicks 'Ok' button the returned value will be true else it will be false.
