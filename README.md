#ExplorerKotlin


## Application Overview

<table>
  <tr>
    <td>Overview screen</td>
     <td>Search screen</td>
      <td>Detail screen</td>
  </tr>
  <tr>
    <td><img src="/screenShots/overview.png" width=270 height=480></td>
    <td><img src="/screenShots/search.png" width=270 height=480></td>
     <td><img src="/screenShots/details.png" width=270 height=480></td>
  </tr>
 
 </table>


## Project Overview
Explorer is an android application that allows users to search through the NASA image directory. NASA has developed a rich archive of images from its few decades of space research and exploration. Users can search by a keyword or by year or by the combination of both. Users can select a search result from the list and check details. The application implements offline caching. The last search result is cached and displayed when the app gets started after a break. If there is no network connection or data not found with the provided search key, the cached result is displayed.


## App Features
- Uses retrofit to download data from NASA API; 
- Uses Glide to display images;
- Implements Navigation graph to work with a single activity and multiple fragments;
- Implements data binding, binding adapters to avoid boilerplate code;
- Uses a repository pattern to support offline caching.
- Uses Room, ViewModels, and LiveData to support offline caching; 
- Implements Recyclerview for efficient and smooth scrolling of search results; 
- Uses coroutines to run long-running tasks in a separate thread; 
- Follows material design guidelines






