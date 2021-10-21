import { createStackNavigator } from "react-navigation-stack";
import { createAppContainer } from "react-navigation";
import Home from "../screen/home";
import Banner from "../screen/banner";
import Interstitial from "../screen/interstitial";
import Universal from "../screen/universal";
import SplashScreen from "../screen/splash";

const screens = {
  SplashScreen: {
    screen: SplashScreen,
    navigationOptions: { headerShown: false },
  },
  Home: {
    screen: Home,
    navigationOptions: {
      headerLeft: () => {
        return null;
      },
      headerStyle: {
        backgroundColor: "#1E2C4A",
      },
      headerTitleStyle: {
        color: "white",
      },
      headerTintColor: "white",
    },
  },
  Banner: {
    screen: Banner,
    navigationOptions: {
      headerStyle: {
        backgroundColor: "#1E2C4A",
      },
      headerTitleStyle: {
        color: "white",
      },
      headerTintColor: "white",
    },
  },
  Interstitial: {
    screen: Interstitial,
    navigationOptions: {
      headerStyle: {
        backgroundColor: "#1E2C4A",
      },
      headerTitleStyle: {
        color: "white",
      },
      headerTintColor: "white",
    },
  },
  Universal: {
    screen: Universal,
    navigationOptions: {
      headerStyle: {
        backgroundColor: "#1E2C4A",
      },
      headerTitleStyle: {
        color: "white",
      },
      headerTintColor: "white",
    },
  },
};

const HomeStack = createStackNavigator(screens);

export default createAppContainer(HomeStack);
