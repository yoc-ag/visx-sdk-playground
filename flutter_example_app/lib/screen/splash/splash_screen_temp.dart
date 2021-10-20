import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class SplashScreenTemp extends StatefulWidget {
  @override
  _MyState createState() => _MyState();
}

class _MyState extends State<SplashScreenTemp> {
  Color _containerColor = Colors.yellow;

  void startHomeScreen() {
    setState(() {
      if (_containerColor == Colors.yellow) {
        _containerColor = Colors.red;
        return;
      }
      _containerColor = Colors.yellow;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: Scaffold(
          appBar: AppBar(title: Text("A Simple App Stateful Widget")),
          body: Container(decoration: BoxDecoration(color: _containerColor)),
          floatingActionButton: FloatingActionButton(
            onPressed: startHomeScreen,
            child: Icon(Icons.add),
            tooltip: "Book Here",
          ),
        ));
  }
}
