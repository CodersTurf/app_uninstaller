import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:app_uninstaller/app_uninstaller.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _isUninstalled=false;

  @override
  void initState() {
    super.initState();
    initialize();
  }

  Future<void> initialize() async {
    bool isUninstalled;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      isUninstalled =
      await AppUninstaller.Uninstall("com.google.android.gm");
    } on Exception {
      isUninstalled = false;
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      isUninstalled = isUninstalled;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('App Uninstalled? : $_isUninstalled\n'),
        ),
      ),
    );
  }
}
