// 参照https://nompor.com/2017/12/14/post-128/

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
import javax.swing.text.PlainDocument;

class SoundEffect {
    private int CLIP_MAX = 1; // 効果音のトラック数
    private String _name[] = {"select01.wav", "se_itemget_004.wav", "se_shot_001.wav", "se_itemget_001.wav", "se_powerdown_006.wav",
                                "se_etc_006.wav", "game_explosion5.wav"}; // 効果音の名前一覧
    public String[] getName() { return _name; } // _nameのゲッター

    private int TitleCursorMoveNumber = 0; // タイトル画面カーソル移動音のトラック番号

    // タイトル画面カーソル移動音を流す
    public void PlayTitleCursorMove() { 
        try {
            Play(_name[TitleCursorMoveNumber], TitleCursorMoveVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    private int TitleCursorDetermineNumber = 1; // タイトル画面決定音のトラック番号
    // タイトル画面決定音を流す
    public void PlayTitleCursorDetermine() {
        try {
            Play(_name[TitleCursorDetermineNumber], TitleCursorDetermineVolume);
        } catch (Exception e) {
            // do nothing
        }
    }


    private int FighterShotNumber = 2; // 自機のショット音のトラック番号
    // 自機のショット音を流す
    public void PlayFighterShot() {
        try {
            Play(_name[FighterShotNumber], FighterShotVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    private int ItemGetNumber = 3; // アイテムゲット音のトラック番号
    // アイテム取得音を流す
    public void PlayItemGet() {
        try {
            Play(_name[ItemGetNumber], ItemGetVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    private int PowerDownNumber = 4; // 残機減少音
    public void PlayPowerDown() { 
        try {
            Play(_name[PowerDownNumber], PowerDownVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    private int ShotHitEnemyNumber = 5; // 弾が敵に当たる音
    // 弾が敵にあたる音を流す
    public void PlayShotHitEnemy() {
        try {
            Play(_name[ShotHitEnemyNumber], ShotHitEnemyVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    private int BombNumber = 6; // ボムの音
    public void PlayBomb() {
        try {
            Play(_name[BombNumber], BombVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    // ----------------音量-----------------//
    private float TitleCursorMoveVolume = 0.4f; // タイトル画面カーソルを動かす音の音量
    public void setTitleCursorMoveVolume(float vol) { TitleCursorMoveVolume = vol;}
    public float getTitleCursorMoveVolume() { return TitleCursorMoveVolume;}
    private float TitleCursorDetermineVolume = 0.7f; // タイトル画面の決定音の音量
    public void setTitleCursorDetermineVolume(float vol) { TitleCursorDetermineVolume = vol;}
    public float getTitleCursorDetermineVolume() { return TitleCursorDetermineVolume;}
    private float FighterShotVolume = 1.0f; // 自機のショット音の音量
    public void setFighterShotVolume(float vol) { FighterShotVolume = vol;}
    public float getFighterShotVolume() {return FighterShotVolume;}
    private float ItemGetVolume = 1.0f; // アイテムのゲット音の音量
    public void setItemGetVolume(float vol) { ItemGetVolume = vol;}
    public float getItemGetVolume() { return ItemGetVolume;}
    private float PowerDownVolume = 1.0f; // パワーダウン音の音量
    public void setPowerDownVolume(float vol) { PowerDownVolume = vol;}
    public float getPowerDownVolume() { return PowerDownVolume;}
    private float ShotHitEnemyVolume = 1.0f; // 敵に自機のショットが当たる音の音量
    public void setShotHitEnemyVolume(float vol) { ShotHitEnemyVolume = vol;}
    public float getShotHitEnemyVolume() {return ShotHitEnemyVolume;}
    private float BombVolume = 1.0f; // ボム音の音量
    public void setBombVolume(float vol) { BombVolume = vol;}
    public float getBombVolume() { return BombVolume;}


    // コンストラクタ
    public SoundEffect() {}

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
    public void Play(String pass, float vol) throws Exception {
        Clip c = createClip(new File(pass));
        c.start();
        Volume(c, vol);
    }

    // 音量設定
    public void Volume(Clip c, float vol) {
        FloatControl ctrl = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
        ctrl.setValue((float) Math.log10(vol) * 20);
    }

    // 停止
    public void Finish(Clip clip) {
        // 一時停止
        clip.stop();
        // 音声再生の内部バッファを削除
        clip.flush();
        // 再生位置を最初に戻す
        clip.setFramePosition(0);
    }
}