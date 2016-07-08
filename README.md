# HillYouGo
Exercise App that uses NYTimes APIs

### Installation and Run
1. Download/Clone the repo code
2. Set the ANDROID_HOME environment variable to your Android SDK folder or create a local.properties at the top level that sets sdk.dir to your Android SDK folder.
3. run ./gradle installDebug

### High Level thoughts
Can't really build anything too robust in a few hours. I had to make quick decisions on which libraries to use, which feature
to support and etc. The architecture is definitely influenced from that first 15 minutes of thinking...In fact, starting
from a template project with Dagger and RxAndroid took too much time to setup. I had to start over.

### Supported features
1. Cache - All articles are due to expire in 30 seconds.
2. Load from Disk during Startup
3. Force refresh and clear
4. Navigation Drawer takes you to the fragment corresponding to the item.
5. Photos are downloaded and cached
6. Maybe API >=16 (not tested). Decided to go with 16 as it covers 95% of phones today.


### Things that can be improved
1. Used FB Fresco for download. It comes with default caching mechanism of memory, encoded memory, disk and network requests. It can be configured but I ran out of time.
 1. I also like this library as we can download a smaller photo and then download the bigger one.
 2. Supports Shares feature pretty easily
2. Fragments especially with Master Detail navigation
 1. Passing info in string and int in order to retrieve data. Probably should have one key instead
 2. The Detail screen will the activity is destroyed and you restart the app
3. Photos - this is a big one. It took a while for me to scale and crop the image. I decided not to pursue it further as that could be a product decision. 
 1. Also, we should add code to get the most appropriate photo for the given screen size.
1. The simple cache that I build can be improved or replaced. It doesn't cover all cases. I could have gone the SQLite route but decided against it as our need is simple.
  (But SQLite would have been useful when I needed one article)
  

### Unsupported
1. Multiple screen sizes 
2. Master-Detail in Tablets

