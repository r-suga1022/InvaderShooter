
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Fighter extends BaseObject{
	protected int nLeft;	// 残機数
	protected int bLeft;	// ボム残り
	protected int Killn;	// 倒した敵の数
	final public static int MAX_NLEFT = 9;  //残機の最大数

	// キー入力のフラグ
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

	private int numShot;	// 画面表示ショット量
	private int numValidShot; // 現在作れるショット数
	private int shotTimer = 0;
	private int muteki = 0; //無敵
	private int time;//経過時間
	private int mutekitime = 50;//無敵時間
	private int muteki_begin_time;//無敵開始時間
	private Shot shot[];
	private int attack;//攻撃力

	private int ATTACK_MAX = 20;
	public int GetAttackMax() { return ATTACK_MAX;}
	private int ATTACK_MIN = 5;
	public int GetAttackMin() {return ATTACK_MIN;}
	private int attack_decrease = 2;
	public int GetAttackDecrease() { return attack_decrease;}

	private BGM _bgm; //BGMの管理用
	private SoundEffect _effect; //効果音の管理用
	public void setBGM(BGM bgm) { _bgm = bgm; }
	public void setSoundEffect(SoundEffect effect) { _effect = effect; }

	// 内部クラス化された自機ショット管理クラス
	// ＊戦闘機はショット生成だけを受け持ち。移動はショット自身が行う
	class Shot extends BaseObject{

		// コンストラクタ
		public Shot()
		{
			super();
		}

		// 弾移動
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

		// 弾表示
		public void Show(Graphics2D g2)
		{

			g2.setPaint(Color.white);
			if(bKeyShift == 2){  //低速移動中は色変更
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
		attack = 10;//初期値
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

	public void SetAttack(int n){//攻撃力セット
		attack = n;
	}

	public int GetAttack(){//攻撃力ゲット
		return attack;
	}

	public void SetMbegin(int n){//無敵開始時間セット
		muteki_begin_time = n;
	}

	public void SetTime(int n){//経過時間セット
		time = n;
	}

	public int GetMuteki(){//無敵フラグを渡す
		return muteki;
	}

	public void SetMuteki(int n){//無敵フラグ立て
		muteki = n;
	}

	public int GetbLeft(){//ボム残りを返す
		return bLeft;
	}

	public void SetbLeft(int n){//ボム残りをセット
		bLeft = n;
	}

	public int GetbKeyX(){//Xの状態を返す
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


	// このへん一緒の関数にも出来るけど名前の付け方めんどくさいので
	public void Show(Graphics2D g2)
	{
		for(int i=0; i<numShot; i++)
		{
			shot[i].Show(g2);
		}
		MutekiCheck();
		if(isEnable && muteki == 0){//無敵か無敵でないかで場合分けして表示
			Image img = Toolkit.getDefaultToolkit().getImage("fighter1.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
		}else if(!isEnable && muteki == 1){
			Image img = Toolkit.getDefaultToolkit().getImage("fighter_hit1.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
		}
		if(bKeyShift == 2){//SHIFTキーを押したときにオーラを出す
			Image img = Toolkit.getDefaultToolkit().getImage("fighterS.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
		}

		if(bKeyX == 2 && muteki != 1){
			Image img = Toolkit.getDefaultToolkit().getImage("fighterB.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
		}
	}

	// 移動
	public void Move()
	{
		// ショット
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


	// ショット生成
	public void Shoot()
	{

		// ボタンおしっぱじゃなくてボタン最初に押した時
		if(bKeyZ == KB_TRIG)
		{
			shotTimer = 0;
		}

		// ボタン押されとるかー？
		if(bKeyZ == KB_PUSH)
		{
			// 2回に一回作るよ。弾の位置を調整
			if(shotTimer % 2 == 0)
			{
				if(numValidShot>=2)
				{
					// ショット音を出す
					_effect.PlayFighterShot();			
						// まず作れるやつあるかどうか見るよ
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

	// 残機を減らす
	//攻撃力も
	public void DecreasenLeft(int n) {
		SetnLeft(GetnLeft()-n);
	}

	public void DecreaseAttack(int n){
		if(GetAttack()-n>ATTACK_MIN) SetAttack(GetAttack()-n);
	}

	// ゲームオーバー判定
	public boolean isGameOver() {
		if(nLeft == 0) {return true;}
		else {return false;}
	}

	public void MutekiCheck(){//無敵が終わったかをチェック
		if(muteki == 1){
			if(time == mutekitime + muteki_begin_time){
				muteki = 0;
				this.Enable(true);
			}
		}
	}


	// ボタン押してるとき
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

	// ボタン離したとき
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

	// ボタン押した瞬間
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
