using Xamarin.Forms;

namespace Xamarin_ShowcaseApp
{
    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);
        }
        void Banner_Clicked(object sender, System.EventArgs e)
        {
            Navigation.PushAsync(new VisxBanner());
        }

        void Interstitial_Clicked(object sender, System.EventArgs e)
        {
            Navigation.PushAsync(new VisxInterstitial());
        }

        void Universal_Clicked(object sender, System.EventArgs e)
        {
            Navigation.PushAsync(new VisxUniversal());
        }
    }
}