namespace Xamarin_ShowcaseApp
{
    /// <summary>
    /// Custom interface for establishing communication bridge for 
    /// Android Platform calls (Init interstitial, set anchorView height,...)
    /// </summary>
    public interface IAndroidNativeVisxlInit

    {
        /// <summary>
        /// Init and display Vis.X Android Mystery (Interstitial) Ad
        /// </summary>
        void InterstitialCall();

        /// <summary>
        /// Setting up anchorView (visible enviroment, parent, where the ad 
        /// should be displayed) max height 
        /// </summary>
        /// <param name="height"></param>
        void AnchorViewMaxHeight(int height);
    }
}