import 'package:flutter/material.dart';

/// Custom Widget for gathering size of wrapped Widget
class SizeProviderWidget extends StatefulWidget {
  final Widget child;
  final Function(Size) onChildSize;

  const SizeProviderWidget(
      {Key key, this.onChildSize, this.child})
      : super(key: key);
  @override
  _SizeProviderWidgetState createState() => _SizeProviderWidgetState();
}

class _SizeProviderWidgetState extends State<SizeProviderWidget> {
  @override
  void initState() {

    /// Add size listener for first build
    _onResize();
    super.initState();
  }

  void _onResize() {
    WidgetsBinding.instance?.addPostFrameCallback((timeStamp) {
      if (context.size is Size) {
        widget.onChildSize(context.size);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    /// Add size listener for every build uncomment the fallowing
    //_onResize();
    return widget.child;
  }
}