package cqx.priv.serv;

/**
 * @author Qingxin Chen
 * @date 2020/12/26 - 23:31
 **/
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放声音的线程
public class PlaySound extends Thread {
    private String filename;

    public PlaySound(String wavfile) {

        filename = "res/" + wavfile;
    }
    //播放声音
    public void run() {
        File soundFile = new File(filename);
        AudioInputStream audioInputStream = null;
        //打开文件
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        auline.start();
        int nBytesRead = 0;
        //文件缓冲
        byte[] abData = new byte[512];
        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            //结束后关闭文件
            auline.close();
        }
    }
}