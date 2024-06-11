import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.geom.*;

public class TitleState implements ModeState{

	private final static int START	= 0;
	private final static int END		= 1;
	//チュートリアル追加
	private final static int TUTORIAL = 2;
	private int _cursorPos = START;

	// メインタイトルの位置を調整
	private final static int TITLEPOSX	= 200;
	private final static int TITLEPOSY	= 150;

	// メインメニュー表示位置。表示間隔。カーソル位置（x座標のみ）
	private final static int MENUPOSX		= 250;
	private final static int MENUPOSY		= 280;
	private final static int MENUINTVL	= 50;
	private final static int CURSOR		= 200;

	// キーフラグ
	private boolean m_bKeyUp;
	public void KeyUp(boolean on){m_bKeyUp = on;}
	private boolean m_bKeyDown;
	public void KeyDown(boolean on){m_bKeyDown = on;}
	private boolean m_bKeyZ;
	public void KeyZ(boolean on){m_bKeyZ = on;}

	// BGM、効果音用オブジェクト
	private BGM _bgm = new BGM();
	public BGM getbgm() { return _bgm; }

	// 効果音のオブジェクト
	private SoundEffect _effect = new SoundEffect();

	public TitleState()
	{
		init();
		// BGMを再生
		_bgm.PlayLoopTitleBGM();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
	
	// キー移動
	public void run(GameManager gm)
	{
		if(m_bKeyUp)
		{
			if(_cursorPos != START) {
				_cursorPos--;
				//連続入力防止
				KeyUp(false);
				// カーソル移動時の効果音を鳴らす
				_effect.PlayTitleCursorMove();
			}
		}
		else if(m_bKeyDown)
		{
			//カーソルの下限をチュートリアルへ
			if(_cursorPos != TUTORIAL) {
				_cursorPos++;
				//連続入力防止
				KeyDown(false);
				// カーソル移動時の効果音を鳴らす
				_effect.PlayTitleCursorMove();
			}
		}

		//Z押したとき
		if(m_bKeyZ)
		{
			// カーソル位置で分岐
			switch(_cursorPos)
			{
				case START:
					// 決定音を流す
					_effect.PlayTitleCursorDetermine();
					// ステージセレクト画面に移動
					StageSelectState stsele = new StageSelectState();
					stsele.setBGM(_bgm);
					stsele.setSoundEffect(_effect);
					stsele.init();
					gm.ChangeMode(stsele);
					// タイトル画面を出るとき、BGMを止める
					break;
				case END:
					gm.ChangeMode(new GameoverState());
					// タイトル画面を出るとき、BGMを止める
					_bgm.Finish(_bgm.getClip());
					break;
				//チュートリアルの項目追加
				case TUTORIAL:
					gm.ChangeMode(new TutorialState());
					//一応音楽も止めてみた
					_bgm.Finish(_bgm.getClip());
					break;
			}
		}
	}

	@Override
	public void Show(Graphics2D g2) {
		Image img = Toolkit.getDefaultToolkit().getImage("Title.jpg");
		g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,0,0), null);
		g2.setFont(new Font("Arial", Font.BOLD, 28));

		//タイトル
		g2.setPaint(Color.white);
		g2.drawString("INVADER SHOOTER",TITLEPOSX,TITLEPOSY);

		//開始
		if(_cursorPos == START)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);
		
		g2.drawString("Game Start",MENUPOSX,MENUPOSY);

		//終了
		if(_cursorPos == END)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);

		g2.drawString("Quit",MENUPOSX,MENUPOSY + MENUINTVL);

		//チュートリアルの項目追加
		if(_cursorPos == TUTORIAL)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);

		g2.drawString("Tutorial",MENUPOSX,MENUPOSY + 2*MENUINTVL);

		//カーソル
		g2.setPaint(Color.green);
		switch(_cursorPos)
		{
			case START:
			g2.drawString("@",CURSOR,MENUPOSY);
				break;
			case END:
			g2.drawString("@",CURSOR,MENUPOSY + MENUINTVL);
				break;
			// (柳谷　1/13) チュートリアルの項目追加
			case TUTORIAL:
			g2.drawString("@",CURSOR,MENUPOSY + 2*MENUINTVL);
				break;
		}

		// 操作表示
		g2.setPaint(Color.yellow);
		g2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		g2.drawString("↑↓キーでカーソル移動。Zキーで決定。", 170, 600);
	}
	
	@Override
	public void KeyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode())
		{
		case KeyEvent.VK_Z:
			KeyZ(true);
			break;
		case KeyEvent.VK_UP:
			KeyUp(true);
			break;
		case KeyEvent.VK_DOWN:
			KeyDown(true);
			break;
		}
	}

	@Override
	public void KeyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode())
		{
		case KeyEvent.VK_Z:
			KeyZ(false);
			break;
		case KeyEvent.VK_UP:
			KeyUp(false);
			break;
		case KeyEvent.VK_DOWN:
			KeyDown(false);
			break;
		}
	}
		
	@Override
	public void KeyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

}
