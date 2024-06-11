import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.geom.*;


public class StageSelectState implements ModeState{
	private BGM _bgm; // BGM
	public void setBGM(BGM b) { _bgm = b; }
	private SoundEffect _effect; // 効果音
	public void setSoundEffect(SoundEffect effect) {_effect = effect;}

    private MainGameState _mgstate;
    public MainGameState GetMGState(){return _mgstate;}

	private final static int START	= 0;
	private final static int END	= 3;
	private String[] stage_data = {"Stage 1", "Stage 2", "Stage 3", "Stage 4"};//ステージ名保存
	private int _cursorPos = START;

	private final static int TITLEPOSX	= 230;
	private final static int TITLEPOSY	= 150;

	private final static int MENUPOSX		= 250;
	private final static int MENUPOSY		= 280;
	private final static int MENUINTVL	= 50;
	private final static int CURSOR		= 200;

	private boolean m_bKeyUp;
	public void KeyUp(boolean on){m_bKeyUp = on;}
	private boolean m_bKeyDown;
	public void KeyDown(boolean on){m_bKeyDown = on;}
	private boolean m_bKeyZ;
	public void KeyZ(boolean on){m_bKeyZ = on;}
	private boolean m_bKeyX;
	public void KeyX(boolean on){m_bKeyX = on;}

	public StageSelectState(){}

	@Override
	public void init() {
		_mgstate = new MainGameState();
		_bgm.PlayLoopStageSelect();
		// TODO Auto-generated method stub
	}
	
	
	public void run(GameManager gm)
	{
		if(m_bKeyUp)
		{
			if(_cursorPos != START) {
				_cursorPos--;
				_effect.PlayTitleCursorMove();
			}
				//連続入力防止
				KeyUp(false);
		}
		else if(m_bKeyDown)
		{
			if(_cursorPos != END) {
				_cursorPos++;
				_effect.PlayTitleCursorMove();
			}
				//連続入力防止
				KeyDown(false);
		}

		
		if(m_bKeyZ)
		{
			_effect.PlayTitleCursorDetermine();
            _mgstate.SetStage(_cursorPos + 1);
			_mgstate.setBGM(_bgm);
			_mgstate.setSoundEffect(_effect);
			_mgstate.init();
			// タイトル画面を出るとき、BGMを止める
			gm.ChangeMode(_mgstate);
		}

		if(m_bKeyX)
		{
			_bgm.Finish(_bgm.getClip());
			gm.ChangeMode(new TitleState());
			
		}
	}

	@Override
	public void Show(Graphics2D g2) {
		Image img = Toolkit.getDefaultToolkit().getImage("Title.jpg");
		g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,0,0), null);
		g2.setFont(new Font("Arial", Font.BOLD, 28));

		
		g2.setPaint(Color.white);
		g2.drawString("Stage Select",TITLEPOSX,TITLEPOSY);
		g2.setPaint(Color.yellow);

		for(int i=START; i<=END; i++){
			g2.drawString(stage_data[i],MENUPOSX,MENUPOSY + MENUINTVL * i);//ステージ名を表示
		}

		g2.setPaint(Color.green);
		g2.drawString(stage_data[_cursorPos], MENUPOSX, MENUPOSY + MENUINTVL * _cursorPos);

		g2.setPaint(Color.green);
		g2.drawString("@",CURSOR,MENUPOSY + MENUINTVL*_cursorPos);
		g2.setPaint(Color.yellow);
		g2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		g2.drawString("↑↓キーでカーソル移動。Zキーで決定。Xキーで戻る。", 70,600);
	}
	
	@Override
	public void KeyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode())
		{
		case KeyEvent.VK_X:
			KeyX(true);
			break;
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
		case KeyEvent.VK_X:
			KeyX(false);
			break;
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