import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.*;//�w�i�̂��߂ɒǉ�
import java.awt.geom.*;
@SuppressWarnings("removal")
public class MainGameState implements ModeState{

	private String[] stage_data = {"stage1.txt", "stage2.txt", "stage3.txt", "stage4.txt"};

	private Fighter	_fighter = null;
	public Fighter GetFighter(){return _fighter;}
	
	private ExitState _exit = null;
	public ExitState GetExitState(){return _exit;}

	// �X�e�[�W�f�[�^�ǂݍ��ݗp
	private StageAnalyze _analyze;
	public StageAnalyze GetStage(){return _analyze;}

	// �Q�[�����^�C�}�[
	private int _gameTimer;
	public int GetTime(){return _gameTimer;}

	// �G�L�����̊Ǘ��p
	private EnemyManager _emanager;

	// �A�C�e���̊Ǘ��p
	private ItemManager _imanager;

	// BGM�̊Ǘ��p
	private BGM _bgm;
	public void setBGM(BGM b) { _bgm = b;}
	public BGM getBGM() {return _bgm;}
	// ���ʉ��̊Ǘ��p
	private SoundEffect _effect;
	public void setSoundEffect(SoundEffect se) { _effect = se;}
	public SoundEffect GetSoundEffect() {return _effect;}

	private int stage;

	public void SetStage(int n){stage = n;}
	public int GetStageNum() { return stage;}

	private int isBOSS = 0; // 0:�{�X�햢�J��, 1:�{�X��J�n, 2:�{�X�풆, 3:�{�X���S, 4:�N���A�X�e�[�g�Œ�, 5:�N���A
	public void setIsBoss(int flag) { isBOSS = flag;}
	private int isBossPrepare = 0; // 0:�{�X�����O, 1:�{�X������
	public void setIsBossPrepare(int flag) { isBossPrepare = flag;}

	private int FadeOutFinishTime; // �t�F�[�h�A�E�g���I��鎞��
	public void setFadeOutFinishTime(int foft) { FadeOutFinishTime = foft;}
	public int getFadeOutFinishTime() {return FadeOutFinishTime;}
	private int FadeStartTime = 0; // �t�F�[�h�A�E�g���n�܂鎞��
	public void setFadeStartTime(int fst) { FadeStartTime = fst;}
	public int getFadeStartTime() { return FadeStartTime;}

	// �w�i�X�N���[���̃X�s�[�h
	private float scrollspeed = 1f;//
	private float backgroundY = 0;//

	// �L�[���͂̃t���O
	private boolean m_bKeyR = false;
	public void KeyR(boolean flag) { m_bKeyR = flag;}

	
	public MainGameState()
	{

	}
	
	// �������p
	public void init()
	{		
		_fighter = new Fighter();

		_exit = new ExitState();

		_fighter.Enable(true);
		_fighter.SetPos(250, 500);
		_fighter.SetVX(16.0f);
		_fighter.SetVY(16.0f);
		
		_analyze	= new StageAnalyze();

		_analyze.Analyze(stage_data[stage-1]);

		_emanager = new EnemyManager(this);

		_imanager = new ItemManager(this);

		_fighter.setBGM(_bgm);
		_bgm.PlayLoopMainGameBGM();

		_fighter.setSoundEffect(_effect);
		_emanager.setSoundEffect(_effect);
		_imanager.setSoundEffect(_effect);
		
		_gameTimer = 0;
	}

	@Override
	public void Show(Graphics2D g2) {
		// TODO Auto-generated method stub
		Image img = Toolkit.getDefaultToolkit().getImage("back.png");
		g2.drawImage(img, 0, (int)backgroundY, 480, 640, null);//���w�i(1/24����)
		g2.drawImage(img, 0, (int)backgroundY - 640, 480, 640, null);
		backgroundY += scrollspeed;
		if(backgroundY == 640) backgroundY = 0;
		_fighter.Show(g2);
		_emanager.Show(g2);

		_imanager.Show(g2);

		Image img1 = Toolkit.getDefaultToolkit().getImage("State.png");
		g2.drawImage(img1, new AffineTransform(1f,0f,0f,1f,480,0), null);
		
		g2.setColor(Color.white);
		g2.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 20));
		g2.drawString("Stage " + new Integer(stage).toString(), 490, 30);
		g2.drawString("�X�R�A:" + new Integer(_fighter.GetKilln()).toString(), 490, 60);//1/20����
		g2.drawString("�U����:" + new Integer(_fighter.GetAttack()).toString(), 490, 90);

		if(isBOSS == 4) {
			g2.setPaint(Color.yellow);
			g2.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 50));
			g2.drawString("You Won!", 150, 320);
		}

		//�c�@���摜(��)�ŕ\���B
		Image imgLeft = Toolkit.getDefaultToolkit().getImage("fighter1.png");
		int left = _fighter.nLeft;
		for(int i=1; i<=left; i++){
			if(i <= 5){
				g2.drawImage(imgLeft, 470+i*30, 480, 18, 22, null);
			}else{
				g2.drawImage(imgLeft, 470+(i-5)*30, 510, 18, 22, null);
			}
		}

		int Bleft = _fighter.bLeft;
		Image imgLeftB = Toolkit.getDefaultToolkit().getImage("bomb.png");
		for(int i=1; i<=Bleft; i++){
			g2.drawImage(imgLeftB, 460+i*40, 350, 33, 24, null);
		}
	}

	@Override
	public void run(GameManager gm) {

		// �X�e�[�W���o�鏈��
		if(m_bKeyR) {
			_bgm.Finish(_bgm.getClip());
			StageSelectState ss = new StageSelectState();
			ss.setBGM(_bgm);
			ss.setSoundEffect(_effect);
			ss.init();
			gm.ChangeMode(ss);
		}

		// �{�X��������
		if(isBossPrepare == 1) {
			_bgm.FadeOut(_gameTimer, FadeOutFinishTime, _bgm.getMainGameBGMVolume());
		}
		// �{�X��̍Œ�
		if(isBOSS == 1) {
			isBossPrepare = 0;
			_emanager = new BossManager(this);
			_emanager.setSoundEffect(_effect);
			_bgm.PlayLoopBossBGM();
			isBOSS = 2;
			System.out.println("�{�X�t���O����");//
		} else if(isBOSS == 2) {
			// do nothing
		} else if(isBOSS == 3) {
			_bgm.Finish(_bgm.getClip());
		} else if(isBOSS == 4) {
			// �N���A�Ȃ𗬂�
			_bgm.PlayBossClear();
			isBOSS = 2;
		} else if(isBOSS == 5) {
			_exit.SetScore(_fighter.GetKilln());
			gm.ChangeMode(_exit);
		}

		// �ړ�����
		_fighter.Move();
		
		// ���@�V���b�g�ˏo
		_fighter.Shoot();

		_fighter.SetTime(_gameTimer); //���ԓn��
		
		//  �A�C�e���̃A�b�v�f�[�g
		_imanager.update(_gameTimer);

		// �G�̃A�b�v�f�[�g
		_emanager.update(_gameTimer++);

		// �����蔻��
		if(_emanager.HitCheck()){
			// �����ǉ��A�����A2022-01-20, 23:43
			if(_fighter.isGameOver()) {
				_bgm.Finish(_bgm.getClip());
				// �ǉ��A�����A1/27
				GameoverState go = new GameoverState();
				go.setBGM(_bgm);
				go.init();
				gm.ChangeMode(go);
			}
		}	
	}
	
	@Override
	public void KeyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		_fighter.KeyPressedAnalyze(arg0);

		// �ǉ��A�����A2022-02-18, 12:13
		switch(arg0.getKeyCode())
		{
			case KeyEvent.VK_R:
				KeyR(true);
				break;
			default:
				break;
		}
	}

	@Override
	public void KeyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		_fighter.KeyReleasedAnalyze(arg0);		
	}

	@Override
	public void KeyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		_fighter.KeyTypedAnalyze(arg0);
	}

}
