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
[assembly: ExportRenderer(typeof(Xamarin_ShowcaseApp.AndroidNativeVisxUniversal), typeof(XamarinVisxUniversalRenderer))]

namespace Xamarin_ShowcaseApp.Droid
{
    public class XamarinVisxUniversalRenderer : ViewRenderer, IActionTracker
    {
        private const string ANDROID_CALLBACK_CHANNEL_UNIVERSAL = "native.android.callback.channel.universal";
        private const string ANDROID_CALLBACK_CHANNEL_UNIVERSAL_Y = "native.android.callback.channel.universal.y";
        private const string YOC_UNIVERSAL_ID = "912268";
        private const string APP_DOMAIN = "yoc.com";
        private VisxAdManager _visxAdManagerUniversal;
        private readonly Context _context;

        /// <summary>
        /// Root View hierarchy of the Android Native View
        /// </summary>
        private Android.Widget.RelativeLayout _root;
        private Android.Views.View _adContainer;

        public XamarinVisxUniversalRenderer(Context context) : base(context)
        {
            this._context = context;
            InitVisxUniversal();
        }

        /// <summary>
        /// Initializing Vis.X Inline Universal with Ad unit ID and size 
        /// </summary>
        private void InitVisxUniversal()
        {
            _visxAdManagerUniversal = new VisxAdManager.Builder()
                .VisxAdUnitID(YOC_UNIVERSAL_ID)
                .AdSize(adSize: AdSize.UNDERSTITIAL300x600)
                .AppDomain(APP_DOMAIN)
                .Context(MainActivity.Instance)
                .Callback(this)
                .Build();

            HandleUnderstitial();
        }

        /// <summary>
        /// Receiving y position from the Xamarin adContainer view element
        /// </summary>
        private void HandleUnderstitial()
        {
            MessagingCenter.Subscribe<object, double>(this, ANDROID_CALLBACK_CHANNEL_UNIVERSAL_Y, (sender, height) =>
            {
                Device.BeginInvokeOnMainThread(() =>
                {
                    /// Send y position to the Vis.X SDK for applying Understitial effect
                    Console.WriteLine("Xamarin.App.Layer.Universal ---> Y: " + height);
                    _visxAdManagerUniversal.SetViewValues(height);
                });
            });
        }

        /// <summary>
        /// Ad callback functions
        /// </summary>
        public void OnAdClicked()
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnAdClicked()");
        }

        public void OnAdLeftApplication()
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnAdLeftApplication()");
        }

        public void OnAdLoadingFailed(VisxAdManager visxAdManager, string message, bool isFinal)
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnAdLoadingFailed(): " + message + " isFinal: " + isFinal);
            MessagingCenter.Send<object, string>(this, ANDROID_CALLBACK_CHANNEL_UNIVERSAL, "0");
        }

        public void OnAdLoadingFinished(VisxAdManager visxAdManager, string message)
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnAdLoadingFinished(): " + message);
            DisplayAd(visxAdManager);
        }

        public void OnAdLoadingStarted(VisxAdManager visxAdManager)
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnAdLoadingStarted()");
        }

        public void OnAdRequestStarted(VisxAdManager visxAdManager)
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnAdRequestStarted()");

            /// Setting maxSizeHeight for the visible parent view where Vis.X Ad is located
            /// Needed for setting the ad frame how much can expand in height on screen
            _visxAdManagerUniversal.SetMaxSizeHeight(MainActivity.MaxSizeHeight);
        }

        public void OnAdResponseReceived(VisxAdManager visxAdManager, string message)
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnAdResponseReceived(): " + message);
            DisplayAd(visxAdManager);
        }

        public void OnAdSizeChanged(int width, int height)
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnAdSizeChanged() w: " + width + "| h: " + height);
            MessagingCenter.Send<object, string>(this, ANDROID_CALLBACK_CHANNEL_UNIVERSAL, height.ToString());
        }

        private void DisplayAd(VisxAdManager visxAdManager)
        {
            /// Adding Vis.X Universal Ad to the created Android 
            /// native View and invoced on the main Xamarin thread
            Android.Widget.RelativeLayout container = new Android.Widget.RelativeLayout(_context);

            MainThread.BeginInvokeOnMainThread(() =>
            {
                container = _adContainer.FindViewById<Android.Widget.RelativeLayout>(Resource.Id.inlineContainer);

                if (visxAdManager.AdContainer.Parent != null)
                {
                    visxAdManager.AdContainer.RemoveFromParent();
                }

                container.AddView(visxAdManager.AdContainer);
            });
        }

        /// <summary>
        /// Creaeting Android Native View Root
        /// </summary>
        /// <param name="e"></param>
        protected override void OnElementChanged(ElementChangedEventArgs<Xamarin.Forms.View> e)
        {
            Console.WriteLine("Xamarin.Android.Layer.Universal ---> OnElementChanged(): " + e);
            base.OnElementChanged(e);

            _visxAdManagerUniversal.SetViewValues(100);

            _root = new Android.Widget.RelativeLayout(Context);
            var inflater = (LayoutInflater)Context.GetSystemService(Context.LayoutInflaterService);
            _adContainer = inflater.Inflate(Resource.Layout.universal, _root);

            SetNativeControl(_adContainer);
        }
    }
}