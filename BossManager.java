import java.awt.Graphics2D;

public class BossManager extends EnemyManager {
    // ボスのオブジェクト
    private Boss[] _boss = {new Boss1(this), new Boss2(this), new Boss3(this), new Boss4(this)};
    private int BossNumber = 4;
    // 弾幕のオブジェクト
    private BossBullet[] _bossbullet;
    public BossBullet[] GetBossBullet() { return _bossbullet; }
    public void SetBossBullet(BossBullet[] b) { _bossbullet = b; }
    // 弾幕の最大数
    private int BOSSBULLET_MAX;
    public void SetBossBulletMax(int m) { BOSSBULLET_MAX = m;}

    // ボスが死亡したか否か
    private boolean isdead = false;
    public void SetIsDead(boolean dead) {isdead = dead;}

    // ボスの死亡ステートの最中か否か
    private boolean isdeadstate = false;
    public void SetIsDeadState(boolean dead) {isdeadstate = dead; SetIsDead(false);}
    public boolean IsDeadState() { return isdeadstate;}

    // ボスクリア曲を流して画面を移せる状態か否か
    private boolean isclearbgm = false;
    public void SetIsClearBGM(boolean flag) { isclearbgm = flag; SetIsDeadState(false);}

    // 現在のボスのステージ数
    private int boss_num;
    public void setBossNum(int num) { boss_num = num;}
    public int getBossNum() {return boss_num;}

    // コンストラクタ
    public BossManager(MainGameState main) {
        super(main);

        setSoundEffect(_main.GetSoundEffect());
        setBossNum(main.GetStageNum());

        _boss[boss_num-1].init();

        init();
    }

    // 初期化
    //@Override
    public void init() {
        super.init();
    }

    // 更新
    public void update(int timer) {
        SetTime(timer);
        _boss[boss_num-1].setTime(timer);

        BossStateCheck();
        BossMove();
        BulletMove();
        Dead();
    }

    // 描画に関するメソッド
    public void Show(Graphics2D g2) {
        super.Show(g2);
        BossShow(g2);
        BulletShow(g2);
    }

    public void BossShow(Graphics2D g2) {
        _boss[boss_num-1].Show(g2);
    }

    // ボスの動きに関するメソッド
    public void BossMove() {
        _boss[boss_num-1].Move();
    }

    public void BossStateCheck() {
        _boss[boss_num-1].Appear();
        _boss[boss_num-1].Battle();
    }

    // 弾を動かす
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

    // 弾を表示する
    public void BulletShow(Graphics2D g2)
	{
		for(int i=0; i<BOSSBULLET_MAX; i++)
		{
			if(_bossbullet[i] == null) return;
			if(!_bossbullet[i].IsEnable()) continue;
			
			_bossbullet[i].Show(g2);
		}
	}

    // ボスが死亡したときにフラグをMainGameStateに渡す
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

    // 当たり判定を行う
    @Override
    public boolean HitCheck()
	{
		boolean rtn = false;
        
        _boss[boss_num-1].HitCheckBossAndShot();

        rtn = _boss[boss_num-1].HitCheckBossBulletAndFighter();
		
		return rtn;
	}
}
