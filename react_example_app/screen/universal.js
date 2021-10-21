import React from "react";
import {
  StyleSheet,
  Text,
  View,
  NativeModules,
  NativeEventEmitter,
  Image,
  ScrollView,
} from "react-native";
import VisxAdContainer from "../modules/universal/VisxUniversalAdUnit";
import * as constants from "../constants";
import { useState } from "react";

export default function Universal() {
  console.log("RN Code: Universal Screen START! Platform: " + Platform.OS);

  const [data, setData] = useState(0);

  if (Platform.OS === "ios") {
    const { VisxViewEmitter } = NativeModules;
    const eventEmitter = new NativeEventEmitter(VisxViewEmitter);
    const subscription = eventEmitter.addListener("sizeChange", (event) => {
      console.log(parseInt(event));
      setData(parseInt(event));
    });
  } else if (Platform.OS === "android") {
    const eventEmitter = new NativeEventEmitter(NativeModules.VisxAdContainer);
    eventEmitter.addListener("sizeChange", (event) => {
      console.log("RN sizeChange: " + event.width + "x" + event.height);
      setData(parseInt(event.height));
    });
  } else {
    console.log("RN Code: Universal Screen START! Unsupported Platform");
  }

  return (
    <View style={styles.container}>
      <ScrollView style={{ alignSelf: "stretch" }}>
        <View
          style={{
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <Image source={require("../assets/teaser_img_article.png")} />

          <Text style={{ alignSelf: "stretch", margin: 10 }}>
            This is a DemoApp for ReactNative
          </Text>

          <Image
            style={styles.imageContainer}
            source={{
              uri: "https://www.positronx.io/wp-content/uploads/2020/02/react-native-150x150-1.jpg",
            }}
          />

          <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>

          <VisxAdContainer
            style={{
              height: data,
              width: "100%",
              alignSelf: "stretch",
              backgroundColor: "#CCCCCC",
              flex: 1,
            }}
          />

          <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
          <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
          <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
          <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
          <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
          <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
        </View>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  scrollView: {
    backgroundColor: "pink",
    marginHorizontal: 20,
  },
  imageContainer: {
    height: 250,
    alignSelf: "stretch",
    marginTop: 10,
    marginBottom: 10,
    marginStart: 24,
    marginEnd: 24,
  },
});