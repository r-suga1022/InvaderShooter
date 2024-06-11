import java.awt.Graphics2D;

public class EnemyManager {
	public final static int ENEMY_MAX	= 	10;
	public final static int BULLET_MAX		=	30;
	
	private Enemy[]	_enemy = new Enemy[ENEMY_MAX];
	public Enemy[] GetEnemy(){return _enemy;}
	private Bullet[] _bullet = new Bullet[BULLET_MAX];
	public Bullet[] GetBullet(){ return _bullet;}
	
	private Fighter _fighter = null;
	public Fighter GetFighter(){return _fighter;}
	
	private StageAnalyze _stage = null;
	public StageAnalyze GetStage(){return _stage;}
	
	private int _time = 0;
	public int GetTime(){return _time;}
	public void SetTime(int time) { _time = time;} 

	private SoundEffect _effect;
	public void setSoundEffect(SoundEffect effect) { _effect = effect; }
	public SoundEffect getSoundEffect() {return _effect;} 

	protected MainGameState _main;
	public MainGameState GetMain() {return _main;}

	private int bTime = -100;// �{���_�ŊJ�n���ԁi�����l���������Ȃ��ƍŏ��ɂ������������Ⴄ�j
	
	public EnemyManager(MainGameState main)
	{
		_fighter = main.GetFighter();
		_stage = main.GetStage();
		_main = main;
		init();
	}
	
	public void init()
	{		
		for(int i=0;i<BULLET_MAX;i++)
		{
			_bullet[i] = new Bullet();
		}
	}

	public void update(int timer)
	{
		_time = timer;
		
		BossCheck();

		EnemyCreate();
		BulletCreate();
		
		EnemyMove();
		BulletMove();
	}
	
	public void Show(Graphics2D g2)
	{
		EnemyShow(g2);
		BulletShow(g2);
		//������������
		if(bTime<=GetTime() && GetTime()<=bTime+6 && GetTime()%4==0){
			g2.fillRect(0, 0, 480, 620);
		}
	}
	
	// �G�𓮂���
	public void EnemyMove()
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;

			if(!_enemy[i].IsEnable()) continue;

			_enemy[i].Move();

			if(((_enemy[i].GetX() >= 530)||(_enemy[i].GetX() <= -50))||((_enemy[i].GetY() >= 690)||(_enemy[i].GetY() <= -50)))
				_enemy[i].Enable(false);
		}
	}

	// �e�𓮂���
	public void BulletMove()
	{
		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null) return;

			if(!_bullet[i].IsEnable()) continue;

			_bullet[i].Move();

			if(((_bullet[i].GetX() >= 530)||(_bullet[i].GetX() <= -50))||((_bullet[i].GetY() >= 690)||(_bullet[i].GetY() <= -50)))
				_bullet[i].Enable(false);
		}
	}
 
	public void EnemyCreate()
	{
		// 
		for(int i=0; i<_stage.GetStringNumber(); i++)
		{
			if(Integer.parseInt(_stage.GetScenario().get(i)[0]) == _time)
			{
				for(int j=0; j<ENEMY_MAX; j++)
				{
					if(_enemy[j] == null)
					{
						_enemy[j] = _stage.GetTemporaryEnemy(this,i);
						_enemy[j].Enable(true);
						break;
					}	
					else if(_enemy[j].IsEnable()) continue;

					_enemy[j] = _stage.GetTemporaryEnemy(this,i);
					_enemy[j].Enable(true);
					break;
				}
			}
		}
	}

	// �e�𐶐�����
	public void BulletCreate()
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;
			if(!_enemy[i].IsEnable()) continue;

			_enemy[i].Fire();
		}
	}
	
	public void EnemyShow(Graphics2D g2)
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;
			if(!_enemy[i].IsEnable()) continue;
			
			_enemy[i].Show(g2);
		}
	}

	public void BulletShow(Graphics2D g2)
	{
		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null) return;
			if(!_bullet[i].IsEnable()) continue;

			switch(_bullet[i].getAppearancetype()){
				case 0:
				_bullet[i].Show2(g2);
				break;

				case 1:
				_bullet[i].Show(g2);
				break;

				case 2:
				_bullet[i].Show3(g2);
				break;
				
			}
		}
	}
	
	//
	public boolean HitCheck()
	{
		boolean rtn = false;
		BombCheck();
		HitCheckEnemyAndShot();
		rtn = HitCheckEnemyAndFighter() | HitCheckBulletAndFighter();
		
		return rtn;
	}

	// �G�Ǝ��@�̓����蔻��
	private boolean HitCheckEnemyAndFighter()
	{
		if(!_fighter.IsEnable()) return false;

		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null || !_enemy[i].IsEnable()) continue;

			float dx, dy, width, height;
			
			dx = _enemy[i].GetX() - _fighter.GetX();
			dy = _enemy[i].GetY() - _fighter.GetY() - 23;

			width  = 30;
			height = 70;

			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				_effect.PlayPowerDown();

				_enemy[i].DecreaseHP(10);
				_fighter.Enable(false);
				_fighter.DecreasenLeft(1);
				_fighter.DecreaseAttack(2);
				// �ǉ�
				if(_fighter.GetAttack() < _fighter.GetAttackMin()) {
					_fighter.SetAttack(_fighter.GetAttackMin());
				}
				_fighter.SetMbegin(_time);
				_fighter.SetMuteki(1);
				return true;
			}
		}
			
		return false;
	}

	// �G�Ǝ��@�̒e�̓����蔻��
	private void HitCheckEnemyAndShot()
	{
		for(int i=0;i<_fighter.GetNumShot();i++)
		{
			// �e���L���łȂ��������΂�
			if(!_fighter.GetShot()[i].IsEnable()) continue;

			// ��ʏ�̑S�Ă̓G������
			for(int j=0;j<ENEMY_MAX;j++)
			{
				// �G���L���łȂ��������΂�
				if(_enemy[j] == null || !_enemy[j].IsEnable()) continue;
					float dx, dy, width, height;
				dx = _enemy[j].GetX() - _fighter.GetShot()[i].GetX() - 5;
				dy = _enemy[j].GetY() - _fighter.GetShot()[i].GetY();

				width = 50;
				height = 60;

				if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
				{
					
					int n = _fighter.GetShift();
					int a = _fighter.GetAttack();//�U���͎󂯎��
					if(n == 2){
						_enemy[j].DecreaseHP(2*a);
					}else{
						_enemy[j].DecreaseHP(a);
					}
					_fighter.GetShot()[i].Enable(false);

					// �G�ɒ��e���鉹
					_effect.PlayShotHitEnemy();
					break;
				}
			}
		}
	}

	// �G�̒e�Ǝ��@�̓����蔻��
	private boolean HitCheckBulletAndFighter()
	{
		if(!_fighter.IsEnable()) return false;

		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null || !_bullet[i].IsEnable()) continue;
				float dx, dy, width, height;
			dx = _bullet[i].GetX() - _fighter.GetX();
			dy = _bullet[i].GetY() - _fighter.GetY() - 23;

			width = 20;
			height = 28;

			// ����������
			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				_effect.PlayPowerDown();

				_bullet[i].Enable(false);
				_fighter.Enable(false);
				_fighter.DecreasenLeft(1);
				_fighter.DecreaseAttack(2);
				// �ǉ�
				if(_fighter.GetAttack() < _fighter.GetAttackMin()) {
					_fighter.SetAttack(_fighter.GetAttackMin());
				}
				_fighter.SetMbegin(_time);
				_fighter.SetMuteki(1);
				return true;
			}
		}		
		return false;
	}
	// �{���̃`�F�b�N
	private void BombCheck(){
		int b = _fighter.GetbLeft();
		int x=0, y=0;
		if(_fighter.GetbKeyX() == 2){
			if(b>0){
				for(int j=0;j<ENEMY_MAX;j++){
					// �G���L���łȂ��������΂�
					if(_enemy[j] == null || !_enemy[j].IsEnable()) continue;
					_enemy[j].DecreaseHP(100);
					x = 1;
				}
				for(int i=0; i<BULLET_MAX; i++){
					if(_bullet[i] == null || !_bullet[i].IsEnable()) continue;
					_bullet[i].Enable(false);
					y = 1;
				}
			}
			_effect.PlayBomb();
			if(x == 1 || y == 1){
				bTime = GetTime();//�J�n���ԑ��
				_fighter.SetbLeft(b-1);
			}
		}
	}

	// �{�X�̊J�n���Ԃ��ۂ��𒲂ׂ�
	public void BossCheck() {
		int bosstime = Integer.parseInt(_stage.GetScenario().get(_stage.GetStringNumber())[1]);
		if(_main.getFadeStartTime() == 0) {
			_main.setFadeStartTime(Integer.parseInt(_stage.GetScenario().get(_stage.GetStringNumber())[2]));
			_main.setFadeOutFinishTime(Integer.parseInt(_stage.GetScenario().get(_stage.GetStringNumber())[3]));
		}
		if(_time == _main.getFadeStartTime()) {
			System.out.println("Prepare\n");//
			_main.setIsBossPrepare(1);
		}
		if(_time == bosstime) {_main.setIsBoss(1);}
	}
}
