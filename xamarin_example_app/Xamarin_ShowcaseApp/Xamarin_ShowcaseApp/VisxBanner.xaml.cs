using System;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace Xamarin_ShowcaseApp
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class VisxBanner : ContentPage
    {
        /// <summary>
        /// Key values for establishing platform-specific communication channels
        /// </summary>
        private const string ANDROID_CALLBACK_CHANNEL_BANNER = "native.android.callback.channel.banner";
        private const string IOS_CALLBACK_CHANNEL_BANNER = "native.ios.callback.channel.banner";

        public VisxBanner()
        {
            InitializeComponent();
        }

        protected override async void OnAppearing()
        {
            base.OnAppearing();

            if (Device.RuntimePlatform == Device.Android)
            {
                InitAndroidBannerAd();
            }
            else if (Device.RuntimePlatform == Device.iOS)
            {
                InitIosBannerAd();
            }
        }

        /// <summary>
        /// Initializing and displaying Vis.X Android Inline Banner/Video Ad
        /// </summary>
        private void InitAndroidBannerAd()
        {
            /// Subscribe callback for adjusting Xamarin adContainer height for the ad
            /// Example: Ad container in Xamarin shared code is zero in height. Vis.X Banner 
            /// ad is called. Responce is succesfull, ad is ready. Ad have specific height and
            /// trigers this callback with height value so the adContainer in Xamarin shared code
            /// is adjusted.
            MessagingCenter.Subscribe<object, string>(this, ANDROID_CALLBACK_CHANNEL_BANNER, (sender, height) =>
            {
                Device.BeginInvokeOnMainThread(() =>
                {
                    Console.WriteLine("Xamarin.App.Layer.Banner ---> Android Native callback with data: " + height);

                    int adContainerHeight = 0;
                    if (int.TryParse(height, out adContainerHeight))
                    {
                        VisxBannerAdContainer.HeightRequest = adContainerHeight;
                    }
                });
            });
        }

        private void InitIosBannerAd()
        {
            //TODO
        }

        /// <summary>
        /// Unsubscribe native callback
        /// </summary>
        protected override void OnDisappearing()
        {
            base.OnDisappearing();

            if (Device.RuntimePlatform == Device.Android)
            {
                MessagingCenter.Unsubscribe<object>(this, ANDROID_CALLBACK_CHANNEL_BANNER);
            }
            else if (Device.RuntimePlatform == Device.iOS)
            {
                //TODO
            }
        }
    }
}