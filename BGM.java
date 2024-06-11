// 参照https://nompor.com/2017/12/14/post-128/

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class BGM {
    private Clip clip;
    public Clip getClip() { return clip; } // clipのゲッター
    private int CLIP_MAX = 1; // BGMのトラック数
    private String _name[] = {"TitleBGM.wav", "MainBGM.wav", "bossbgm.wav", "BossClear.wav", "GameOver.wav", "stageselectBGM.wav"}; // BGMの名前一覧
    public String[] getName() { return _name; }

    private int TitleBGMNumber = 0; // タイトルBGMのトラック番号
    public int getTitleBGMNumber() { return TitleBGMNumber; } // TitleBGMNumberのゲッタ―

    private int MainBGMNumber = 1; // メインゲームBGMのトラック番号
    public int getMainBGMNumber() { return MainBGMNumber; } // MainBGMNumberのゲッタ―

    private int BossBGMNumber = 2; // ボスBGMのトラック番号
    public int getBossBGMNumber() { return BossBGMNumber; }

    private int BossClearNumber = 3; // ボスのクリア音楽のトラック番号
    public int getBossClearNumber() {return BossClearNumber;}

    private int GameOverNumber = 4; // ゲームオーバー音楽のトラック番号
    public int getGameOverNumber() {return GameOverNumber;}

    private int StageSelectNumber = 5; // ステージセレクト画面の音楽のトラック番号
    public int getStageSelectNumber() {return StageSelectNumber;}


    // ----------------- 音量 --------------------//
    private double TitleBGMVolume = 1.0; // タイトルBGMの音量
    public void setTitleBGMVolume(double vol) { TitleBGMVolume = vol;}
    public double getTitleBGMVolume() { return TitleBGMVolume;}
    private double MainGameBGMVolume = 0.8; // メインゲームBGMの音量
    public void setMainGameBGMVolume(double vol) { MainGameBGMVolume = vol;}
    public double getMainGameBGMVolume() { return MainGameBGMVolume;}
    private double BossBGMVolume = 0.5; // ボスBGMの音量
    public void setBossBGMVolume(double vol) { BossBGMVolume = vol;}
    public double getBossBGMVolume() {return BossBGMVolume;}
    private double BossClearVolume = 0.5; // ボスのクリア音楽の音量
    public void setBossClearVolume(double vol) {BossClearVolume = vol;}
    public double getBossClearVolume() {return BossClearVolume;}
    private double GameOverVolume = 0.7; // ゲームオーバー音楽の音量
    public void setGameOverVolume(double gov) {GameOverVolume = gov;}
    public double getGameOverVolume() {return GameOverVolume;}
    private double StageSelectVolume = 0.7; // ステージセレクト画面の音楽の音量
    public void setStageSelectVolume(double ssv) {StageSelectVolume = ssv;}
    public double getStageSelectVolume() {return StageSelectVolume;}

    //-------------------フェードアウト---------------//
    private int time;
    public void setTime(int t) { time = t;} 
    public int getTime() {return time;}
    private int FadeFinishTime;
    public void setFadeFinishTime(int fft) { FadeFinishTime = fft;}
    public int getFadeFinishTime() { return FadeFinishTime; }
    private int FadeSpan = 0;
    public void setFadeSpan(int fs) { FadeSpan = fs;}
    public int getFadeSpan() { return FadeSpan;}

    // タイトルBGMをループ再生する
    public void PlayLoopTitleBGM() {
        try {
			Loop(getName()[getTitleBGMNumber()], TitleBGMVolume);
		} catch (Exception e){
			// do nothing
		}
    }

    // メインゲームBGMをループ再生する
    public void PlayLoopMainGameBGM() {
        try {
            Loop(getName()[getMainBGMNumber()], MainGameBGMVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    // ボスのBGMをループ再生する
    public void PlayLoopBossBGM() {
        try {
            Loop(getName()[getBossBGMNumber()], BossBGMVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    // ボスのクリア音楽を再生する
    public void PlayBossClear() {
        try {
            Play(getName()[getBossClearNumber()], BossClearVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    public void PlayGameOver() {
        try {
            Play(getName()[getGameOverNumber()], GameOverVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    public void PlayLoopStageSelect() {
        try {
            Loop(getName()[getStageSelectNumber()], StageSelectVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    // コンストラクタ
    public BGM() {}

    // 指定されたURLのオーディオ入力ストリームを取得
    public static Clip createClip(File path) {
        // 入力ストリームを取得
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)) {

            // ファイルの形式取得
            AudioFormat af = ais.getFormat();

            // 単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
            DataLine.Info dataLine = new DataLine.Info(Clip.class,af);

            // 指定されたLine.Infoオブジェクトの記述に一致するラインを取得
            Clip c = (Clip)AudioSystem.getLine(dataLine);

            // 再生準備完了
            c.open(ais);

            return c;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 再生
    public void Play(String pass, double vol) throws Exception {
        if(this.clip != null) Finish(clip);
        this.clip = createClip(new File(pass));
        Volume(vol);
        this.clip.start();
        Thread.sleep(3000);
    }

    // ループ再生
    public void Loop(String pass, double vol) throws Exception {
        if(this.clip != null) Finish(clip);
        this.clip = createClip(new File(pass));
        Volume(vol);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // 音量設定
    public void Volume(double vol) {
        FloatControl ctrl = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        ctrl.setValue((float) Math.log10(vol) * 20f);
    }

    // フェードアウト
    public void FadeOut(int time, int finishtime, double currentvol) {
        if(getFadeSpan() == 0) {setFadeSpan(finishtime-time);}
        setTime(time); setFadeFinishTime(finishtime);
        Volume(currentvol * ((double)(getFadeFinishTime()-getTime()) / (double)getFadeSpan()));
    }

    // 停止
    public void Finish(Clip clip) {
        // 一時停止
        clip.stop();
        // 音声再生の内部バッファを削除
        clip.flush();
        // 再生位置を最初に戻す
        clip.setFramePosition(0);
        System.out.println("BGM stop");//
    }
}
