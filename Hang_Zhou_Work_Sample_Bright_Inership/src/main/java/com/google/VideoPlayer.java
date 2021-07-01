package com.google;


import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.lang.Math;
import java.util.Iterator;
import java.util.Scanner;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private String stoppingVideoID;
  private String playingVideoID;
  private String pausingVideoID;
  private Map<String,String> flaggedVideos;
  private Map<String,List<Video>> playLists;
  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playLists = new HashMap<>();
    this.flaggedVideos = new HashMap<>();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }
  //completed
  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> videos =  videoLibrary.getVideos();
    Collections.sort(videos);
    for(int i=0;i<videos.size();i++){
      //can be replaced with videos toString method
      System.out.print("  " + videos.get(i).getTitle() + " (" + videos.get(i).getVideoId() + ") [");
      List video_tags = videos.get(i).getTags();
      for(int j=0;j<video_tags.size();j++){
        System.out.print(video_tags.get(j));
        if(j+1 != video_tags.size()){
          System.out.print(" ");
        }
      }
      System.out.print("]");
      if(flaggedVideos.containsKey(videos.get(i).getVideoId())){
        System.out.print(" - FLAGGED (reason: "+ flaggedVideos.get(videos.get(i).getVideoId())+")");
      }
      System.out.println();
    }


  }
  //completed
  public void playVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video!=null && !flaggedVideos.containsKey(videoId)) {
      if(playingVideoID !=null){
        stoppingVideoID = playingVideoID;
        System.out.println("Stopping video: "+ videoLibrary.getVideo(stoppingVideoID).getTitle());
        //remove state of pausing whenever new video is played.
        pausingVideoID = null;
      }
      playingVideoID = videoId;
      System.out.println("Playing video: " + video.getTitle());
    }
    else if(flaggedVideos.containsKey(videoId)){
      System.out.println("Cannot play video: Video is currently flagged (reason: "+flaggedVideos.get(videoId)+")");
    }
    else{
      System.out.println("Cannot play video: Video does not exist");
    }
  }
  //completed
  public void stopVideo() {
    if(playingVideoID !=null){
      stoppingVideoID = playingVideoID;
      System.out.println("Stopping video: "+ videoLibrary.getVideo(stoppingVideoID).getTitle());
      playingVideoID = null;
      //remove state of pausing whenever old video is closed.
      pausingVideoID = null;
    }
    else{
      System.out.println("Cannot stop video: No video is currently playing");

    }
  }
  //completed
  public void playRandomVideo() {
    List<Video> videos =  videoLibrary.getVideos();
    //if all videos in list is flagged, no video is available
    if(videos.size() == 0 || flaggedVideos.size() == videos.size()){
      System.out.println("No videos available");
    }
    else {
      int i = (int) (videos.size() * Math.random());
      while(flaggedVideos.containsKey(videos.get(i).getVideoId())){
        i = (int) (videos.size() * Math.random());
      }
      playVideo(videos.get(i).getVideoId());
    }


  }
  //completed
  public void pauseVideo() {
    if(playingVideoID != null) {
      //if paused once in the same video
      if(pausingVideoID != null) {
        if (pausingVideoID.equals(playingVideoID)) {
          System.out.println("Video already paused: " + videoLibrary.getVideo(pausingVideoID).getTitle());
        }
      }
      //if current video is not paused before, old video is closed, or new video has played
      else {
        pausingVideoID = playingVideoID;
        System.out.println("Pausing video: " + videoLibrary.getVideo(pausingVideoID).getTitle());
      }
    }else{
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }
  //completed
  public void continueVideo() {
    if(playingVideoID !=null) {
      if(pausingVideoID != null){
        System.out.println("Continuing video: "+ videoLibrary.getVideo(pausingVideoID).getTitle());
        pausingVideoID = null;
      }else{
        System.out.println("Cannot continue video: Video is not paused");
      }
    }else{
      System.out.println("Cannot continue video: No video is currently playing");
    }
  }
  //completed
  public void showPlaying() {
    if(playingVideoID !=null) {
      Video video = videoLibrary.getVideo(playingVideoID);
      System.out.print("Currently playing: " + video.getTitle() + " (" + video.getVideoId() + ") [");
      List video_tags = video.getTags();
      for(int j=0;j<video_tags.size();j++){
        System.out.print(video_tags.get(j));
        if(j+1 != video_tags.size()){
          System.out.print(" ");
        }
      }
      System.out.print("]");
      if(pausingVideoID != null){
        System.out.print(" - PAUSED");
      }

      System.out.println("");
    }else{
      System.out.println("No video is currently playing");
    }
  }
  //completed
  public void createPlaylist(String playlistName) {
    boolean nameExist = false;
    for(String name: this.playLists.keySet()){
      if(playlistName.toLowerCase().equals(name.toLowerCase())){
        System.out.println("Cannot create playlist: A playlist with the same name already exists");
        nameExist = true;
        break;
      }
    }
    if(!nameExist){
      this.playLists.put(playlistName,new LinkedList<Video>());
      System.out.println("Successfully created new playlist: "+playlistName);
    }

  }
  //completed
  public void addVideoToPlaylist(String playlistName, String videoId) {
    List playlist = null;
    for(String name: this.playLists.keySet()){
      if(playlistName.toLowerCase().equals(name.toLowerCase())){
        playlist = playLists.get(name);
        break;
      }
    }
    if(playlist == null && videoLibrary.getVideo(videoId) == null){
      System.out.println("Cannot add video to "+ playlistName+": Playlist does not exist");
    }
    else if(videoLibrary.getVideo(videoId) == null){
      System.out.println("Cannot add video to "+ playlistName+": Video does not exist");
    }
    else if(flaggedVideos.containsKey(videoId)){
      System.out.println("Cannot add video to "+ playlistName+
              ": Video is currently flagged (reason: "+flaggedVideos.get(videoId)+")");
    }
    else if(playlist == null){
      System.out.println("Cannot add video to "+ playlistName+": Playlist does not exist");
    }

    else{
        boolean inList = false;
        Iterator<Video> it = playlist.iterator();
        while(it.hasNext()){
          if(videoId.equals(it.next().getVideoId())){
            inList = true;
            break;
          }
        }
        if(inList && flaggedVideos.containsKey(videoId)){
          System.out.println("Cannot play video: Video is currently flagged (reason: "+flaggedVideos.get(videoId)+")");
        }else if(inList){
          System.out.println("Cannot add video to "+playlistName+": Video already added");
        }else{
          playlist.add(videoLibrary.getVideo(videoId));
          System.out.println("Added video to "+ playlistName+": " + videoLibrary.getVideo(videoId).getTitle());
        }
    }
  }
  //completed
  public void showAllPlaylists() {
    if(playLists.isEmpty()){
      System.out.println("No playlists exist yet");
    }
    else{
      System.out.println("Showing all playlists:");
      List<String> names = new LinkedList<>(playLists.keySet());
      Collections.sort(names);
      for(String name:names){
        System.out.println(name);
      }
    }
  }
  //completed
  public void showPlaylist(String playlistName) {
    List<Video> playlist = null;
    for(String name: this.playLists.keySet()){
      if(playlistName.toLowerCase().equals(name.toLowerCase())){
        playlist = playLists.get(name);
        break;
      }
    }
    if(playlist != null){
      System.out.println("Showing playlist: "+ playlistName);
      if(playlist.isEmpty()){
        System.out.println("  No videos here yet");
      }
      else{
        for(Video video: playlist){
          System.out.print("  " + video.getTitle() + " (" + video.getVideoId() + ") [");
          List video_tags = video.getTags();
          for(int j=0;j<video_tags.size();j++){
            System.out.print(video_tags.get(j));
            if(j+1 != video_tags.size()){
              System.out.print(" ");
            }
          }
          System.out.print("]");
          if(flaggedVideos.containsKey(video.getVideoId())){
            System.out.print(" - FLAGGED (reason: "+ flaggedVideos.get(video.getVideoId())+")");
          }
          System.out.println();
        }
      }
    }
    else{
      System.out.println("Cannot show playlist "+ playlistName + ": Playlist does not exist");
    }

  }
  //completed
  public void removeFromPlaylist(String playlistName, String videoId) {
    List playlist = null;
    for(String name: this.playLists.keySet()){
      if(playlistName.toLowerCase().equals(name.toLowerCase())){
        playlist = playLists.get(name);
        break;
      }
    }
    if(playlist == null && videoLibrary.getVideo(videoId) == null){
      System.out.println("Cannot remove video from "+ playlistName+": Playlist does not exist");
    }
    else if(videoLibrary.getVideo(videoId) == null){
      System.out.println("Cannot remove video from "+ playlistName+": Video does not exist");
    }
    else if(playlist == null){
      System.out.println("Cannot remove video from "+ playlistName+": Playlist does not exist");
    }
    else{
      boolean inList = false;
      Iterator<Video> it = playlist.iterator();
      while(it.hasNext()){
        if(videoId.equals(it.next().getVideoId())){
          inList = true;
          it.remove();
          System.out.println("Removed video from "+ playlistName+": " + videoLibrary.getVideo(videoId).getTitle());
          break;
        }
      }
      if(!inList){
        System.out.println("Cannot remove video from "+playlistName+": Video is not in playlist");
      }
    }
  }
  //completed
  public void clearPlaylist(String playlistName) {
    List playlist = null;
    for(String name: this.playLists.keySet()){
      if(playlistName.toLowerCase().equals(name.toLowerCase())){
        playlist = playLists.get(name);
        playlist.clear();
        System.out.println("Successfully removed all videos from "+ playlistName);
        break;
      }
    }
    if(playlist == null){
      System.out.println("Cannot clear playlist "+playlistName+": Playlist does not exist");
    }
  }
  //completed
  public void deletePlaylist(String playlistName) {
    boolean hasList = false;
    Iterator<Map.Entry<String,List<Video>>> it  = playLists.entrySet().iterator();
    while(it.hasNext()){
      if(it.next().getKey().toLowerCase().equals(playlistName.toLowerCase())){
        hasList = true;
        it.remove();
        System.out.println("Deleted playlist: "+ playlistName);
      }
    }
    if(!hasList){
      System.out.println("Cannot delete playlist "+playlistName+": Playlist does not exist");
    }
  }

  //completed
  public void searchVideos(String searchTerm) {
    //Addition and deletion operation will be faster in linked list
    List<Video> searchList = new LinkedList<Video>();
    Scanner scanner = new Scanner(System.in);
    for(Video video: videoLibrary.getVideos()){
      if(video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())
      && !flaggedVideos.containsKey(video.getVideoId())){
        searchList.add(video);
      }
    }
    if(!searchList.isEmpty()) {
      Collections.sort(searchList);
      Iterator<Video> it = searchList.iterator();
      int n = 1;
      System.out.println("Here are the results for "+searchTerm+":");
      while(it.hasNext()){
        System.out.println("  "+ n +") "+it.next().toString());
        n++;
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");

      String input = scanner.nextLine();
      try {
        int chosenNumber = Integer.parseInt(input)-1;
        if ( chosenNumber < searchList.size() && chosenNumber> -1) {
          playVideo(searchList.get(chosenNumber).getVideoId());
        }
      }catch (NumberFormatException e){
        //do nothing if input is outbound or has invalid input format
      }
    }
    else{
      System.out.println("No search results for "+ searchTerm);
    }

  }
  //completed
  public void searchVideosWithTag(String videoTag) {
    //Though it is kind of "informal", putting the check at the front can save search time complexity
    if(!videoTag.contains("#")){
      System.out.println("No search results for "+videoTag);
    }
    else {
      List<Video> searchList = new LinkedList<Video>();
      Scanner scanner = new Scanner(System.in);
      for (Video video : videoLibrary.getVideos()) {
        for(String tag : video.getTags()){
          if (tag.toLowerCase().contains(videoTag.toLowerCase())
          && !flaggedVideos.containsKey(video.getVideoId())) {
            searchList.add(video);
            break;
          }
        }
      }
      if (!searchList.isEmpty()) {
        Collections.sort(searchList);
        Iterator<Video> it = searchList.iterator();
        int n = 1;
        System.out.println("Here are the results for " + videoTag + ":");
        while (it.hasNext()) {
          System.out.println("  " + n + ") " + it.next().toString());
          n++;
        }
        System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
        System.out.println("If your answer is not a valid number, we will assume it's a no.");

        String input = scanner.nextLine();
        try {
          int chosenNumber = Integer.parseInt(input) - 1;
          if (chosenNumber < searchList.size() && chosenNumber>-1) {
            playVideo(searchList.get(chosenNumber).getVideoId());
          }
        } catch (NumberFormatException e) {
          //do nothing if input is outbound or has invalid input format
        }
      } else {
        System.out.println("No search results for " + videoTag);
      }
    }
  }
  //completed
  public void flagVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot flag video: Video does not exist");
    }
    else {
      if (!flaggedVideos.containsKey(videoId)) {
        if(pausingVideoID!=null){
          if(pausingVideoID.equals(videoId)){
            stopVideo();
          }
        }
        else if(playingVideoID!= null) {
          if (playingVideoID.equals(videoId)) {
            stopVideo();
          }
        }
        flaggedVideos.put(videoId,"Not supplied");
        System.out.println("Successfully flagged video: " + video.getTitle() + " (reason: Not supplied)");
      } else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
    }
  }
  //completed
  public void flagVideo(String videoId, String reason) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot flag video: Video does not exist");
    }
    else {
      if (!flaggedVideos.containsKey(videoId)) {
        if(pausingVideoID!=null){
          if(pausingVideoID.equals(videoId)){
            stopVideo();
          }
        }
        else if(playingVideoID!= null) {
          if (playingVideoID.equals(videoId)) {
            stopVideo();
          }
        }
        flaggedVideos.put(videoId,reason);
        System.out.println("Successfully flagged video: " + video.getTitle() + " (reason: "+reason+")");
      } else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
    }
  }
  //completed...Finallyyyyyyyy
  public void allowVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot remove flag from video: Video does not exist");
    }else{
      if(!flaggedVideos.containsKey(videoId)){
        System.out.println("Cannot remove flag from video: Video is not flagged");
      }
      else{
        flaggedVideos.remove(videoId);
        System.out.println("Successfully removed flag from video: "+video.getTitle());
      }
    }
  }
}