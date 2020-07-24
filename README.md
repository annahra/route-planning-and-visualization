# Route Planning and Navigation with Google API

AccountAbility is a concept for a social media mobile application where users are dedicated to fact-checking news claims/sources and general media. The first tab of the app allows users to sort media by different categories, the second tab allows the user to read different news articles and comment on them, and the third tab shows a user's profile which allows other users to comment on the user's method of claim evaluation.

## Getting Started

You will need the [Eclipse IDE](https://www.eclipse.org/), and the [Java 8](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) runtime environment. It is very important that you do not use anything greater than Java 8 as this project uses [Unfolding Maps](http://unfoldingmaps.org/), which has certain Java 8 dependencies which are deprecated in later versions.

You may also choose to get a [Google API Key](https://developers.google.com/maps/documentation/javascript/get-api-key), but it is not necessary. If you don't have one, the map interface will have the text "Developer Mode" plastered over it. 

### Setup

Importing the project into Eclipse: 
	1. Create a new Java Project in your workspace
	2. Import the starter files:
	  File -> Import -> Select "File System" -> Next -> Browse and set 
	  root directory to folder contents -> Finish

Feel free to use another IDE or manually compile and run your programs.

### Prerequisites

To install Node.js and npm, follow this [link](https://www.npmjs.com/get-npm). Verify you've correctly installed both Node.js and npm by running the following commands:

```
node --version
npm --version
```

To install the [Angular CLI](https://angular.io/guide/setup-local) and to verify that the installation was successful, run the following :

```
npm install -g @angular/cli
ng --version
```

To install the [Ionic CLI](https://ionicframework.com/docs/intro/cli) and to verify that the installation was successful, run the following:

```
npm -g install -g @ionic/cli
ionic --version
```

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


