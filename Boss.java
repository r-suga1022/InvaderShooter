// �ꕔ�Q�l�ɂ����T�C�g
// https://nompor.com/2017/12/06/post-1665/

import java.awt.*;
import java.awt.geom.*;
import java.nio.file.ProviderMismatchException;
import java.util.Random;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

// �{�X�̃x�[�X�N���X
public class Boss extends BaseObject {
    
    // �I�u�W�F�N�g�֘A�̃t�B�[���h
    protected BossManager _bmanager;
    private int BOSSBULLET_MAX; // ��ʏ�ɕ\���ł���e���̍ő吔
    public int GetBossBulletMax() {return BOSSBULLET_MAX;}
    public void SetBossBulletMax(int bm) { BOSSBULLET_MAX = bm; }
    private BossBullet[] _bullet; // ��ʏ�̒e��
    public BossBullet[] GetBossBullet() { return _bullet; }
    public void SetBossBullet(BossBullet[] bb) {_bullet = bb;}
    private Fighter _fighter;
    public Fighter GetFighter() {return _fighter;}
    public void SetFighter(Fighter f) {_fighter = f;}
    private SoundEffect _effect; // �T�E���h�G�t�F�N�g�̃I�u�W�F�N�g
    public void setSoundEffect(SoundEffect ef) {_effect = ef;}
    public SoundEffect getSoundEffect() {return _effect;}

    // ���ԂɊւ���t�B�[���h
    private int _time; // �Q�[��������
    public void setTime(int time) { _time = time; }
    public int getTime() { return _time;}
    private int StartTime; // �{�X�̏o���X�e�[�g���n�܂鎞��
    public int getStartTime() { return StartTime;}
    public void setStartTime(int time) { StartTime = time; }
    private int AppearSpan; // �o���X�e�[�g�̌p������
    public void setAppearSpan(int as) { AppearSpan = as;}
    public int getAppearSpan() { return AppearSpan;}
    private int AppearFinishTime; // �o���X�e�[�g���I��鎞��
    public void setAppearFinishTime(int aft) { AppearFinishTime = aft;}
    public int getAppearFinishTime() {return AppearFinishTime;}
    private int AppearBattleIntvl; // �o���X�e�[�g���I����Ă���퓬�X�e�[�g���J�n����܂ł̋󂫎���
    public void setAppearBattleIntvl(int abi) {AppearBattleIntvl = abi;}
    public int getAppearBattleIntvl() {return AppearBattleIntvl;}
    private int BattleStartTime; // �퓬�X�e�[�g�i���1�j���J�n���鎞��
    public void setBattleStartTime(int bst) { BattleStartTime = bst;}
    public int getBattleStartTime() {return BattleStartTime;}
    private int State1FinishTime; // ���1���I����������
    public void setState1FinishTime(int time) { State1FinishTime = time;}
    public int getState1FinishTime() { return State1FinishTime;}
    private int State1and2intvl; // ���1�Ə��2�̊Ԃ̃C���^�[�o��
    public void setState1and2Intvl(int si) {State1and2intvl = si;}
    public int getState1and2Intvl() { return State1and2intvl;}
    private int BattleState2Start; // ���2�̊J�n����
    public void setBattleState2Start(int bss) { BattleState2Start = bss;}
    public int getBattleState2Start() { return BattleState2Start;}
    private int State2FinishTime; // ���2���I����������
    public void setState2FinishTime(int time) {State2FinishTime = time;}
    public int getState2FinishTime() { return State2FinishTime;}
    private int State2and3intvl; // ���2�Ə��3�̊Ԃ̃C���^�[�o��
    public void setState2and3Intvl(int si) { State2and3intvl = si;}
    public int getState2and3Intvl() {return State2and3intvl;}
    private int BattleState3Start; // ���3�̊J�n����
    public void setBattleState3Start(int bss) { BattleState3Start = bss;}
    public int getBattleState3Start(){return BattleState3Start;}
    private int State3FinishTime; // ���3�̏I������
    public void setState3FinishTime(int sft) {State3FinishTime = sft;}
    public int getState3FinishTime() { return State3FinishTime;}
    private int DeathStateStart; // ���S�X�e�[�g�J�n����
    public void setDeathStateStart(int dss) { DeathStateStart = dss;}
    public int getDeathStateStart() { return DeathStateStart;}
    private int BossWinkIntvl; // �{�X�̓_�ŊԊu
    public void setBossWinkIntvl(int bwi) {BossWinkIntvl = bwi;}
    public int getBossWinkIntvl() {return BossWinkIntvl;}
    private int ScreenWinkIntvl; // ��ʂ������_�ł���Ԋu
    public void setScreenWinkIntvl(int swi) {ScreenWinkIntvl = swi;}
    public int getScreenWinkIntvl() { return ScreenWinkIntvl;}
    private int BossWinkSpan; // �{�X���_�ł��Ă��鎞��
    public void setBossWinkSpan(int bws) { BossWinkSpan = bws;}
    public int getBossWinkSpan() {return BossWinkSpan;}
    private int ScreenWinkSpan; // ��ʂ������_�ł��鎞��
    public void setScreenWinkSpan(int sws) {ScreenWinkSpan = sws;}
    public int getScreenWinkSpan() {return ScreenWinkSpan;}
    private int WinkFinishSpan; // ��������N���A���y�𗬂��n�߂�܂ł̊Ԃ̎���
    public void setWinkFinishSpan(int wfs) {WinkFinishSpan = wfs;}
    public int getWinkFinishSpan() {return WinkFinishSpan;}
    private int ClearBGMSpan; // �N���A���y�𗬂��n�߂Ă���N���A��ʂɈړ�����܂ł̎���
    public void setClearBGMSpan(int cbs) { ClearBGMSpan = cbs;}
    public int getClearBGMSpan() { return ClearBGMSpan;}

    // ���@�̈ʒu�⑬�x�Ɋւ���t�B�[���h
    private float fVX_appear = 0f; // �o���X�e�[�g�J�n���Wx
    private float fVY_appear = 1f; // �o���X�e�[�g�J�n���Wy
    public void setVAppear(float x, float y) { fVX_appear = x; fVY_appear = y;}
    public float getVXAppear() { return fVX_appear;}
    public float getVYAppear() { return fVY_appear;}
    private float State1StartX; // ���i�K�̊J�n���Wx
    private float State1StartY; // ���i�K�̊J�n���Wy
    public void setState1StartPos(float x, float y) { State1StartX = x; State1StartY = y;}
    public float getState1StartX() { return State1StartX; }
    public float getState1StartY() { return State1StartY;}
    private float State2StartX; // ���i�K�̊J�n���Wx
    private float State2StartY; // ���i�K�̊J�n���Wy
    public void setState2StartPos(float x, float y) { State2StartX = x; State2StartY = y;}
    public float getState2StartX() { return State2StartX;}
    public float getState2StartY() { return State2StartY;}
    private float State3StartX; // ��O�i�K�̊J�n���Wx
    private float State3StartY; // ��O�i�K�̊J�n���Wy
    public void setState3StartPos(float x, float y) {State2StartX = x; State2StartY = y;}
    public float getState3StartX() {return State3StartX;}
    public float getState3StartY() {return State3StartY;}

    // ������̈ʒu�⎞�ԂɊւ���t�B�[���h
    private int AppearStringIntvl; // "Warning!"�̓_�Ŏ���(��F10�̎���5�b�ŕ\���A5�b�Ŕ�\���̂悤�ȓ_��)
    public void setAppearStringIntvl(int asi) { AppearStringIntvl = asi;}
    public int getAppearStringIntvl() { return AppearStringIntvl; }
    private int AppearStringX;
    private int AppearStringY;
    public void setAppearStringPos(int x, int y) {AppearStringX = x; AppearStringY = y;}
    public int getAppearStringX() { return AppearStringX;}
    public int getAppearStringY() { return AppearStringY;}

    // HP�Ɋւ���t�B�[���h
    private int HP_MAX; // ���^����HP�l
    public void setHP_MAX(int maxHP) {HP_MAX = maxHP;}
    public int getHP_MAX() {return HP_MAX;}
    private int HP; // ���݂�HP
    public void setHP(int newHP) { HP = newHP; }
    public int getHP() {return HP;}

    // �{�X�̒e���֘A
    private int eim_speed; // ���@�_���e�̑��x
    public void setEimSpeed(int es) { eim_speed = es;}
    public int getEimSpeed() { return eim_speed;}
    private int eim_intvl; // ���@�_���e�̔��ˊԊu
    public void SetEimIntvl(int i) {eim_intvl = i;}
    public int GetEimIntvl() { return eim_intvl; }
    private int rotate_intvl; // �Q�����^�e�̔��ԊԊu
    public void SetRotateIntvl(int si) {rotate_intvl = si;}
    public int GetRotateIntvl() { return rotate_intvl; }
    private int rotate_speed; // �Q�����^�e�̑��x
    public void SetRotateSpeed(int ss) { rotate_speed = ss;}
    public int GetRotateSpeed() { return rotate_speed;}
    private int current_angle; // ���݂̔��ˊp�x
    public void SetCurrentAngle(int ca) {current_angle = ca;}
    public int GetCurrentAngle() {return current_angle;}
    public int angle_adjustX; // ���ˊp�x�����ix�����j
    public int angle_adjustY; // ���ˊp�x�����iy�����j
    public void SetAngleAdjust(int x, int y) { angle_adjustX = x; angle_adjustY = y;}
    public int GetAngleAdjustX() { return angle_adjustX;}
    public int GetAngleAdjustY() { return angle_adjustY;}
    private int firework_speed; // �ԉ΂̔��ˑ��x
    public void SetFireworkSpeed(int fs) { firework_speed = fs;}
    public int GetFireworkSpeed() { return firework_speed;}
    private int firework_shoot_intvl; // �ԉ΂̔��ˊԊu
    public void SetFireworkShootIntvl(int fsi) { firework_shoot_intvl = fsi;}
    public int GetFireworkShootIntvl() {return firework_shoot_intvl;}
    private int piece_num; // �ԉ΂̔�����̌��Ђ̐�
    public void SetPieceNum(int pn) { piece_num = pn;}
    public int GetPieceNum() { return piece_num;}
    private int shoot_explode_intvl; // ���ˌ㔚������܂ł̎���
    public void SetShootExplodeIntvl(int sei) { shoot_explode_intvl = sei;}
    public int GetShootExplodeIntvl() { return shoot_explode_intvl;}
    private float velocitytimesfirework; // �����O�ƌ�ő��������{�ɂȂ邩
    public void SetVelocityTimesFirework(float vt) { velocitytimesfirework = vt;}
    public float GetVelocityTimesFirework() { return velocitytimesfirework;}
    private int radiation_speed; // ���˒e�̔��ˑ��x
    public void SetRadiationSpeed(int s) {radiation_speed = s;}
    public int GetRadiationSpeed() {return radiation_speed;}
    private int radiation_number; // ��x�ɕ��˂���e�̌�
    public void SetRadiationNumber(int rn) {radiation_number = rn;}
    public int GetRadiationNumber() {return radiation_number;}
    private int radiation_intvl; // ���˒e�𔭎˂���Ԋu
    public void SetRadiationIntvl(int ri) {radiation_intvl = ri;}
    public int GetRadiationIntvl() { return radiation_intvl;}
    private int angle_add; // ��]�p
    public void SetAngleAdd(int aa) {angle_add = aa;}
    public int GetAngleAdd() {return angle_add;}
    private int howmany_added; // ����p�x��ǉ�������
    public void SetHowManyAdded(int hma) { howmany_added = hma;}
    public int GetHowManyAdded() { return howmany_added;}
    private int random_intvl; // �����_���e�̔��ˊԊu
    public void SetRandomIntvl(int ri) { random_intvl = ri;}
    public int GetRandomIntvl() {return random_intvl;}
    private int random_speed; // �����_���e�̊�{�I���x
    public void SetRandomSpeed(int rs) {random_speed = rs;}
    public int GetRandomSpeed() {return random_speed;}
    private int random_range; // �����͈̔͐ݒ�i�p�x�̍ő�l�z
    public void SetRandomRange(int rr) {random_range = rr;}
    public int GetRandomRange() {return random_range;}

    // �t���O
    private int isStateFinished; // ��Ԃ�؂�ւ��邽�߂̃t���O�i��ԊJ�n���ԃZ�b�g���ɖ𗧂j
    public void setIsStateFinished(int flag) { isStateFinished = flag;}
    public int IsStateFinished() {return isStateFinished;}

    // �R���X�g���N�^
    public Boss(BossManager manager) {
        _bmanager = manager;
    }

    // ������
    public void init() {
        _effect = _bmanager.getSoundEffect();

        setIsStateFinished(0);
    }

    // �e���̏�����
    public BossBullet[] initBullet(BossBullet[] b) {
        return b;
    }

    // �{�X�̕\��
    public void Show(Graphics2D g2) {

        if(!isEnable) return;
    }

    // �{�X�̓���
    public void Move() {
        super.Move();
    }

    // ----------�X�e�[�g�n�̃��\�b�h---------- //
    // �{�X�̏o���X�e�[�g
    public void Appear() {}

    // �{�X�̐퓬�X�e�[�g
    public void Battle() {}

    // �퓬�X�e�[�g�̏��1
    public void BattleState1() {}

    // �퓬�X�e�[�g�̏��2
    public void BattleState2() {}

    // �퓬�X�e�[�g�̏��3
    public void BattleState3() {}

    // �{�X�����S����Ƃ��̃X�e�[�g
    public void DeathState() {}

    // ------------�X�e�[�g����n���\�b�h--------------------//
    // �o���X�e�[�g���ۂ��𔻒肷��
    public boolean IsAppear() {
        if(getTime() < getBattleStartTime()) {return true;}
        return false;
    }

    // ���1�Ə��2�̊Ԃ̎��Ԃ��ۂ��𔻒肷��
    public boolean IsIntvl1And2() {
        if(getTime() >= getState1FinishTime() && getTime() < getBattleState2Start() ) {return true;}
        return false;
    }

    // ���2�Ə��3�̊Ԃ̎��Ԃ��ۂ��𔻒肷��
    public boolean IsIntvl2And3() {
        if(getTime() >= getState2FinishTime() && getTime() < getBattleState3Start()) {return true;}
        return false;
    }

    // ���S�X�e�[�g���𔻒肷��
    public boolean IsDeath() {
        if(getHP() <= 0) {return true;}
        return false;
    }

    // -------�e���n�̃��\�b�h------- //
    // ���@�_���̒e���𐶐�
    public void CreateEimsBullet(int type)
    {
        Fighter fighter = _bmanager.GetFighter();
        BossBullet[] bullet = _bmanager.GetBossBullet();
        
        if(!isEnable) return;

        if(fY > 240) return;
        System.out.println("eimintvl = "+GetEimIntvl());//
        if((getTime() % GetEimIntvl()) == 0) {
            for(int i=0; i<GetBossBulletMax(); i++)
            {
                if(bullet[i].isEnable) continue;

                float dx, dy, d, speed;

                dx = fighter.GetX() - fX;
                dy = fighter.GetY() + 23 - fY;

                speed = getEimSpeed();

                d = (float)Math.sqrt(dx*dx + dy*dy);

                bullet[i].SetPos(fX, fY);
                bullet[i].SetVX((dx/d)*speed);
                bullet[i].SetVY((dy/d)*speed);
                bullet[i].setBossBulletType(type);

                bullet[i].Enable(true);

                break;
            }

            _bmanager.SetBossBullet(GetBossBullet());
        }
        
    }

    // �Q�����`�̒e���𐶐�
    public void CreateRotateBullet(int type) {
        BossBullet[] bullet = _bmanager.GetBossBullet();

        if(!isEnable) return;

        if(fY > 240) return;
        if(getTime() % GetRotateIntvl() == 0) {
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].isEnable) {
                    continue;
                }
                float speed = GetRotateSpeed();

                SetCurrentAngle(GetCurrentAngle() + GetAngleAdjustX());
                bullet[i].SetPos(fX, fY);
                bullet[i].SetVX(speed * (float)Math.cos((float)Math.toRadians((float)(GetCurrentAngle() + GetAngleAdjustX()))));
                bullet[i].SetVY(speed * (float)Math.sin((float)Math.toRadians((float)(GetCurrentAngle() + GetAngleAdjustY()))));
                bullet[i].setBossBulletType(type);

                bullet[i].Enable(true);

                break;
            }
            _bmanager.SetBossBullet(GetBossBullet());
        }
    }

    // �ԉΒe���𐶐�
    public void CreateFireWorksBullet(int type, int typeafter) {
        int i = 0, j;
        int isCreate = 0;
        BossBullet[] bullet = _bmanager.GetBossBullet();
        Fighter fighter = _bmanager.GetFighter();

        if(!isEnable) return;

        if(fY > 240) return;
        
        while(i < GetBossBulletMax()) {
            // ���ɔ��˂���Ă������
            if(bullet[i].IsEnable()) {
                // ���ɔ������Ă����牽�����Ȃ�
                if(bullet[i].getIsExploded()) {++i; continue;}
                else {
                    // �������������鎞�ԂɂȂ��Ă�����
                    int time = getTime() - bullet[i].getShootedTime();
                    if(time == GetShootExplodeIntvl()) {
                        // �s�[�X�̐���������������
                        j = i + GetPieceNum();
                        while(i < j) {
                            float dx = bullet[i].GetVX(), dy = bullet[i].GetVY();
                            float d = (float)Math.sqrt(dx*dx + dy*dy);
                            bullet[i].setBossBulletType(typeafter);
                            bullet[i].SetVX((float)Math.cos(Math.acos(dx / d) + (360f * (j-i))) * GetVelocityTimesFirework() * d);
                            bullet[i].SetVY((float)Math.sin(Math.asin(dy / d) + (360f * (j-i))) * GetVelocityTimesFirework() * d);
                            bullet[i].setIsExploded(true);
                            ++i;
                        }
                    // �܂����������鎞�ԂłȂ����
                    } else {
                        ++i; continue;
                    }
                }//
            } else {
                // �܂����˂���Ă��Ȃ�����
                if(isCreate == 0 && getTime() % GetFireworkShootIntvl() == 0 && i % GetPieceNum() == 0) {
                    j = i + GetPieceNum();
                    boolean isallfalse = false;
                    // �s�[�X���ׂĂ�false�łȂ���΍��Ȃ�
                    if(i % GetPieceNum() == 0) {
                        while(i < j) {if(bullet[i].IsEnable()) isallfalse = true; ++i;}
                        if(isallfalse) continue;
                    }
                    i = j - GetPieceNum();
                    while(i < j) {
                        float dx, dy, d, speed;

                        dx = fighter.GetX() - fX;
                        dy = fighter.GetY() + 23 - fY;

                        speed = GetFireworkSpeed();

                        d = (float)Math.sqrt(dx*dx + dy*dy);

                        bullet[i].SetPos(fX, fY);
                        bullet[i].SetVX(0);
                        bullet[i].SetVY(speed);
                        bullet[i].setBossBulletType(type);
                        bullet[i].setIsExploded(false);
                        bullet[i].setShootedTime(getTime());
                        bullet[i].Enable(true);
                        ++i;
                    }
                    isCreate = 1;
                    continue;
                } else {
                    ++i; continue;
                }
            }
        }
        _bmanager.SetBossBullet(GetBossBullet());
    }

    // �Q�����`�̒e���𐶐�
    public void CreateRadiationBullet(int type) {
        int j, k;
        BossBullet[] bullet = _bmanager.GetBossBullet();

        if(!isEnable) return;

        if(fY > 240) return;
        if(getTime() % GetRadiationIntvl() == 0) {
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].isEnable) {
                    continue;
                }

                float num = GetRadiationNumber();
                j = i; k = 0;
                int angle_add = GetAngleAdd();
                int howmany = GetHowManyAdded();
                int angle_add_true = angle_add * howmany;
                if(angle_add_true >= 360) {angle_add_true -= 360; howmany = angle_add_true / 360;}
                SetHowManyAdded(howmany + 1);

                while(k < num) {
                    if(bullet[j].isEnable) {++j; continue;}

                    float speed = GetRadiationSpeed();
                    float angle = (float)360 / (float)num;

                    bullet[j].SetPos(fX + (float)Math.cos(Math.toRadians((float)angle_add_true + (float)k * angle)), fY + (float)Math.sin(Math.toRadians((float)angle_add_true + (float)k * angle)));
                    bullet[j].SetVX(speed * (float)Math.cos(Math.toRadians((float)angle_add_true + (float)k * angle)));
                    bullet[j].SetVY(speed * (float)Math.sin(Math.toRadians((float)angle_add_true + (float)k * angle)));
                    bullet[j].setBossBulletType(type);

                    bullet[j].Enable(true);
                    ++j; ++k;
                }
                
                break;
            }
            _bmanager.SetBossBullet(GetBossBullet());
        }
    }

    // �����_���e�𐶐�
    public void CreateRandomBullet(int type)
    {
        BossBullet[] bullet = _bmanager.GetBossBullet();
        Random rand = new Random();
        int randomValue;
        
        if(!isEnable) return;

        if(fY > 240) return;
        if((getTime() % GetRandomIntvl()) == 0) {
            for(int i=0; i<GetBossBulletMax(); i++)
            {
                if(bullet[i].isEnable) continue;

                float speed = GetRandomSpeed();
                randomValue = rand.nextInt(GetRandomRange());

                bullet[i].SetPos(fX, fY);
                bullet[i].SetVX(speed * (float)Math.cos(180 + Math.toRadians(randomValue)));
                bullet[i].SetVY(speed * (float)Math.sin(-180 + Math.toRadians(randomValue)));
                bullet[i].setBossBulletType(type);

                bullet[i].Enable(true);

                break;
            }

            _bmanager.SetBossBullet(GetBossBullet());
        }
        
    }

    // ------------�����蔻��n���\�b�h-------------- //
    // �e���Ǝ��@�̓����蔻��
    public boolean HitCheckBossBulletAndFighter() {
        if(!GetFighter().IsEnable()) return false;

		for(int i=0; i<GetBossBulletMax(); i++)
		{
			if(GetBossBullet()[i] == null || !GetBossBullet()[i].IsEnable()) continue;
				float dx, dy, width, height;
			dx = GetBossBullet()[i].GetX() - GetFighter().GetX();
			dy = GetBossBullet()[i].GetY() - GetFighter().GetY() - 23;

			width = 20;
			height = 28;

			// ����������
			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				// ���@���p���[�_�E��������ʉ�
				getSoundEffect().PlayPowerDown();
				
				GetBossBullet()[i].Enable(false);
				GetFighter().Enable(false);
				GetFighter().DecreasenLeft(1);
				GetFighter().SetMbegin(getTime());
				GetFighter().SetMuteki(1);
				return true;
			}
		}		
		return false;
    }

    // �{�X�Ǝ��@�̒e�̓����蔻��
    public void HitCheckBossAndShot()
	{
        if(!IsAppear() && !IsIntvl1And2() && !IsIntvl2And3()) {
            for(int i=0;i<GetFighter().GetNumShot();i++)
            {
                // �e���L���łȂ��Ƃ��A��΂�
                if(!GetFighter().GetShot()[i].IsEnable()) continue;

                if(!IsEnable()) continue;
                float dx, dy, width, height;
                dx = GetX() - GetFighter().GetShot()[i].GetX() - 5;
                dy = GetY() - GetFighter().GetShot()[i].GetY();

                width = 50;
                height = 60;

                if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
                {
                    
                    int n = GetFighter().GetShift();
                    if(n == 2){
                        setHP(getHP() - 2);
                    }else{
                        setHP(getHP() - 1);
                        System.out.println("HP = "+getHP());//
                    }
                    GetFighter().GetShot()[i].Enable(false);

                    // �{�X�ɒe�������鉹���o��
                    getSoundEffect().PlayShotHitEnemy();
                    
                    break;
                }
            }
        }
	}
    
}


//------------------------------------------------------------

// �X�e�[�W1�̃{�X�N���X
class Boss1 extends Boss {

    // Warning�̃t�H���g
    private Font warfont = new Font("Arial", Font.BOLD, 40);

    // �{�X1�̒e���N���X�i�����N���X�ABossBullet�̌p���j
    class BossBullet1 extends BossBullet {
        // �e�^�C�v
        int type;
        // �R���X�g���N�^
        public BossBullet1() {
            super();
        }

        @Override
        public void Show(Graphics2D g2) {
            if(!isEnable) return;

            // �e�^�C�v�ɂ���ďꍇ����
            switch(getBossBulletType()) {
                case 0:
                    g2.setPaint(Color.red);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 1:
                    g2.setPaint(Color.YELLOW);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 2:
                    g2.setPaint(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 3:
                    g2.setPaint(Color.blue);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 4:
                    g2.setPaint(Color.pink);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                default:
                    break;
            }
        }
    }

    // �{�X1�̃R���X�g���N�^
    public Boss1(BossManager manager) {
        super(manager);
    }

    // �e�^�C�v�̏�����
    @Override
    public BossBullet[] initBullet(BossBullet[] b) {
        for(int i = 0; i < GetBossBulletMax(); ++i) {
            b[i] = new BossBullet1();
        }
        return b;
    }

    // ������
    @Override
    public void init() {
        super.init();

        // ���W�⑬�x�̐ݒ�
        SetPos(240f, 0f);
        SetVX(0f); SetVY(1f);
        setVAppear(0f, 2f);
        setStartTime(_bmanager.GetTime());
        Enable(true);
        
        // �X�^�[�g���Ԃ̐ݒ�
        // ���Ԋ֘A�̐ݒ�
        setStartTime(_bmanager.GetMain().GetTime());
        setAppearSpan(60);
        setAppearBattleIntvl(30);
        setAppearFinishTime(getStartTime() + getAppearSpan());
        setBattleStartTime(getAppearFinishTime() + getAppearBattleIntvl());
        setState1and2Intvl(70);
        setState2and3Intvl(70);
        setBossWinkIntvl(4);
        setBossWinkSpan(70);
        setScreenWinkIntvl(2);
        setScreenWinkSpan(10);
        setWinkFinishSpan(30);
        setClearBGMSpan(70);

        // HP�̐ݒ�
        setHP(100);
        setHP_MAX(100);

        // �e���֘A�̐ݒ�
        SetBossBulletMax(300);
        setEimSpeed(20);
        SetEimIntvl(8);
        SetRotateIntvl(1);
        SetRotateSpeed(20);
        SetAngleAdjust(30, 30);
        SetPieceNum(5);
        SetFireworkSpeed(10);
        SetFireworkShootIntvl(1);
        SetShootExplodeIntvl(20);
        SetVelocityTimesFirework(2);

        // ������֘A�̐ݒ�("Warning"�Ȃ�)
        setAppearStringIntvl(10);
        setAppearStringPos(100, 250);

        // �e���̃I�u�W�F�N�g�̐ݒ�
        SetBossBullet(new BossBullet[GetBossBulletMax()]);
        SetBossBullet(initBullet(GetBossBullet()));
        _bmanager.SetBossBullet(GetBossBullet());
        _bmanager.SetBossBulletMax(GetBossBulletMax());

        // ���@�̃I�u�W�F�N�g�̐ݒ�
        SetFighter(_bmanager.GetFighter());
    }

    // �{�X1�̕`��
    @Override
    public void Show(Graphics2D g2) {
        super.Show(g2);

        // HP�o�[
        if(!IsAppear() && !IsDeath()) {
            g2.setPaint(Color.white);
            g2.drawString("�{�XHP", 20, 20);
            float rate = (float)getHP() / (float)getHP_MAX();
            g2.setPaint(new Color((int)(255f - 255f * rate), (int)(0f + 255f * rate), 0));
            g2.fillRect(20, 40, (int)Math.ceil(440f * rate), 10);
        }
        
        // ���S�X�e�[�g�łȂ��Ƃ��A�Ⴕ���͎��S�X�e�[�g�Ŕ�������O�ɁA�{�X��`�悷��
        if(!IsDeath() || (IsDeath() && (getTime() <= getDeathStateStart() + getBossWinkSpan()))) {
            if(IsDeath() && (getTime() % getBossWinkIntvl() <= getBossWinkIntvl() / 2)) {
                // do nothing
            } else {
                Image img = Toolkit.getDefaultToolkit().getImage("Boss1.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
            }
        }

        // �o���X�e�[�g�ɂ����āAWarning�ƕ\��
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.RED);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // ���S�X�e�[�g�ɂāA�������[�V�����\��
        if(IsDeath() && getTime() >= getDeathStateStart() + getBossWinkSpan() && getTime() < getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan()) {
            if(getTime() % getScreenWinkIntvl() == getScreenWinkIntvl() / 2) {
                g2.setPaint(Color.white);
                g2.fillRect(0, 0, 480, 640);
            }
        }
    }

    // �{�X1�̓���
    @Override
    public void Move() {
        super.Move();
    }

    // ----------- �e���n���\�b�h ------------ //
    // ���@�_���̒e�𐶐�
    @Override
    public void CreateEimsBullet(int type) {
        super.CreateEimsBullet(type);
    }
    
    // �Q�����e���̐���
    @Override
    public void CreateRotateBullet(int type) {
        super.CreateRotateBullet(type);
    }

    // �ԉΒe���̐���
    @Override
    public void CreateFireWorksBullet(int type, int typeafter) {
        super.CreateFireWorksBullet(type, typeafter);
    }

    // ----------- �X�e�[�g�n���\�b�h ---------- //
    // �o���X�e�[�g
    @Override
    public void Appear() {
        super.Appear();
        if(getTime() < getAppearFinishTime()) {
            SetVX(getVXAppear()); SetVY(getVYAppear());
        } else if(getTime() >= getAppearFinishTime() && getTime() < getBattleStartTime()) {
            SetVX(0); SetVY(0); setState1StartPos(GetX(), GetY());
        }
    }

    // �퓬�X�e�[�g
    @Override
    public void Battle() {
        super.Battle();
        if(getTime() >= getBattleStartTime() && getHP()>= getHP_MAX()* 2 / 3) {
            BattleState1();
        } else if(getHP() < (getHP_MAX()* 2 / 3) && getHP() >= (getHP_MAX() / 3)) {
            BattleState2();
        } else if(getHP() > 0 && getHP() < (getHP_MAX() / 3)) {
            BattleState3();
        } else if(getHP() <= 0) {
            DeathState();
        }
    }

    // �{�X1�̏��1
    @Override
    public void BattleState1() {
        if(GetVX() == 0 && GetVY() == 0) SetVX(10); SetVY(0);
        if(fX <= 0 && GetVX() < 0) {
            SetVX(10); SetVY(0);
        } else if(fX >= 430 && GetVX() > 0) {
            SetVX(-10); SetVY(0);
        }
        CreateEimsBullet(0);
    }

    // �{�X1�̏��2
    @Override
    public void BattleState2() {
        Random rand = new Random();
        // ���1���I������΂���̎�
        if(IsStateFinished() == 0) {
            setState1FinishTime(getTime());
            setBattleState2Start(getState1FinishTime() + getState1and2Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl1And2()) {
            setState2StartPos(getState1StartX(), getState1StartY());
            SetPos(getState1StartX(), getState1StartY());
        } else {
            // �ŏ��͐��̕����ɓ���
            if((IsStateFinished() == 1 && fX == getState2StartX()) || fX <= 0) {
                setIsStateFinished(2);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            }
            CreateFireWorksBullet(1, 4);
        }
    }

    // �{�X1�̏��3
    @Override
    public void BattleState3() {
        // ���2���I������΂���̎�
        if(IsStateFinished() == 2) {
            setState2FinishTime(getTime());
            setBattleState3Start(getState2FinishTime() + getState2and3Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl2And3()) {
            setState3StartPos(getState2StartX(), getState2StartY());
            SetPos(getState2StartX(), getState2StartY());
        } else {
            // �ŏ��͐��̕����ɓ���
            if(IsStateFinished() == 1 || fX <= 0) {
                setIsStateFinished(0);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            } else {
                // do nothing
            }
            CreateRotateBullet(2);
        }
    }

    // �{�X�����S����Ƃ��̃X�e�[�g
    @Override
    public void DeathState() {
        BossBullet[] bullet;
        if(IsStateFinished() == 0) {
            setIsStateFinished(1);
            setState3FinishTime(getTime());
            setDeathStateStart(getTime());
            // �{�X���~�܂�
            SetVX(0); SetVY(0);
            // �e�����ׂď���
            bullet = _bmanager.GetBossBullet();
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].IsEnable()) {bullet[i].Enable(false);}
            }
            // ���S�X�e�[�g�J�n�̃t���O
            _bmanager.SetIsDead(true);
        } else {
            int time = getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan() + getWinkFinishSpan();
            if(getTime() >= time - getScreenWinkSpan() - getWinkFinishSpan() && getTime() < time - getWinkFinishSpan()) {
                if(getTime() % getScreenWinkIntvl() == 0) {
                    getSoundEffect().PlayBomb();
                }
            }
            if(getTime() == time) {
                if(!_bmanager.IsDeadState()) {_bmanager.SetIsDeadState(true);}
            } else if(getTime() > time + getClearBGMSpan()){
                _bmanager.SetIsClearBGM(true);
            }
        }
    }
}


// ----------------------------------------------------------------------------------------

// �X�e�[�W2�̃{�X�N���X
class Boss2 extends Boss {
    // Warning�̃t�H���g
    private Font warfont = new Font("Arial", Font.BOLD, 40);

    // �{�X2�̒e���N���X�i�����N���X�ABossBullet�̌p���j
    class BossBullet2 extends BossBullet {
        // �e�^�C�v
        int type;
        // �R���X�g���N�^
        public BossBullet2() {
            super();
        }

        @Override
        public void Show(Graphics2D g2) {
            if(!isEnable) return;

            // �e�^�C�v�ɂ���ďꍇ����
            switch(getBossBulletType()) {
                case 0:
                    g2.setPaint(Color.red);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 1:
                    g2.setPaint(Color.YELLOW);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 2:
                    g2.setPaint(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 3:
                    g2.setPaint(Color.blue);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 4:
                    g2.setPaint(Color.pink);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 5:
                    g2.setPaint(Color.orange);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                default:
                    break;
            }
        }
    }

    // �R���X�g���N�^
    public Boss2(BossManager manager) {
        super(manager);
    }

    // �e�^�C�v�̏�����
    @Override
    public BossBullet[] initBullet(BossBullet[] b) {
        for(int i = 0; i < GetBossBulletMax(); ++i) {
            b[i] = new BossBullet2();
        }
        return b;
    }

    @Override
    public void init() {
        super.init();

        // ���W�⑬�x�̐ݒ�
        SetPos(240f, 0f);
        SetVX(0f); SetVY(1f);
        setVAppear(0f, 2f);
        setStartTime(_bmanager.GetTime());
        Enable(true);
        
        // �X�^�[�g���Ԃ̐ݒ�
        // ���Ԋ֘A�̐ݒ�
        setStartTime(_bmanager.GetMain().GetTime());
        setAppearSpan(60);
        setAppearBattleIntvl(30);
        setAppearFinishTime(getStartTime() + getAppearSpan());
        setBattleStartTime(getAppearFinishTime() + getAppearBattleIntvl());
        setState1and2Intvl(70);
        setState2and3Intvl(70);
        setBossWinkIntvl(4);
        setBossWinkSpan(70);
        setScreenWinkIntvl(2);
        setScreenWinkSpan(10);
        setWinkFinishSpan(30);
        setClearBGMSpan(70);

        // HP�̐ݒ�
        setHP(100);
        setHP_MAX(100);

        // �e���֘A�̐ݒ�
        SetBossBulletMax(300);
        setEimSpeed(20);
        SetEimIntvl(8);
        SetRotateIntvl(1);
        SetRotateSpeed(20);
        SetAngleAdjust(1,1);
        SetPieceNum(8);
        SetFireworkSpeed(10);
        SetFireworkShootIntvl(15);
        SetShootExplodeIntvl(15);
        SetVelocityTimesFirework(2);
        SetRadiationIntvl(15);
        SetRadiationNumber(20);
        SetRadiationSpeed(15);
        SetAngleAdd(15);
        SetHowManyAdded(0);
        SetRandomIntvl(5);
        SetRandomSpeed(15);
        SetRandomRange(60);

        // ������֘A�̐ݒ�("Warning"�Ȃ�)
        setAppearStringIntvl(10);
        setAppearStringPos(100, 250);

        // �e���̃I�u�W�F�N�g�̐ݒ�
        SetBossBullet(new BossBullet[GetBossBulletMax()]);
        SetBossBullet(initBullet(GetBossBullet()));
        _bmanager.SetBossBullet(GetBossBullet());
        _bmanager.SetBossBulletMax(GetBossBulletMax());

        // ���@�̃I�u�W�F�N�g�̐ݒ�
        SetFighter(_bmanager.GetFighter());
    }

    @Override
    public void Show(Graphics2D g2) {
        super.Show(g2);

        // HP�o�[
        if(!IsAppear() && !IsDeath()) {
            g2.setPaint(Color.white);
            g2.drawString("�{�XHP", 20, 20);
            float rate = (float)getHP() / (float)getHP_MAX();
            g2.setPaint(new Color((int)(255f - 255f * rate), (int)(0f + 255f * rate), 0));
            g2.fillRect(20, 40, (int)Math.ceil(440f * rate), 10);
        }

        // ���S�X�e�[�g�łȂ��Ƃ��A�Ⴕ���͎��S�X�e�[�g�Ŕ�������O�ɁA�{�X��`�悷��
        if(!IsDeath() || (IsDeath() && (getTime() <= getDeathStateStart() + getBossWinkSpan()))) {
            if(IsDeath() && (getTime() % getBossWinkIntvl() <= getBossWinkIntvl() / 2)) {
                // do nothing
            } else {
                Image img = Toolkit.getDefaultToolkit().getImage("Boss2.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
            }
        }

        // �o���X�e�[�g�ɂ����āAWarning�ƕ\��
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.yellow);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // �o���X�e�[�g�ɂ����āAWarning�ƕ\��
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.RED);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // ���S�X�e�[�g�ɂāA�������[�V�����\��
        if(IsDeath() && getTime() >= getDeathStateStart() + getBossWinkSpan() && getTime() < getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan()) {
            if(getTime() % getScreenWinkIntvl() == getScreenWinkIntvl() / 2) {
                g2.setPaint(Color.white);
                g2.fillRect(0, 0, 480, 640);
            }
        }
    }

    @Override
    public void Move() {
        super.Move();
    }

        // ----------- �e���n���\�b�h ------------ //
    // ���@�_���̒e�𐶐�
    @Override
    public void CreateEimsBullet(int type) {
        super.CreateEimsBullet(type);
    }
    
    // �Q�����e���̐���
    @Override
    public void CreateRotateBullet(int type) {
        super.CreateRotateBullet(type);
    }

    // �ԉΒe���̐���
    @Override
    public void CreateFireWorksBullet(int type, int typeafter) {
        super.CreateFireWorksBullet(type, typeafter);
    }

    @Override
    public void CreateRadiationBullet(int type) {
        super.CreateRadiationBullet(type);
    }

    // ----------- �X�e�[�g�n���\�b�h ---------- //

    @Override
    public void Appear() {
        super.Appear();
        if(getTime() < getAppearFinishTime()) {
            SetVX(getVXAppear()); SetVY(getVYAppear());
        } else if(getTime() >= getAppearFinishTime() && getTime() < getBattleStartTime()) {
            SetVX(0); SetVY(0); setState1StartPos(GetX(), GetY());
        }
    }

    @Override
    public void Battle() {
        super.Battle();
        if(getTime() >= getBattleStartTime() && getHP()>= getHP_MAX()* 2 / 3) {
            BattleState1();
        } else if(getHP() < (getHP_MAX()* 2 / 3) && getHP() >= (getHP_MAX() / 3)) {
            BattleState2();
        } else if(getHP() > 0 && getHP() < (getHP_MAX() / 3)) {
            BattleState3();
        }
        if(getHP() <= 0) {DeathState();}
    }

    // �{�X2�̏��1
    @Override
    public void BattleState1() {
        if(GetVX() == 0 && GetVY() == 0) SetVX(10); SetVY(0);
        if(fX <= 0 && GetVX() < 0) {
            SetVX(10); SetVY(0);
        } else if(fX >= 430 && GetVX() > 0) {
            SetVX(-10); SetVY(0);
        }
        CreateFireWorksBullet(1, 5);
    }

    // �{�X2�̏��2
    @Override
    public void BattleState2() {
        Random rand = new Random();
        // ���1���I������΂���̎�
        if(IsStateFinished() == 0) {
            setState1FinishTime(getTime());
            setBattleState2Start(getState1FinishTime() + getState1and2Intvl());
            setIsStateFinished(3);
        }
        if(IsIntvl1And2()) {
            setState2StartPos(getState1StartX(), getState1StartY());
            SetPos(getState1StartX(), getState1StartY());
        } else {
            // �ŏ��͐��̕����ɓ���
            if((IsStateFinished() == 1 && fX == getState2StartX()) || fX <= 0) {
                setIsStateFinished(2);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            }
            CreateRadiationBullet(4);
        }
    }

    // �{�X2�̏��3
    @Override
    public void BattleState3() {
        // ���2���I������΂���̎�
        if(IsStateFinished() == 2) {
            setState2FinishTime(getTime());
            setBattleState3Start(getState2FinishTime() + getState2and3Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl2And3()) {
            setState3StartPos(getState2StartX(), getState2StartY());
            SetPos(getState2StartX(), getState2StartY());
        } else {
            // �ŏ��͐��̕����ɓ���
            if(IsStateFinished() == 1 || fX <= 0) {
                setIsStateFinished(0);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            } else {
                // do nothing
            }
            CreateRandomBullet(0);
            
        }
    }

    // �{�X�����S����Ƃ��̃X�e�[�g
    @Override
    public void DeathState() {
        BossBullet[] bullet;
        if(IsStateFinished() == 0) {
            setIsStateFinished(1);
            setState3FinishTime(getTime());
            setDeathStateStart(getTime());
            // �{�X���~�܂�
            SetVX(0); SetVY(0);
            // �e�����ׂď���
            bullet = _bmanager.GetBossBullet();
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].IsEnable()) {bullet[i].Enable(false);}
            }
            // ���S�X�e�[�g�J�n�̃t���O
            _bmanager.SetIsDead(true);
        } else {
            int time = getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan() + getWinkFinishSpan();
            if(getTime() >= time - getScreenWinkSpan() - getWinkFinishSpan() && getTime() < time - getWinkFinishSpan()) {
                if(getTime() % getScreenWinkIntvl() == 0) {
                    getSoundEffect().PlayBomb();
                }
            }
            if(getTime() == time) {
                if(!_bmanager.IsDeadState()) {_bmanager.SetIsDeadState(true);}
            } else if(getTime() > time + getClearBGMSpan()){
                _bmanager.SetIsClearBGM(true);
            }
        }
    }
}


// ----------------------------------------------------------------------------------


// �X�e�[�W3�̃{�X�N���X
class Boss3 extends Boss {
    // Warning�̃t�H���g
    private Font warfont = new Font("Arial", Font.BOLD, 40);

    // �{�X3�̒e���N���X�i�����N���X�ABossBullet�̌p���j
    class BossBullet3 extends BossBullet {
        // �e�^�C�v
        int type;
        // �R���X�g���N�^
        public BossBullet3() {
            super();
        }

        @Override
        public void Show(Graphics2D g2) {
            if(!isEnable) return;

            // �e�^�C�v�ɂ���ďꍇ����
            switch(getBossBulletType()) {
                case 0:
                    g2.setPaint(Color.red);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 1:
                    g2.setPaint(Color.YELLOW);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 2:
                    g2.setPaint(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 3:
                    g2.setPaint(Color.magenta);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 4:
                    g2.setPaint(Color.pink);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                default:
                    break;
            }
        }
    }

    // �R���X�g���N�^
    public Boss3(BossManager manager) {
        super(manager);
    }

    // �e�^�C�v�̏�����
    @Override
    public BossBullet[] initBullet(BossBullet[] b) {
        for(int i = 0; i < GetBossBulletMax(); ++i) {
            b[i] = new BossBullet3();
        }
        return b;
    }

    @Override
    public void init() {
        super.init();

        // ���W�⑬�x�̐ݒ�
        SetPos(240f, 0f);
        SetVX(0f); SetVY(1f);
        setVAppear(0f, 2f);
        setStartTime(_bmanager.GetTime());
        Enable(true);
        
        // �X�^�[�g���Ԃ̐ݒ�
        // ���Ԋ֘A�̐ݒ�
        setStartTime(_bmanager.GetMain().GetTime());
        setAppearSpan(60);
        setAppearBattleIntvl(30);
        setAppearFinishTime(getStartTime() + getAppearSpan());
        setBattleStartTime(getAppearFinishTime() + getAppearBattleIntvl());
        setState1and2Intvl(70);
        setState2and3Intvl(70);
        setBossWinkIntvl(4);
        setBossWinkSpan(70);
        setScreenWinkIntvl(2);
        setScreenWinkSpan(10);
        setWinkFinishSpan(30);
        setClearBGMSpan(70);

        // HP�̐ݒ�
        setHP(100);
        setHP_MAX(100);

        // �e���֘A�̐ݒ�
        SetBossBulletMax(300);
        setEimSpeed(20);
        SetEimIntvl(8);
        SetRotateIntvl(1);
        SetRotateSpeed(20);
        SetCurrentAngle(0);
        SetAngleAdjust(30, 30);
        SetPieceNum(8);
        SetFireworkSpeed(10);
        SetFireworkShootIntvl(60);
        SetShootExplodeIntvl(15);
        SetVelocityTimesFirework(2);
        SetRadiationIntvl(15);
        SetRadiationNumber(20);
        SetRadiationSpeed(15);
        SetAngleAdd(15);
        SetHowManyAdded(0);
        SetRandomIntvl(1);
        SetRandomSpeed(15);
        SetRandomRange(60);

        // ������֘A�̐ݒ�("Warning"�Ȃ�)
        setAppearStringIntvl(10);
        setAppearStringPos(100, 250);

        // �e���̃I�u�W�F�N�g�̐ݒ�
        SetBossBullet(new BossBullet[GetBossBulletMax()]);
        SetBossBullet(initBullet(GetBossBullet()));
        _bmanager.SetBossBullet(GetBossBullet());
        _bmanager.SetBossBulletMax(GetBossBulletMax());

        // ���@�̃I�u�W�F�N�g�̐ݒ�
        SetFighter(_bmanager.GetFighter());
    }

    @Override
    public void Show(Graphics2D g2) {
        super.Show(g2);

        // HP�o�[
        if(!IsAppear() && !IsDeath()) {
            g2.setPaint(Color.white);
            g2.drawString("�{�XHP", 20, 20);
            float rate = (float)getHP() / (float)getHP_MAX();
            g2.setPaint(new Color((int)(255f - 255f * rate), (int)(0f + 255f * rate), 0));
            g2.fillRect(20, 40, (int)Math.ceil(440f * rate), 10);
        }

        // ���S�X�e�[�g�łȂ��Ƃ��A�Ⴕ���͎��S�X�e�[�g�Ŕ�������O�ɁA�{�X��`�悷��
        if(!IsDeath() || (IsDeath() && (getTime() <= getDeathStateStart() + getBossWinkSpan()))) {
            if(IsDeath() && (getTime() % getBossWinkIntvl() <= getBossWinkIntvl() / 2)) {
                // do nothing
            } else {
                Image img = Toolkit.getDefaultToolkit().getImage("Boss3.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
            }
        }

        // �o���X�e�[�g�ɂ����āAWarning�ƕ\��
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.yellow);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // �o���X�e�[�g�ɂ����āAWarning�ƕ\��
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.RED);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // ���S�X�e�[�g�ɂāA�������[�V�����\��
        if(IsDeath() && getTime() >= getDeathStateStart() + getBossWinkSpan() && getTime() < getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan()) {
            if(getTime() % getScreenWinkIntvl() == getScreenWinkIntvl() / 2) {
                g2.setPaint(Color.white);
                g2.fillRect(0, 0, 480, 640);
            }
        }
    }

    @Override
    public void Move() {
        super.Move();
    }

        // ----------- �e���n���\�b�h ------------ //
    // ���@�_���̒e�𐶐�
    @Override
    public void CreateEimsBullet(int type) {
        super.CreateEimsBullet(type);
    }
    
    // �Q�����e���̐���
    @Override
    public void CreateRotateBullet(int type) {
        super.CreateRotateBullet(type);
    }

    // �ԉΒe���̐���
    @Override
    public void CreateFireWorksBullet(int type, int typeafter) {
        super.CreateFireWorksBullet(type, typeafter);
    }

    @Override
    public void CreateRadiationBullet(int type) {
        super.CreateRadiationBullet(type);
    }

    // ----------- �X�e�[�g�n���\�b�h ---------- //

    @Override
    public void Appear() {
        super.Appear();
        if(getTime() < getAppearFinishTime()) {
            SetVX(getVXAppear()); SetVY(getVYAppear());
        } else if(getTime() >= getAppearFinishTime() && getTime() < getBattleStartTime()) {
            SetVX(0); SetVY(0); setState1StartPos(GetX(), GetY());
        }
    }

    @Override
    public void Battle() {
        super.Battle();
        if(getTime() >= getBattleStartTime() && getHP()>= getHP_MAX()* 2 / 3) {
            BattleState1();
        } else if(getHP() < (getHP_MAX()* 2 / 3) && getHP() >= (getHP_MAX() / 3)) {
            BattleState2();
        } else if(getHP() > 0 && getHP() < (getHP_MAX() / 3)) {
            BattleState3();
        }
        if(getHP() <= 0) {DeathState();}
    }

    // �{�X3�̏��1
    @Override
    public void BattleState1() {
        if(GetVX() == 0 && GetVY() == 0) SetVX(10); SetVY(0);
        if(fX <= 0 && GetVX() < 0) {
            SetVX(10); SetVY(0);
        } else if(fX >= 430 && GetVX() > 0) {
            SetVX(-10); SetVY(0);
        }
        CreateRotateBullet(1);
    }

    // �{�X3�̏��2
    @Override
    public void BattleState2() {
        Random rand = new Random();
        // ���1���I������΂���̎�
        if(IsStateFinished() == 0) {
            setState1FinishTime(getTime());
            setBattleState2Start(getState1FinishTime() + getState1and2Intvl());
            setIsStateFinished(3);
        }
        if(IsIntvl1And2()) {
            setState2StartPos(getState1StartX(), getState1StartY());
            SetPos(getState1StartX(), getState1StartY());
        } else {
            // �ŏ��͐��̕����ɓ���
            if((IsStateFinished() == 1 && fX == getState2StartX()) || fX <= 0) {
                setIsStateFinished(2);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            }
            CreateRadiationBullet(3);
        }
    }

    // �{�X3�̏��3
    @Override
    public void BattleState3() {
        // ���2���I������΂���̎�
        if(IsStateFinished() == 2) {
            setState2FinishTime(getTime());
            setBattleState3Start(getState2FinishTime() + getState2and3Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl2And3()) {
            setState3StartPos(getState2StartX(), getState2StartY());
            SetPos(getState2StartX(), getState2StartY());
        } else {
            // �ŏ��͐��̕����ɓ���
            if(IsStateFinished() == 1 || fX <= 0) {
                setIsStateFinished(0);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            } else {
                // do nothing
            }
            CreateRandomBullet(2);
            CreateRadiationBullet(3);
            
        }
    }

    // �{�X�����S����Ƃ��̃X�e�[�g
    @Override
    public void DeathState() {
        BossBullet[] bullet;
        if(IsStateFinished() == 0) {
            setIsStateFinished(1);
            setState3FinishTime(getTime());
            setDeathStateStart(getTime());
            // �{�X���~�܂�
            SetVX(0); SetVY(0);
            // �e�����ׂď���
            bullet = _bmanager.GetBossBullet();
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].IsEnable()) {bullet[i].Enable(false);}
            }
            // ���S�X�e�[�g�J�n�̃t���O
            _bmanager.SetIsDead(true);
        } else {
            int time = getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan() + getWinkFinishSpan();
            if(getTime() >= time - getScreenWinkSpan() - getWinkFinishSpan() && getTime() < time - getWinkFinishSpan()) {
                if(getTime() % getScreenWinkIntvl() == 0) {
                    getSoundEffect().PlayBomb();
                }
            }
            if(getTime() == time) {
                if(!_bmanager.IsDeadState()) {_bmanager.SetIsDeadState(true);}
            } else if(getTime() > time + getClearBGMSpan()){
                _bmanager.SetIsClearBGM(true);
            }
        }
    }
}


// ----------------------------------------------------------------------------------------------------------

// �X�e�[�W4�̃{�X�N���X
class Boss4 extends Boss {
    // Warning�̃t�H���g
    private Font warfont = new Font("Arial", Font.BOLD, 40);

    // �{�X4�̒e���N���X�i�����N���X�ABossBullet�̌p���j
    class BossBullet4 extends BossBullet {
        // �e�^�C�v
        int type;
        // �R���X�g���N�^
        public BossBullet4() {
            super();
        }

        @Override
        public void Show(Graphics2D g2) {
            if(!isEnable) return;

            // �e�^�C�v�ɂ���ďꍇ����
            switch(getBossBulletType()) {
                case 0:
                    g2.setPaint(Color.red);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 1:
                    g2.setPaint(Color.YELLOW);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 2:
                    g2.setPaint(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 3:
                    g2.setPaint(Color.magenta);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 4:
                    g2.setPaint(Color.pink);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                default:
                    break;
            }
        }
    }

    // �R���X�g���N�^
    public Boss4(BossManager manager) {
        super(manager);
    }

    // �e�^�C�v�̏�����
    @Override
    public BossBullet[] initBullet(BossBullet[] b) {
        for(int i = 0; i < GetBossBulletMax(); ++i) {
            b[i] = new BossBullet4();
        }
        return b;
    }

    @Override
    public void init() {
        super.init();

        // ���W�⑬�x�̐ݒ�
        SetPos(240f, 0f);
        SetVX(0f); SetVY(1f);
        setVAppear(0f, 2f);
        setStartTime(_bmanager.GetTime());
        Enable(true);
        
        // �X�^�[�g���Ԃ̐ݒ�
        // ���Ԋ֘A�̐ݒ�
        setStartTime(_bmanager.GetMain().GetTime());
        setAppearSpan(60);
        setAppearBattleIntvl(30);
        setAppearFinishTime(getStartTime() + getAppearSpan());
        setBattleStartTime(getAppearFinishTime() + getAppearBattleIntvl());
        setState1and2Intvl(70);
        setState2and3Intvl(70);
        setBossWinkIntvl(4);
        setBossWinkSpan(70);
        setScreenWinkIntvl(2);
        setScreenWinkSpan(10);
        setWinkFinishSpan(30);
        setClearBGMSpan(70);

        // HP�̐ݒ�
        setHP(100);
        setHP_MAX(100);

        // �e���֘A�̐ݒ�
        SetBossBulletMax(300);
        setEimSpeed(20);
        SetEimIntvl(8);
        SetRotateIntvl(1);
        SetRotateSpeed(20);
        SetCurrentAngle(0);
        SetAngleAdjust(30, 30);
        SetPieceNum(8);
        SetFireworkSpeed(10);
        SetFireworkShootIntvl(5);
        SetShootExplodeIntvl(10);
        SetVelocityTimesFirework(2);
        SetRadiationIntvl(10);
        SetRadiationNumber(20);
        SetRadiationSpeed(10);
        SetAngleAdd(15);
        SetHowManyAdded(0);
        SetRandomIntvl(1);
        SetRandomSpeed(15);
        SetRandomRange(60);

        // ������֘A�̐ݒ�("Warning"�Ȃ�)
        setAppearStringIntvl(10);
        setAppearStringPos(100, 250);

        // �e���̃I�u�W�F�N�g�̐ݒ�
        SetBossBullet(new BossBullet[GetBossBulletMax()]);
        SetBossBullet(initBullet(GetBossBullet()));
        _bmanager.SetBossBullet(GetBossBullet());
        _bmanager.SetBossBulletMax(GetBossBulletMax());

        // ���@�̃I�u�W�F�N�g�̐ݒ�
        SetFighter(_bmanager.GetFighter());
    }

    @Override
    public void Show(Graphics2D g2) {
        super.Show(g2);

        // HP�o�[
        if(!IsAppear() && !IsDeath()) {
            g2.setPaint(Color.white);
            g2.drawString("�{�XHP", 20, 20);
            float rate = (float)getHP() / (float)getHP_MAX();
            g2.setPaint(new Color((int)(255f - 255f * rate), (int)(0f + 255f * rate), 0));
            g2.fillRect(20, 40, (int)Math.ceil(440f * rate), 10);
        }

        // ���S�X�e�[�g�łȂ��Ƃ��A�Ⴕ���͎��S�X�e�[�g�Ŕ�������O�ɁA�{�X��`�悷��
        if(!IsDeath() || (IsDeath() && (getTime() <= getDeathStateStart() + getBossWinkSpan()))) {
            if(IsDeath() && (getTime() % getBossWinkIntvl() <= getBossWinkIntvl() / 2)) {
                // do nothing
            } else {
                Image img = Toolkit.getDefaultToolkit().getImage("Boss1.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
            }
        }

        // �o���X�e�[�g�ɂ����āAWarning�ƕ\��
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.yellow);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // �o���X�e�[�g�ɂ����āAWarning�ƕ\��
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.RED);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // ���S�X�e�[�g�ɂāA�������[�V�����\��
        if(IsDeath() && getTime() >= getDeathStateStart() + getBossWinkSpan() && getTime() < getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan()) {
            if(getTime() % getScreenWinkIntvl() == getScreenWinkIntvl() / 2) {
                g2.setPaint(Color.white);
                g2.fillRect(0, 0, 480, 640);
            }
        }
    }

    @Override
    public void Move() {
        super.Move();
    }

        // ----------- �e���n���\�b�h ------------ //
    // ���@�_���̒e�𐶐�
    @Override
    public void CreateEimsBullet(int type) {
        super.CreateEimsBullet(type);
    }
    
    // �Q�����e���̐���
    @Override
    public void CreateRotateBullet(int type) {
        super.CreateRotateBullet(type);
    }

    // �ԉΒe���̐���
    @Override
    public void CreateFireWorksBullet(int type, int typeafter) {
        super.CreateFireWorksBullet(type, typeafter);
    }

    @Override
    public void CreateRadiationBullet(int type) {
        super.CreateRadiationBullet(type);
    }

    // ----------- �X�e�[�g�n���\�b�h ---------- //

    @Override
    public void Appear() {
        super.Appear();
        if(getTime() < getAppearFinishTime()) {
            SetVX(getVXAppear()); SetVY(getVYAppear());
        } else if(getTime() >= getAppearFinishTime() && getTime() < getBattleStartTime()) {
            SetVX(0); SetVY(0); setState1StartPos(GetX(), GetY());
        }
    }

    @Override
    public void Battle() {
        super.Battle();
        if(getTime() >= getBattleStartTime() && getHP()>= getHP_MAX()* 2 / 3) {
            BattleState1();
        } else if(getHP() < (getHP_MAX()* 2 / 3) && getHP() >= (getHP_MAX() / 3)) {
            BattleState2();
        } else if(getHP() > 0 && getHP() < (getHP_MAX() / 3)) {
            BattleState3();
        }
        if(getHP() <= 0) {DeathState();}
    }

    // �{�X4�̏��1
    @Override
    public void BattleState1() {
        if(GetVX() == 0 && GetVY() == 0) SetVX(10); SetVY(0);
        if(fX <= 0 && GetVX() < 0) {
            SetVX(10); SetVY(0);
        } else if(fX >= 430 && GetVX() > 0) {
            SetVX(-10); SetVY(0);
        }
        CreateEimsBullet(2);
    }

    // �{�X4�̏��2
    @Override
    public void BattleState2() {
        Random rand = new Random();
        // ���1���I������΂���̎�
        if(IsStateFinished() == 0) {
            setState1FinishTime(getTime());
            setBattleState2Start(getState1FinishTime() + getState1and2Intvl());
            setIsStateFinished(3);
        }
        if(IsIntvl1And2()) {
            setState2StartPos(getState1StartX(), getState1StartY());
            SetPos(getState1StartX(), getState1StartY());
        } else {
            // �ŏ��͐��̕����ɓ���
            if((IsStateFinished() == 1 && fX == getState2StartX()) || fX <= 0) {
                setIsStateFinished(2);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            }
            CreateFireWorksBullet(2, 4);
        }
    }

    // �{�X4�̏��3
    @Override
    public void BattleState3() {
        // ���2���I������΂���̎�
        if(IsStateFinished() == 2) {
            setState2FinishTime(getTime());
            setBattleState3Start(getState2FinishTime() + getState2and3Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl2And3()) {
            setState3StartPos(getState2StartX(), getState2StartY());
            SetPos(getState2StartX(), getState2StartY());
        } else {
            // �ŏ��͐��̕����ɓ���
            if(IsStateFinished() == 1 || fX <= 0) {
                setIsStateFinished(0);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            } else {
                // do nothing
            }
            CreateRandomBullet(0);
        }
    }

    // �{�X�����S����Ƃ��̃X�e�[�g
    @Override
    public void DeathState() {
        BossBullet[] bullet;
        if(IsStateFinished() == 0) {
            setIsStateFinished(1);
            setState3FinishTime(getTime());
            setDeathStateStart(getTime());
            // �{�X���~�܂�
            SetVX(0); SetVY(0);
            // �e�����ׂď���
            bullet = _bmanager.GetBossBullet();
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].IsEnable()) {bullet[i].Enable(false);}
            }
            // ���S�X�e�[�g�J�n�̃t���O
            _bmanager.SetIsDead(true);
        } else {
            int time = getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan() + getWinkFinishSpan();
            if(getTime() >= time - getScreenWinkSpan() - getWinkFinishSpan() && getTime() < time - getWinkFinishSpan()) {
                if(getTime() % getScreenWinkIntvl() == 0) {
                    getSoundEffect().PlayBomb();
                }
            }
            if(getTime() == time) {
                if(!_bmanager.IsDeadState()) {_bmanager.SetIsDeadState(true);}
            } else if(getTime() > time + getClearBGMSpan()){
                _bmanager.SetIsClearBGM(true);
            }
        }
    }
}