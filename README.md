# Route Planning and Navigation with Google API

This project was apart of UCSD's online Java course designed to apply object oriented programming oncepts, become familar with the Google Maps API, implement famous graph algorithms, and compare their run times. In this project, I learned to implement the Breadth First Search(BFS) Algorithm, Dijkstra's algorithm, and the A* algorithm. I have learned that, for the specific problem of finding a route, out of all three algorithms, A* is the most efficient (on average finding the route using only half the nodes that BFS and Dijkstra uses). 

This is interesting because BFS' time complexity is usually O(|V|+|E|) where V is number of vertices in the graph and E is the number of edges, and Dijkstra's and A*'s time complexities are on average O((|V|+|E|)\**log(|V|)*). However, BFS' time complexity is usually for elementary graphs, whereas, in the case of route finding, the problem becomes much larger as the number of possible vertices and edges increase based on the size of the map area. 

In this problem, it is much more beneficial to use a *heuristic*, which is a function that ranks alternative routes at each step based on some form of available information. In our context, the "information" is the distance between a vertex and the destination point. With this slight change, the algorithm will not visit vertices which are further away from the destination point. In Dijkstra's Algorithm, this heuristic term is equal to 0 which is why Dijkstra's performs similarly to BFS. However in the A* algorithm, the heuristic term is not zero, therefore, using a [Priority Queue](https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html) and coupling my MapVertex class with a [Comparable Interface](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html), I was able to reduce the run time by prioritizing which vertices to visit over others based on the distance from the previous vertex to the current vertex, and the distance from the current vertex to the destination point. 

## Getting Started

You will need the [Eclipse IDE](https://www.eclipse.org/), and the [Java 8](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) runtime environment. It is very important that you do not use anything greater than Java 8 as this project uses [Unfolding Maps](http://unfoldingmaps.org/), which has certain Java 8 dependencies which are deprecated in later versions.

You may also choose to get a [Google API Key](https://developers.google.com/maps/documentation/javascript/get-api-key), but it is not necessary. If you don't have one, the map interface will have the text "Developer Mode" plastered over it, but will still be functional. 

### Setup

To import the project into Eclipse, first create a new Java Project in the Eclipse workspace. Then perform the import using File -> Import -> Select "File System" -> Next -> Browse and set root directory to folder contents -> Finish

Feel free to use another IDE or manually compile and run your programs.

### Generate your own map files

You can use the front end to generate raw map data files. Then you can use the GraphLoader utility class to convert these files into an intersections file, which is suitable for use in the graph visualization tool.

1. Navigate the map to the section you would like to collect data for. The application will fetch all of the road data in the visible part of the map. **Note**: Make sure this region is not too big or the file will be gigantic and it will take forever to download. The data for a single small to medium city is about as large as we recommend.

2. Enter a name for your map data file in the text box in the bottom left corner of the window. This name must end with the extension .map and it will be automatically saved into the data/maps folder.

3. Click the "Fetch data" button. You will see a dialog box informing you that the fetching is occurring in the background. The "Fetch data" button  is disabled as long as the data is still being fetched. **Note**: this process can take several minutes.

4. When the fetch completes, another dialog box will appear. Your data file is now in the data/maps folder. You probably need to right-click on it and select "Refresh" in Eclipse to see it.

5. If you want your new map file to appear in the list of files available in the app when you restart it, you need to add it to the file mapfiles.list. You can find this file in the data/maps folder. Just open that file and type the name of the map file you just created then save that file. Your new map will appear in the list when you open the Map app in the future.

6. In the main method of the util.GraphLoader class (in the file util/GraphLoader.java just above the class header for the RoadLineInfo class, which is declared in the same file), you will see the following code:

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

To deploy the application locally, navigate to src -> application -> MapApp.java and press run. The application should pop up as a new window. You can then load your map file under the **Choose map file** section, and hit **Show Intersections**.

Then select a start and destination point and pick any of the three algorithms to plan your route, and hit **Show Route**. Then hit **Start Visualization** and a pop up window should appear telling you how many nodes it took to find that route. When you exit out of the popup, you will see how the algorithm calculates the route.

## Built With

* [Java 8](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) - Programming language used
* [Google Maps API](https://cloud.google.com/maps-platform/) - The geo-location framework used
* [Unfolding Maps](http://unfoldingmaps.org/) - The library used to create interactive maps

## Acknowledgements

I would like to give credit to the UCSD MOOC team for creating the course, the instructions, and the UI and engine for the application.

