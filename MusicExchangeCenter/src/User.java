import java.util.ArrayList;

public class User {
  private String     userName;
  private boolean    online;
  private ArrayList<Song> songList = new ArrayList<>();
  private int numSongs;
  
  public User()  { this(""); }
  
  public User(String u)  {
    userName = u;
    online = false;
  }

public void register(MusicExchangeCenter m){m.registerUser(this);}
public void logon(){online=true;}
public void logoff(){online=false;}
public String getUserName() { return userName; }
public ArrayList<Song> getSongList() {return songList;}
public boolean isOnline() { return online; }

public Song songWithTitle(String title){
    Song select;
    for(int x=0;x<songList.size();x++){
        String sngTitle = songList.get(x).getTitle();
        select = songList.get(x);
          if(sngTitle.equals(title)){
              return select;
          }
          else{return null;}
      }
      return null;
}




public void downloadSong(MusicExchangeCenter m, String title, String ownerName){

      Song downloading = m.getSong(title,ownerName);

      if(downloading!=null){
          Song dsong = new Song(downloading.getTitle(),downloading.getArtist(),downloading.getMinutes(),downloading.getSeconds());
          addSong(dsong);
      }

}


public ArrayList<String> requestSonglistByArtist(MusicExchangeCenter m, String artist){
    ArrayList<String> artistList = new ArrayList<>();

    for(int x=0;x<m.allAvailableSongs().size();x++){
      if(artist.equals(m.allAvailableSongs().get(x).getArtist())){
        String dtitle = m.allAvailableSongs().get(x).getTitle();
        String dartist = m.allAvailableSongs().get(x).getArtist();
        String dtime = m.allAvailableSongs().get(x).getMinutes() + ":"+ m.allAvailableSongs().get(x).getSeconds();
        User downer = m.allAvailableSongs().get(x).getOwner();



        artistList.add(0,String.format("%-30s", dtitle) + String.format("%-20s",dartist) + String.format("%-10s", dtime) + String.format("%-10s", downer));
      }
    }

  artistList.add(0,"------------------------------------------------------------------------------------------");
  artistList.add(0, String.format("%-30s", "TITLE") + String.format("%-20s", "ARTIST") + String.format("%-10s", "TIME") + String.format("%-10s", "OWNER"));


    return artistList;
}


public ArrayList<String> requestCompleteSonglist(MusicExchangeCenter m){
    ArrayList<Song> list = m.allAvailableSongs();
    ArrayList<String> listNames = new ArrayList<>(10);

    for(int x=0;x<list.size();x++){
      listNames.add(x,String.format("%-30s", list.get(x).getTitle()) + String.format("%-20s",list.get(x).getArtist()) + String.format("%-10s", list.get(x).getMinutes()+":"+list.get(x).getSeconds()) + String.format("%-10s", list.get(x).getOwner()));

    }

  listNames.add(0,"------------------------------------------------------------------------------------------");
  listNames.add(0, String.format("%-30s", "TITLE") + String.format("%-20s", "ARTIST") + String.format("%-10s", "TIME") + String.format("%-10s", "OWNER"));

    return listNames;
}

  public void addSong(Song s){
      s.setOwner(this);
      songList.add(0,s);
  }

  public int totalSongTime(){
    int duration=0;
    for(int x=0;x<songList.size();x++){
      duration = duration + songList.get(x).getDuration();
    }
    return duration;
  }

  
  public String toString()  {
    String s = "" + userName + ": "+songList.size()+" songs (";
    if (!online) s += "not ";
    return s + "online)";
  }
}
