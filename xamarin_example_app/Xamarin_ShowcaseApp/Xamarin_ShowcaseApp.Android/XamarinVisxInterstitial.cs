using Com.Yoc.Visx.Sdk.Adview;
using Xamarin.Forms.Platform.Android;

namespace Xamarin_ShowcaseApp.Droid
{
    class XamarinVisxInterstitial : FormsAppCompatActivity
    {
        private const string YOC_INTERSTITIAL_ID = "912263";
        private const string APP_DOMAIN = "yoc.com";

        public XamarinVisxInterstitial()
        {
            InitVisxInterstitial();
        }

        /// <summary>
        /// Initializing and displaying Vis.X Mystery (Interstitial) Ad
        /// </summary>
        private void InitVisxInterstitial()
        {
            /// This is short implementation without callbacks
            /// If Callbacks are needed, please use regular implementation
            new VisxAd(MainActivity.Instance).DisplayInterstitial(YOC_INTERSTITIAL_ID, APP_DOMAIN);
        }
    }
}