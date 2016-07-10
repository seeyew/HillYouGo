# HillYouGo
Exercise App that uses NYTimes APIs

### Supported features
1. Cache - All articles are due to expire in 30 seconds.
2. Load from Disk during Startup
3. Force refresh and clear
4. Navigation Drawer takes you to the fragment corresponding to the item.
5. Photos are downloaded and cached
6. Maybe API >=16 (not tested). Decided to go with 16 as it covers 95% of phones today.

### Installation and Run
1. Download/Clone the repo code
2. Set the ANDROID_HOME environment variable to your Android SDK folder or create a local.properties at the top level that sets sdk.dir to your Android SDK folder.
3. run ./gradle installDebug

### High Level thoughts
Can't really build anything too robust in a few hours. I had to make quick decisions on which libraries to use, which features to support and etc. The architecture is definitely influenced from that first 15 minutes of thinking...In fact, starting from a template project with Dagger and RxAndroid took too much time to setup. I had to start over.

#### Networking
1. I am using Retrofit with OkHTTP3 for the simplicity. Read-only rest calls can be easily setup.
1. Jackson is used for JSON serialization and de-serialization.
1. I am not currently handling any errors as I wanted to focus on the "Happy Path". Someone of retries, and auto
   download should be implemented at some point.

#### Caching
1. The simple cache that I build can be improved or replaced. It doesn't cover all cases. I could have gone the SQLite route but decided against it as our need is simple.
  (But SQLite would have been useful when I needed one article)
1. The reactive pattern could have been better thought out and the observables could be setup cleaner..
1. Also, I think it's a such a common use case that I am refactoring this code to make it a library

#### Bitmap
1. I am  Using FB Fresco for download. It comes with default caching mechanism of memory, encoded memory, disk and network requests. It can be configured but I ran out of time.
    1. Fresco persist bitmaps into ashmen memory region to avoid out of memory issue.
    1. I also like this library as we can download a smaller photo and then download the bigger one.
    1. Supports Shares feature pretty easily
1. Some questions persist: 
    1. How long should we keep the photos? Should we use Http headers (etag and expires?)
    1. What's the desired size for in-memory and disk cache?
1. Due to time constraint, I didn't have time to ensure that cache is cleared and setup the way I want it to be.

#### Activity and Fragment
1. Keeping this to minimal - two activities (one for main screen and the other for detail) 
1. Problems: Fragments especially with Master Detail navigation
    1. Passing info in string and int in order to retrieve data. Probably should have one key instead
    1. The Detail screen will be empty if the activity is destroyed
    1. Master-Detail screen doesn't work in tablet
    1. We could cache information loaded via an unattached fragment. 
1. Lifecycle is problematic as I didn't completely make sure subscription are unregistered.
1. We should also consider how to handle the case in which referesh is on-going but the user is in Detail View.
    
#### Layout, Style and Themes
1. Layout is minimally done to show the data. 
1. Style is currently incosistent, would have prefer to complete a Theme based on Material Design. 
1. ImageView - this is a big one. It took a while for me to scale and crop the image. I decided not to pursue it further as that could be a product decision. 
    1. Also, we should add code to get the most appropriate photo for the given screen size.
    

#### Code structure
1. If I had enough time, I would refactor some of the code into different classes (especially DataHelper)
1. I can use more generics in the classes but I was tyring ot get things to work.
1. Some re-organization is necessary. Currently, the code is structued to sub-packages based on functions.

#### Tests
1. No tests written currently. Oops.

#### Other Things that can be improved
1. Support for Multiple screen sizes and master-detail navigation in tablets.
2. Make sure we only have one copy for menu text and path used for network calls.
3. Disable logging.
3. Getting Thumbnails.
4. No spinner when data is being loaded.
5. No Text to inform users that there are no data and if we run into network errors.
6. Singletons and context in Android. Sigh.
