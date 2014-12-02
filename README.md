#shareawarelibrary
=================

##Library for integrating shareware functionality.

ShareAware is a mobile software library supported on Android and iOS devices. Its allows the app developers to implement capabilities in their app to exchange content between users. The library first allows users to establish trust before exchanging any content.

It allows to exchange artifacts, tokens with each other when application users are connected and in proximity of one another. Connection between two such user or players is secured via simple means of authentication pin exchanges (like bluetooth). This is useful for applications/games which can peer with another user or player and allow each other to interact as long as the two (user/player) are connected with each other. 

The concept of sharing content has been around in many devices such as Nintendo DS. But, in mobile apps, it's still not available. Many commercial use cases such as lending in-app content for a short period are possible with this application. A complete commerce eco-system can also be built by developers within their app to increase referral and monetization opportunities.

This library is available in [Android] and [iOS]. Usage of each is explained below.

Contact developer for any further details on new and on-going improvements and development in progress and help if needed to integrate or commercialize this feature.

##Sample App:
============
The sample is a means to explain the core and basic functionality of this library. Think of it as an application or game which has a few resources (artifacts) that can be shared or gifted to a partner or player or fellow-user. The resource or the artifact could be

	- Document that can be shared with due tokens and viewed or edited.
	- Functionality of an application which otherwise is available only to one user or player as opposed to other.
	- This functionality or access or privilege can be obtained depending on the intended use of the application or game itself. It could come after reaching a certain level or loyalty or user having had to pay for this.

The sample app has a list of buttons which light up when the user has paid for this. So, the sample app can work as the 'Paid' version as well as the 'Free' version. You can view the differences when chosing this radio button or the SegmentedControl (as the case may be). 

Sample App - Free          | Sample App - Paid
:-------------------------:|:-------------------------:
![Sample App - Free](https://raw.github.com/ryanmaxwell/iArrived/master/iOS/screen-shots/SampleApp-Free.png)  |  ![Sample App - Paid](https://raw.github.com/ryanmaxwell/iArrived/master/iOS/screen-shots/SampleApp-Paid.png)

In the sample app, the 'lit up' (enabled) buttons simply show a dialog if/when they're enabled (if not - they're greyed out). A button is 'lit up' (or enabled) either by default in the Paid version (because the user has paid for it or earned it) or it was requested for by the Free version and allowed by the Donor or Benefactor (the Paid version). 

Sample App - Paid          | Sample App - Paid
:-------------------------:|:-------------------------:
![Sample App - Paid](https://raw.github.com/ryanmaxwell/iArrived/master/iOS/screen-shots/SampleApp-Ex1.png)  |  ![Sample App - Paid](https://raw.github.com/ryanmaxwell/iArrived/master/iOS/screen-shots/SampleApp-Ex2.png)

The sample also demonstrates the use of the share functionality by a Menu or Button to find peers in the same WIFI network. Upon finding appropriate peers - the two user or players can connect with each other using simple PIN based authentication mechanism. 

Share UI - Free          | Share UI - Paid
:-------------------------:|:-------------------------:
![Share UI - Free](https://raw.github.com/ryanmaxwell/iArrived/master/iOS/screen-shots/ShareUI-Free.png)  |  ![Share UI - Paid](https://raw.github.com/ryanmaxwell/iArrived/master/iOS/screen-shots/ShareUI-Paid.png)

Once the two user or players are connected with each other. The benefactor (the Paid version or the one having these functionalities) can share or allow (or deny) requests coming from the other user.

The library uses UI functionality hence is packaged and bundled (and some needs for integration) are elaborated below.	

##Android:
=========
The android library is built as a library which would need to be referred to by the application intending to use this functionality. All resources and core functions neccessary for the library to function are encapsulated in the android-library code. As seen from the git bundle - there're two projects - 

	- android-library: This is the core library containing the functions, classes and resources neccessary for the library to work.
	- android-sampleapp: This is the sample app demonstrating its use
	

###Instructions to Build and Run Android Sample app:
===================================================

1) git clone https://github.com/ShareAware/shareawarelibrary.git

2) eclipse - 4.1 w/ ADT

3) Import Library

4) Remember to import ActionBarSherlock and re-do the Library settings to point to current project in Eclipse

5) Import SampleApp

6) Build and Run on two devices

7) Check out MainActivity and how it is launching the Library - follow similar steps in your own app.

8) Need to have two devices/apps working on the same WIFI network.

9) SampleApp has two versions

9.1) Paid

9.2) Free

10) Same/sample app can be run as Paid and Free versions on two diff devices.

11) Share->Start on both devices

12) Connect on finding each other - use any char/num as PINs to connect/authenticate with each other.

13) Request/Revoke functionality of Paid versions to the Free version

14) Observe the functionality (for now abstracted as simple buttons) being shared by Paid version of the app w/ The Free version

###Instructions to Integrate with Android library:
=================================================

launchLibrary - can be called from a Menu/button as needed.
```
	/**
	* Method to launch the Share library
	**/
	void launchLibrary() {
    	Handler uh = new Handler(MainActivity.this.getMainLooper()) {
    	@Override
    		public void handleMessage(Message msg) {
    		if (msg != null) {
    			String name 		= msg.getData().getString("name");
    			String permission 	= msg.getData().getString("permission");
	            	String sharedWith 	= msg.getData().getString("sharedWith");
	            	updatePermission(name, permission, sharedWith);
	            	Log.i(TAG, "Message from Library: " + name + ", " + permission + ", " + sharedWith);
            	}
            }
        };

		ShareWareLibrary swl = new ShareWareLibrary(uh);
		swl.setIsFree(MainActivity.this.isFree);
		swl.setShareWareArtifact(MainActivity.this.artifactsArray);
		swl.launchUI(MainActivity.this);	
    }
    
     
   /**
     * UpdatePermission - callback from the library handler
     * @param name - Name of artifact
     * @param p    - Permission
     * @param s    - Shared with
     */

    void updatePermission(String n, String p, String s) {

    	Log.i(TAG, "updatePermission: " + n + ", " + p + ", " + s);
    	int ii = 0;
    	ShareWareArtifact tSWA = null;
    	for (ShareWareArtifact swa : this.artifactsArray) {
    		if (swa.name.equals(n)) {
    			swa.permission = p;
    			swa.sharedWith = s;
    			tSWA = swa;
    			break;
    		}
			ii++;
    	}
    	if (tSWA != null) {
    		artifactsArray[ii] = tSWA;
    	}
    	ii = 0; 
		for (Button button : featureArray) {
			button.setEnabled(artifactsArray[ii].permission.equals("true"));
			ii++;
		}
    }
```

##iOS:
=========

iOS library is built as a Static framework library. Since the library uses UI - some steps are necessary to import/add these resources over - as explained below.

###Instructions to Build and Run iOS Sample app:
===================================================

1. git clone https://github.com/ShareAware/shareawarelibrary.git
2. XCode - 6.1
3. Sample App- #import "ShareWareLibrary.h"
4. Target - SampleApp
5. Build Phases
6. Link Binary w/ Libs
7. +, Add Other
8. Pick the folder where you downloaded the iOS libraries containing the resources (storyboard), libraries and .h files.
9. Copy Bundle Resources (storyboard and .h) - pick all under include/ShareWareLibrary
10. Build and Run on two devices
11. Check out Main ViewController and how it is launching the Library - follow similar steps in your own app.
12. Need to have two devices/apps working on the same WIFI network.
13. SampleApp has two versions Paid, Free
14. Same/sample app can be run as Paid and Free versions on two diff devices.
15. Share->Start on both devices
16. Connect on finding each other - use any char/num as PINs to connect/authenticate with each other.
17. Request/Revoke functionality of Paid versions to the Free version
18. Observe the functionality (for now abstracted as simple buttons) being shared by Paid version of the app w/ The Free version


###Instructions to Integrate with iOS library:
=================================================

'''
// This will launch the Library
- (IBAction)handleShareButtonItem:(id)sender {
    _shareWareLibrary = [[ShareWareLibrary alloc] init];
    _shareWareLibrary.isFree = ([self.isFreeOrPaidSegControl selectedSegmentIndex] == 0);
    _shareWareLibrary.sDelegate = self;
    
    _shareWareLibrary.artifactsArray = _artifactsArray;
    
    [_shareWareLibrary launchUI:self];
}

#pragma mark - ShareWareLibraryDelegate delegate
// This is an implementation of the Delegate which is called when permissions are involved.
- (void) updatePermission : (NSString *) key : (NSString *) value : (NSString *) sharedWith {
    // Don't mess unless its free
    if ([self.isFreeOrPaidSegControl selectedSegmentIndex] == 0) {
        NSLog(@"updatePermission: %@, %@", key, value);
        int idx = -1;
        int ii = 0;
        ShareWareArtifact *tSwa = nil;
        for (ShareWareArtifact *swa in _artifactsArray) {
            if ([swa.name isEqualToString:key]) {
                swa.permission = value;
                swa.sharedWith = sharedWith;
                idx = ii;
                tSwa = swa;
                break;
            }
            ii++;
        }
        if (idx > 0) {
            [_artifactsArray replaceObjectAtIndex:idx withObject:tSwa];
            // [self.artifactsTableView reloadData];
        }
        [self viewWillAppear:TRUE];
    }
}

'''

[Android] : https://github.com/ShareAware/shareawarelibrary#android

[iOS] : https://github.com/ShareAware/shareawarelibrary#ios

