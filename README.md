# Route Planning and Navigation with Google API

AccountAbility is a concept for a social media mobile application where users are dedicated to fact-checking news claims/sources and general media. The first tab of the app allows users to sort media by different categories, the second tab allows the user to read different news articles and comment on them, and the third tab shows a user's profile which allows other users to comment on the user's method of claim evaluation.

## Getting Started

You will need the [Eclipse IDE](https://www.eclipse.org/), and the [Java 8](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) runtime environment. It is very important that you do not use anything greater than Java 8 as this project uses [Unfolding Maps](http://unfoldingmaps.org/), which has certain Java 8 dependencies which are deprecated in later versions.

You may also choose to get a [Google API Key](https://developers.google.com/maps/documentation/javascript/get-api-key), but it is not necessary. If you don't have one, the map interface will have the text "Developer Mode" plastered over it. 

### Setup

To import the project into Eclipse, first create a new Java Project in the Eclipse workspace. Then perform the import using File -> Import -> Select "File System" -> Next -> Browse and set root directory to folder contents -> Finish

Feel free to use another IDE or manually compile and run your programs.

### Generate your own map files

You can use the front end to generate raw map data files. Then you can use the GraphLoader utility class to convert these files into an intersections file, which is suitable for use in the graph visualization tool.

1. Navigate the map to the section you would like to collect data for. The application will fetch all of the road data in the visible part of the map. **Note**: Make sure this region is not too big or the file will be gigantic and it will take forever to download. The data for a single small to medium city is about as large as we recommend.

2. Enter a name for your map data file in the text box in the bottom left corner of the window. This name must end with the extension .map and it will be automatically saved into the data/maps folder.

3. Click the "Fetch data" button. You will see a dialog box informing you that the fetching is occurring in the background. The "Fetch data" button  is disabled as long as the data is still being fetched. **Note**: this process can take several minutes.

4. When the fetch completes, another dialog box will appear. Your data file is now in the data/maps folder. You probably need to right-click on it and select "Refresh" in Eclipse to see it.

4a. If you want your new map file to appear in the list of files available in the app when you restart it, you need to add it to the file mapfiles.list. You can find this file in the data/maps folder. Just open that file and type the name of the map file you just created then save that file. Your new map will appear in the list when you open the Map app in the future.

5. In the main method of the util.GraphLoader class (in the file util/GraphLoader.java just above the class header for the RoadLineInfo class, which is declared in the same file), you will see the following code:

```
public static void main(String[] args) {
    //...
    // To use this method to convert your custom map files to custom 
    //intersections files just change YOURFILE in the Strings below 
    //to be the name of the file you saved.
    GraphLoader.createIntesectionsFile("data/maps/YOURFILE.map",
                                       "data/intersections/YOURFILE.intersections");
}
```

Change YOURFILE to be the name of the file you just saved from the front end and then run this class. You will see your .intersections file appear in the data/intersections directory. Again, from Eclipse you will need to right-click on the data directory and select Refresh.


## Deployment

Once you've download this project, you will need to install all of the packages included in the package.json file. To do this, cd into the project directory and run 

```
npm install
```

To deploy this application on your localhost, cd into the project directory and run

```
ionic serve
```

## Built With

* [Ionic](https://ionicframework.com/docs) - The web framework used
* [Angular](https://angular.io/docs) - The framework with which Ionic is built upon
* [News API](https://newsapi.org/docs) - Used to generate news articles
* [Random User API](https://randomuser.me) - Used to generate random users for show


