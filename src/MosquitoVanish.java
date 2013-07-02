/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.midlet.*;

/**
 * @author Md Imran Hasan
 */
public class MosquitoVanish extends MIDlet implements CommandListener {

    Display display;
    List list;
    String[] files = {
        "44000.wav"//
        , "40000.wav"//
        , "30000.wav"//
        , "25000.wav"//
        , "22357.mp3"//
        , "21101.mp3"//
        , "19916.mp3"//
        , "18798.mp3"//
        , "17742.mp3"//
        , "16746.mp3"//
        , "15805.mp3"//
        , "14918.mp3"//
        , "14080.mp3"//
        , "12000.mp3"//
        , "10000.mp3"//
        , "8000.mp3"//
        , "anti.mosquito.sound.wav" //
    };
    String[] fileNames = {
        "44000 Hz"//
        , "40000 Hz"//
        , "30000 Hz"//
        , "25000 Hz"//
        , "22357 Hz"//
        , "21101 Hz"//
        , "19916 Hz"//
        , "18798 Hz"//
        , "17742 Hz"//
        , "16746 Hz"//
        , "15805 Hz"//
        , "14918 Hz"//
        , "14080 Hz"//
        , "12000 Hz"//
        , "10000 Hz"//
        , " 8000 Hz"//
        , "Extra-1" //
    };
    String[] mediaType = {
        "X-wav"//
        , "X-wav"//
        , "X-wav"//
        , "X-wav"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "mp3"//
        , "X-wav"
    };
    Player player;

    public void startApp() {
        display = Display.getDisplay(this);

        list = new List("Mosquito", List.EXCLUSIVE);
        for (int i = 0; i < files.length; i++) {
            list.append(fileNames[i], null);
        }
        list.addCommand(new Command("Play", Command.OK, 1));
        list.addCommand(new Command("Exit", Command.EXIT, 1));
        list.addCommand(new Command("Play in loop", Command.ITEM, 1));
        list.addCommand(new Command("Stop", Command.ITEM, 1));
        list.addCommand(new Command("Help", Command.ITEM, 1));
        list.setCommandListener(this);

        display.setCurrent(list);


    }

    /**
     * 
     * @param fileAddr <String> Address of the file from where to be parsed
     * @return the whole file data as a string
     * @throws IOException 
     */
    public String parseWholeFile(String fileAddr) throws IOException {
        InputStream is = getClass().getResourceAsStream(
                "/" + fileAddr);
        DataInputStream dis = new DataInputStream(is);

        StringBuffer sb = new StringBuffer();
        byte[] buffer = new byte[1024];
        for (int numRead = dis.read(buffer); numRead != -1; numRead = dis.read(buffer)) {
            String str = new String(buffer, 0, numRead);
            sb.append(str);
        }
        return sb.toString();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {

        switch (c.getCommandType()) {
            case Command.OK:
                startPlaying(false);
                break;

            case Command.ITEM:
                String label = c.getLabel();
                if (label.equals("Stop")) {
                    stopPlaying();
                } else if (label.equals("Play in loop")) {
                    startPlaying(true);
                } else if (label.equals("Help")) {
                    showMessage("This is a fun app. It doesn't ensure any repelancy. \n\nTo run select a frequency and click play/play in loop from menu. \t\n\n--\nImran Hasan Hira\t\nimranhasanhira@gmail.com");
                }
                break;

            case Command.EXIT:
                stopPlaying();
                this.notifyDestroyed();
                break;
        }

    }

    void startPlaying(boolean isLoop) {
        stopPlaying();
        try {
            int selectedIndex = list.getSelectedIndex();
            String clickedFileName = files[ selectedIndex];
            player = Manager.createPlayer(getClass().getResourceAsStream("/" + clickedFileName), "audio/" + mediaType[selectedIndex]);
            if (isLoop) {
                player.setLoopCount(1000);
            }
            player.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage(ex.getMessage());
        }
    }

    void stopPlaying() {
        if (player != null) {
            try {
                player.stop();
            } catch (MediaException ex) {
                ex.printStackTrace();
                showMessage(ex.getMessage());
            }
            player.deallocate();
            player = null;
        }
    }

    void showMessage(String message) {
//        System.out.println(message);
        display.setCurrent(new Alert("", message, null, AlertType.INFO), list);
    }
}
