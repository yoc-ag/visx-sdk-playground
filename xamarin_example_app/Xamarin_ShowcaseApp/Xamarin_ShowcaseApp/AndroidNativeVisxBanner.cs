using Xamarin.Forms;

namespace Xamarin_ShowcaseApp
{
    /// <summary>
    /// This class represents ViewHolder for Android Native View 
    /// for displaying Vis.X Inline Banner/Video Ad
    /// </summary>
    public class AndroidNativeVisxBanner : ContentView
    {
        public AndroidNativeVisxBanner()
        {
            SetPlaceholderContent();
        }

        private void SetPlaceholderContent()
        {
            Content = new Grid();
        }
    }
}
