using Xamarin.Forms;

namespace Xamarin_ShowcaseApp
{
    /// <summary>
    /// This class represents ViewHolder for Android Native View 
    /// for displaying Vis.X Universal Ad
    /// </summary>
    public class AndroidNativeVisxUniversal : ContentView
    {
        public AndroidNativeVisxUniversal()
        {
            SetPlaceholderContent();
        }

        private void SetPlaceholderContent()
        {
            Content = new Grid();
        }
    }
}
