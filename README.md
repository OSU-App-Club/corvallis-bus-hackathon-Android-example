corvallis-bus-hackathon-Android-example
=======================================

Android example for pulling down Corvallis Bus data in json, breaking it up, and displaying it.

This example is an Android Studio project, if you have eclipse you can create a project and use the src to recreate this project (Don't forget to allow internet permissions in your manifest!).

The essential files to this project are:

   -MainActivity.java

   -RetrieveJson.java
   
   -activity_main.xml
   
   -bus_item.xml
   
   -options.xml
   
The java files include the main visuals and the backend json component. The xml files are utilized in the MainActivity to provide a way to display our retrieved data.

This is only an example, so feel free to customize this to fit your needs.
The method named "retrieveAndShowJson" in the 'MainActivity.java' file is the piece where I request and receive my json. 
There are some hardcoded things I have added to suite my particular needs for this demo, so do feel free to adjust this to fit your needs.

In reference to this allow me to clarify what the parameters to this method are for (allow me to apologize for some of the names, they're not exactly spot on).

retrieveAndShowJson(String url, final Button tgtButton, String[] jsonSearchList, final String requestType, final String[] additionalParams);

   url = Input url where we will fetch data from (ex. "http://www.corvallis-bus.appspot.com/routes")
   
   tgtButton = This is specific to this example. This allows me to have a handle to the button (of the 2 here) so as to change the text when it's working and when it's done. Feel free to remove this.
   
   jsonSearchList = This is a string array indicating the elements you wish to retrieve. An example would be passing "new String[]{"Name","Description"};". 
   What you will get back is a string array where each element is just the Name & Description! This is particularly useful if you're getting back "Name,Description,ExtraName,ExtraDescription,MoreInfo,StuffWeDontCareAbout", and you only want just the 2!
   Feel free to modify the code to, say, return a HashMap or an array of doubles[]. As long as it suites your needs.
   
   requestType = This is a particular var that is important as to splitting the data you are receiving. If you send the url "http://www.corvallis-bus.appspot.com/routes" your json will start with 'routes', so you will need to start spliting by that name.
   This is not particularly convenient, considering requests for certain stops will require passing the individual stop numbers here as well. 
   You can see more about what the first object you should expect is here: https://github.com/OSU-App-Club/corvallis-bus-server.
   
   additionalParams = If you would like to ONLY receive data that contains a specific String, you may pass an array with that String and any others you wish to include.
   You will ONLY receive data that has at least one component that matches that string word for word. This is convenient for getting a particular stop by Address, for example "new String[]{"NW Harrison Blvd"}".
   
   
The following parameters are useful to trimming any data that you are pulling, however they are no substitute for GET!
Using this is far more powerful and effective, and you can see examples of how to utilize this in the url I provided previously, here again: https://github.com/OSU-App-Club/corvallis-bus-server.

If you have any questions about the functionality or any issues getting this working feel free to get a hold of me at the hackathon on Saturday!
   
   
   
   
   
   
   
   
