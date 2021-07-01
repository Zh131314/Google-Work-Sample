package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video implements Comparable<Video>{

  private final String title;
  private final String videoId;
  private final List<String> tags;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  @java.lang.Override
  public int compareTo(Video o) {
    return this.title.compareTo(o.title);

  }

  public java.lang.String toString(){
    java.lang.String information =  getTitle() + " (" + getVideoId() + ") [";
    List video_tags = getTags();
    for(int j=0;j<video_tags.size();j++){
      information += video_tags.get(j);
      if(j+1 != video_tags.size()){
        information += " ";
      }
    }
    information+="]";
    return information;
  }
}
