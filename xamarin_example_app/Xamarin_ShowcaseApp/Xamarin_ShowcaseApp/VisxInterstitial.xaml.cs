
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace Xamarin_ShowcaseApp
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class VisxInterstitial : ContentPage
    {
        public VisxInterstitial()
        {
            InitializeComponent();
            InitInterstitial();
        }

        /// <summary>
        /// Initialize and display platform specific Interstitial Ad
        /// </summary>
        private void InitInterstitial()
        {
            if (Device.RuntimePlatform == Device.Android)
            {
                DependencyService.Get<IAndroidNativeVisxlInit>().InterstitialCall();
            }
            else if (Device.RuntimePlatform == Device.iOS)
            {
                //TODO
            }
        }
    }
}