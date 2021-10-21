using Xamarin.Forms;

namespace Xamarin_ShowcaseApp
{
    class SplashScreen : ContentPage
    {
        readonly Image splashImage;

        public SplashScreen()
        {
            NavigationPage.SetHasNavigationBar(this, false);
            var sub = new AbsoluteLayout();
            splashImage = new Image
            {
                Source = "yoc_logo.png",
                WidthRequest = 100,
                HeightRequest = 100,

            };
            AbsoluteLayout.SetLayoutFlags(splashImage,
            AbsoluteLayoutFlags.PositionProportional);
            AbsoluteLayout.SetLayoutBounds(splashImage,
             new Rectangle(0.5, 0.5, AbsoluteLayout.AutoSize, AbsoluteLayout.AutoSize));

            sub.Children.Add(splashImage);

            this.BackgroundColor = Color.FromHex("#FFFFFF");
            this.Content = sub;
        }

        protected override async void OnAppearing()
        {
            base.OnAppearing();

            await splashImage.ScaleTo(1, 2000); //Time-consuming processes such as initialization
            Application.Current.MainPage = new NavigationPage(new MainPage());    //After loading  MainPage it gets Navigated to new Page
        }
    }
}
