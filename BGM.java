// �Q��https://nompor.com/2017/12/14/post-128/

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
    public Clip getClip() { return clip; } // clip�̃Q�b�^�[
    private int CLIP_MAX = 1; // BGM�̃g���b�N��
    private String _name[] = {"TitleBGM.wav", "MainBGM.wav", "bossbgm.wav", "BossClear.wav", "GameOver.wav", "stageselectBGM.wav"}; // BGM�̖��O�ꗗ
    public String[] getName() { return _name; }

    private int TitleBGMNumber = 0; // �^�C�g��BGM�̃g���b�N�ԍ�
    public int getTitleBGMNumber() { return TitleBGMNumber; } // TitleBGMNumber�̃Q�b�^�\

    private int MainBGMNumber = 1; // ���C���Q�[��BGM�̃g���b�N�ԍ�
    public int getMainBGMNumber() { return MainBGMNumber; } // MainBGMNumber�̃Q�b�^�\

    private int BossBGMNumber = 2; // �{�XBGM�̃g���b�N�ԍ�
    public int getBossBGMNumber() { return BossBGMNumber; }

    private int BossClearNumber = 3; // �{�X�̃N���A���y�̃g���b�N�ԍ�
    public int getBossClearNumber() {return BossClearNumber;}

    private int GameOverNumber = 4; // �Q�[���I�[�o�[���y�̃g���b�N�ԍ�
    public int getGameOverNumber() {return GameOverNumber;}

    private int StageSelectNumber = 5; // �X�e�[�W�Z���N�g��ʂ̉��y�̃g���b�N�ԍ�
    public int getStageSelectNumber() {return StageSelectNumber;}


    // ----------------- ���� --------------------//
    private double TitleBGMVolume = 1.0; // �^�C�g��BGM�̉���
    public void setTitleBGMVolume(double vol) { TitleBGMVolume = vol;}
    public double getTitleBGMVolume() { return TitleBGMVolume;}
    private double MainGameBGMVolume = 0.8; // ���C���Q�[��BGM�̉���
    public void setMainGameBGMVolume(double vol) { MainGameBGMVolume = vol;}
    public double getMainGameBGMVolume() { return MainGameBGMVolume;}
    private double BossBGMVolume = 0.5; // �{�XBGM�̉���
    public void setBossBGMVolume(double vol) { BossBGMVolume = vol;}
    public double getBossBGMVolume() {return BossBGMVolume;}
    private double BossClearVolume = 0.5; // �{�X�̃N���A���y�̉���
    public void setBossClearVolume(double vol) {BossClearVolume = vol;}
    public double getBossClearVolume() {return BossClearVolume;}
    private double GameOverVolume = 0.7; // �Q�[���I�[�o�[���y�̉���
    public void setGameOverVolume(double gov) {GameOverVolume = gov;}
    public double getGameOverVolume() {return GameOverVolume;}
    private double StageSelectVolume = 0.7; // �X�e�[�W�Z���N�g��ʂ̉��y�̉���
    public void setStageSelectVolume(double ssv) {StageSelectVolume = ssv;}
    public double getStageSelectVolume() {return StageSelectVolume;}

    //-------------------�t�F�[�h�A�E�g---------------//
    private int time;
    public void setTime(int t) { time = t;} 
    public int getTime() {return time;}
    private int FadeFinishTime;
    public void setFadeFinishTime(int fft) { FadeFinishTime = fft;}
    public int getFadeFinishTime() { return FadeFinishTime; }
    private int FadeSpan = 0;
    public void setFadeSpan(int fs) { FadeSpan = fs;}
    public int getFadeSpan() { return FadeSpan;}

    // �^�C�g��BGM�����[�v�Đ�����
    public void PlayLoopTitleBGM() {
        try {
			Loop(getName()[getTitleBGMNumber()], TitleBGMVolume);
		} catch (Exception e){
			// do nothing
		}
    }

    // ���C���Q�[��BGM�����[�v�Đ�����
    public void PlayLoopMainGameBGM() {
        try {
            Loop(getName()[getMainBGMNumber()], MainGameBGMVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    // �{�X��BGM�����[�v�Đ�����
    public void PlayLoopBossBGM() {
        try {
            Loop(getName()[getBossBGMNumber()], BossBGMVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    // �{�X�̃N���A���y���Đ�����
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

    // �R���X�g���N�^
    public BGM() {}

    // �w�肳�ꂽURL�̃I�[�f�B�I���̓X�g���[�����擾
    public static Clip createClip(File path) {
        // ���̓X�g���[�����擾
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)) {

            // �t�@�C���̌`���擾
            AudioFormat af = ais.getFormat();

            // �P��̃I�[�f�B�I�`�����܂ގw�肵����񂩂�f�[�^���C���̏��I�u�W�F�N�g���\�z
            DataLine.Info dataLine = new DataLine.Info(Clip.class,af);

            // �w�肳�ꂽLine.Info�I�u�W�F�N�g�̋L�q�Ɉ�v���郉�C�����擾
            Clip c = (Clip)AudioSystem.getLine(dataLine);

            // �Đ���������
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

    // �Đ�
    public void Play(String pass, double vol) throws Exception {
        if(this.clip != null) Finish(clip);
        this.clip = createClip(new File(pass));
        Volume(vol);
        this.clip.start();
        Thread.sleep(3000);
    }

    // ���[�v�Đ�
    public void Loop(String pass, double vol) throws Exception {
        if(this.clip != null) Finish(clip);
        this.clip = createClip(new File(pass));
        Volume(vol);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // ���ʐݒ�
    public void Volume(double vol) {
        FloatControl ctrl = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        ctrl.setValue((float) Math.log10(vol) * 20f);
    }

    // �t�F�[�h�A�E�g
    public void FadeOut(int time, int finishtime, double currentvol) {
        if(getFadeSpan() == 0) {setFadeSpan(finishtime-time);}
        setTime(time); setFadeFinishTime(finishtime);
        Volume(currentvol * ((double)(getFadeFinishTime()-getTime()) / (double)getFadeSpan()));
    }

    // ��~
    public void Finish(Clip clip) {
        // �ꎞ��~
        clip.stop();
        // �����Đ��̓����o�b�t�@���폜
        clip.flush();
        // �Đ��ʒu���ŏ��ɖ߂�
        clip.setFramePosition(0);
        System.out.println("BGM stop");//
    }
}
