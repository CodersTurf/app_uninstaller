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
  String uninstallStatus = "";

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                FlatButton(
                  textColor: Colors.white,
                  color: Colors.pink,
                  onPressed: () async {
                    try {
                      var isUninstalled = await AppUninstaller.Uninstall(
                          "com.example.testapplication");
                      setState(() {
                        uninstallStatus = isUninstalled
                            ? "Successfully Uninstalled!"
                            : "Cancelled by user";
                      });
                    } on Exception {
                      uninstallStatus = "Some error occurred";
                    }
                  },
                  child: Text(
                    "Uninstall Test Application",
                    style: TextStyle(fontSize: 18),
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                Text(
                  uninstallStatus,
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w500),
                )
              ]),
        ),
      ),
    );
  }
}
