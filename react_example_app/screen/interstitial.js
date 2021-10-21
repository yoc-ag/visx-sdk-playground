import React from "react";
import {
  StyleSheet,
  View,
  Image,
  Text,
  ScrollView,
  Button,
  NativeModules,
  Platform,
} from "react-native";
import * as constants from "../constants";
const { VisxInterstitialModule } = NativeModules;

export default function Interstitial() {
  console.log("RN Code: Insterstitial Screen START! Platform: " + Platform.OS);

  return (
    <View style={styles.con}>
      <ScrollView style={{ alignSelf: "stretch" }}>
        <Image source={require("../assets/teaser_img_article.png")} />

        <Text style={{ alignSelf: "stretch", margin: 10 }}>
          This is a DemoApp for ReactNative
        </Text>

        <Image
          style={styles.imageContainer}
          source={{
            uri:
              "https://www.positronx.io/wp-content/uploads/2020/02/react-native-150x150-1.jpg",
          }}
        />

        <View style={styles.button}>
          <Button title="Display Interstitial" onPress={initInterstitial} />
        </View>

        <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
        <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
        <Text style={{ margin: 10 }}>{constants.LoremIpsum}</Text>
      </ScrollView>
    </View>
  );
}

const initInterstitial = () => {
  if (Platform.OS === "android") {
    VisxInterstitialModule.getVisxAdManager(
      "912263",
      "yoc.react-native.com"
    );
  } else if (Platform.OS === "ios")  {
    VisxInterstitialModule.loadInterstitial()
  }
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  imageContainer: {
    height: 250,
    alignSelf: "stretch",
    marginTop: 10,
    marginBottom: 10,
    marginStart: 24,
    marginEnd: 24,
  },
  button: {
    marginStart: 24,
    marginEnd: 24,
    marginTop: 10,
    marginBottom: 10,
  },
});
