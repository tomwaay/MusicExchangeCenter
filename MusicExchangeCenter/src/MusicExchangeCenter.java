

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

public class MusicExchangeCenter {
    private ArrayList<User> users = new ArrayList<>();
    private HashMap<String, Double> royalties = new HashMap<String, Double>();
    private ArrayList<Song> downloadedSongs = new ArrayList<>();




    public MusicExchangeCenter(){
    }

    public User userWithName(String s){
        for(int x=0;x<users.size();x++){

            if(s.toUpperCase().equals(users.get(x).getUserName().toUpperCase())){
                return users.get(x);
            }

        }


        return null;
    }

    public ArrayList<Song> getDownloadedSongs(){return downloadedSongs;}

    public void registerUser(User x){
        if(userWithName(x.getUserName())==null){
            users.add(x);
        }
    }


    public void displayRoyalties(){
        System.out.println(String.format("%-10s","Artist")+String.format("%-10s","Amount"));
        System.out.println("------------------");

        for(HashMap.Entry<String, Double> m: royalties.entrySet()){
            System.out.println(String.format("%-10s",m.getValue())+String.format("%-10s",m.getKey()));
        }

    }


    public ArrayList<Song> availableSongsByArtist(String artist){
        ArrayList<Song> songsArtist = new ArrayList<>();

        for(int x=0;x<allAvailableSongs().size();x++){

            if(allAvailableSongs().get(x).getArtist().toUpperCase()==artist.toUpperCase()){
                songsArtist.add(allAvailableSongs().get(x));
            }

        }
        return songsArtist;

    }



    public ArrayList<User> onlineUsers(){
        ArrayList<User> usersOnline = new ArrayList<>();

        for(int x=0; x<users.size();x++){
            if(users.get(x).isOnline()==true){
                usersOnline.add(users.get(x));
            }
        }
        return usersOnline;
    }

    public ArrayList<Song> allAvailableSongs(){
        ArrayList<Song> availSongs = new ArrayList<>();

        for(int i=0; i<onlineUsers().size();i++){
            if(onlineUsers().get(i) != null){
                for(int x=0;x<onlineUsers().get(i).getSongList().size();x++){
                    availSongs.add(onlineUsers().get(i).getSongList().get(x));
                }
            }
        }

        return availSongs;
    }

    public String toString(){
        return "Music Exchange Center ("+onlineUsers().size()+" users on line, "+allAvailableSongs().size()+" songs available)";
    }


    public TreeSet<Song> uniqueDownloads(){
        TreeSet<Song> uDowns = new TreeSet<>();
        for(Song song: downloadedSongs){
            if(uDowns.contains(song)){
                continue;
            }
            else{
                uDowns.add(song);
            }
        }
        return uDowns;
    }



    public ArrayList<Pair<Integer, Song>> songsByPopularity(){
        int i =0;
        ArrayList<Pair<Integer, Song>> popSongs = new ArrayList<Pair<Integer, Song>>();
        for(Song one : uniqueDownloads()){
            for(Song two : downloadedSongs){
                if(one.getTitle().equals(two.getTitle())){
                    i = i + 1;
                }
            }
            popSongs.add(new Pair<>(i, one));
            i = 0;
        }
        Collections.sort(popSongs,(p1,p2) -> p2.getKey().compareTo(p1.getKey()));
        return popSongs;
    }






    public Song getSong(String title, String ownerName){

        for(int x=0;x<onlineUsers().size();x++){
            if(ownerName.equals(onlineUsers().get(x).getUserName())){

                for(int i =0;i<onlineUsers().get(x).getSongList().size();i++){
                    if(title.equals(onlineUsers().get(x).getSongList().get(i).getTitle())){
                        onlineUsers().get(x).getSongList().get(i).addDownload();
                        downloadedSongs.add(0,onlineUsers().get(x).getSongList().get(i));



                        if(!royalties.containsKey(onlineUsers().get(x).getSongList().get(i).getArtist())){
                            royalties.put(onlineUsers().get(x).getSongList().get(i).getArtist(),.25);
                        }
                        else if(royalties.containsKey(onlineUsers().get(x).getSongList().get(i).getArtist())){
                            royalties.put(onlineUsers().get(x).getSongList().get(i).getArtist(),royalties.get(onlineUsers().get(x).getSongList().get(i).getArtist())+.25);
                        }


                        return onlineUsers().get(x).getSongList().get(i);
                    }
                }

            }


        }


        return null;
    }






}
