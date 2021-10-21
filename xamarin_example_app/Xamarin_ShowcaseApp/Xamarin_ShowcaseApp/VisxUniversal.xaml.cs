using System;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace Xamarin_ShowcaseApp
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class VisxUniversal : ContentPage
    {
        private const string ANDROID_CALLBACK_CHANNEL_UNIVERSAL = "native.android.callback.channel.universal";

        //private const string IOS_CALLBACK_CHANNEL_UNIVERSAL = "native.ios.callback.channel.universal";
        public VisxUniversal()
        {
            InitializeComponent();
        }

        protected override async void OnAppearing()
        {
            base.OnAppearing();
            
            if (Device.RuntimePlatform == Device.Android)
            {
                DependencyService.Get<IAndroidNativeVisxlInit>().AnchorViewMaxHeight((int)scrollView.Height);
                InitAndroidUniversalAd();
            }
            else if (Device.RuntimePlatform == Device.iOS)
            {
                InitIosUniversalAd();
            }
        }

        private void InitAndroidUniversalAd()
        {
            MessagingCenter.Subscribe<object, string>(this, ANDROID_CALLBACK_CHANNEL_UNIVERSAL, (sender, height) =>
            {
                Device.BeginInvokeOnMainThread(() =>
                {
                    Console.WriteLine("Xamarin.App.Layer.Universal ---> Android Native callback with data: " + height);

                    if (int.TryParse(height, out int adContainerHeight))
                    {
                        VisxUniversalAdContainer.HeightRequest = adContainerHeight;
                    }
                });
            });
        }

        private void InitIosUniversalAd()
        {
            //TODO
        }

        protected override void OnDisappearing()
        {
            base.OnDisappearing();

            if (Device.RuntimePlatform == Device.Android)
            {
                MessagingCenter.Unsubscribe<object>(this, ANDROID_CALLBACK_CHANNEL_UNIVERSAL);
            }
            else if (Device.RuntimePlatform == Device.iOS)
            {
                //TODO
            }
        }

        private const string ANDROID_CALLBACK_CHANNEL_UNIVERSAL_Y = "native.android.callback.channel.universal.y";
        void Handle_Scrolled(object sender, ScrolledEventArgs e)
        {

            var adContainerY = -(e.ScrollY - VisxUniversalAdContainer.Y);

            Console.WriteLine("Xamarin.App.Layer.Universal ---> Scroll data: "
                + " | scrollView.height: " + scrollView.Height
                + " | scrollY: " + e.ScrollY
                + " | scrollView.ContentSize.Height: " + scrollView.ContentSize.Height
                + " | y: " + adContainerY);

            MessagingCenter.Send<object, double>(this, ANDROID_CALLBACK_CHANNEL_UNIVERSAL_Y, adContainerY);
        }
    }
}
