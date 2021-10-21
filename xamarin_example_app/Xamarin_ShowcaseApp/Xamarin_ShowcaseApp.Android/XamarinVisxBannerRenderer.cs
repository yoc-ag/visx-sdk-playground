using Android.Content;
using Android.Views;
using Com.Yoc.Visx.Sdk;
using Com.Yoc.Visx.Sdk.Util;
using Com.Yoc.Visx.Sdk.View.Category;
using System;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;
using Xamarin_ShowcaseApp.Droid;

/// This line is for establishing connection bridge with the shared Xamarin code
[assembly: ExportRenderer(typeof(Xamarin_ShowcaseApp.AndroidNativeVisxBanner), typeof(XamarinVisxBannerRenderer))]
namespace Xamarin_ShowcaseApp.Droid
{
    public class XamarinVisxBannerRenderer : ViewRenderer, IActionTracker
    {
        private const string ANDROID_CALLBACK_CHANNEL_BANNER = "native.android.callback.channel.banner";
        private const string YOC_BANNER_ID = "912261";
        private const string APP_DOMAIN = "yoc.com";
        private readonly Context _context;
        public XamarinVisxBannerRenderer(Context context) : base(context)
        {
            this._context = context;
            InitVisxBanner();
        }

        /// <summary>
        /// Initializing Vis.X Inline Banner/Video with Ad unit ID and size 
        /// </summary>
        private void InitVisxBanner()
        {
            new VisxAdManager.Builder()
                .VisxAdUnitID(YOC_BANNER_ID)
                .AdSize(adSize: AdSize.MEDIUMRECTANGLE300x250)
                .AppDomain(APP_DOMAIN)
                .Context(MainActivity.Instance)
                .Callback(this)
                .IsFixedSize(true)
                .Build();
        }

        /// <summary>
        /// Ad callback functions
        /// </summary>
        public void OnAdClicked()
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnAdClicked()");
        }

        public void OnAdLeftApplication()
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnAdLeftApplication()");
        }

        public void OnAdLoadingFailed(VisxAdManager visxAdManager, string message, bool isFinal)
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnAdLoadingFailed(): " + message + " isFinal: " + isFinal);
        }

        public void OnAdLoadingFinished(VisxAdManager visxAdManager, string message)
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnAdLoadingFinished(): " + message);
            DisplayAd(visxAdManager);
        }

        public void OnAdLoadingStarted(VisxAdManager visxAdManager)
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnAdLoadingStarted()");
        }

        public void OnAdRequestStarted(VisxAdManager visxAdManager)
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnAdRequestStarted()");
        }

        public void OnAdResponseReceived(VisxAdManager visxAdManager, string message)
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnAdResponseReceived(): " + message);
        }

        public void OnAdSizeChanged(int width, int height)
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnAdSizeChanged() w: " + width + "| h: " + height);
            
            /// Send values for adContainer to shared Xamarin App Code
            MessagingCenter.Send<object, string>(this, ANDROID_CALLBACK_CHANNEL_BANNER, height.ToString());
        }

        private void DisplayAd(VisxAdManager visxAdManager)
        {

            /// Adding Vis.X Banner/Video Ad to the created Android 
            /// native View and invoced on the main Xamarin thread
            Android.Widget.RelativeLayout container = new Android.Widget.RelativeLayout(_context);
            MainThread.BeginInvokeOnMainThread(() =>
            {
                container = adContainer.FindViewById<Android.Widget.RelativeLayout>(Resource.Id.inlineContainer);
                container.AddView(visxAdManager.AdContainer);
            });
        }

        /// <summary>
        /// Root View hierarchy of the Android Native View
        /// </summary>
        Android.Widget.RelativeLayout root;
        Android.Views.View adContainer;

        /// <summary>
        /// Creaeting Android Native View Root
        /// </summary>
        /// <param name="e"></param>
        protected override void OnElementChanged(ElementChangedEventArgs<Xamarin.Forms.View> e)
        {
            Console.WriteLine("Xamarin.Android.Layer.Banner ---> OnElementChanged(): " + e);
            base.OnElementChanged(e);

            root = new Android.Widget.RelativeLayout(Context);
            var inflater = (LayoutInflater)Context.GetSystemService(Context.LayoutInflaterService);
            adContainer = inflater.Inflate(Resource.Layout.banner, root);

            SetNativeControl(adContainer);
        }
    }
}