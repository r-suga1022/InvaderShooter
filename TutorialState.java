import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.geom.*;

public class TutorialState implements ModeState {

	private boolean m_bKeyZ;
	public void KeyZ(boolean on){m_bKeyZ = on;}

	public TutorialState()
	{
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
	
	public void run(GameManager gm)
	{
        if(m_bKeyZ)
		{
			gm.ChangeMode(new TitleState());//Zが押されたらタイトルに戻る。
		}
		
	}

	@Override
	public void Show(Graphics2D g2) {
		Image img = Toolkit.getDefaultToolkit().getImage("Tutorial2.png");
		g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,0,0), null);		
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
