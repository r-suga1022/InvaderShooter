import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.geom.*;

public class TitleState implements ModeState{

	private final static int START	= 0;
	private final static int END		= 1;
	//�`���[�g���A���ǉ�
	private final static int TUTORIAL = 2;
	private int _cursorPos = START;

	// ���C���^�C�g���̈ʒu�𒲐�
	private final static int TITLEPOSX	= 200;
	private final static int TITLEPOSY	= 150;

	// ���C�����j���[�\���ʒu�B�\���Ԋu�B�J�[�\���ʒu�ix���W�̂݁j
	private final static int MENUPOSX		= 250;
	private final static int MENUPOSY		= 280;
	private final static int MENUINTVL	= 50;
	private final static int CURSOR		= 200;

	// �L�[�t���O
	private boolean m_bKeyUp;
	public void KeyUp(boolean on){m_bKeyUp = on;}
	private boolean m_bKeyDown;
	public void KeyDown(boolean on){m_bKeyDown = on;}
	private boolean m_bKeyZ;
	public void KeyZ(boolean on){m_bKeyZ = on;}

	// BGM�A���ʉ��p�I�u�W�F�N�g
	private BGM _bgm = new BGM();
	public BGM getbgm() { return _bgm; }

	// ���ʉ��̃I�u�W�F�N�g
	private SoundEffect _effect = new SoundEffect();

	public TitleState()
	{
		init();
		// BGM���Đ�
		_bgm.PlayLoopTitleBGM();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
	
	// �L�[�ړ�
	public void run(GameManager gm)
	{
		if(m_bKeyUp)
		{
			if(_cursorPos != START) {
				_cursorPos--;
				//�A�����͖h�~
				KeyUp(false);
				// �J�[�\���ړ����̌��ʉ���炷
				_effect.PlayTitleCursorMove();
			}
		}
		else if(m_bKeyDown)
		{
			//�J�[�\���̉������`���[�g���A����
			if(_cursorPos != TUTORIAL) {
				_cursorPos++;
				//�A�����͖h�~
				KeyDown(false);
				// �J�[�\���ړ����̌��ʉ���炷
				_effect.PlayTitleCursorMove();
			}
		}

		//Z�������Ƃ�
		if(m_bKeyZ)
		{
			// �J�[�\���ʒu�ŕ���
			switch(_cursorPos)
			{
				case START:
					// ���艹�𗬂�
					_effect.PlayTitleCursorDetermine();
					// �X�e�[�W�Z���N�g��ʂɈړ�
					StageSelectState stsele = new StageSelectState();
					stsele.setBGM(_bgm);
					stsele.setSoundEffect(_effect);
					stsele.init();
					gm.ChangeMode(stsele);
					// �^�C�g����ʂ��o��Ƃ��ABGM���~�߂�
					break;
				case END:
					gm.ChangeMode(new GameoverState());
					// �^�C�g����ʂ��o��Ƃ��ABGM���~�߂�
					_bgm.Finish(_bgm.getClip());
					break;
				//�`���[�g���A���̍��ڒǉ�
				case TUTORIAL:
					gm.ChangeMode(new TutorialState());
					//�ꉞ���y���~�߂Ă݂�
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

		//�^�C�g��
		g2.setPaint(Color.white);
		g2.drawString("INVADER SHOOTER",TITLEPOSX,TITLEPOSY);

		//�J�n
		if(_cursorPos == START)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);
		
		g2.drawString("Game Start",MENUPOSX,MENUPOSY);

		//�I��
		if(_cursorPos == END)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);

		g2.drawString("Quit",MENUPOSX,MENUPOSY + MENUINTVL);

		//�`���[�g���A���̍��ڒǉ�
		if(_cursorPos == TUTORIAL)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);

		g2.drawString("Tutorial",MENUPOSX,MENUPOSY + 2*MENUINTVL);

		//�J�[�\��
		g2.setPaint(Color.green);
		switch(_cursorPos)
		{
			case START:
			g2.drawString("@",CURSOR,MENUPOSY);
				break;
			case END:
			g2.drawString("@",CURSOR,MENUPOSY + MENUINTVL);
				break;
			// (���J�@1/13) �`���[�g���A���̍��ڒǉ�
			case TUTORIAL:
			g2.drawString("@",CURSOR,MENUPOSY + 2*MENUINTVL);
				break;
		}

		// ����\��
		g2.setPaint(Color.yellow);
		g2.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 20));
		g2.drawString("�����L�[�ŃJ�[�\���ړ��BZ�L�[�Ō���B", 170, 600);
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
