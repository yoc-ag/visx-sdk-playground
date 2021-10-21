import React from "react";
import { StyleSheet, View, Text, Button } from "react-native";


export default function Home({ navigation }) {
  console.log("RN Code: Home Screen START!");

  const onBannerPressed = () => {
    navigation.navigate("Banner");
  };

  const onInterstitialPressed = () => {
    navigation.push("Interstitial");
  };

  const onUniversalPressed = () => {
    navigation.push("Universal");
  };

  return (
    <View style={styles.container}>
      <Text
        style={{ width: 300, margin: 10, fontSize: 20, fontWeight: "bold" }}
      >
        YOC VIS.X React Native Showcase
      </Text>

      <View style={{ margin: 10 }}>
        <Button
          title="VIS.X Banner Showcase"
          onPress={onBannerPressed}
          style={{ margin: 10 }}
        ></Button>
      </View>

      <View style={{ margin: 10 }}>
        <Button
          title="VIS.X Interstitial Showcase"
          onPress={onInterstitialPressed}
        ></Button>
      </View>

      <View style={{ margin: 10 }}>
        <Button
          title="VIS.X Universal Showcase"
          onPress={onUniversalPressed}
        ></Button>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 24,
  },
});
