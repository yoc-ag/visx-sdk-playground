import 'package:flutter/services.dart';

/// Event Channel bridge key
const EventChannel CHANNEL = EventChannel("event.channel.ad.size.change");

/// Custom Event Channel class.
///
/// Responsible for listening ad size changes on native Android/iOS platform
class EventChannelData {

  /// AdSize values
  ///
  /// x - width
  /// y - height
  final double x;
  final double y;

  EventChannelData(this.x, this.y);

  @override
  String toString() => "[EventChannelData(x: $x, y: $y)]";
}

EventChannelData _listOfValues(List<double> data) {
  return EventChannelData(data[0], data[1]);
}

Stream<EventChannelData> _adSizeChangeEvent;

/// Return adSize values as Stream
Stream<EventChannelData> get eventData {
  if (_adSizeChangeEvent == null) {
    _adSizeChangeEvent =
        CHANNEL.receiveBroadcastStream().map((event) => _listOfValues(event.cast<double>()));
  }
  return _adSizeChangeEvent;
}
