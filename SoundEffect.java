// �Q��https://nompor.com/2017/12/14/post-128/

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
    private int CLIP_MAX = 1; // ���ʉ��̃g���b�N��
    private String _name[] = {"select01.wav", "se_itemget_004.wav", "se_shot_001.wav", "se_itemget_001.wav", "se_powerdown_006.wav",
                                "se_etc_006.wav", "game_explosion5.wav"}; // ���ʉ��̖��O�ꗗ
    public String[] getName() { return _name; } // _name�̃Q�b�^�[

    private int TitleCursorMoveNumber = 0; // �^�C�g����ʃJ�[�\���ړ����̃g���b�N�ԍ�

    // �^�C�g����ʃJ�[�\���ړ����𗬂�
    public void PlayTitleCursorMove() { 
        try {
            Play(_name[TitleCursorMoveNumber], TitleCursorMoveVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    private int TitleCursorDetermineNumber = 1; // �^�C�g����ʌ��艹�̃g���b�N�ԍ�
    // �^�C�g����ʌ��艹�𗬂�
    public void PlayTitleCursorDetermine() {
        try {
            Play(_name[TitleCursorDetermineNumber], TitleCursorDetermineVolume);
        } catch (Exception e) {
            // do nothing
        }
    }


    private int FighterShotNumber = 2; // ���@�̃V���b�g���̃g���b�N�ԍ�
    // ���@�̃V���b�g���𗬂�
    public void PlayFighterShot() {
        try {
            Play(_name[FighterShotNumber], FighterShotVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    private int ItemGetNumber = 3; // �A�C�e���Q�b�g���̃g���b�N�ԍ�
    // �A�C�e���擾���𗬂�
    public void PlayItemGet() {
        try {
            Play(_name[ItemGetNumber], ItemGetVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    private int PowerDownNumber = 4; // �c�@������
    public void PlayPowerDown() { 
        try {
            Play(_name[PowerDownNumber], PowerDownVolume);
        } catch (Exception e) {
            // do nothing
        }
    }

    private int ShotHitEnemyNumber = 5; // �e���G�ɓ����鉹
    // �e���G�ɂ����鉹�𗬂�
    public void PlayShotHitEnemy() {
        try {
            Play(_name[ShotHitEnemyNumber], ShotHitEnemyVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    private int BombNumber = 6; // �{���̉�
    public void PlayBomb() {
        try {
            Play(_name[BombNumber], BombVolume);
        } catch(Exception e) {
            // do nothing
        }
    }

    // ----------------����-----------------//
    private float TitleCursorMoveVolume = 0.4f; // �^�C�g����ʃJ�[�\���𓮂������̉���
    public void setTitleCursorMoveVolume(float vol) { TitleCursorMoveVolume = vol;}
    public float getTitleCursorMoveVolume() { return TitleCursorMoveVolume;}
    private float TitleCursorDetermineVolume = 0.7f; // �^�C�g����ʂ̌��艹�̉���
    public void setTitleCursorDetermineVolume(float vol) { TitleCursorDetermineVolume = vol;}
    public float getTitleCursorDetermineVolume() { return TitleCursorDetermineVolume;}
    private float FighterShotVolume = 1.0f; // ���@�̃V���b�g���̉���
    public void setFighterShotVolume(float vol) { FighterShotVolume = vol;}
    public float getFighterShotVolume() {return FighterShotVolume;}
    private float ItemGetVolume = 1.0f; // �A�C�e���̃Q�b�g���̉���
    public void setItemGetVolume(float vol) { ItemGetVolume = vol;}
    public float getItemGetVolume() { return ItemGetVolume;}
    private float PowerDownVolume = 1.0f; // �p���[�_�E�����̉���
    public void setPowerDownVolume(float vol) { PowerDownVolume = vol;}
    public float getPowerDownVolume() { return PowerDownVolume;}
    private float ShotHitEnemyVolume = 1.0f; // �G�Ɏ��@�̃V���b�g�������鉹�̉���
    public void setShotHitEnemyVolume(float vol) { ShotHitEnemyVolume = vol;}
    public float getShotHitEnemyVolume() {return ShotHitEnemyVolume;}
    private float BombVolume = 1.0f; // �{�����̉���
    public void setBombVolume(float vol) { BombVolume = vol;}
    public float getBombVolume() { return BombVolume;}


    // �R���X�g���N�^
    public SoundEffect() {}

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
    public void Play(String pass, float vol) throws Exception {
        Clip c = createClip(new File(pass));
        c.start();
        Volume(c, vol);
    }

    // ���ʐݒ�
    public void Volume(Clip c, float vol) {
        FloatControl ctrl = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
        ctrl.setValue((float) Math.log10(vol) * 20);
    }

    // ��~
    public void Finish(Clip clip) {
        // �ꎞ��~
        clip.stop();
        // �����Đ��̓����o�b�t�@���폜
        clip.flush();
        // �Đ��ʒu���ŏ��ɖ߂�
        clip.setFramePosition(0);
    }
}