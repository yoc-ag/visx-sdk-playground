import React, { Component } from "react";
import { View, Image, StyleSheet } from "react-native";

export default class SplashScreen extends Component {
  constructor(props) {
    super(props);
    setTimeout(() => {
      this.props.navigation.navigate("Home");
    }, 2000);
  }

  render() {
    return (
      <View style={styles.container}>
        <Image
          style={styles.imageContainer}
          source={require("../assets/yoc_logo_white.png")}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    padding: 24,
    backgroundColor: "#1E2C4A",
    flex: 1,
    resizeMode: "cover",
    justifyContent: 'center',
    alignItems: 'center'
  },
  imageContainer: {
    height: 150,
    width: 150,
  },
});
