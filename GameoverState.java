import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.geom.*;

// ゲームオーバー時のモード
public class GameoverState implements ModeState{

	private BGM _bgm;
	public void setBGM(BGM b) { _bgm = b;}

	private boolean m_bKeyZ;
	public void KeyZ(boolean on){m_bKeyZ = on;}

	public GameoverState()
	{

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		_bgm.PlayGameOver();
	}
	
	@Override
	public void Show(Graphics2D g2) {
		// TODO Auto-generated method stub
		Image img = Toolkit.getDefaultToolkit().getImage("Gameover.png");//画像で表示
		g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,0,0), null);
	}

	@Override
	public void run(GameManager gm) {
		// TODO Auto-generated method stub
		if(m_bKeyZ)
		{
			gm.ChangeMode(new TitleState());
		}
		
	}

	@Override
	public void KeyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode())
		{
		case KeyEvent.VK_Z:
			KeyZ(true);
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
		}
		
	}

	@Override
	public void KeyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
