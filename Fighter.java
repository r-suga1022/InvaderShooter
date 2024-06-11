
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Fighter extends BaseObject{
	protected int nLeft;	// �c�@��
	protected int bLeft;	// �{���c��
	protected int Killn;	// �|�����G�̐�
	final public static int MAX_NLEFT = 9;  //�c�@�̍ő吔

	// �L�[���͂̃t���O
	protected boolean bKeyLeft;
	protected boolean bKeyRight;
	protected boolean bKeyUp;
	protected boolean bKeyDown;
	protected int bKeyX;
	protected int bKeyZ;
	protected int bKeyShift;

	public final static int KB_NONE	=	0;
	public final static int KB_TRIG	= 	1;
	public final static int KB_PUSH	= 	2;
	public final static int KB_PULL	=	3;

	private int numShot;	// ��ʕ\���V���b�g��
	private int numValidShot; // ���ݍ���V���b�g��
	private int shotTimer = 0;
	private int muteki = 0; //���G
	private int time;//�o�ߎ���
	private int mutekitime = 50;//���G����
	private int muteki_begin_time;//���G�J�n����
	private Shot shot[];
	private int attack;//�U����

	private int ATTACK_MAX = 20;
	public int GetAttackMax() { return ATTACK_MAX;}
	private int ATTACK_MIN = 5;
	public int GetAttackMin() {return ATTACK_MIN;}
	private int attack_decrease = 2;
	public int GetAttackDecrease() { return attack_decrease;}

	private BGM _bgm; //BGM�̊Ǘ��p
	private SoundEffect _effect; //���ʉ��̊Ǘ��p
	public void setBGM(BGM bgm) { _bgm = bgm; }
	public void setSoundEffect(SoundEffect effect) { _effect = effect; }

	// �����N���X�����ꂽ���@�V���b�g�Ǘ��N���X
	// ���퓬�@�̓V���b�g�����������󂯎����B�ړ��̓V���b�g���g���s��
	class Shot extends BaseObject{

		// �R���X�g���N�^
		public Shot()
		{
			super();
		}

		// �e�ړ�
		public void Move()
		{

			if(fY >= 0)
			{
				fX += fVX;
				fY += fVY;
			}
			else
				this.Enable(false);
		}

		// �e�\��
		public void Show(Graphics2D g2)
		{

			g2.setPaint(Color.white);
			if(bKeyShift == 2){  //�ᑬ�ړ����͐F�ύX
				g2.setPaint(Color.CYAN);
			}
			g2.fill(new Ellipse2D.Double(fX - 10f, fY - 10f, 7f, 13f));

		}
	}

	public Fighter()
	{
		super();
		nLeft = 3;
		bLeft = 3;
		attack = 10;//�����l
		Killn = 0;//
		bKeyLeft = bKeyRight = bKeyUp = bKeyDown =  false;

		bKeyZ = KB_NONE;

		numShot = 6;
		numValidShot = 6;

		shot = new Shot[numShot];
		for(int i=0; i<numShot; i++)
		{
			shot[i] = new Shot();
		}
	}

	public void SetAttack(int n){//�U���̓Z�b�g
		attack = n;
	}

	public int GetAttack(){//�U���̓Q�b�g
		return attack;
	}

	public void SetMbegin(int n){//���G�J�n���ԃZ�b�g
		muteki_begin_time = n;
	}

	public void SetTime(int n){//�o�ߎ��ԃZ�b�g
		time = n;
	}

	public int GetMuteki(){//���G�t���O��n��
		return muteki;
	}

	public void SetMuteki(int n){//���G�t���O����
		muteki = n;
	}

	public int GetbLeft(){//�{���c���Ԃ�
		return bLeft;
	}

	public void SetbLeft(int n){//�{���c����Z�b�g
		bLeft = n;
	}

	public int GetbKeyX(){//X�̏�Ԃ�Ԃ�
		return bKeyX;
	}

	public int GetnLeft(){
		return nLeft;
	}

	public void SetnLeft(int n){
		nLeft = n;
	}

	public int GetKilln(){
		return Killn;
	}

	public void SetKilln(int n){
		Killn = n;
	}

	public int GetShift(){
		return bKeyShift;
	}

	public Shot[] GetShot()
	{
		return shot;
	}

	public int GetNumShot()
	{
		return numShot;
	}


	// ���̂ւ�ꏏ�̊֐��ɂ��o���邯�ǖ��O�̕t�����߂�ǂ������̂�
	public void Show(Graphics2D g2)
	{
		for(int i=0; i<numShot; i++)
		{
			shot[i].Show(g2);
		}
		MutekiCheck();
		if(isEnable && muteki == 0){//���G�����G�łȂ����ŏꍇ�������ĕ\��
			Image img = Toolkit.getDefaultToolkit().getImage("fighter1.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
		}else if(!isEnable && muteki == 1){
			Image img = Toolkit.getDefaultToolkit().getImage("fighter_hit1.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
		}
		if(bKeyShift == 2){//SHIFT�L�[���������Ƃ��ɃI�[�����o��
			Image img = Toolkit.getDefaultToolkit().getImage("fighterS.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
		}

		if(bKeyX == 2 && muteki != 1){
			Image img = Toolkit.getDefaultToolkit().getImage("fighterB.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
		}
	}

	// �ړ�
	public void Move()
	{
		// �V���b�g
		for(int i=0; i<numShot; i++)
		{
			shot[i].Move();
		}

		if(bKeyLeft)
		{
			if(fX >= 0){
				if(bKeyShift == 2){
					fX -= fVX/2;
				}else{
					fX -= fVX;
				}
			}
		}
		else if(bKeyRight)
		{
			if(fX <= 480){
				if(bKeyShift == 2){
					fX += fVX/2;
				}else{
					fX += fVX;
				}
			}
		}

		if(bKeyUp)
		{
			if(fY >= - 30){
				if(bKeyShift == 2){
					fY -= fVY/2;
				}else{
					fY -= fVY;
				}
			}
				
		}
		else if(bKeyDown)
		{
			if(fY <= 610){
				if(bKeyShift == 2){
					fY += fVY/2;
				}else{
					fY += fVY;
				}
			}
		}
	}


	// �V���b�g����
	public void Shoot()
	{

		// �{�^���������ς���Ȃ��ă{�^���ŏ��ɉ�������
		if(bKeyZ == KB_TRIG)
		{
			shotTimer = 0;
		}

		// �{�^��������Ƃ邩�[�H
		if(bKeyZ == KB_PUSH)
		{
			// 2��Ɉ�����B�e�̈ʒu�𒲐�
			if(shotTimer % 2 == 0)
			{
				if(numValidShot>=2)
				{
					// �V���b�g�����o��
					_effect.PlayFighterShot();			
						// �܂��������邩�ǂ��������
						for(int j=0; j<numShot; j++)
						{
							if(shot[j].isEnable) continue;
							shot[j].SetVX(0);
							shot[j].SetVY(-40);
							shot[j].SetPos(Fighter.this.GetX()+6, Fighter.this.GetY());
							shot[j].Enable(true);
							break;
						}
					
				}
			}
			shotTimer++;
		}
	}

	// �c�@�����炷
	//�U���͂�
	public void DecreasenLeft(int n) {
		SetnLeft(GetnLeft()-n);
	}

	public void DecreaseAttack(int n){
		if(GetAttack()-n>ATTACK_MIN) SetAttack(GetAttack()-n);
	}

	// �Q�[���I�[�o�[����
	public boolean isGameOver() {
		if(nLeft == 0) {return true;}
		else {return false;}
	}

	public void MutekiCheck(){//���G���I����������`�F�b�N
		if(muteki == 1){
			if(time == mutekitime + muteki_begin_time){
				muteki = 0;
				this.Enable(true);
			}
		}
	}


	// �{�^�������Ă�Ƃ�
	public void KeyPressedAnalyze(KeyEvent e)
	{

		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			bKeyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			bKeyRight = true;
			break;
		case KeyEvent.VK_UP:
			bKeyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			bKeyDown = true;
			break;
		case KeyEvent.VK_X:
			bKeyX	= KB_PUSH;
			break;
		case KeyEvent.VK_Z:
			bKeyZ	= KB_PUSH;
			break;
		case KeyEvent.VK_SHIFT:
			bKeyShift	= KB_PUSH;
			break;
		}
	}

	// �{�^���������Ƃ�
	public void KeyReleasedAnalyze(KeyEvent e)
	{
	

		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			bKeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			bKeyRight = false;
			break;
		case KeyEvent.VK_UP:
			bKeyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			bKeyDown = false;
			break;
		case KeyEvent.VK_X:
			bKeyX	= KB_PULL;
			break;
		case KeyEvent.VK_Z:
			bKeyZ	= KB_PULL;
			break;
		case KeyEvent.VK_SHIFT:
			bKeyShift	= KB_PULL;
			break;
		}
	}

	// �{�^���������u��
	public void KeyTypedAnalyze(KeyEvent e)
	{

		switch(e.getKeyCode())
		{
		case KeyEvent.VK_X:
			bKeyX	= KB_TRIG;
			break;
		case KeyEvent.VK_Z:
			bKeyZ	= KB_TRIG;
			break;
		case KeyEvent.VK_SHIFT:
			bKeyShift	= KB_TRIG;
			break;
		}
	}

}
