import 'package:demo_showcase_flutter/common/colors.dart';
import 'package:demo_showcase_flutter/screen/home/home_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class SplashScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    SchedulerBinding.instance
        ?.addPostFrameCallback((_) => startHomeScreen(context));

    return Scaffold(
      backgroundColor: AppColors.colorPrimaryDark,
      body: Padding(
        padding: const EdgeInsets.all(150),
        child: Center(
          child: Image(
            image: AssetImage(
              'assets/images/yoc_logo.png',
            ),
          ),
        ),
      ),
    );
  }

  startHomeScreen(BuildContext context) {
    new Future.delayed(const Duration(seconds: 2), () {
      Route route = MaterialPageRoute(builder: (context) => HomeScreen());
      Navigator.pushReplacement(context, route);
    });
  }
}
