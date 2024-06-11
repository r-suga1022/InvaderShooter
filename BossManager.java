import java.awt.Graphics2D;

public class BossManager extends EnemyManager {
    // �{�X�̃I�u�W�F�N�g
    private Boss[] _boss = {new Boss1(this), new Boss2(this), new Boss3(this), new Boss4(this)};
    private int BossNumber = 4;
    // �e���̃I�u�W�F�N�g
    private BossBullet[] _bossbullet;
    public BossBullet[] GetBossBullet() { return _bossbullet; }
    public void SetBossBullet(BossBullet[] b) { _bossbullet = b; }
    // �e���̍ő吔
    private int BOSSBULLET_MAX;
    public void SetBossBulletMax(int m) { BOSSBULLET_MAX = m;}

    // �{�X�����S�������ۂ�
    private boolean isdead = false;
    public void SetIsDead(boolean dead) {isdead = dead;}

    // �{�X�̎��S�X�e�[�g�̍Œ����ۂ�
    private boolean isdeadstate = false;
    public void SetIsDeadState(boolean dead) {isdeadstate = dead; SetIsDead(false);}
    public boolean IsDeadState() { return isdeadstate;}

    // �{�X�N���A�Ȃ𗬂��ĉ�ʂ��ڂ����Ԃ��ۂ�
    private boolean isclearbgm = false;
    public void SetIsClearBGM(boolean flag) { isclearbgm = flag; SetIsDeadState(false);}

    // ���݂̃{�X�̃X�e�[�W��
    private int boss_num;
    public void setBossNum(int num) { boss_num = num;}
    public int getBossNum() {return boss_num;}

    // �R���X�g���N�^
    public BossManager(MainGameState main) {
        super(main);

        setSoundEffect(_main.GetSoundEffect());
        setBossNum(main.GetStageNum());

        _boss[boss_num-1].init();

        init();
    }

    // ������
    //@Override
    public void init() {
        super.init();
    }

    // �X�V
    public void update(int timer) {
        SetTime(timer);
        _boss[boss_num-1].setTime(timer);

        BossStateCheck();
        BossMove();
        BulletMove();
        Dead();
    }

    // �`��Ɋւ��郁�\�b�h
    public void Show(Graphics2D g2) {
        super.Show(g2);
        BossShow(g2);
        BulletShow(g2);
    }

    public void BossShow(Graphics2D g2) {
        _boss[boss_num-1].Show(g2);
    }

    // �{�X�̓����Ɋւ��郁�\�b�h
    public void BossMove() {
        _boss[boss_num-1].Move();
    }

    public void BossStateCheck() {
        _boss[boss_num-1].Appear();
        _boss[boss_num-1].Battle();
    }

    // �e�𓮂���
    public void BulletMove() {
        for(int i=0; i<BOSSBULLET_MAX; i++)
		{
			if(_bossbullet[i] == null) return;

			if(!_bossbullet[i].IsEnable()) continue;

			_bossbullet[i].Move();

			if(((_bossbullet[i].GetX() >= 530)||(_bossbullet[i].GetX() <= -50))||((_bossbullet[i].GetY() >= 690)||(_bossbullet[i].GetY() <= -50)))
				_bossbullet[i].Enable(false);
		}
    }

    // �e��\������
    public void BulletShow(Graphics2D g2)
	{
		for(int i=0; i<BOSSBULLET_MAX; i++)
		{
			if(_bossbullet[i] == null) return;
			if(!_bossbullet[i].IsEnable()) continue;
			
			_bossbullet[i].Show(g2);
		}
	}

    // �{�X�����S�����Ƃ��Ƀt���O��MainGameState�ɓn��
    public void Dead() {
        if(isdead == true) {
            _main.setIsBoss(3);
        } else if(isdeadstate == true) {
            _main.setIsBoss(4);
            SetIsDeadState(false);
        } else if(isclearbgm == true) {
            _main.setIsBoss(5);
        }
    }

    // �����蔻����s��
    @Override
    public boolean HitCheck()
	{
		boolean rtn = false;
        
        _boss[boss_num-1].HitCheckBossAndShot();

        rtn = _boss[boss_num-1].HitCheckBossBulletAndFighter();
		
		return rtn;
	}
}
