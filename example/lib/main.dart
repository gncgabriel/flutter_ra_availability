import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_ra_availability/flutter_ra_availability.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool? _isSupported;

  Future<void> checkPlataformState() async {
    bool? isSupported = await RaAvailability.isSupported;

    setState(() {
      _isSupported = isSupported;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('RA Availability example'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'Click at the button bellow to check if device support AR.',
              textAlign: TextAlign.center,
              style: TextStyle(
                  fontSize: 16,
                  color: Colors.blue,
                  fontWeight: FontWeight.w800),
            ),
            ElevatedButton(
              onPressed: checkPlataformState,
              child: const Text("Check"),
            ),
            _isSupported != null
                ? Text(
                    _isSupported.toString(),
                    style: const TextStyle(
                      fontSize: 32,
                    ),
                  )
                : const SizedBox(),
          ],
        ),
      ),
    );
  }
}
