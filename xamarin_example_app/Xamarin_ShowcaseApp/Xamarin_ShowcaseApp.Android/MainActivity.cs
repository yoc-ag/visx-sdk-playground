
using Android.App;
using Android.Content.PM;
using Android.OS;
using Android.Runtime;
using System;
using Xamarin.Forms.Platform.Android;

/// This line is for establishing receiving channel with the shared Xamarin code
[assembly: Xamarin.Forms.Dependency(typeof(Xamarin_ShowcaseApp.Droid.MainActivity))]

namespace Xamarin_ShowcaseApp.Droid
{
    [Activity(Label = "YOC Xamarin", Icon = "@mipmap/icon", Theme = "@style/MainTheme", MainLauncher = true, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation | ConfigChanges.UiMode | ConfigChanges.ScreenLayout | ConfigChanges.SmallestScreenSize)]
    public class MainActivity : FormsAppCompatActivity, IAndroidNativeVisxlInit
    {
        /// <summary>
        /// Context as singleton needed in different classes for initializing Vis.X Ad
        /// </summary>
        public static FormsAppCompatActivity Instance { get; private set; }

        /// <summary>
        /// Maximum height of the visible part of the screen (rectangle, frame) where the
        /// Vis.X Ad should be displayed
        /// </summary>
        public static int MaxSizeHeight = 0;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            global::Xamarin.Forms.Forms.Init(this, savedInstanceState);
            LoadApplication(new App());
            Instance = this;
        }

        /// <summary>
        /// Initializing and displaying Vis.X Mystery (Interstitial) Ad
        /// </summary>
        public void InterstitialCall()
        {
            new XamarinVisxInterstitial();
        }

        /// <summary>
        /// Setting up maxHeight
        /// </summary>
        /// <param name="height"></param>
        public void AnchorViewMaxHeight(int height)
        {
            Console.WriteLine("Xamarin.Android.Layer.MainActivity ---> anchorViewMaxHeight(): " + height);
            MaxSizeHeight = height;
        }

        public override void OnRequestPermissionsResult(int requestCode, string[] permissions, [GeneratedEnum] Android.Content.PM.Permission[] grantResults)
        {
            Xamarin.Essentials.Platform.OnRequestPermissionsResult(requestCode, permissions, grantResults);

            base.OnRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}