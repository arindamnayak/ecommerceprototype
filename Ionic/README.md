1) Follow the link for setting up ionic.
http://ionicframework.com/docs/guide/installation.html

2) Check out the code from the repository.

3) The next thing we want to do is add the Apache Cordova camera plugin. 
This can be done by running the following

	cordova plugin add org.apache.cordova.camera
	
Add AngularJS extension set for Apache Cordova called ngCordova.  Start by downloading the latest release of ngCordova and placing the ng-cordova.min.js file in your project’s www/js directory.

4) Run the following (to avoid white screen on apps) 

	ionic plugin add https://github.com/apache/cordova-plugin-whitelist.git

5) Replace the following files in the android platform from the repository.

6) Replace the  Application ID and Client key in all the places.

7) Now you can build the app and run it on the android device by running

	ionic run android

8) To run the app on the browser

	ionic serve

Note:  Parse has been used as BAAS for this project.
