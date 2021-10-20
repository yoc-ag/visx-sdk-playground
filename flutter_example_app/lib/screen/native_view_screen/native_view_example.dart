import 'package:demo_showcase_flutter/common/dimen.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';

class AndroidView extends StatefulWidget {
  AndroidView({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _AndroidViewState createState() => _AndroidViewState();
}

class _AndroidViewState extends State<AndroidView> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    // This is used in the platform side to register the view.
    final String viewType = 'hybrid-view-type';
    // Pass parameters to the platform side.
    final Map<String, dynamic> creationParams = <String, dynamic>{};

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            Flexible(
              child: ListView(
                physics: const AlwaysScrollableScrollPhysics(),
                scrollDirection: Axis.vertical,
                shrinkWrap: true,
                children: [
                  Container(
                    width: 300,
                    height: 250,
                    child: PlatformViewLink(
                      viewType: viewType,
                      surfaceFactory: (BuildContext context, PlatformViewController controller) {
                        return AndroidViewSurface(
                          controller: controller,
                          gestureRecognizers: const <Factory<OneSequenceGestureRecognizer>>{},
                          hitTestBehavior: PlatformViewHitTestBehavior.opaque,
                        );
                      },
                      onCreatePlatformView: (PlatformViewCreationParams params) {
                        return PlatformViewsService.initSurfaceAndroidView(
                          id: params.id,
                          viewType: viewType,
                          layoutDirection: TextDirection.ltr,
                          creationParams: creationParams,
                          creationParamsCodec: StandardMessageCodec(),
                        )
                          ..addOnPlatformViewCreatedListener(params.onPlatformViewCreated)
                          ..create();
                      },
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
