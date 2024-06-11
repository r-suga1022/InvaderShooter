import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.geom.*;
@SuppressWarnings("removal")
// クリア時のモード
public class ExitState implements ModeState{

	private int score;

	private boolean m_bKeyZ;
	public void KeyZ(boolean on){m_bKeyZ = on;}

	public void SetScore(int n){//スコアのセッター
		score = n;
	}

	public ExitState()
	{
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void Show(Graphics2D g2) {
		// TODO Auto-generated method stub
		Image img = Toolkit.getDefaultToolkit().getImage("Clear.png");
		g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,0,0), null);
		g2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		g2.setPaint(Color.white);
		g2.drawString("スコア:" + new Integer(score).toString(), 270, 350);//スコア表示
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
